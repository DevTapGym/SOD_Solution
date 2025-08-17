package web.internship.SODSolutions.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ReqForgotPasswordDTO {
    String code;
    String password;
    String email;
}
