package web.internship.SODSolutions.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.internship.SODSolutions.dto.request.ReqProjectDTO;
import web.internship.SODSolutions.dto.response.ResProjectDTO;
import web.internship.SODSolutions.mapper.ProjectMapper;
import web.internship.SODSolutions.model.Project;
import web.internship.SODSolutions.repository.FieldRepository;
import web.internship.SODSolutions.repository.ProjectRepository;
import web.internship.SODSolutions.repository.UserRepository;
import web.internship.SODSolutions.util.error.AppException;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ProjectService {
    ProjectRepository projectRepository;
    ProjectMapper projectMapper;
    UserRepository userRepository;
    FieldRepository fieldRepository;

    public List<ResProjectDTO> getAllProjects() {
        return projectMapper.toResProjectDTO(projectRepository.findAll());
    }

    public List<ResProjectDTO> getProjectByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("User not found with id :" + email);
        }

        return projectMapper.toResProjectDTO(projectRepository.getProjectByUser_Email(email));
    }

    @Transactional
    public ResProjectDTO createProject(ReqProjectDTO request) {
        if(!fieldRepository.existsById(request.getFieldId())) {
            throw new AppException("Field not found with id :" + request.getFieldId());
        }

        if (!userRepository.existsById(request.getUserId())) {
            throw new RuntimeException("User not found with id :" + request.getUserId());
        }

        // Check if startDate is before endDate
        if (request.getStartDate() != null && request.getEndDate() != null
                && !request.getStartDate().isBefore(request.getEndDate())) {
            throw new AppException("Start date must be before end date");
        }

        Project project = projectMapper.toProject(request);
        project.setField(fieldRepository.getFieldById((request.getFieldId())));
        project.setUser(userRepository.getUserById((request.getUserId())));

        projectRepository.save(project);
        return projectMapper.toResProjectDTO(projectRepository.save(project));
    }

    @Transactional
    public ResProjectDTO updateProject(ReqProjectDTO request) {
        Project existingProject = projectRepository.findById(request.getId())
                .orElseThrow(()-> new AppException("Project not found with id:" + request.getId()));

        // Check if startDate is before endDate
        if (request.getStartDate() != null && request.getEndDate() != null
                && !request.getStartDate().isBefore(request.getEndDate())) {
            throw new AppException("Start date must be before end date");
        }

        existingProject.setName(request.getName());
        existingProject.setDescription(request.getDescription());
        existingProject.setStartDate(request.getStartDate());
        existingProject.setEndDate(request.getEndDate());
        existingProject.setStatus(request.getStatus());
        projectRepository.save(existingProject);

        return projectMapper.toResProjectDTO(existingProject);
    }

    @Transactional
    public void deleteProject(Long projectId) {
        if(!projectRepository.existsById(projectId)) {
            throw new RuntimeException("Project not found with id :" + projectId);
        }

        projectRepository.deleteById(projectId);
    }
}