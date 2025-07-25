package web.internship.SODSolutions.mapper;

import org.mapstruct.Mapper;
import web.internship.SODSolutions.dto.request.ReqUserDTO;
import web.internship.SODSolutions.dto.response.ResUserDTO;
import web.internship.SODSolutions.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(ReqUserDTO reqUserDTO);

    ResUserDTO toResUserDTO(User user);
}
