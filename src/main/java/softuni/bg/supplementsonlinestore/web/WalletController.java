package softuni.bg.supplementsonlinestore.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import softuni.bg.supplementsonlinestore.security.MetaDataAuthentication;
import softuni.bg.supplementsonlinestore.transaction.model.Transaction;
import softuni.bg.supplementsonlinestore.transaction.service.TransactionService;
import softuni.bg.supplementsonlinestore.user.model.User;
import softuni.bg.supplementsonlinestore.user.service.UserService;
import softuni.bg.supplementsonlinestore.wallet.service.WalletService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/wallet")
public class WalletController {

    private final UserService userService;
    private final WalletService walletService;
    private final TransactionService transactionService;

    public WalletController(UserService userService, WalletService walletService, TransactionService transactionService) {
        this.userService = userService;
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @GetMapping
    public ModelAndView getWallet(@AuthenticationPrincipal MetaDataAuthentication metaDataAuthentication) {
        ModelAndView modelAndView = new ModelAndView("wallet");
        User user = userService.findById(metaDataAuthentication.getId());
        List<Transaction> transactions = transactionService.findAll();
        modelAndView.addObject("user", user);
        modelAndView.addObject("transactions", transactions);
        return modelAndView;
    }

    @PutMapping("/{id}/add-funds")
    public java.lang.String addFunds(@PathVariable UUID id){
        return "redirect:/wallet";
    }
}
