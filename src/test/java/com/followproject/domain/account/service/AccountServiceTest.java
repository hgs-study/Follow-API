package com.followproject.domain.account.service;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.respository.AccountRepository;
import com.followproject.business.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @InjectMocks
    AccountService accountService;

    @Mock
    AccountRepository accountRepository;

    @DisplayName("회원가입")
    @Test
    void join(){
        final Account account = new Account("hgstudy@naver.com","password123!!");

        given(accountRepository.save(any(Account.class))).willReturn(account);

        accountService.join(account);

        verify(accountRepository).save(account);
    }


    @DisplayName("유저 상세 조회")
    @Test
    void findByEmail(){
        final Account account = new Account("hgstudy_@naver.com","password");

        given(accountRepository.findByEmail(account.getEmail())).willReturn(Optional.of(account));

        final Account findAccount = accountService.findByEmail(account.getEmail());

        assertEquals(account.getEmail(), findAccount.getEmail());
        verify(accountRepository).findByEmail(account.getEmail());
    }

    @DisplayName("유저 목록 조회")
    @Test
    void findAll(){
        final Account firstAccount = new Account("hgstudy_@naver.com","password");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password");
        final List<Account> accounts = Arrays.asList(firstAccount, secondAccount);

        given(accountRepository.findAll()).willReturn(accounts);

        final List<Account> findAccounts = accountService.findAll();

        assertEquals(findAccounts.get(0).getEmail(), firstAccount.getEmail());
        assertEquals(findAccounts.get(1).getEmail(), secondAccount.getEmail());
        verify(accountRepository).findAll();
    }
}
