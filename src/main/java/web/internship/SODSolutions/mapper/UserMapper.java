package web.internship.SODSolutions.mapper;

import org.mapstruct.Mapper;
import web.internship.SODSolutions.dto.request.ReqUserDTO;
import web.internship.SODSolutions.dto.response.ResUserDTO;
import web.internship.SODSolutions.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(ReqUserDTO reqUserDTO);

    ResUserDTO toResUserDTO(User user);

    List<ResUserDTO> toResListUserDTO(List<User> userList);
}
