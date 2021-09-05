package com.followproject.business.profile.api;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.service.AccountService;
import com.followproject.business.follow.entity.Follow;
import com.followproject.business.follow.service.FollowService;
import com.followproject.business.profile.form.ProfileForm;
import com.followproject.business.profile.form.ProfileForm.*;
import com.followproject.common.validator.CommonValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    private final FollowService followService;
    private final AccountService accountService;
    private final CommonValidator commonValidator;

    @GetMapping("/profiles/{email}")
    public Response.Find getProfile(@PathVariable String email){
        commonValidator.validateEmail(email);

        final Account account = accountService.findByEmail(email);
        final List<String> toAccountEmails = followService.findAllByFromAccount(account)
                                                          .stream()
                                                          .map(Follow::getToAccount)
                                                          .map(e -> e.getEmail())
                                                          .collect(Collectors.toList());

        return new Response.Find(followService.findCount(account), toAccountEmails);
    }


}
