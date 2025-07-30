package web.internship.SODSolutions.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.internship.SODSolutions.dto.request.ReqProjectPhaseDTO;
import web.internship.SODSolutions.dto.response.ResProjectPhaseDTO;
import web.internship.SODSolutions.mapper.ProjectPhaseMapper;
import web.internship.SODSolutions.model.Project;
import web.internship.SODSolutions.model.ProjectPhase;
import web.internship.SODSolutions.repository.ProjectPhaseRepository;
import web.internship.SODSolutions.repository.ProjectRepository;
import web.internship.SODSolutions.util.error.AppException;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProjectPhaseService {
    ProjectPhaseRepository projectPhaseRepository;
    ProjectPhaseMapper projectPhaseMapper;
    ProjectRepository projectRepository;

    public List<ResProjectPhaseDTO> getProjectPhasesByProjectId(Long projectId) {
        if(!projectRepository.existsById(projectId)) {
            throw new AppException("Project not found with id: " + projectId);
        }

        return projectPhaseMapper.toResProjectPhaseDTO(projectPhaseRepository.getProjectPhasesByProject_Id(projectId));
    }

    @Transactional
    public ResProjectPhaseDTO createProjectPhase(ReqProjectPhaseDTO projectPhaseDTO) {
        Project project = projectRepository.findById(projectPhaseDTO.getProjectId())
                .orElseThrow(() -> new AppException("Project not found with id: " + projectPhaseDTO.getProjectId()));

        ProjectPhase projectPhase = projectPhaseMapper.toProjectPhase(projectPhaseDTO);
        projectPhase.setProject(project);
        projectPhaseRepository.save(projectPhase);

        return projectPhaseMapper.toResProjectPhaseDTO(projectPhase);
    }

    @Transactional
    public ResProjectPhaseDTO updateProjectPhase(ReqProjectPhaseDTO projectPhaseDTO) {
        Project project = projectRepository.findById(projectPhaseDTO.getProjectId())
                .orElseThrow(() -> new AppException("Project not found with id: " + projectPhaseDTO.getProjectId()));

        ProjectPhase projectPhaseExisting = projectPhaseRepository.findById(projectPhaseDTO.getId())
                .orElseThrow(() -> new AppException("Project phase not found with id: " + projectPhaseDTO.getId()));

        projectPhaseExisting.setProject(project);
        projectPhaseExisting.setPhaseName(projectPhaseDTO.getPhaseName());
        projectPhaseExisting.setDescription(projectPhaseDTO.getDescription());
        projectPhaseExisting.setAmountDue(projectPhaseDTO.getAmountDue());
        projectPhaseExisting.setEndDate(projectPhaseDTO.getEndDate());
        projectPhaseExisting.setStartDate(projectPhaseDTO.getStartDate());
        projectPhaseExisting.setStatus(projectPhaseDTO.getStatus());
        projectPhaseRepository.save(projectPhaseExisting);

        return projectPhaseMapper.toResProjectPhaseDTO(projectPhaseExisting);
    }

    @Transactional
    public void deleteProjectPhase(Long projectPhaseId) {
        if(!projectPhaseRepository.existsById(projectPhaseId)) {
            throw new AppException("Project phase not found with id: " + projectPhaseId);
        }

        projectPhaseRepository.deleteById(projectPhaseId);
    }
}
