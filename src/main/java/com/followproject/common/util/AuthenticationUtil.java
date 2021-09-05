package com.followproject.common.util;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Component
@RequiredArgsConstructor
public class AuthenticationUtil {
    public String getAuthenticationEmail(){
        UserDetails user = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getUsername();
    }
}
