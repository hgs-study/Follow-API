package com.followproject.business.follow.api;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.service.AccountService;
import com.followproject.business.follow.entity.Follow;
import com.followproject.business.follow.form.FollowForm.*;
import com.followproject.business.follow.service.FollowService;
import com.followproject.common.response.dto.ResponseDto;
import com.followproject.common.response.util.ApiResponse;
import com.followproject.common.util.AuthenticationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "3. Follow")
@RestController
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final AccountService accountService;
    private final AuthenticationUtil authenticationUtil;

    @ApiOperation(value="팔로우" , notes = "특정 유저를 팔로우합니다.")
    @PostMapping("/follows")
    public ResponseEntity<ResponseDto> follow(@Valid @RequestBody Request.Add add){
        final String authenticationEmail = authenticationUtil.getAuthenticationEmail();
        final Account fromAccount = accountService.findByEmail(authenticationEmail);
        final Account toAccount = accountService.findByEmail(add.getEmail());

        final Follow follow = new Follow(fromAccount, toAccount);

        followService.follow(follow);

        return ApiResponse.success(HttpStatus.OK, "정상적으로 팔로우 되었습니다.");
    }

    @ApiOperation(value="언팔로우" , notes = "특정 유저를 언팔로우합니다.")
    @DeleteMapping("/follows")
    public ResponseEntity<ResponseDto> unfollow(@Valid @RequestBody Request.Delete delete){
        final String authenticationEmail = authenticationUtil.getAuthenticationEmail();
        final Account fromAccount = accountService.findByEmail(authenticationEmail);
        final Account toAccount = accountService.findByEmail(delete.getEmail());

        followService.unfollow(fromAccount, toAccount);

        return ApiResponse.success(HttpStatus.OK, "정상적으로 언팔로우 되었습니다.");
    }

    @ApiOperation(value="Bulk 팔로우" , notes = "특정 유저를 팔로우합니다.")
    @PostMapping("/follows/bulk")
    public ResponseEntity<ResponseDto> bulkFollow(@Valid @RequestBody Request.Add add){
        final String authenticationEmail = authenticationUtil.getAuthenticationEmail();
        final Account fromAccount = accountService.findByEmail(authenticationEmail);
        List<Account> accounts = new ArrayList<>();
        List<Follow> follows = new ArrayList<>();

        for (int i = 0; i < 100_000; i++) {
            final Account toAccount = new Account("hgstudy@naver.com"+i,"password1234!");
            accounts.add(toAccount);
        }
        accountService.saveAll(accounts);

        for (int i = 0; i < 100_000; i++) {
            final Follow follow = new Follow(fromAccount, accounts.get(i));
            follows.add(follow);
        }
        followService.saveAll(follows);

        return ApiResponse.success(HttpStatus.OK, "정상적으로 팔로우 되었습니다.");
    }

}
