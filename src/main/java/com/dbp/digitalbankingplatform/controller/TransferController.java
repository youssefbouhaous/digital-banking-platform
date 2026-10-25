package com.dbp.digitalbankingplatform.controller;

import com.dbp.digitalbankingplatform.dto.request.TransferRequest;
import com.dbp.digitalbankingplatform.dto.response.TransferResponse;
import com.dbp.digitalbankingplatform.exception.AccountNotActiveException;
import com.dbp.digitalbankingplatform.exception.AccountNotFoundException;
import com.dbp.digitalbankingplatform.exception.InsufficientFundsException;
import com.dbp.digitalbankingplatform.exception.InvalidTransferException;
import com.dbp.digitalbankingplatform.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TransferController
 *
 * @author Youssef Bouhaous
 * @since 9/28/26
 */
@RequestMapping("/api/transfers")
@RestController
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferRequest request){
        try {
            TransferResponse transferResponse = new TransferResponse(transferService.transfer(request));
            return ResponseEntity.status(HttpStatus.CREATED).body(transferResponse);
        }
        catch (AccountNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (InsufficientFundsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        catch (InvalidTransferException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (AccountNotActiveException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}
