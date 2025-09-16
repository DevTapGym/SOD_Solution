package web.internship.SODSolutions.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isAdvised")
    boolean isAdvised;
    String companyName;
    String fieldName;
    boolean hasAccount;
}
