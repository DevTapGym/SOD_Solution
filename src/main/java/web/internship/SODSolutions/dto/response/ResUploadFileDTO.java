package web.internship.SODSolutions.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ResUploadFileDTO {
    String fileName;
    String fileUrl;
    Instant uploadedAt;
}
