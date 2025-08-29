package web.internship.SODSolutions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import web.internship.SODSolutions.dto.request.ReqFormDTO;
import web.internship.SODSolutions.dto.response.ResFormDTO;
import web.internship.SODSolutions.model.Form;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FormMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "field", ignore = true)
    Form toForm(ReqFormDTO reqFormDTO);

    @Mapping(source = "field.fieldName", target = "fieldName")
    @Mapping(source = "advised",target = "isAdvised")
    ResFormDTO toResFormDTO(Form form);

    List<ResFormDTO> toResFormDTO(List<Form> forms);
}
