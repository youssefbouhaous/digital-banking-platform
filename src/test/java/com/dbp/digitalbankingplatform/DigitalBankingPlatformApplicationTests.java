package com.dbp.digitalbankingplatform;

import com.dbp.digitalbankingplatform.dto.request.CreateAccountRequest;
import com.dbp.digitalbankingplatform.model.Account;
import com.dbp.digitalbankingplatform.service.AccountLifecycleService;
import com.dbp.digitalbankingplatform.service.AccountService;
import com.dbp.digitalbankingplatform.service.TransferService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DigitalBankingPlatformApplicationTests {
    @Autowired
    AccountService accountService;
    @Autowired
    TransferService transferService;
    @Autowired
    AccountLifecycleService accountLifecycleService;
    @Test
    void contextLoads() {
    }

    @Test
    void testTransfer(){
        Account sender = accountService.create(new CreateAccountRequest("","",""));
        Account receiver = accountService.create(new CreateAccountRequest("","",""));
        accountLifecycleService.activateAccount(sender.getId());
        accountService.addBalance(sender.getId(),new BigDecimal(1000));
    }
}
