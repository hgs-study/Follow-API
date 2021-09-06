package com.followproject.business.block.service;

import com.followproject.business.account.entity.Account;
import com.followproject.business.block.entity.Block;
import com.followproject.business.block.repository.BlockRepository;
import com.followproject.common.error.code.ErrorCode;
import com.followproject.common.error.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockService {
    private final BlockRepository blockRepository;

    @Transactional
    public void block(Block block){
        blockRepository.save(block);
    }

    public boolean isBlockAccount(Account fromAccount, Account toAccount){
        if(blockRepository.findByFromAccountAndToAccount(fromAccount, toAccount) != null ||
           blockRepository.findByFromAccountAndToAccount(toAccount, fromAccount) != null)
            return true;
        return false;
    }

    public void validateBlockedAccount(Account fromAccount, Account toAccount){
        if(isBlockAccount(fromAccount,toAccount))
            throw new BusinessException(ErrorCode.BLOCKED_PROFILE);
    }

}
