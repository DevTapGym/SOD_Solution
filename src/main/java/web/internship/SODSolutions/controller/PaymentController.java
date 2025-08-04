package web.internship.SODSolutions.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.internship.SODSolutions.dto.request.ReqPaymentDTO;
import web.internship.SODSolutions.dto.response.ApiResponse;
import web.internship.SODSolutions.dto.response.ResPaymentDTO;
import web.internship.SODSolutions.services.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;

    @GetMapping("/project/{projectId}")
    public ResponseEntity<ApiResponse<List<ResPaymentDTO>>> getPaymentsByProjectId(@PathVariable Long projectId) {
        List<ResPaymentDTO> payments = paymentService.getPaymentsByProjectId(projectId);
        ApiResponse<List<ResPaymentDTO>> response = ApiResponse.<List<ResPaymentDTO>>builder()
                .status(HttpStatus.OK.value())
                .message("Payments fetched successfully for project ID: " + projectId)
                .data(payments)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ResPaymentDTO>> createPayment(@Valid @RequestBody ReqPaymentDTO request) {
        ResPaymentDTO createdPayment = paymentService.createPayment(request);
        ApiResponse<ResPaymentDTO> response = ApiResponse.<ResPaymentDTO>builder()
                .status(HttpStatus.CREATED.value())
                .message("Payment created successfully")
                .data(createdPayment)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<ResPaymentDTO>> updatePayment(@Valid @RequestBody ReqPaymentDTO request) {
        ResPaymentDTO updatedPayment = paymentService.updatePayment(request);
        ApiResponse<ResPaymentDTO> response = ApiResponse.<ResPaymentDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Payment updated successfully")
                .data(updatedPayment)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(HttpStatus.NO_CONTENT.value())
                .message("Payment deleted successfully")
                .data("")
                .build();
        return ResponseEntity.ok(response);
    }
}
