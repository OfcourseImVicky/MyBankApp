package training.task.bank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.task.bank.entity.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
	List<TransactionEntity> findBySourceAccountNumberOrDestinationAccountNumber(Long sourceAccountNumber,
			Long destinationAccountNumber);
}
