package com.followproject.business.follow.api;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.service.AccountService;
import com.followproject.business.follow.entity.Follow;
import com.followproject.business.follow.form.FollowForm.*;
import com.followproject.business.follow.service.FollowService;
import com.followproject.common.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final AccountService accountService;
    private final AuthenticationUtil authenticationUtil;

    @PostMapping("/follows")
    public void follow(@Valid @RequestBody Request.Add add){
        final String authenticationEmail = authenticationUtil.getAuthenticationEmail();
        final Account fromAccount = accountService.findByEmail(authenticationEmail);
        final Account toAccount = accountService.findByEmail(add.getEmail());

        final Follow follow = new Follow(fromAccount, toAccount);

        followService.follow(follow);
    }

    @DeleteMapping("/follows")
    public void unfollow(@Valid @RequestBody Request.Delete delete){
        final String authenticationEmail = authenticationUtil.getAuthenticationEmail();
        final Account fromAccount = accountService.findByEmail(authenticationEmail);
        final Account toAccount = accountService.findByEmail(delete.getEmail());

        followService.unfollow(fromAccount, toAccount);
    }

}
