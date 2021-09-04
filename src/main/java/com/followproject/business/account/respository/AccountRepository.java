package com.followproject.business.account.respository;


import com.followproject.business.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByUserKey(String userKey);
}
