package web.internship.SODSolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.internship.SODSolutions.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}