package com.rank.interactive.dao;

import lombok.Data;
import lombok.NonNull;

@Data
public class AdminUser {

    public AdminUser(){

    }

    private String username;

    @NonNull
    private String password;
}
