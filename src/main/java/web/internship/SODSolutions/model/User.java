package web.internship.SODSolutions.model;

import jakarta.persistence.*;
import lombok.*;
import web.internship.SODSolutions.model.common.Auditable;

import java.time.Instant;

@Entity
@Getter
@Setter
@ToString
@Data
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String phone;

    private String address;

    private String avatar;

    private boolean isActive;

    private String codeId;

    private Instant codeExpired;

}
