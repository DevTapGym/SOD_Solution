package web.internship.SODSolutions.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.internship.SODSolutions.dto.request.ReqContractDTO;
import web.internship.SODSolutions.dto.response.ApiResponse;
import web.internship.SODSolutions.dto.response.ResContractDTO;
import web.internship.SODSolutions.services.ContractService;


import java.util.List;

@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class ContractController {
    ContractService contractService;

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<List<ResContractDTO>>> getContractsByEmail() {
        List<ResContractDTO> contracts = contractService.getContractsByEmail();
        ApiResponse<List<ResContractDTO>> response = ApiResponse.<List<ResContractDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Contracts fetched successfully")
                .data(contracts)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<ResContractDTO>>> getContractsByProjectId(@PathVariable Long projectId) {
        List<ResContractDTO> contracts = contractService.getContractByProjectId(projectId);
        ApiResponse<List<ResContractDTO>> response = ApiResponse.<List<ResContractDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Contracts fetched successfully for project ID: " + projectId)
                .data(contracts)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResContractDTO>> createContract(@Valid @RequestBody ReqContractDTO request) {
        ResContractDTO createdContract = contractService.createContract(request);
        ApiResponse<ResContractDTO> response = ApiResponse.<ResContractDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Contract created successfully")
                .data(createdContract)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ResContractDTO>> updateContract(@Valid @RequestBody ReqContractDTO request) {
        ResContractDTO updatedContract = contractService.updateContract(request);
        ApiResponse<ResContractDTO> response = ApiResponse.<ResContractDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Contract updated successfully")
                .data(updatedContract)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteContract(@PathVariable Long id) {
        contractService.deleteContract(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Contract deleted successfully")
                .data("")
                .build();
        return ResponseEntity.ok(response);
    }
}