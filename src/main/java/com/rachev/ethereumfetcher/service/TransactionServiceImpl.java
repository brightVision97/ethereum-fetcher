package com.rachev.ethereumfetcher.service;

import com.rachev.ethereumfetcher.entity.Transaction;
import com.rachev.ethereumfetcher.model.TransactionDto;
import com.rachev.ethereumfetcher.model.TransactionsDto;
import com.rachev.ethereumfetcher.repository.TransactionRepository;
import com.rachev.ethereumfetcher.service.base.EthereumNodeRequestSender;
import com.rachev.ethereumfetcher.service.base.TransactionService;
import com.rachev.ethereumfetcher.util.RlpDecoderUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    private final EthereumNodeRequestSender ethereumNodeRequestSender;

    private final RlpDecoderUtil rlpDecoderUtil;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public TransactionsDto getTransactionsByHashes(final String rlpHex) {
        List<TransactionDto> transactions = rlpDecoderUtil.decodeRlpToList(rlpHex).stream()
                .map(hash -> transactionRepository.findByHash(hash)
                        .orElseGet(() -> {
                            TransactionDto transactionDto = ethereumNodeRequestSender.getTransactionByHashFromEthereumNode(hash);
                            if (transactionDto != null) {
                                return saveTransaction(transactionDto);
                            }
                            return null;
                        }))
                .filter(Objects::nonNull)
                .map(transaction -> modelMapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toList());
        return TransactionsDto.builder()
                .transactions(transactions)
                .build();
    }

    private Transaction saveTransaction(TransactionDto transactionDto) {
        return transactionRepository.save(modelMapper.map(transactionDto, Transaction.class));
    }

    @Override
    public TransactionsDto getAllTransactions() {
        List<TransactionDto> transactions = transactionRepository.findAll().stream()
                .map(transaction -> modelMapper.map(transaction, TransactionDto.class))
                .collect(Collectors.toList());
        return TransactionsDto.builder()
                .transactions(transactions)
                .build();
    }
}
