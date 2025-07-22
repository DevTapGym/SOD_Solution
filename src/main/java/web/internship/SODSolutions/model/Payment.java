package web.internship.SODSolutions.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.internship.SODSolutions.model.common.Auditable;
import web.internship.SODSolutions.model.common.PaymentStatus;


import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payments")
public class Payment extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "phase_id", nullable = false)
    private ProjectPhase projectPhase;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('pending', 'completed', 'failed') DEFAULT 'pending'")
    private PaymentStatus paymentStatus;

    @Column(name = "transaction_id")
    private String transactionId;
}
