package com.ekhonni.backend.service.payment.provider.transfer;

import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.PaymentMethod;
import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.exception.payment.InvalidTransactionRequestException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.service.AccountService;
import com.ekhonni.backend.service.BidService;
import com.ekhonni.backend.service.TransactionService;
import com.ekhonni.backend.service.payment.PaymentProvider;
import com.ekhonni.backend.service.payment.provider.sslcommrez.response.InitiatePaymentResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumSet;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */
@Slf4j
@Service
@AllArgsConstructor
public class TransferPaymentProvider implements PaymentProvider  {

    private final AccountService accountService;
    private final TransactionService transactionService;
    private final BidService bidService;

    @Override
    public InitiatePaymentResponse processPayment(Long bidId) {

        Bid bid = bidService.get(bidId).orElseThrow(() -> {
                    log.warn("Payment request for invalid bid: {}", bidId);
                    return new InvalidTransactionRequestException();
                });

        verifyBid(bid);

        Transaction transaction = transactionService.findByBidId(bidId)
                .orElseGet(() -> transactionService.create(bid, PaymentMethod.TRANSFER, TransactionStatus.TRANSFERRING));

        if (EnumSet.of(TransactionStatus.VALID, TransactionStatus.VALIDATED, TransactionStatus.VALID_WITH_RISK)
                .contains(transaction.getStatus())) {
            log.warn("Transaction already processed: {}", transaction.getId());
            throw new InvalidTransactionRequestException();
        }
        transactionService.updateStatus(transaction, TransactionStatus.TRANSFERRING);
        transactionService.updateMethod(transaction, PaymentMethod.TRANSFER);
        try {
            updateTransaction(transaction, bid);
        } catch (Exception e) {
            transactionService.updateStatus(transaction, TransactionStatus.FAILED);
            throw e;
        }
        return null;
    }

    @Override
    public InitiatePaymentResponse processCashIn(Double amount) {
        throw new RuntimeException("Invalid request");
    }

    private void verifyBid(Bid bid) {
        if (bid.getStatus() == BidStatus.PAID) {
            log.warn("Duplicate payment request for bid: {}", bid.getId());
            throw new InvalidTransactionRequestException();
        }
        if (bid.getStatus() != BidStatus.ACCEPTED) {
            log.warn("Payment request for unaccepted bid: {}", bid.getId());
            throw new InvalidTransactionRequestException();
        }
    }

    @Transactional
    public void updateTransaction(Transaction transaction, Bid bid) {
        transaction.setBdtAmount(bid.getAmount());
        transaction.setProcessedAt(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.VALID);
        transaction.getBid().setStatus(BidStatus.PAID);

        Account sellerAccount = accountService.getByUserId(transaction.getSeller().getId());
        Account buyerAccount = accountService.getByUserId(transaction.getBuyer().getId());
        accountService.transfer(buyerAccount, sellerAccount, bid.getAmount());
    }

}
