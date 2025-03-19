package softuni.bg.supplementsonlinestore.wallet.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import softuni.bg.supplementsonlinestore.exception.DomainException;
import softuni.bg.supplementsonlinestore.transaction.model.Transaction;
import softuni.bg.supplementsonlinestore.transaction.model.TransactionStatus;
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
@Slf4j
public class WalletService {
    private final WalletRepository walletRepository;
    private final TransactionService transactionService;
    private final UserService userService;
    private static final String NUTRIBOOST_LTD = "NUTRIBOOST_LTD";

    public WalletService(WalletRepository walletRepository, TransactionService transactionService, @Lazy UserService userService) {
        this.walletRepository = walletRepository;
        this.transactionService = transactionService;
        this.userService = userService;
    }

    public Wallet createNewWallet(User user) {
        Wallet wallet = Wallet.builder()
                .owner(user)
                .balance(BigDecimal.ZERO)
                .currency(Currency.getInstance("EUR"))
                .build();
        return walletRepository.save(wallet);
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

        charge(wallet.getOwner(), amount, true);

        return transactionService.createDepositTransaction(
                amount,
                TransactionStatus.SUCCEEDED,
                LocalDateTime.now(),
                NUTRIBOOST_LTD,
                wallet.getOwner(),
                null
        );
    }

    @Transactional
    public void sendToAFriend(@Valid SendToAFriendRequest sendToAFriendRequest) {
        log.info("Initiating sendToAFriend: sender={}, receiver={}, amount={}",
                userService.getCurrentUser().getUsername(), sendToAFriendRequest.getToUser(), sendToAFriendRequest.getAmount());

        User sender = userService.getCurrentUser();
        User receiver = userService.findByUsername(sendToAFriendRequest.getToUser());
        if (receiver == null) {
            log.error("Recipient user not found: {}", sendToAFriendRequest.getToUser());
            throw new DomainException("User not found");
        }

        BigDecimal amount = sendToAFriendRequest.getAmount();

        try {
            charge(sender, amount, false);
            charge(receiver, amount, true);


            transactionService.createWithdrawalTransaction(
                    amount,
                    TransactionStatus.SUCCEEDED,
                    LocalDateTime.now(),
                    sender,
                    null
            );

            transactionService.createDepositTransaction(
                    amount,
                    TransactionStatus.SUCCEEDED,
                    LocalDateTime.now(),
                    sender.getUsername(),
                    receiver,
                    null
            );

            log.info("Funds transferred successfully: sender={}, receiver={}, amount={}",
                    sender.getUsername(), receiver.getUsername(), amount);
        } catch (DomainException e) {
            log.warn("Transaction failed: {}", e.getMessage());
            transactionService.createDepositTransaction(
                    amount,
                    TransactionStatus.FAILED,
                    LocalDateTime.now(),
                    sender.getUsername(),
                    receiver,
                    "Transfer failed"
            );
        }
    }

    @Transactional
    public void charge(User user, BigDecimal amount, boolean isCredit) {
        Wallet wallet = findWalletById(user.getWallet().getId());

        if (!isCredit && wallet.getBalance().compareTo(amount) < 0) {
            log.warn("Insufficient funds: user={}, balance={}, requested={}",
                    user.getUsername(), wallet.getBalance(), amount);
            throw new DomainException("Insufficient funds");
        }

        BigDecimal newBalance = isCredit ? wallet.getBalance().add(amount) : wallet.getBalance().subtract(amount);
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        log.info("{} wallet balance updated: user={}, new balance={}",
                isCredit ? "Credited" : "Debited", user.getUsername(), wallet.getBalance());
    }
}