package com.followproject.domain.account.repository;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.respository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryTest {

    @Mock
    AccountRepository accountRepository;

    @DisplayName("유저 저장")
    @Test
    void save(){
        final Account account = new Account("hgstudy@naver.com","password123!!");

        given(accountRepository.save(account)).willReturn(account);

        final Account saveAccount = accountRepository.save(account);

        assertEquals(account.getEmail(), saveAccount.getEmail());
        verify(accountRepository).save(account);
    }

    @DisplayName("유저 상세 조회")
    @Test
    void findByEmail(){
        final Account account = new Account("hgstudy@naver.com","password123!!");

        given(accountRepository.findByEmail(account.getEmail())).willReturn(Optional.of(account));

        final Account findAccount = accountRepository.findByEmail(account.getEmail()).get();

        assertEquals(account.getEmail(), findAccount.getEmail());
        verify(accountRepository).findByEmail(account.getEmail());
    }
}
