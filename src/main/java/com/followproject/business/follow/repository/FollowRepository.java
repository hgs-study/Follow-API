package com.followproject.business.follow.repository;

import com.followproject.business.account.entity.Account;
import com.followproject.business.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByFromAccount(Account account);

    Optional<Follow> findByFromAccountAndToAccount(Account fromAccount, Account toAccount);
}
