package com.dbp.digitalbankingplatform;

import com.dbp.digitalbankingplatform.model.Account;
import com.dbp.digitalbankingplatform.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DigitalBankingPlatformApplicationTests {
    @Autowired
    AccountService accountService;
    @Test
    void contextLoads() {
    }

    @Test
    void testTransfer(){
        Account sender = new Account();
        Account receiver = new Account();
        sender.setBalance(new BigDecimal(1000));
        accountService.create(sender);
        accountService.create(receiver);
        assertEquals(true, accountService.transfer(sender.getId(),receiver.getId(),new BigDecimal(100)));
    }
}
