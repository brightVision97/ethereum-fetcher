package com.rachev.ethereumfetcher.service;

import com.rachev.ethereumfetcher.entity.Transaction;
import com.rachev.ethereumfetcher.model.transaction.TransactionsDto;
import com.rachev.ethereumfetcher.model.transaction.UnifiedTransactionDto;
import com.rachev.ethereumfetcher.repository.TransactionRepository;
import com.rachev.ethereumfetcher.service.base.EthereumNodeRequestSender;
import com.rachev.ethereumfetcher.service.base.TransactionService;
import com.rachev.ethereumfetcher.service.base.UserService;
import com.rachev.ethereumfetcher.util.RlpDecoderUtil;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public TransactionsDto getTransactionsByHashes(final String rlpHex, @Nullable String networkSwitch, String currentPrincipalUsername) {
        var currentRequester = userService.getUserByUsername(currentPrincipalUsername);
        List<UnifiedTransactionDto> transactions = rlpDecoderUtil.decodeRlpToList(rlpHex).stream()
                .map(decodedHash -> transactionRepository.findByTransactionHash(decodedHash)
                        .orElseGet(() -> {
                            var transactionDto = ethereumNodeRequestSender.getTransactionByHash(decodedHash, networkSwitch);
                            return transactionDto == null ? null : saveTransaction(transactionDto);
                        })
                )
                .filter(Objects::nonNull)
                .peek(currentRequester::linkTransaction)
                .map(transaction -> modelMapper.map(transaction, UnifiedTransactionDto.class))
                .onClose(() -> userService.updateUser(currentRequester))
                .toList();
        return TransactionsDto.builder()
                .transactions(transactions)
                .build();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private Transaction saveTransaction(UnifiedTransactionDto transactionDto) {
        return transactionRepository.save(modelMapper.map(transactionDto, Transaction.class));
    }

    @Override
    public TransactionsDto getAllTransactions() {
        List<UnifiedTransactionDto> transactions = transactionRepository.findAll().stream()
                .map(transaction -> modelMapper.map(transaction, UnifiedTransactionDto.class))
                .toList();
        return TransactionsDto.builder()
                .transactions(transactions)
                .build();
    }

    @Override
    public TransactionsDto getMyTransactions(String username) {
        List<UnifiedTransactionDto> transactions = userService.getUserByUsername(username)
                .getTransactions()
                .stream()
                .map(transaction -> modelMapper.map(transaction, UnifiedTransactionDto.class))
                .toList();
        return TransactionsDto.builder()
                .transactions(transactions)
                .build();
    }
}
