package com.dbp.digitalbankingplatform.service;

import com.dbp.digitalbankingplatform.dto.request.CreateAccountRequest;
import com.dbp.digitalbankingplatform.dto.response.TransferByAccountResponse;
import com.dbp.digitalbankingplatform.dto.response.TransferResponse;
import com.dbp.digitalbankingplatform.exception.AccountNotFoundException;
import com.dbp.digitalbankingplatform.model.Account;
import com.dbp.digitalbankingplatform.repository.AccountRepository;
import com.dbp.digitalbankingplatform.repository.TransferRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * AccountService
 *
 * @author Youssef Bouhaous
 * @since 9/25/26
 */

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransferRepository transferRepository;
    public AccountService(AccountRepository accountRepository, TransferRepository transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    public Account create(CreateAccountRequest createAccountRequest){
        Account account = new Account();
        account.setUsername(createAccountRequest.userName().strip());
        account.setFirstName(createAccountRequest.firstName().strip());
        account.setLastName(createAccountRequest.lastName().strip());
        return accountRepository.save(account);
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public BigDecimal checkBalance(UUID uuid){
        Optional<Account> optionalAccount = accountRepository.findById(uuid);
        if (optionalAccount.isPresent())
        return optionalAccount.get().getBalance();
        else throw new RuntimeException("Account not found");
    }

    public boolean addBalance(UUID uuid,BigDecimal amount){
        if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0) throw new RuntimeException("amount is not positive or null");
        Optional<Account> accountOpt = accountRepository.findById(uuid);
        if (accountOpt.isPresent()){
            Account account = accountOpt.get();
            account.setBalance(account.getBalance().add(amount));
            accountRepository.save(account);
            return true;
        }else throw new RuntimeException("Account not found");
    }

    public boolean subBalance(UUID uuid, BigDecimal amount){
        if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0) throw new RuntimeException("amount is not positive or null");
        Optional<Account> accountOpt = accountRepository.findById(uuid);
        if (accountOpt.isPresent()){
            Account account = accountOpt.get();
            if (account.getBalance().compareTo(amount)>=0){
                account.setBalance(account.getBalance().subtract(amount));
                accountRepository.save(account);
                return true;
            }
            else{
                return false;
            }
        }else throw new RuntimeException("Account not found");
    }


    public Page<TransferByAccountResponse> historyByAccount(UUID id,Pageable pageable){
        Optional<Account> accountOpt = accountRepository.findById(id);
        if (accountOpt.isEmpty())throw  new AccountNotFoundException("Account not found");
        Account account=accountOpt.get();
        return transferRepository
                .findAllBySenderOrReceiverOrderByCreatedAtDesc(account, account, pageable)
                .map(transfer -> new TransferByAccountResponse(
                        new TransferResponse(transfer),
                        transfer.getReceiver().getId().equals(id)
                                ? "INCOMING"
                                : "OUTGOING"
                ));
    }
}
