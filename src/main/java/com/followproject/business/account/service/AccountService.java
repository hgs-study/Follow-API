package com.followproject.business.account.service;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.respository.AccountRepository;
import com.followproject.common.error.code.ErrorCode;
import com.followproject.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AccountService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        return accountRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException(ErrorCode.NOT_FOUND_USER.getMessage()));
    }

    @Transactional
    public void join(Account account){
        accountRepository.findByEmail(account.getEmail())
                         .ifPresent(e -> {
                             throw new BusinessException(ErrorCode.EXIST_EMAIL);
                         });

        accountRepository.save(account);
    }

    @Cacheable(key ="email", value = "findAccount")
    public Account findByEmail(String email){
        return accountRepository.findByEmail(email)
                                .orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_USER));
    }

    @Cacheable( value = "findAccounts")
    public List<Account> findAll(){
        return accountRepository.findAll();
    }

    @Cacheable(key ="useKey", value = "findAccount")
    public Account findByUserKey(String userKey) throws UsernameNotFoundException{
        return accountRepository.findByUserKey(userKey)
                                .orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_USER));
    }

    @Cacheable(key ="useKey", value = "findUserDetails")
    public UserDetails findUserDetailsByUserKey(String userKey) throws UsernameNotFoundException{
        return accountRepository.findByUserKey(userKey)
                                .orElseThrow(()-> new BusinessException(ErrorCode.NOT_FOUND_USER));
    }


}
