package web.internship.SODSolutions.mapper;

import org.mapstruct.*;
import web.internship.SODSolutions.dto.request.ReqProjectDTO;
import web.internship.SODSolutions.dto.response.ResProjectDTO;
import web.internship.SODSolutions.model.Project;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "field", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    @Mapping(target = "projectPhases", ignore = true)
    Project toProject(ReqProjectDTO reqProjectDTO);

    ResProjectDTO toResProjectDTO(Project project);

    List<ResProjectDTO> toResProjectDTO(List<Project> projects);

}