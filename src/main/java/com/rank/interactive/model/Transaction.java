package com.rank.interactive.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "TRANSACTION")
public class Transaction implements Serializable{

    @Id
    @Column(name = "TRANSACTION_ID")
    private long transactionId;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "AMOUNT")
    private double amount;
}
