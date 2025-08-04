package web.internship.SODSolutions.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.internship.SODSolutions.dto.request.ReqLoginDTO;
import web.internship.SODSolutions.dto.response.ApiResponse;
import web.internship.SODSolutions.dto.response.ResLoginDTO;
import web.internship.SODSolutions.dto.response.ResUserDTO;
import web.internship.SODSolutions.services.UserService;
import web.internship.SODSolutions.util.error.AppException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserController {

    final UserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse<List<ResUserDTO>>> getAllUsers() {
        List<ResUserDTO> res = userService.getAllUsers();
        ApiResponse<List<ResUserDTO>> response = ApiResponse.<List<ResUserDTO>>builder()
                .status(200)
                .message("Fetch data successfully")
                .data(res)
                .build();

        return ResponseEntity.ok(response);
    }



}
