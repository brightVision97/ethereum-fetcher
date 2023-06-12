package com.rachev.ethereumfetcher.service;

import com.rachev.ethereumfetcher.entity.Transaction;
import com.rachev.ethereumfetcher.entity.User;
import com.rachev.ethereumfetcher.model.transaction.TransactionsDto;
import com.rachev.ethereumfetcher.model.transaction.UnifiedTransactionDto;
import com.rachev.ethereumfetcher.repository.TransactionRepository;
import com.rachev.ethereumfetcher.service.base.EthereumNodeRequestSender;
import com.rachev.ethereumfetcher.service.base.TransactionService;
import com.rachev.ethereumfetcher.service.base.UserService;
import com.rachev.ethereumfetcher.util.RlpDecoderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final EthereumNodeRequestSender ethereumNodeRequestSender;

    private final RlpDecoderUtil rlpDecoderUtil;

    private final ModelMapper modelMapper;

    private final UserService userService;

    @Override
    public List<UnifiedTransactionDto> getTransactionsByHashes(final String rlpHex, String currentPrincipalUsername) {
        var currentRequester = userService.getUserByUsername(currentPrincipalUsername);
        return rlpDecoderUtil.decodeRlpToList(rlpHex).stream()
                .map(decodedHash -> transactionRepository.findByTransactionHash(decodedHash).orElseGet(() -> {
                    var ethTransaction = ethereumNodeRequestSender.getTransactionByHash(decodedHash);
                    return saveTransaction(currentRequester, ethTransaction);
                })).map(transaction -> modelMapper.map(transaction, UnifiedTransactionDto.class))
                .onClose(() -> userService.updateUser(currentRequester))
                .toList();
    }

    @Transactional
    private Transaction saveTransaction(User user, UnifiedTransactionDto transactionDto) {
        return user.linkTransaction(transactionRepository.save(modelMapper.map(transactionDto, Transaction.class)));
    }

    @Override
    public TransactionsDto getAllTransactions() {
        List<UnifiedTransactionDto> transactions = transactionRepository.findAll(Sort.by(Sort.Direction.ASC, "created"))
                .stream()
                .map(transaction -> modelMapper.map(transaction, UnifiedTransactionDto.class))
                .toList();
        return TransactionsDto.builder()
                .transactions(transactions)
                .build();
    }

    @Override
    public TransactionsDto getMyTransactions(String username) {
        var user = userService.getUserByUsername(username);
        List<UnifiedTransactionDto> transactions = transactionRepository.findAllByUsersIn(List.of(user), Sort.by(Sort.Direction.ASC, "created"))
                .stream()
                .map(transaction -> modelMapper.map(transaction, UnifiedTransactionDto.class))
                .toList();
        return TransactionsDto.builder()
                .transactions(transactions)
                .build();
    }
}
