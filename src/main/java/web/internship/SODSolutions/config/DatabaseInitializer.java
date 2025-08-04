package web.internship.SODSolutions.config;

import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import web.internship.SODSolutions.model.Permission;
import web.internship.SODSolutions.model.Role;
import web.internship.SODSolutions.model.User;
import web.internship.SODSolutions.repository.PermissionRepository;
import web.internship.SODSolutions.repository.RoleRepository;
import web.internship.SODSolutions.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class DatabaseInitializer implements CommandLineRunner {
    PermissionRepository permissionRepository;
    RoleRepository roleRepository;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public DatabaseInitializer(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> START INIT DATABASE");

        // Kiểm tra dữ liệu hiện có
        if (permissionRepository.count() == 0) {
            List<Permission> permissions = Arrays.asList(
                    new Permission(null, "VIEW_ALL_USER", "/api/v1/user/getAll", "GET", "USER"),
                    new Permission(null, "CREATE_USER", "/api/v1/auth", "POST", "USER"),
                    new Permission(null, "UPDATE_USER", "/api/v1/account", "PUT", "USER"),

                    new Permission(null, "VIEW_CONTRACT_BY_EMAIL", "/api/v1/contracts/email/{email}", "GET", "CONTRACT"),
                    new Permission(null, "VIEW_CONTRACT_BY_PROJECT", "/api/v1/contracts/project/{projectId}", "GET", "CONTRACT"),
                    new Permission(null, "CREATE_CONTRACT", "/api/v1/contracts", "POST", "CONTRACT"),
                    new Permission(null, "UPDATE_CONTRACT", "/api/v1/contracts", "PUT", "CONTRACT"),
                    new Permission(null, "DELETE_CONTRACT", "/api/v1/contracts/{id}", "DELETE", "CONTRACT"),

                    new Permission(null, "VIEW_ALL_PROJECT", "/api/v1/projects/getAll", "GET", "PROJECT"),
                    new Permission(null, "VIEW_PROJECT_BY_EMAIL", "/api/v1/projects/{email}", "GET", "PROJECT"),
                    new Permission(null, "CREATE_PROJECT", "/api/v1/projects", "POST", "PROJECT"),
                    new Permission(null, "UPDATE_PROJECT", "/api/v1/projects", "PUT", "PROJECT"),
                    new Permission(null, "DELETE_PROJECT", "/api/v1/projects/{id}", "DELETE", "PROJECT"),

                    new Permission(null, "GET_PROJECT_PHASE_BY_PROJECT", "/api/v1/project-phases/project/{projectId}", "GET", "PROJECT-PHASE"),
                    new Permission(null, "CREATE_PROJECT_PHASE", "/api/v1/project-phases", "POST", "PROJECT-PHASE"),
                    new Permission(null, "UPDATE_PROJECT_PHASE", "/api/v1/project-phases", "PUT", "PROJECT-PHASE"),
                    new Permission(null, "DELETE_PROJECT_PHASE", "/api/v1/project-phases/{id}", "DELETE", "PROJECT-PHASE"),

                    new Permission(null, "VIEW_ALL_FIELD", "/api/v1/fields/getAll", "GET", "FIELD"),
                    new Permission(null, "CREATE_FIELD", "/api/v1/fields", "POST", "FIELD"),
                    new Permission(null, "UPDATE_FIELD", "/api/v1/fields", "PUT", "FIELD"),
                    new Permission(null, "DELETE_FIELD", "/api/v1/fields/{id}", "DELETE", "FIELD"),

                    new Permission(null, "VIEW_PAYMENT_BY_PROJECT", "/api/v1/payments/project/{projectId}", "GET", "PAYMENT"),
                    new Permission(null, "CREATE_PAYMENT", "/api/v1/payments", "POST", "PAYMENT"),
                    new Permission(null, "UPDATE_PAYMENT", "/api/v1/payments", "PUT", "PAYMENT"),
                    new Permission(null, "DELETE_PAYMENT", "/api/v1/payments/{id}", "DELETE", "PAYMENT")
            );
            permissionRepository.saveAll(permissions);
        }

        if (roleRepository.count() == 0) {
            List<Permission> allPermissions = permissionRepository.findAll();

            // Tạo vai trò ADMIN với tất cả quyền
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setDescription("Administrator with full permissions");
            adminRole.setPermissions(allPermissions);
            roleRepository.save(adminRole);

            // Tạo vai trò USER với các quyền được chọn
            Set<String> userPermissionNames = Set.of(
                    "VIEW_CONTRACT_BY_EMAIL",
                    "VIEW_CONTRACT_BY_PROJECT",
                    "VIEW_PROJECT_BY_EMAIL",
                    "GET_PROJECT_PHASE_BY_PROJECT",
                    "VIEW_PAYMENT_BY_PROJECT"
            );
            List<Permission> userPermissions = allPermissions.stream()
                    .filter(p -> userPermissionNames.contains(p.getName()))
                    .collect(Collectors.toList());
            Role userRole = new Role();
            userRole.setName("USER");
            userRole.setDescription("Regular user with limited permissions");
            userRole.setPermissions(userPermissions);
            roleRepository.save(userRole);
        }

        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setName("Admin User");
            admin.setActive(true);
            admin.setRole(roleRepository.findByName("ADMIN"));
            userRepository.save(admin);

            User user = new User();
            user.setEmail("user@gmail.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setName("Regular User");
            user.setActive(true);
            user.setRole(roleRepository.findByName("USER"));
            userRepository.save(user);
        }

        System.out.println(">>> END INIT DATABASE");
    }
}
