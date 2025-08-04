package web.internship.SODSolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.internship.SODSolutions.model.Contract;

import java.util.List;


public interface ContractRepository extends JpaRepository<Contract, Long> {
    List<Contract> getContractsByProject_Id(Long projectId);

    List<Contract> getContractsByProject_User_Email(String email);

    Contract getContractsById(Long id);
}
