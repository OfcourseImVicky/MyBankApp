package training.task.bank.service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import training.task.bank.entity.AccountDetailsEntity;
import training.task.bank.entity.CustomerDetailsEntity;
import training.task.bank.entity.TransactionEntity;
import training.task.bank.repository.AccountDetailsRepository;
import training.task.bank.repository.CustomerDetailsRepository;
import training.task.bank.repository.TransactionRepository;

@Service
public class MyBankService {

	@Autowired
	CustomerDetailsRepository customerDetailsRepository;

	@Autowired
	AccountDetailsRepository accountDetailsRepository;

	@Autowired
	TransactionRepository transactionRepository;

	public String registerAccount(CustomerDetailsEntity customerDetails) {
		long accountNumber = generateAccountNumber();
		boolean customerDtoFlag = saveCustomerDetails(customerDetails, accountNumber);
		boolean accountDtoFlag = saveAccountRegistrationDetails(accountNumber);
		if (customerDtoFlag && accountDtoFlag) {
			return "Registration Successful";
		} else {
			return "Registration failed";
		}
	}

	public String transferMoney(Long sourceAccountNumber, Long destinationAccountNumber, int amount, String comments) {
		AccountDetailsEntity sourceAccountDetails = findAccountByAccountNumber(sourceAccountNumber);
		AccountDetailsEntity destinationAccountDetails = findAccountByAccountNumber(destinationAccountNumber);
		if (Objects.nonNull(sourceAccountDetails) && (!ObjectUtils.isEmpty(sourceAccountDetails))
				&& Objects.nonNull(destinationAccountDetails) && (!ObjectUtils.isEmpty(destinationAccountDetails))) {
			if (sourceAccountDetails.getBalance() > amount) {
				sourceAccountDetails.setBalance(sourceAccountDetails.getBalance() - amount);
				destinationAccountDetails.setBalance(destinationAccountDetails.getBalance() + amount);
				saveAccountDetailsToDB(sourceAccountDetails);
				saveAccountDetailsToDB(destinationAccountDetails);
				saveTransaction(sourceAccountNumber, destinationAccountNumber, amount, comments);
				return "Money Transferred Successfully";
			} else {
				return "Insufficient Balance";
			}
		} else {
			return "Check Account numbers";
		}

	}

	private void saveTransaction(Long sourceAccountNumber, Long destinationAccountNumber, int transactionAmount,
			String reasonForTransaction) {
		TransactionEntity transactionEntity = new TransactionEntity();
		transactionEntity.setSourceAccountNumber(sourceAccountNumber);
		transactionEntity.setDestinationAccountNumber(destinationAccountNumber);
		transactionEntity.setTransactionAmount(transactionAmount);
		transactionEntity.setReasonForTransaction(reasonForTransaction);
		transactionEntity.setTransactionDateTime(LocalDateTime.now());
		saveTransactionDetailsToDB(transactionEntity);
	}

	private void saveTransactionDetailsToDB(TransactionEntity transactionEntity) {
		transactionRepository.save(transactionEntity);
	}

	public AccountDetailsEntity findAccountByAccountNumber(Long accountNumber) {
		return accountDetailsRepository.findByAccountNumber(accountNumber);
	}

	public boolean saveAccountRegistrationDetails(long accountNumber) {
		AccountDetailsEntity accountDetails = new AccountDetailsEntity();
		accountDetails.setAccountNumber(accountNumber);
		accountDetails.setBalance(10000);
		AccountDetailsEntity savedDetails = saveAccountDetailsToDB(accountDetails);
		if (ObjectUtils.isEmpty(savedDetails)) {
			return false;
		} else {
			return true;
		}
	}

	public boolean saveCustomerDetails(CustomerDetailsEntity customerDetails, long accountNumber) {
		customerDetails.setAccountNumber(accountNumber);
		CustomerDetailsEntity savedDetails = saveCustomerDetailsToDB(customerDetails);
		if (ObjectUtils.isEmpty(savedDetails)) {
			return false;
		} else {
			return true;
		}
	}

	public AccountDetailsEntity saveAccountDetailsToDB(AccountDetailsEntity accountDetails) {
		return accountDetailsRepository.save(accountDetails);
	}

	public CustomerDetailsEntity saveCustomerDetailsToDB(CustomerDetailsEntity customerDetails) {
		return customerDetailsRepository.save(customerDetails);
	}

	private long generateAccountNumber() {
		Random random = new Random();
		return random.nextLong();
	}

	public List<TransactionEntity> generateStatementDetails(Long accountNumber, Month month, String year) {
		List<TransactionEntity> transactionEntityList = getTransactionDetailsByAccountNumber(accountNumber);
		List<TransactionEntity> statementList = transactionEntityList.stream().filter(
				entity -> month.toString().equals(entity.getTransactionDateTime().toLocalDate().getMonth().name()))
				.filter(entity -> year.equals(String.valueOf(entity.getTransactionDateTime().toLocalDate().getYear())))
				.collect(Collectors.toList());
		return statementList;

	}

	private List<TransactionEntity> getTransactionDetailsByAccountNumber(Long accountNumber) {
		return transactionRepository.findBySourceAccountNumberOrDestinationAccountNumber(accountNumber, accountNumber);
	}
}
