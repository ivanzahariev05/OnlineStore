package softuni.bg.supplementsonlinestore.wallet.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import softuni.bg.supplementsonlinestore.exception.DomainException;
import softuni.bg.supplementsonlinestore.transaction.model.Transaction;
import softuni.bg.supplementsonlinestore.transaction.model.TransactionStatus;
import softuni.bg.supplementsonlinestore.transaction.model.TransactionType;
import softuni.bg.supplementsonlinestore.transaction.service.TransactionService;
import softuni.bg.supplementsonlinestore.user.model.User;
import softuni.bg.supplementsonlinestore.user.service.UserService;
import softuni.bg.supplementsonlinestore.wallet.model.Wallet;
import softuni.bg.supplementsonlinestore.wallet.repository.WalletRepository;
import softuni.bg.supplementsonlinestore.web.dto.SendToAFriendRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Service

public class WalletService {


    private final WalletRepository walletRepository;
    private final TransactionService transactionService;
    private final String NUTRIBOOST_LTD = "NUTRIBOOST_LTD";
    private final UserService userService;


    public WalletService(WalletRepository walletRepository, TransactionService transactionService, @Lazy UserService userService) {
        this.walletRepository = walletRepository;

        this.transactionService = transactionService;
        this.userService = userService;
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


    public Wallet findWalletById(UUID id) {
        return walletRepository.findWalletById(id)
                .orElseThrow(() -> new DomainException("Wallet not found"));
    }

    public Transaction addFunds(UUID id, BigDecimal amount) {
        Wallet wallet = findWalletById(id);
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new DomainException("Amount must be greater than zero");
        }
        wallet.setBalance(wallet.getBalance().add(amount));

        walletRepository.save(wallet);

        return transactionService.createNewTransaction(
                amount,
                TransactionType.DEPOSIT,
                TransactionStatus.SUCCEED,
                LocalDateTime.now(),
                NUTRIBOOST_LTD,
                wallet.getOwner(),
                null
        );

    }

    @Transactional
    public Transaction sendToAFriend(@Valid SendToAFriendRequest sendToAFriendRequest) {

        User userReceiver = userService.findByUsername(sendToAFriendRequest.getToUser());
        User userSender = userService.findByUsername(sendToAFriendRequest.getFromUser());
        if (userReceiver == null) {
            throw new DomainException("User not found");
        }
        Wallet walletReceiver = findWalletById(userReceiver.getWallet().getId());
        Wallet walletSender = findWalletById(userSender.getWallet().getId());
        if (walletSender.getBalance().compareTo(sendToAFriendRequest.getAmount()) < 0) {
            return transactionService.createNewTransaction(
                    sendToAFriendRequest.getAmount(),
                    TransactionType.WITHDRAWAL,
                    TransactionStatus.FAILED,
                    LocalDateTime.now(),
                    userSender.getUsername(),
                    userReceiver,
                    "insufficient funds");
        }
        walletSender.setBalance(walletSender.getBalance().subtract(sendToAFriendRequest.getAmount()));
        walletRepository.save(walletSender);
        walletReceiver.setBalance(walletReceiver.getBalance().add(sendToAFriendRequest.getAmount()));
        walletRepository.save(walletReceiver);
        return transactionService.createNewTransaction(
                sendToAFriendRequest.getAmount(),
                TransactionType.WITHDRAWAL,
                TransactionStatus.SUCCEED,
                LocalDateTime.now(),
                userSender.getUsername(),
                userReceiver,
                null);

    }



    @Transactional
    public Transaction charge(Wallet sender, UUID walletId, BigDecimal amount) {

        Wallet wallet = findWalletById(walletId);

        if (wallet.getBalance().compareTo(amount) < 0) {
            return transactionService.createNewTransaction(
                    amount,
                    TransactionType.WITHDRAWAL,
                    TransactionStatus.FAILED,
                    LocalDateTime.now(),
                    sender.getId().toString(),
                    wallet.getOwner(),
                    "Insufficient funds");


        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);
        return transactionService.createNewTransaction(amount,
                TransactionType.WITHDRAWAL,
                TransactionStatus.SUCCEED,
                LocalDateTime.now(),
                sender.getId().toString(),
                wallet.getOwner(),
                null);


    }
}