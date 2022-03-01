package com.rank.interactive.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "ACCOUNT")
public class Account implements Serializable {

    @Id
    @Column(name = "PLAYER_ID")
    private long playerId;

    @Column(name = "BALANCE")
    private double balance;
}
