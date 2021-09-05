package com.followproject.business.follow.repository;

import com.followproject.business.account.entity.Account;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.followproject.business.follow.entity.QFollow.follow;

@Repository
@RequiredArgsConstructor
public class FollowQueryRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Long findCountByFromAccount(Account account){
        return jpaQueryFactory
                        .selectFrom(follow)
                        .where(follow.fromAccount.eq(account))
                        .fetchCount();
    }

    public void deleteByFromAccountAndToAccount(Account fromAccount, Account toAccount){
        jpaQueryFactory
                .delete(follow)
                .where(follow.fromAccount.eq(fromAccount)
                .and(follow.toAccount.eq(toAccount)))
                .execute();
    }
}
