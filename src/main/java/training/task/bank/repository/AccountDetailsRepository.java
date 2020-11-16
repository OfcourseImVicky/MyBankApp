package training.task.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.task.bank.entity.AccountDetailsEntity;

@Repository
public interface AccountDetailsRepository extends JpaRepository<AccountDetailsEntity, Long> {

	AccountDetailsEntity findByAccountNumber(Long accountNumber);

}
