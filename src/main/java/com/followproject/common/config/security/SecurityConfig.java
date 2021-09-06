package com.followproject.common.config.security;

import com.followproject.business.account.service.AccountService;
import com.followproject.common.redis.RedisUtil;
import com.followproject.common.securirty.cookie.CookieUtil;
import com.followproject.common.securirty.handler.CustomLogoutHandler;
import com.followproject.common.securirty.jwt.CustomAuthenticationFilter;
import com.followproject.common.securirty.jwt.JwtAuthenticationFilter;
import com.followproject.common.securirty.jwt.JwtProperties;
import com.followproject.common.securirty.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomLogoutHandler customLogoutHandler;
    private final CustomAuthenticationFilter customAuthenticationFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // authenticationManager를 Bean 등록합니다.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider,
                                                           AccountService accountService,
                                                           CookieUtil cookieUtil,
                                                           RedisUtil redisUtil){
        return new JwtAuthenticationFilter(jwtTokenProvider,accountService,cookieUtil,redisUtil);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .headers().frameOptions().disable();

        http
            .httpBasic().disable() // rest api 만을 고려하여 기본 설정은 해제하겠습니다.
            .csrf().disable() // csrf 보안 토큰 disable처리.
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
            .and()
            .authorizeRequests() // 요청에 대한 사용권한 체크
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/accounts/**","/follows/**","/blocks/**","/profiles/**").hasRole("USER")
            .anyRequest().permitAll()// 나머지 요청은 누구나 접근 가능
            .and()
//            .addFilter(jwtAuthenticationFilter)
            .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다

        http
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
            .deleteCookies(JwtProperties.ACCESS_TOKEN_NAME)
            .deleteCookies(JwtProperties.REFRESH_TOKEN_NAME)
            .addLogoutHandler(customLogoutHandler);

    }


}
