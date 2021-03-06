package com.followproject.business.account.api;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.form.AccountForm.*;
import com.followproject.business.account.mapper.AccountMapper;
import com.followproject.business.account.service.AccountService;
import com.followproject.business.account.util.AccountUtil;
import com.followproject.business.account.validator.AccountValidator;
import com.followproject.common.response.dto.ResponseDto;
import com.followproject.common.response.util.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "1. Account")
@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountValidator accountValidator;
    private final AccountUtil accountUtil;
    private final AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    @ApiOperation(value="회원가입" , notes = "유저 등록(회원가입)합니다.")
    @PostMapping("/join")
    public ResponseEntity<ResponseDto> join(@Valid @RequestBody Request.Join join) {
        final Account account = accountUtil.createJoinAccount(join);
        accountService.join(account);

        return ApiResponse.success(HttpStatus.CREATED, join.getEmail()+" 정상적으로 회원가입 되었습니다.");

    }

    @ApiOperation(value="유저 목록 조회" , notes = "유저 목록을 조회합니다.")
    @GetMapping("/accounts")
    public List<Response.Find> findAccounts(){
        return accountService.findAll().stream()
                                       .map((entity) -> mapper.toDto(entity))
                                       .collect(Collectors.toList());
    }

    @ApiOperation(value="유저 상세 조회" , notes = "유저 상세 조회합니다.")
    @GetMapping("/accounts/{email}")
    public Response.Find findAccount(@PathVariable String email){
        accountValidator.validate(email);

        return mapper.toDto(accountService.findByEmail(email));
    }
}
