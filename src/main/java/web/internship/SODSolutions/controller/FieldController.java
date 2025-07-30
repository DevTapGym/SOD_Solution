package web.internship.SODSolutions.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.internship.SODSolutions.dto.response.ApiResponse;
import web.internship.SODSolutions.dto.response.ResFieldDTO;
import web.internship.SODSolutions.services.FieldService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/fields")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FieldController {
    FieldService fieldService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ResFieldDTO>>> getAllFields() {
        List<ResFieldDTO> fields = fieldService.getAllFields();
        ApiResponse<List<ResFieldDTO>> response = ApiResponse.<List<ResFieldDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Fields fetched successfully")
                .data(fields)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResFieldDTO>> createField(@Valid @RequestBody ResFieldDTO request) {
        ResFieldDTO createdField = fieldService.createField(request);
        ApiResponse<ResFieldDTO> response = ApiResponse.<ResFieldDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Field created successfully")
                .data(createdField)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ResFieldDTO>> updateField(@Valid @RequestBody ResFieldDTO request) {
        ResFieldDTO updatedField = fieldService.updateField(request);
        ApiResponse<ResFieldDTO> response = ApiResponse.<ResFieldDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Field updated successfully")
                .data(updatedField)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteField(@PathVariable Long id) {
        fieldService.deleteField(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Field deleted successfully")
                .data("")
                .build();
        return ResponseEntity.ok(response);
    }
}
