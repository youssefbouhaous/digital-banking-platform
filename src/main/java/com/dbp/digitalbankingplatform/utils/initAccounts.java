package com.dbp.digitalbankingplatform.utils;

import com.dbp.digitalbankingplatform.dto.request.CreateAccountRequest;
import com.dbp.digitalbankingplatform.model.Account;
import com.dbp.digitalbankingplatform.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * initAccounts
 *
 * @author Youssef Bouhaous
 * @since 9/25/26
 */
@Component
public class initAccounts implements CommandLineRunner {
    private final AccountService accountService;

    public initAccounts(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!accountService.findAll().isEmpty())return;
        String[] firstNames = {
                "Youssef", "Amine", "Sara", "Omar", "Salma",
                "Mehdi", "Imane", "Adam", "Lina", "Hamza",
                "Nora", "Anas", "Aya", "Ilyas", "Meryem",
                "Zakaria", "Sofia", "Rayan", "Kenza", "Ayoub"
        };

        String[] lastNames = {
                "Alaoui", "Bennani", "El Amrani", "Idrissi", "Tazi",
                "Benjelloun", "Berrada", "Mansouri", "Chraibi", "Fassi",
                "Naciri", "Lahlou", "Bennis", "Skalli", "Kabbaj",
                "Amrani", "Belkadi", "Tahiri", "Ouazzani", "Lamrani"
        };

        for (int i = 0; i < firstNames.length; i++) {
//            Account account = new Account();
//            account.setFirstName(firstNames[i]);
//            account.setLastName(lastNames[i]);
//            account.setUsername(firstNames[i]+"-"+lastNames[i]);
            accountService.create(new CreateAccountRequest(firstNames[i]+"-"+lastNames[i],firstNames[i],lastNames[i]));
        }
    }
}
