package com.followproject.business.account.util;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.form.AccountForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountUtil {
    private final PasswordEncoder passwordEncoder;

    public Account createJoinAccount(AccountForm.Request.Join join){
        return Account.builder()
                      .email(join.getEmail())
                      .password(passwordEncoder.encode(join.getPassword()))
                      .roles(Collections.singletonList("ROLE_USER"))
                      .userKey(UUID.randomUUID().toString())
                      .build();
    }
}
