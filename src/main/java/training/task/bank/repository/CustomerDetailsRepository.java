package training.task.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import training.task.bank.entity.CustomerDetailsEntity;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetailsEntity, Long> {

}
