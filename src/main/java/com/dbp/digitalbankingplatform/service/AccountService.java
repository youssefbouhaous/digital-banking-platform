package com.dbp.digitalbankingplatform.service;

import com.dbp.digitalbankingplatform.dto.request.CreateAccountRequest;
import com.dbp.digitalbankingplatform.model.Account;
import com.dbp.digitalbankingplatform.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account create(CreateAccountRequest createAccountRequest){
        Account account = new Account();
        account.setUsername(createAccountRequest.userName());
        account.setFirstName(createAccountRequest.firstName());
        account.setLastName(createAccountRequest.lastName());
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

    @Transactional
    public boolean transfer(UUID idSender,UUID idReceiver,BigDecimal amount){
        if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0) throw new RuntimeException("amount is not positive or null");
        Optional<Account> accountSenderOpt = accountRepository.findById(idSender);
        Optional<Account> accountReceiverOpt = accountRepository.findById(idReceiver);
        if (accountReceiverOpt.isEmpty() || accountSenderOpt.isEmpty()){
            throw new RuntimeException("Receiver or Sender account not found");
        }
        Account accountSender = accountSenderOpt.get();
        Account accountReceiver = accountReceiverOpt.get();
        if (accountSender.getBalance().compareTo(amount)>=0){
            accountSender.setBalance(accountSender.getBalance().subtract(amount));
            accountReceiver.setBalance(accountReceiver.getBalance().add(amount));
            accountRepository.save(accountSender);
            accountRepository.save(accountReceiver);
            return true;
        }else{
            System.out.println("Amount is not enough");
            return false;
        }
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
}
