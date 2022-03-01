package com.rank.interactive.controller;

import com.rank.interactive.dao.AdminUser;
import com.rank.interactive.dao.BalanceResponse;
import com.rank.interactive.dao.CasinoRequest;
import com.rank.interactive.dao.Transaction;
import com.rank.interactive.enums.TransactionType;
import com.rank.interactive.exception.UserNotFoundException;
import com.rank.interactive.service.CasinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CasinoController {

    @Value("user.password")
    private String pass;// = "swordfish";
    @Autowired
    private CasinoService casinoService;


    @GetMapping(value = "/balance")
    public ResponseEntity<BalanceResponse> getBalance (@RequestParam int transactionId, @RequestParam long playerId){
        System.out.println("re tsene " + playerId);

        BalanceResponse response;

        try {
            response =  casinoService.getBalance(playerId);
        } catch (UserNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/wager")
    public ResponseEntity<BalanceResponse> wager (@RequestParam long transactionId, @RequestParam long playerId, @RequestParam double amount){
        System.out.println("re tsene");

        CasinoRequest request = new CasinoRequest();
        request.setAmount(amount);
        request.setPlayerId(playerId);
        request.setTransactionId(transactionId);
        request.setType(TransactionType.deduct.name());

        BalanceResponse response;

        try {
            response = casinoService.withdraw(request);
        } catch (UserNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/deposit")
    public ResponseEntity<BalanceResponse> depositWinning (@RequestParam long transactionId, @RequestParam long playerId, @RequestParam double amount){

        CasinoRequest request = new CasinoRequest();
        request.setAmount(amount);
        request.setPlayerId(playerId);
        request.setTransactionId(transactionId);
        request.setType(TransactionType.deposit.name());

        BalanceResponse response;

        try {
            response = casinoService.deposit(request);
        } catch (UserNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/deposit",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Transaction>> getLastTenDeposit (@RequestParam int transactionId, @RequestParam long playerId, @RequestBody AdminUser user){

        if (!user.getPassword().equalsIgnoreCase("swordfish")){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorised");
        }

        CasinoRequest request = new CasinoRequest();
        request.setPlayerId(playerId);
        request.setTransactionId(transactionId);
        request.setType(TransactionType.deposit.name());

        List<Transaction> response;

        try {
            response = casinoService.getLastTenDeposits(request);
        } catch (UserNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        }

        return new ResponseEntity<List<Transaction>>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/wager",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Transaction>> getLastTenWager (@RequestParam int transactionId, @RequestParam long playerId, @RequestBody AdminUser user){

        if (!user.getPassword().equalsIgnoreCase(pass)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorised");
        }

        CasinoRequest request = new CasinoRequest();
        request.setPlayerId(playerId);
        request.setTransactionId(transactionId);
        request.setType(TransactionType.deposit.name());

        List<Transaction> response;

        try {
            response = casinoService.getLastTenWagers(request);
        } catch (UserNotFoundException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        }

        return new ResponseEntity<List<Transaction>>(response, HttpStatus.OK);
    }

}
