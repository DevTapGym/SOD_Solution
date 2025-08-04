package web.internship.SODSolutions.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import web.internship.SODSolutions.dto.request.ReqProjectDTO;
import web.internship.SODSolutions.dto.response.ResProjectDTO;
import web.internship.SODSolutions.model.Project;
import web.internship.SODSolutions.model.common.ProjectStatus;


import java.util.List;

@Mapper(componentModel = "spring")
public interface ProjectMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "field", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    @Mapping(target = "projectPhases", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "stringToProjectStatus")
    Project toProject(ReqProjectDTO reqProjectDTO);

    @Mapping(target = "status", source = "status", qualifiedByName = "projectStatusToString")
    ResProjectDTO toResProjectDTO(Project project);

    List<ResProjectDTO> toResProjectDTO(List<Project> projects);

    @Named("stringToProjectStatus")
    default ProjectStatus stringToProjectStatus(String status) {
        if (status == null) {
            return null;
        }
        try {
            return ProjectStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid project status: " + status);
        }
    }

    @Named("projectStatusToString")
    default String projectStatusToString(ProjectStatus status) {
        return status != null ? status.name() : null;
    }
}