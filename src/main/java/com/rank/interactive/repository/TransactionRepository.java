package com.rank.interactive.repository;

import com.rank.interactive.model.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {


}
