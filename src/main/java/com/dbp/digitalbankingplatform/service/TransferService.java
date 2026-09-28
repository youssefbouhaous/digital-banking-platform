package com.dbp.digitalbankingplatform.service;

import com.dbp.digitalbankingplatform.dto.request.TransferRequest;
import com.dbp.digitalbankingplatform.exception.AccountNotFoundException;
import com.dbp.digitalbankingplatform.exception.InsufficientFundsException;
import com.dbp.digitalbankingplatform.exception.InvalidTransferException;
import com.dbp.digitalbankingplatform.model.Account;
import com.dbp.digitalbankingplatform.model.Transfer;
import com.dbp.digitalbankingplatform.repository.AccountRepository;
import com.dbp.digitalbankingplatform.repository.TransferRepository;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * TransferService
 *
 * @author Youssef Bouhaous
 * @since 9/28/26
 */
@Service
public class TransferService {
    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    public TransferService(TransferRepository transferRepository,AccountRepository accountRepository) {
        this.transferRepository = transferRepository;
        this.accountRepository = accountRepository;
    }
    @Transactional
    public Transfer transfer(TransferRequest request){
        return transfer(request.senderId(),request.receiverId(),request.amount());
    }
    @Transactional
    public Transfer transfer(@NotNull UUID idSender,@NotNull UUID idReceiver, BigDecimal amount){
        if(amount==null || amount.compareTo(BigDecimal.ZERO)<=0) throw new InvalidTransferException("amount is not positive or null.");
        if(idSender.equals(idReceiver))throw new InvalidTransferException("the sender and receiver are identical.");
        Optional<Account> accountSenderOpt = accountRepository.findById(idSender);
        Optional<Account> accountReceiverOpt = accountRepository.findById(idReceiver);
        if (accountReceiverOpt.isEmpty() || accountSenderOpt.isEmpty()){
            throw new AccountNotFoundException("Receiver or Sender account not found");
        }
        Account accountSender = accountSenderOpt.get();
        Account accountReceiver = accountReceiverOpt.get();
        if (accountSender.getBalance().compareTo(amount)>=0){
            accountSender.setBalance(accountSender.getBalance().subtract(amount));
            accountReceiver.setBalance(accountReceiver.getBalance().add(amount));
            accountRepository.save(accountSender);
            accountRepository.save(accountReceiver);
            Transfer transfer = new Transfer();
            transfer.setAmount(amount);
            transfer.setSender(accountSender);
            transfer.setReceiver(accountReceiver);
            return transferRepository.save(transfer);
        }else{
            throw new InsufficientFundsException("insufficient funds");
        }
    }
}
