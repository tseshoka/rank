package com.rank.interactive.dao;

import lombok.Data;

@Data
public class Transaction {

    private long transactionId;

    private String type;

    private double amount;
}