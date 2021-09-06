package com.followproject.domain.follow.repository;

import com.followproject.business.account.entity.Account;
import com.followproject.business.follow.entity.Follow;
import com.followproject.business.follow.repository.FollowRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FollowRepositoryTest {

    @Mock
    FollowRepository followRepository;

    @DisplayName("팔로우 등록")
    @Test
    void save(){
        final Account firstAccount = new Account("hgstudy_@naver.com1","password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");
        final Follow follow = new Follow(firstAccount,secondAccount);

        given(followRepository.save(follow)).willReturn(follow);

        final Follow saveFollow = followRepository.save(follow);

        assertEquals(saveFollow.getFromAccount(), follow.getFromAccount());
        verify(followRepository).save(follow);
    }

    @DisplayName("팔로우 삭제")
    @Test
    void delete(){
        final Account firstAccount = new Account("hgstudy_@naver.com1","password123!!");
        final Account secondAccount = new Account("hgstudy_@naver.com2","password123!!");
        final Follow follow = new Follow(firstAccount,secondAccount);

        doNothing().when(followRepository).delete(follow);

        followRepository.delete(follow);

        verify(followRepository).delete(follow);
    }



}
