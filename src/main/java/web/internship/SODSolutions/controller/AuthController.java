package web.internship.SODSolutions.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.internship.SODSolutions.dto.request.ReqChangePasswordRetryDTO;
import web.internship.SODSolutions.dto.request.ReqCheckCodeDTO;
import web.internship.SODSolutions.dto.request.ReqLoginDTO;
import web.internship.SODSolutions.dto.request.ReqUserDTO;
import web.internship.SODSolutions.dto.response.ApiResponse;
import web.internship.SODSolutions.dto.response.ResLoginDTO;
import web.internship.SODSolutions.dto.response.ResUserDTO;
import web.internship.SODSolutions.services.AuthService;
import web.internship.SODSolutions.util.error.AppException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AuthController {

    final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<ResLoginDTO>> login(@RequestBody ReqLoginDTO rqLogin) throws AppException {
        ResLoginDTO res = this.authService.login(rqLogin);
        ApiResponse<ResLoginDTO> response = ApiResponse.<ResLoginDTO>builder()
                .status(200)
                .message("Login successful")
                .data(res)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<ResUserDTO>> register(@RequestBody ReqUserDTO rqUser) throws AppException {
        ResUserDTO res = this.authService.register(rqUser);
        ApiResponse<ResUserDTO> response = ApiResponse.<ResUserDTO>builder()
                .status(200)
                .message("Register successful")
                .data(res)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/check-code")
    public ResponseEntity<ApiResponse<ResLoginDTO>> checkCode(@RequestBody ReqCheckCodeDTO req) throws AppException {
        ResLoginDTO dto = authService.checkCode(req.getEmail(), req.getCode());
        ApiResponse<ResLoginDTO> response = ApiResponse.<ResLoginDTO>builder()
                .status(200)
                .message("Check code successful")
                .data(dto)
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-code")
    public ResponseEntity<ApiResponse<String>> resendCode(@RequestBody ReqCheckCodeDTO req) throws AppException {
        this.authService.resendCode(req.getEmail());
        ApiResponse<String> response = ApiResponse.<String>builder()
                .status(200)
                .message("Resend code successful")
                .data("OK")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<ResUserDTO>> getCurrentUser() throws AppException {
        ResUserDTO res = this.authService.getCurrentUser();
        ApiResponse<ResUserDTO> response = ApiResponse.<ResUserDTO>builder()
                .status(200)
                .message("Get current user successful")
                .data(res)
                .build();
        return ResponseEntity.ok(response);
    }


    @PostMapping("/retry-password")
    public ResponseEntity<ApiResponse<ResUserDTO>> retryPassword(@RequestParam("email") String email) throws AppException {
        ResUserDTO res = this.authService.retryPassword(email);
        ApiResponse<ResUserDTO> response = ApiResponse.<ResUserDTO>builder()
                .status(200)
                .message("Retry password successful")
                .data(res)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-password-retry")
    public ResponseEntity<ApiResponse<ResUserDTO>> changePasswordRetry(@RequestBody ReqChangePasswordRetryDTO req) throws AppException {
        ResUserDTO res = this.authService.changePasswordRetry(req.getEmail(), req.getCode(), req.getPassword());
        ApiResponse<ResUserDTO> response = ApiResponse.<ResUserDTO>builder()
                .status(200)
                .message("Change password retry successful")
                .data(res)
                .build();
        return ResponseEntity.ok(response);
    }
}
