package web.internship.SODSolutions.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResFormDTO {
    Long id;
    String name;
    String phone;
    String email;
    boolean isAdvised;
    String companyName;
    String fieldName;
}
