package com.followproject.business.profile.api;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.service.AccountService;
import com.followproject.business.block.service.BlockService;
import com.followproject.business.follow.entity.Follow;
import com.followproject.business.follow.service.FollowService;
import com.followproject.business.profile.form.ProfileForm.*;
import com.followproject.common.error.code.ErrorCode;
import com.followproject.common.error.exception.BusinessException;
import com.followproject.common.util.AuthenticationUtil;
import com.followproject.common.validator.CommonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final FollowService followService;
    private final AccountService accountService;
    private final BlockService blockService;
    private final AuthenticationUtil authenticationUtil;
    private final CommonValidator commonValidator;

    @GetMapping("/profiles")
    public Response.Find getProfile(){
        final String authenticationEmail = authenticationUtil.getAuthenticationEmail();
        final Account fromAccount = accountService.findByEmail(authenticationEmail);

        final List<String> toAccountEmails = followService.findAllByFromAccount(fromAccount)
                                                          .stream()
                                                          .map(Follow::getToAccount)
                                                          .map(e -> e.getEmail())
                                                          .collect(Collectors.toList());

        return new Response.Find(followService.findCountByFromAccount(fromAccount), toAccountEmails);
    }

    @GetMapping("/profiles/{email}")
    public Response.Find getToAccountProfile(@PathVariable String email){
        commonValidator.validateEmail(email);

        final String authenticationEmail = authenticationUtil.getAuthenticationEmail();
        final Account fromAccount = accountService.findByEmail(authenticationEmail);
        final Account toAccount = accountService.findByEmail(email);

        blockService.validateBlockedAccount(fromAccount,toAccount);

        final List<String> toAccountEmails = followService.findAllByFromAccount(toAccount)
                                                          .stream()
                                                          .map(Follow::getToAccount)
                                                          .map(e -> e.getEmail())
                                                          .collect(Collectors.toList());

        return new Response.Find(followService.findCountByFromAccount(toAccount), toAccountEmails);
    }


}
