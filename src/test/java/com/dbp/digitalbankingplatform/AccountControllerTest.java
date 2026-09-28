package com.dbp.digitalbankingplatform;

import com.dbp.digitalbankingplatform.controller.AccountController;
import com.dbp.digitalbankingplatform.dto.request.CreateAccountRequest;
import com.dbp.digitalbankingplatform.dto.response.AccountResponse;
import com.dbp.digitalbankingplatform.model.Account;
import com.dbp.digitalbankingplatform.service.AccountService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * AccountControllerTest
 *
 * @author Youssef Bouhaous
 * @since 9/28/26
 */
@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

    @Test
    public void checkingBalance() throws Exception {
        UUID id = UUID.randomUUID();
        when(accountService.checkBalance(id)).thenReturn(new BigDecimal("125.50"));
        mockMvc.perform(get("/api/accounts/{id}/balance", id))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$").value(125.50));
    }

    @Test
    public void createAccount() throws Exception {
        CreateAccountRequest createAccountRequest = new CreateAccountRequest("test","test","test");
        Account savedAccount = new Account();
        savedAccount.setUsername("test");
        savedAccount.setFirstName("test");
        savedAccount.setLastName("test");
        when(accountService.create(createAccountRequest)).thenReturn(savedAccount);
        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "userName": "test",
                              "firstName": "test",
                              "lastName": "test"
                            }
                            """))
                .andExpect(status().isCreated());
    }

    @Test
    public void listAccounts() throws Exception {
        List<Account> accounts = new ArrayList<>();
        when(accountService.findAll()).thenReturn(accounts);
        mockMvc.perform(get("/api/accounts"))
                .andExpect(status().isOk());
    }
}
