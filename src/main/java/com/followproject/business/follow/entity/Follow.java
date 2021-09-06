package com.followproject.business.follow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.followproject.business.account.entity.Account;
import com.followproject.common.domain.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Follow extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    public Follow(Account fromAccount, Account toAccount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }
}
