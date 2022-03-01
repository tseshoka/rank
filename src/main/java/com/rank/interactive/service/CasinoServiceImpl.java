package com.rank.interactive.service;

import com.rank.interactive.dao.BalanceResponse;
import com.rank.interactive.dao.CasinoRequest;
import com.rank.interactive.dao.Transaction;
import com.rank.interactive.enums.TransactionType;
import com.rank.interactive.exception.UserNotFoundException;
import com.rank.interactive.model.Account;
import com.rank.interactive.repository.AccountRepository;
import com.rank.interactive.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CasinoServiceImpl implements CasinoService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public BalanceResponse getBalance(long playerId) throws UserNotFoundException {

        Optional<Account> account = accountRepository.findById(playerId);

        BalanceResponse balanceResponse = new BalanceResponse();

        if (account.isPresent()) {

            balanceResponse.setBalance(account.get().getBalance());
            balanceResponse.setPlayerId(playerId);
        } else {

            throw new UserNotFoundException("User not found");
        }

        System.out.println("The balance  is ZAR " + balanceResponse.getBalance());
        return balanceResponse;
    }

    @Override
    public synchronized BalanceResponse withdraw(CasinoRequest casinoRequest) throws UserNotFoundException {

        Optional<Account> account = accountRepository.findById(casinoRequest.getPlayerId());

        BalanceResponse balanceResponse = new BalanceResponse();

        if (transactionRepository.findById(casinoRequest.getTransactionId()).isPresent()) {
            return balanceResponse;
        }

        if (account.isPresent()) {

            double newBalance = account.get().getBalance() - casinoRequest.getAmount();
            account.get().setBalance(newBalance);

            accountRepository.save(account.get());
            updateTransaction(casinoRequest);

            balanceResponse.setBalance(newBalance);
            balanceResponse.setPlayerId(casinoRequest.getPlayerId());
        } else {
            throw new UserNotFoundException("User not found");
        }

        return balanceResponse;
    }

    @Override
    public synchronized BalanceResponse deposit(CasinoRequest casinoRequest) throws UserNotFoundException {

        Optional<Account> account = accountRepository.findById(casinoRequest.getPlayerId());

        BalanceResponse balanceResponse = new BalanceResponse();

        if (transactionRepository.findById(casinoRequest.getTransactionId()).isPresent()) {
            return balanceResponse;
        }

        if (account.isPresent()) {

            double newBalance = account.get().getBalance() + casinoRequest.getAmount();
            account.get().setBalance(newBalance);

            accountRepository.save(account.get());
            updateTransaction(casinoRequest);

            balanceResponse.setBalance(newBalance);
            balanceResponse.setPlayerId(casinoRequest.getPlayerId());
        } else {

            throw new UserNotFoundException("User not found");
        }
        return balanceResponse;
    }

    @Override
    public List<Transaction> getLastTenWagers(CasinoRequest casinoRequest) throws UserNotFoundException {

        List<Long> transactionIds = new ArrayList<>();
        transactionIds.add(casinoRequest.getTransactionId());

        Iterable<com.rank.interactive.model.Transaction> transactions = transactionRepository.findAll();

        List<Transaction> transactionList = new ArrayList<>();
        int count = 0;

        for (com.rank.interactive.model.Transaction trans : transactions) {

            if (trans.getType().equalsIgnoreCase(TransactionType.deduct.name()) && count < 10) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(trans.getTransactionId());
                transaction.setType(trans.getType());
                transaction.setAmount(trans.getAmount());

                transactionList.add(transaction);

                count++;
            }
        }

        return transactionList;
    }

    @Override
    public List<Transaction> getLastTenDeposits(CasinoRequest casinoRequest) throws UserNotFoundException {

        List<Long> transactionIds = new ArrayList<>();
        transactionIds.add(casinoRequest.getTransactionId());

        Iterable<com.rank.interactive.model.Transaction> transactions = transactionRepository.findAll();

        List<Transaction> transactionList = new ArrayList<>();
        int count = 0;

        for (com.rank.interactive.model.Transaction trans : transactions) {

            if (trans.getType().equalsIgnoreCase(TransactionType.deposit.name()) && count < 10) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(trans.getTransactionId());
                transaction.setType(trans.getType());
                transaction.setAmount(trans.getAmount());

                transactionList.add(transaction);

                count++;
            }
        }

        return transactionList;
    }

    private synchronized void updateTransaction (CasinoRequest request) {

        com.rank.interactive.model.Transaction transaction = new com.rank.interactive.model.Transaction();
        transaction.setTransactionId(request.getTransactionId());
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());

        transactionRepository.save(transaction);
    }
}
