package web.internship.SODSolutions.config;

import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
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
                    new Permission(null, "CREATE_USER", "/api/v1/auth", "POST", "USER"),
                    new Permission(null, "VIEW_USER", "/api/v1/account/{id}", "GET", "USER"),
                    new Permission(null, "UPDATE_USER", "/api/v1/account", "PUT", "USER")



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

            // Tạo vai trò USER với quyền hạn chế
            List<Permission> userPermissions = allPermissions.stream()
                    .filter(p -> p.getModule().equals("BOOKS") || p.getName().equals("VIEW_ACCOUNT"))
                    .collect(Collectors.toList());
            Role userRole = new Role();
            userRole.setName("USER");
            userRole.setDescription("User with limited permissions");
            userRole.setPermissions(userPermissions);
            roleRepository.save(userRole);
        }

        if (userRepository.count() == 0) {
            // Tạo tài khoản admin
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setName("Admin User");
            admin.setActive(true);
            admin.setRole(roleRepository.findByName("ADMIN"));
            userRepository.save(admin);

            // Tạo tài khoản user
            User user = new User();
            user.setEmail("user@example.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setName("Regular User");
            user.setActive(true);
            user.setRole(roleRepository.findByName("USER"));
            userRepository.save(user);
        }

        System.out.println(">>> END INIT DATABASE");
    }
}
