package web.internship.SODSolutions.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UpdateFormDTO {
    Long id;

    @JsonProperty("isAdvised")
    boolean isAdvised;
}
