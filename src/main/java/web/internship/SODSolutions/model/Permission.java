package web.internship.SODSolutions.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Ví dụ: CREATE_ACCOUNT
    private String apiPath; // Ví dụ: /api/v1/account
    private String method; // Ví dụ: POST
    private String module; // Ví dụ: ACCOUNTS
}
