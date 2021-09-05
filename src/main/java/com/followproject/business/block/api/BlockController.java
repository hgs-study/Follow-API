package com.followproject.business.block.api;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.service.AccountService;
import com.followproject.business.block.entity.Block;
import com.followproject.business.block.form.BlockForm.*;
import com.followproject.business.block.service.BlockService;
import com.followproject.business.follow.service.FollowService;
import com.followproject.common.response.dto.ResponseDto;
import com.followproject.common.response.util.ApiResponse;
import com.followproject.common.util.AuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BlockController {
    private final AuthenticationUtil authenticationUtil;
    private final AccountService accountService;
    private final BlockService blockService;
    private final FollowService followService;

    @PostMapping("/blocks")
    public ResponseEntity<ResponseDto> block(@Valid @RequestBody Request.Add add){
        final String authenticationEmail = authenticationUtil.getAuthenticationEmail();
        final Account fromAccount = accountService.findByEmail(authenticationEmail);
        final Account toAccount = accountService.findByEmail(add.getEmail());

        final Block block = new Block(fromAccount, toAccount);

        unfollowWhenFollow(fromAccount, toAccount);
        blockService.block(block);

        return ApiResponse.success(HttpStatus.OK, "정상적으로 블락 되었습니다.");
    }

    private void unfollowWhenFollow(Account fromAccount, Account toAccount) {
        if(followService.findByFromAccountAndToAccount(fromAccount, toAccount) != null)
            followService.unfollow(fromAccount, toAccount);
    }
}
