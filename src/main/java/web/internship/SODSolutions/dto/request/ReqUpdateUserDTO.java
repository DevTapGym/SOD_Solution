package web.internship.SODSolutions.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ReqUpdateUserDTO {
    String name;
    String phone;
    String address;
    String avatar;
    String companyName;
}