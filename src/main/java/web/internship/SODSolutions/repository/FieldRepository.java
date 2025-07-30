package web.internship.SODSolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.internship.SODSolutions.model.Field;

public interface FieldRepository extends JpaRepository<Field, Long> {
    Field getFieldById(Long id);
}