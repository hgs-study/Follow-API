package com.followproject.business.block.service;

import com.followproject.business.account.entity.Account;
import com.followproject.business.block.entity.Block;
import com.followproject.business.block.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BlockService {
    private final BlockRepository blockRepository;

    public void block(Block block){
        blockRepository.save(block);
    }

    public boolean isBlockAccount(Account fromAccount, Account toAccount){
        blockRepository.findByFromAccountAndToAccount(fromAccount, toAccount)
    }

}
