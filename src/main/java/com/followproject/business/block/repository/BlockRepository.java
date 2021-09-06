package com.followproject.business.block.repository;


import com.followproject.business.account.entity.Account;
import com.followproject.business.block.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlockRepository extends JpaRepository<Block,Long> {

    Block findByFromAccountAndToAccount(Account fromAccount, Account toAccount);
}

