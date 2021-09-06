package com.followproject.domain.follow.service;

import com.followproject.business.account.entity.Account;
import com.followproject.business.follow.entity.Follow;
import com.followproject.business.follow.repository.FollowQueryRepository;
import com.followproject.business.follow.repository.FollowRepository;
import com.followproject.business.follow.service.FollowService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FollowServiceTest {

    @InjectMocks
    FollowService followService;

    @Mock
    FollowRepository followRepository;

    @Mock
    FollowQueryRepository followQueryRepository;

    @DisplayName("팔로우")
    @Test
    void follow(){
        final Account firstAccount = new Account("hgstudy_@naver.com1","password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");
        final Follow follow = new Follow(firstAccount,secondAccount);

        given(followRepository.save(follow)).willReturn(follow);

        followService.follow(follow);

        verify(followRepository).save(follow);
    }


    @DisplayName("언팔로우")
    @Test
    void unfollow(){
        final Account firstAccount = new Account("hgstudy_@naver.com1","password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");

        doNothing().when(followQueryRepository).deleteByFromAccountAndToAccount(firstAccount,secondAccount);

        followService.unfollow(firstAccount, secondAccount);

        verify(followQueryRepository).deleteByFromAccountAndToAccount(firstAccount, secondAccount);
    }

    @DisplayName("팔로우 검색")
    @Test
    void findByFromAccountAndToAccount(){
        final Account firstAccount = new Account("hgstudy_@naver.com1","password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");
        final Follow follow = new Follow(firstAccount,secondAccount);

        given(followRepository.findByFromAccountAndToAccount(firstAccount, secondAccount)).willReturn(Optional.of(follow));

        followService.findByFromAccountAndToAccount(firstAccount, secondAccount);

        verify(followRepository).findByFromAccountAndToAccount(firstAccount, secondAccount);
    }


}
