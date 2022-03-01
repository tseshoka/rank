package com.rank.interactive.service;

import com.rank.interactive.dao.BalanceResponse;
import com.rank.interactive.dao.CasinoRequest;
import com.rank.interactive.dao.Transaction;
import com.rank.interactive.exception.UserNotFoundException;

import java.util.List;

public interface CasinoService {

    public BalanceResponse getBalance (long playerId) throws UserNotFoundException;

    public BalanceResponse withdraw (CasinoRequest casinoRequest) throws UserNotFoundException;

    public BalanceResponse deposit (CasinoRequest casinoRequest) throws UserNotFoundException;

    public List<Transaction> getLastTenWagers (CasinoRequest casinoRequest) throws UserNotFoundException;

    public List<Transaction> getLastTenDeposits (CasinoRequest casinoRequest) throws UserNotFoundException;
}
