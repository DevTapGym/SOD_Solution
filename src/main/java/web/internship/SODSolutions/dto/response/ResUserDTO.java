package web.internship.SODSolutions.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResUserDTO {
    long id;
    String email;
    String name;
    String phone;
    String companyName;
    String avatar;
}
