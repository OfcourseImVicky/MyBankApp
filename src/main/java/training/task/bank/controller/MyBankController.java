package training.task.bank.controller;

import java.time.Month;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import training.task.bank.entity.CustomerDetailsEntity;
import training.task.bank.entity.TransactionEntity;
import training.task.bank.service.MyBankService;

@RestController
@RequestMapping("/myBank")
public class MyBankController {

	@Autowired
	MyBankService myBankService;

	@PostMapping("/register")
	public String createRegistration(@RequestBody CustomerDetailsEntity customerDetails) {
		return myBankService.registerAccount(customerDetails);
	}

	@PostMapping("/transfer")
	public void transferMoney(@RequestParam Long sourceAccountNumber, @RequestParam Long destinationAccountNumber,
			@RequestParam int amount, @RequestParam String comments) {
		myBankService.transferMoney(sourceAccountNumber, destinationAccountNumber, amount, comments);
	}

	@GetMapping("/generateStatement")
	public List<TransactionEntity> generateStatement(@RequestParam Long accountNumber, @RequestParam Month month,
			@RequestParam String year) {
		return myBankService.generateStatementDetails(accountNumber, month, year);
	}
}
