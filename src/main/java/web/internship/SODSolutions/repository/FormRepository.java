package web.internship.SODSolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.internship.SODSolutions.model.Form;

public interface FormRepository extends JpaRepository<Form, Long> {
    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);
}