package web.internship.SODSolutions.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.internship.SODSolutions.model.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> getPaymentsByProjectPhase_Project_Id(Long projectPhaseProjectId);
}