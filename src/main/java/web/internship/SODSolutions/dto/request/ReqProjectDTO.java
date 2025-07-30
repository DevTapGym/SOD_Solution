package web.internship.SODSolutions.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import web.internship.SODSolutions.model.common.ProjectStatus;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ReqProjectDTO {
    Long id;
    String name;
    String description;
    LocalDate startDate;
    LocalDate endDate;
    ProjectStatus status;
    Long userId;
    Long fieldId;
}
