package web.internship.SODSolutions.mapper;

import org.mapstruct.*;
import web.internship.SODSolutions.dto.request.ReqProjectPhaseDTO;
import web.internship.SODSolutions.dto.response.ResProjectPhaseDTO;
import web.internship.SODSolutions.model.ProjectPhase;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectPhaseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "project", ignore = true)
    ProjectPhase toProjectPhase(ReqProjectPhaseDTO reqProjectPhaseDTO);

    ResProjectPhaseDTO toResProjectPhaseDTO(ProjectPhase projectPhase);

    List<ResProjectPhaseDTO> toResProjectPhaseDTO(List<ProjectPhase> projectPhaseList);
}