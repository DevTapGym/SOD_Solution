package web.internship.SODSolutions.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.internship.SODSolutions.dto.request.ReqFormDTO;
import web.internship.SODSolutions.dto.request.UpdateFormDTO;
import web.internship.SODSolutions.dto.response.ApiResponse;
import web.internship.SODSolutions.dto.response.ResFormDTO;
import web.internship.SODSolutions.services.FormService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FormController {

    FormService formService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ResFormDTO>>> getAllForms() {
        List<ResFormDTO> forms = formService.getAllForms();
        ApiResponse<List<ResFormDTO>> response = ApiResponse.<List<ResFormDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Forms fetched successfully")
                .data(forms)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ResFormDTO>> getFormById(@PathVariable Long id) {
        ResFormDTO form = formService.getFormById(id);
        ApiResponse<ResFormDTO> response = ApiResponse.<ResFormDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Form fetched successfully with id: " + id)
                .data(form)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResFormDTO>> createForm(@RequestBody ReqFormDTO reqFormDTO) {
        ResFormDTO createdForm = formService.createForm(reqFormDTO);
        ApiResponse<ResFormDTO> response = ApiResponse.<ResFormDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Form created successfully")
                .data(createdForm)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ResFormDTO>> updateForm(@RequestBody UpdateFormDTO updateFormDTO) {
        ResFormDTO createdForm = formService.updateForm(updateFormDTO);
        ApiResponse<ResFormDTO> response = ApiResponse.<ResFormDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Form updated successfully")
                .data(createdForm)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteForm(@PathVariable Long id) {
        formService.deleteForm(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Form deleted successfully")
                .data("")
                .build();
        return ResponseEntity.ok(response);
    }
}
