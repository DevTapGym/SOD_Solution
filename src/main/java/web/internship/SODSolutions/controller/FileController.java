package web.internship.SODSolutions.controller;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import web.internship.SODSolutions.dto.response.ApiResponse;
import web.internship.SODSolutions.dto.response.ResUploadFileDTO;
import web.internship.SODSolutions.services.FileService;
import web.internship.SODSolutions.util.error.AppException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class FileController {

    FileService fileService;

    @PostMapping("/files")
    public ResponseEntity<ApiResponse<ResUploadFileDTO>> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("folder") String folder) throws URISyntaxException, IOException, AppException {

        // ✅ Validate file
        if (file == null || file.isEmpty()) {
            throw new AppException("File is empty. Please upload a file.");
        }

        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");

        boolean isValid = allowedExtensions.stream()
                .anyMatch(ext -> fileName.toLowerCase().endsWith(ext));

        if (!isValid) {
            throw new AppException("Invalid file extension. Only allows: " + allowedExtensions);
        }

        // ✅ Tạo folder nếu chưa tồn tại
        fileService.createUploadFolder(fileService.getBaseURI() + folder);

        // ✅ Lưu file và lấy URL public
        String fileUrl = fileService.store(file, folder);

        // ✅ Tách fileName từ URL
        String savedFileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        // ✅ Build response DTO
        ResUploadFileDTO res = ResUploadFileDTO.builder()
                .fileName(savedFileName)
                .fileUrl(fileUrl)
                .uploadedAt(Instant.now())
                .build();

        ApiResponse<ResUploadFileDTO> response = ApiResponse.<ResUploadFileDTO>builder()
                .status(HttpStatus.OK.value())
                .message("Upload file successful")
                .data(res)
                .build();

        return ResponseEntity.ok(response);
    }
}
