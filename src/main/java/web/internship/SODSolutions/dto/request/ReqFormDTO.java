package web.internship.SODSolutions.dto.request;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import web.internship.SODSolutions.model.Field;

@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ReqFormDTO {
     Long id;
     String name;
     String phone;
     String email;
     boolean isAdvised;
     String companyName;
     Long fieldId;
}
