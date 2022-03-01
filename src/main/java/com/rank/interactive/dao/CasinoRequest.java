package com.rank.interactive.dao;

import lombok.Data;

@Data
public class CasinoRequest {

    private long playerId;
    private long transactionId;
    private String type;
    private double amount;
}
