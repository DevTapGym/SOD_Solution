package web.internship.SODSolutions.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import web.internship.SODSolutions.dto.request.ReqPaymentDTO;
import web.internship.SODSolutions.dto.response.ResPaymentDTO;
import web.internship.SODSolutions.mapper.PaymentMapper;
import web.internship.SODSolutions.model.Payment;
import web.internship.SODSolutions.model.ProjectPhase;
import web.internship.SODSolutions.repository.PaymentRepository;
import web.internship.SODSolutions.repository.ProjectPhaseRepository;
import web.internship.SODSolutions.repository.ProjectRepository;
import web.internship.SODSolutions.util.error.AppException;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PaymentService {
    PaymentRepository paymentRepository;
    PaymentMapper paymentMapper;
    ProjectRepository projectRepository;
    ProjectPhaseRepository projectPhaseRepository;

    public List<ResPaymentDTO> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();

        return paymentMapper.toResPaymentDTO(payments);
    }

    public List<ResPaymentDTO>  getPaymentsByProjectId(long projectId){
        if(!projectRepository.existsById(projectId)){
            throw new AppException("Project not found with id: " + projectId);
        }

        return paymentMapper.toResPaymentDTO(paymentRepository.getPaymentsByProjectPhase_Project_Id(projectId));
    }

    @Transactional
    public ResPaymentDTO createPayment(ReqPaymentDTO paymentDTO) {
        ProjectPhase existingProjectPhase = projectPhaseRepository.findById(paymentDTO.getProjectPhaseId())
                .orElseThrow(() -> new AppException("Project phase not found with id: " + paymentDTO.getProjectPhaseId()));

        paymentRepository.findByProjectPhaseId(existingProjectPhase.getId())
                .ifPresent(p -> { throw new AppException("This project phase already has a payment."); });

        Payment payment = paymentMapper.toPayment(paymentDTO);
        payment.setProjectPhase(existingProjectPhase);
        paymentRepository.save(payment);

        return paymentMapper.toResPaymentDTO(payment);
    }


    @Transactional
    public ResPaymentDTO updatePayment(ReqPaymentDTO paymentDTO) {
        Payment existingPayment = paymentRepository.findById(paymentDTO.getId())
                .orElseThrow(() -> new AppException("Payment not found with id: " + paymentDTO.getId()));

        ProjectPhase existingProjectPhase = projectPhaseRepository.findById(paymentDTO.getProjectPhaseId())
                .orElseThrow(() -> new AppException("Project phase not found with id: " + paymentDTO.getProjectPhaseId()));

        if (!existingPayment.getProjectPhase().getId().equals(existingProjectPhase.getId())) {
            paymentRepository.findByProjectPhaseId(existingProjectPhase.getId())
                    .ifPresent(p -> { throw new AppException("This project phase already has a payment."); });
        }

        existingPayment.setProjectPhase(existingProjectPhase);
        existingPayment.setPaymentDate(paymentDTO.getPaymentDate());
        existingPayment.setPaymentStatus(paymentDTO.getPaymentStatus());
        existingPayment.setTransactionId(paymentDTO.getTransactionId());
        paymentRepository.save(existingPayment);

        return paymentMapper.toResPaymentDTO(existingPayment);
    }


    @Transactional
    public void deletePayment(long paymentId){
        if(!paymentRepository.existsById(paymentId)){
            throw new AppException("Payment not found with id: " + paymentId);
        }

        paymentRepository.deleteById(paymentId);
    }

}
