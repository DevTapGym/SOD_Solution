package web.internship.SODSolutions.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import web.internship.SODSolutions.model.common.PhaseStatus;


import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResProjectPhaseDTO {
    Long id;
    Long projectId;
    String phaseName;
    String description;
    LocalDate startDate;
    LocalDate endDate;
    PhaseStatus status;
    BigDecimal amountDue;
}