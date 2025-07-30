package web.internship.SODSolutions.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.internship.SODSolutions.dto.request.ReqProjectPhaseDTO;
import web.internship.SODSolutions.dto.response.ApiResponse;
import web.internship.SODSolutions.dto.response.ResProjectPhaseDTO;
import web.internship.SODSolutions.services.ProjectPhaseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/project-phases")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ProjectPhaseController {
    ProjectPhaseService projectPhaseService;

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<ResProjectPhaseDTO>>> getProjectPhasesByProjectId(@PathVariable Long projectId) {
        List<ResProjectPhaseDTO> projectPhases = projectPhaseService.getProjectPhasesByProjectId(projectId);
        ApiResponse<List<ResProjectPhaseDTO>> response = ApiResponse.<List<ResProjectPhaseDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Project phases fetched successfully for project ID: " + projectId)
                .data(projectPhases)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResProjectPhaseDTO>> createProjectPhase(@Valid @RequestBody ReqProjectPhaseDTO request) {
        ResProjectPhaseDTO createdProjectPhase = projectPhaseService.createProjectPhase(request);
        ApiResponse<ResProjectPhaseDTO> response = ApiResponse.<ResProjectPhaseDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Project phase created successfully")
                .data(createdProjectPhase)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ResProjectPhaseDTO>> updateProjectPhase(@Valid @RequestBody ReqProjectPhaseDTO request) {
        ResProjectPhaseDTO updatedProjectPhase = projectPhaseService.updateProjectPhase(request);
        ApiResponse<ResProjectPhaseDTO> response = ApiResponse.<ResProjectPhaseDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Project phase updated successfully")
                .data(updatedProjectPhase)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProjectPhase(@PathVariable Long id) {
        projectPhaseService.deleteProjectPhase(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Project phase deleted successfully")
                .data("")
                .build();
        return ResponseEntity.ok(response);
    }
}
