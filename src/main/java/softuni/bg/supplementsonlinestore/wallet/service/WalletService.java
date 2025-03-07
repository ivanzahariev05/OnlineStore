package softuni.bg.supplementsonlinestore.wallet.service;

import org.springframework.stereotype.Service;
import softuni.bg.supplementsonlinestore.exception.DomainException;
import softuni.bg.supplementsonlinestore.transaction.model.Transaction;
import softuni.bg.supplementsonlinestore.transaction.model.TransactionStatus;
import softuni.bg.supplementsonlinestore.transaction.model.TransactionType;
import softuni.bg.supplementsonlinestore.transaction.service.TransactionService;
import softuni.bg.supplementsonlinestore.user.model.User;
import softuni.bg.supplementsonlinestore.wallet.model.Wallet;
import softuni.bg.supplementsonlinestore.wallet.repository.WalletRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Service

public class WalletService {


    private final WalletRepository walletRepository;
    private final TransactionService transactionService;



    public WalletService(WalletRepository walletRepository, TransactionService transactionService) {
        this.walletRepository = walletRepository;

        this.transactionService = transactionService;
    }


    public Wallet createNewWallet(User user) {

        Wallet wallet = initializeWallet(user);
        walletRepository.save(wallet);
        return wallet;
    }

    private Wallet initializeWallet(User user) {
        return Wallet.builder()
                .owner(user)
                .balance(new BigDecimal("0.00"))
                .currency(Currency.getInstance("EUR"))
                .build();
    }


    public Wallet findById(UUID id) {
        return walletRepository.findWalletById(id)
                .orElseThrow(() -> new DomainException("Wallet not found"));
    }

//    public Transaction addFunds(UUID id, BigDecimal amount) {
//        Wallet wallet = findById(id);
//        wallet.setBalance(wallet.getBalance().add(amount));
//
//        walletRepository.save(wallet);
//
////        return transactionService.createNewTransaction(amount,
////                TransactionType.DEPOSIT,
////                TransactionStatus.SUCCEED,
////                LocalDateTime.now(),
////                wallet.getOwner(),
////                wallet.getOwner(),
////                )
////
////
//    }
}