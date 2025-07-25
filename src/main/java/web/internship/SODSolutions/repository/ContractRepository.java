package web.internship.SODSolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.internship.SODSolutions.model.Permission;

public interface ContractRepository extends JpaRepository<Permission, Long> {
}
