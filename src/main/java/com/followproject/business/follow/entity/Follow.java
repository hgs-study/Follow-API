package com.followproject.business.follow.entity;

import com.followproject.business.account.entity.Account;
import com.followproject.business.profile.entity.Profile;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "profile_id")
//    private Profile profile;


    public Follow(Account fromAccount, Account toAccount) {
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
    }
}
