package com.followproject.business.profile.api;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.service.AccountService;
import com.followproject.business.block.service.BlockService;
import com.followproject.business.follow.entity.Follow;
import com.followproject.business.follow.service.FollowService;
import com.followproject.business.profile.form.ProfileForm.*;
import com.followproject.common.util.AuthenticationUtil;
import com.followproject.common.validator.CommonValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "2. Profile")
@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final FollowService followService;
    private final AccountService accountService;
    private final BlockService blockService;
    private final AuthenticationUtil authenticationUtil;
    private final CommonValidator commonValidator;

    @ApiOperation(value="자신 프로필 조회" , notes = "자신 프로필을 조회합니다.")
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

    @ApiOperation(value="유저 프로필 조회" , notes = "유저 프로필을 조회합니다.")
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
