package com.followproject.common.securirty.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.followproject.business.account.entity.Account;
import com.followproject.business.account.form.AccountForm.*;
import com.followproject.business.account.service.AccountService;
import com.followproject.common.redis.RedisUtil;
import com.followproject.common.securirty.cookie.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final AccountService accountService;
    private final CookieUtil cookieUtil;
    private final RedisUtil redisUtil;

    private final String ACCESS_TOKEN_NAME = JwtProperties.ACCESS_TOKEN_NAME;
    private final String REFRESH_TOKEN_NAME = JwtProperties.REFRESH_TOKEN_NAME;
    private final long REFRESH_TOKEN_EXPIRATION_TIME = JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME;
    private final long ACCESS_TOKEN_EXPIRATION_TIME = JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, AccountService accountService, CookieUtil cookieUtil, RedisUtil redisUtil) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.accountService = accountService;
        this.cookieUtil = cookieUtil;
        this.redisUtil = redisUtil;
    }

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            //  "/login"??? 1????????? ??? /getInputStream() : post ????????? ?????? ?????? ?????? ??? ??????
            Request.Login creds = new ObjectMapper().readValue(request.getInputStream(), Request.Login.class);

            //UsernamePasswordAuthenticationToken ?????? ?????? ??? AuthenticationManager??? ???????????? ??????
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        final String email = ((Account)authResult.getPrincipal()).getEmail();
        final Account account = accountService.findByEmail(email);

        final String accessToken = jwtTokenProvider.createToken(account.getUserKey(), account.getRoles(), ACCESS_TOKEN_EXPIRATION_TIME);
        final String refreshToken = jwtTokenProvider.createToken(account.getUserKey(), account.getRoles(), REFRESH_TOKEN_EXPIRATION_TIME);

        final Cookie accessTokenCookie = cookieUtil.createCookie(ACCESS_TOKEN_NAME, accessToken, ACCESS_TOKEN_EXPIRATION_TIME);
        final Cookie refreshTokenCookie = cookieUtil.createCookie(REFRESH_TOKEN_NAME, refreshToken, REFRESH_TOKEN_EXPIRATION_TIME);

        redisUtil.setDataExpire(refreshToken, account.getUserKey() , JwtProperties.REFRESH_TOKEN_EXPIRATION_TIME);

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException exception) throws IOException, ServletException {
        String message = exception.getMessage();

        if(exception instanceof BadCredentialsException) {
            message = "???????????? ??????????????? ?????? ????????????.";
        } else if(exception instanceof DisabledException) {
            message = "????????? ???????????????????????????. ??????????????? ???????????????.";
        } else if(exception instanceof CredentialsExpiredException) {
            message = "???????????? ??????????????? ?????? ???????????????. ??????????????? ???????????????.";
        }


        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, message);
    }
}
