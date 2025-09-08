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

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProjectPhaseService {
    ProjectPhaseRepository projectPhaseRepository;
    ProjectPhaseMapper projectPhaseMapper;
    ProjectRepository projectRepository;

    public List<ResProjectPhaseDTO> getProjectPhasesByProjectId(Long projectId) {
        if (!projectRepository.existsById(projectId)) {
            throw new AppException("Project not found with id: " + projectId);
        }
        return projectPhaseMapper.toResProjectPhaseDTO(projectPhaseRepository.getProjectPhasesByProject_Id(projectId));
    }


    @Transactional
    public ResProjectPhaseDTO createProjectPhase(ReqProjectPhaseDTO projectPhaseDTO) {
        Project project = projectRepository.findById(projectPhaseDTO.getProjectId())
                .orElseThrow(() -> new AppException("Project not found with id: " + projectPhaseDTO.getProjectId()));

        long phaseCount = projectPhaseRepository.countByProject_Id(project.getId());
        if (phaseCount >= 5) {
            throw new AppException("A project can only have up to 5 phases");
        }

        validatePhaseDates(projectPhaseDTO.getStartDate(), projectPhaseDTO.getEndDate(), project);

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

        // Validate start and end dates
        validatePhaseDates(projectPhaseDTO.getStartDate(), projectPhaseDTO.getEndDate(), project);

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
        if (!projectPhaseRepository.existsById(projectPhaseId)) {
            throw new AppException("Project phase not found with id: " + projectPhaseId);
        }

        projectPhaseRepository.deleteById(projectPhaseId);
    }

    private void validatePhaseDates(LocalDate startDate, LocalDate endDate, Project project) {
        // Check if start date is before end date
        if (startDate != null && endDate != null && !startDate.isBefore(endDate)) {
            throw new AppException("Start date must be before end date");
        }

        // Check if phase dates are within project dates
        if (project.getStartDate() != null && startDate != null && startDate.isBefore(project.getStartDate())) {
            throw new AppException("Phase start date cannot be before project start date: " + project.getStartDate());
        }

        if (project.getEndDate() != null && endDate != null && endDate.isAfter(project.getEndDate())) {
            throw new AppException("Phase end date cannot be after project end date: " + project.getEndDate());
        }
    }
}