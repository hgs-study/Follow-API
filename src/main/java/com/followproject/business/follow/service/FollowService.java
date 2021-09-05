package com.followproject.business.follow.service;

import com.followproject.business.account.entity.Account;
import com.followproject.business.follow.entity.Follow;
import com.followproject.business.follow.repository.FollowQueryRepository;
import com.followproject.business.follow.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {
    private final FollowRepository followRepository;
    private final FollowQueryRepository followQueryRepository;

    @Transactional
    public void save(Follow follow){
        followRepository.save(follow);
    }

    public List<Follow> findAllByFromAccount(Account account){
        return followRepository.findAllByFromAccount(account);
    }

    public Long findCount(Account account){
        return followQueryRepository.findCountByFromAccount(account);
    }

}
