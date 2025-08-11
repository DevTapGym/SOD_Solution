package web.internship.SODSolutions.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.internship.SODSolutions.dto.response.ApiResponse;
import web.internship.SODSolutions.model.Form;
import web.internship.SODSolutions.services.FormService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/forms")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FormController {

    FormService formService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<Form>>> getAllForms() {
        List<Form> forms = formService.getAllForms();
        ApiResponse<List<Form>> response = ApiResponse.<List<Form>>builder()
                .status(HttpStatus.OK.value())
                .message("Forms fetched successfully")
                .data(forms)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Form>> getFormById(@PathVariable Long id) {
        Form form = formService.getFormById(id);
        ApiResponse<Form> response = ApiResponse.<Form>builder()
                .status(HttpStatus.OK.value())
                .message("Form fetched successfully with id: " + id)
                .data(form)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Form>> createForm(@RequestBody Form form) {
        Form createdForm = formService.createForm(form);
        ApiResponse<Form> response = ApiResponse.<Form>builder()
                .status(HttpStatus.CREATED.value())
                .message("Form created successfully")
                .data(createdForm)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
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
