package web.internship.SODSolutions.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import web.internship.SODSolutions.model.common.ProjectStatus;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResProjectDTO {
    Instant createdAt;
    Instant updatedAt;
    String createdBy;
    String updatedBy;
    Long id;
    String name;
    String description;
    LocalDate startDate;
    LocalDate endDate;
    int rating;
    String review;
    ProjectStatus status;
}