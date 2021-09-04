package com.followproject.business.account.mapper;

import com.followproject.business.account.entity.Account;
import com.followproject.business.account.form.AccountForm.*;
import com.followproject.common.mapper.GenericMapper;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper extends GenericMapper<Response.Find, Account> {

}
