package com.followproject.business.account.service;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.respository.AccountRepository;
import com.followproject.common.error.code.ErrorCode;
import com.followproject.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {
    private final AccountRepository accountRepository;

    @Transactional
    public void join(Account account){
        accountRepository.findByEmail(account.getEmail())
                         .ifPresent(e -> {
                             throw new BusinessException(ErrorCode.EXIST_EMAIL);
                         });

        accountRepository.save(account);
    }

    public Account findByEmail(String email){
        return accountRepository.findByEmail(email)
                                .orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_USER));
    }

    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    public Account findByUserKey(String userKey) throws UsernameNotFoundException{
        return accountRepository.findByUserKey(userKey)
                                .orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_USER));
    }

    public UserDetails findUserDetailsByUserKey(String userKey) throws UsernameNotFoundException{
        return accountRepository.findByUserKey(userKey)
                                .orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_USER));
    }

}
