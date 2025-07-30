package web.internship.SODSolutions.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import web.internship.SODSolutions.model.Project;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ReqContractDTO {
    Instant createdAt;
    Instant updatedAt;
    Long id;
    String contractNumber;
    LocalDate signedDate;
    BigDecimal totalAmount;
    String contractFile;
    Long projectId;
}
