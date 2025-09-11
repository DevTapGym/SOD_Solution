package web.internship.SODSolutions.config;

import lombok.experimental.FieldDefaults;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import web.internship.SODSolutions.model.Field;
import web.internship.SODSolutions.model.Permission;
import web.internship.SODSolutions.model.Role;
import web.internship.SODSolutions.model.User;
import web.internship.SODSolutions.repository.*;

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
    FieldRepository fieldRepository;

    public DatabaseInitializer(PermissionRepository permissionRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, FieldRepository fieldRepository) {
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.fieldRepository = fieldRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> START INIT DATABASE");

        // Kiểm tra dữ liệu hiện có
        if (permissionRepository.count() == 0) {
            List<Permission> permissions = Arrays.asList(
                    new Permission(null, "VIEW_ALL_USER", "/api/v1/user/getAll", "GET", "USER"),
                    new Permission(null, "UPDATE_USER", "/api/v1/user", "PUT", "USER"),
                    new Permission(null, "CHANGE_PASSWORD", "/api/v1/user/change-password", "POST", "USER"),
                    new Permission(null, "CREATE_USER", "/api/v1/user", "POST", "USER"),

                    new Permission(null, "FORGOT-PASSWORD", "/api/v1/auth/forgot-password", "POST", "AUTH"),
                    new Permission(null, "SENT-ACCOUNT", "/api/v1/auth/send-account", "POST", "AUTH"),

                    new Permission(null, "UPLOAD-AVATAR", "/api/v1/files/avatar", "POST", "FILE"),
                    new Permission(null, "UPLOAD-CONTRACT", "/api/v1/files/contracts", "POST", "FILE"),

                    new Permission(null, "VIEW_CONTRACT_BY_EMAIL", "/api/v1/contracts/email", "GET", "CONTRACT"),
                    new Permission(null, "VIEW_CONTRACT_BY_PROJECT", "/api/v1/contracts/project/{projectId}", "GET", "CONTRACT"),
                    new Permission(null, "CREATE_CONTRACT", "/api/v1/contracts", "POST", "CONTRACT"),
                    new Permission(null, "UPDATE_CONTRACT", "/api/v1/contracts", "PUT", "CONTRACT"),
                    new Permission(null, "DELETE_CONTRACT", "/api/v1/contracts/{id}", "DELETE", "CONTRACT"),

                    new Permission(null, "VIEW_ALL_PROJECT", "/api/v1/projects/getAll", "GET", "PROJECT"),
                    new Permission(null, "VIEW_PROJECT_BY_EMAIL", "/api/v1/projects", "GET", "PROJECT"),
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

                    new Permission(null, "VIEW_ALL_PAYMENT", "/api/v1/payments", "GET", "PAYMENT"),
                    new Permission(null, "VIEW_PAYMENT_BY_PROJECT", "/api/v1/payments/project/{projectId}", "GET", "PAYMENT"),
                    new Permission(null, "CREATE_PAYMENT", "/api/v1/payments", "POST", "PAYMENT"),
                    new Permission(null, "UPDATE_PAYMENT", "/api/v1/payments", "PUT", "PAYMENT"),
                    new Permission(null, "DELETE_PAYMENT", "/api/v1/payments/{id}", "DELETE", "PAYMENT"),

                    new Permission(null, "VIEW_ALL_FORM", "/api/v1/forms/getAll", "GET", "FORM"),
                    new Permission(null, "VIEW_FORM_BY_ID", "/api/v1/forms/{id}", "GET", "FORM"),
                    new Permission(null, "CREATE_FORM", "/api/v1/forms", "POST", "FORM"),
                    new Permission(null, "DELETE_FORM", "/api/v1/forms/{id}", "DELETE", "FORM")

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
                    "VIEW_PAYMENT_BY_PROJECT",
                    "UPDATE_USER",
                    "CHANGE_PASSWORD",
                    "CREATE_FORM",
                    "UPLOAD-AVATAR"
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
            admin.setRole(roleRepository.getRoleAdmin());
            userRepository.save(admin);

            User user = new User();
            user.setEmail("user@gmail.com");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setName("Regular User");
            user.setActive(true);
            user.setRole(roleRepository.getRoleUser());
            userRepository.save(user);
        }

        if(fieldRepository.count() == 0) {
            Field field1 = new Field();
            field1.setFieldName("Bán lẻ & Thương mại điện tử");
            field1.setDescription("Những ứng dụng hỗ trợ mua bán sản phẩm, hàng hóa trên Internet, giúp doanh nghiệp tiếp cận khách hàng nhanh hơn, tối ưu quản lý đơn hàng và tăng doanh số hiệu quả.");
            fieldRepository.save(field1);

            Field field2 = new Field();
            field2.setFieldName("Giáo dục & Đào tạo");
            field2.setDescription("Các ứng dụng học trực tuyến, luyện thi, quản lý lớp học, giúp người học tiếp cận kiến thức dễ dàng, tiện lợi và tiết kiệm thời gian.");
            fieldRepository.save(field2);

            Field field3 = new Field();
            field3.setFieldName("Y tế & Chăm sóc sức khỏe");
            field3.setDescription("Ứng dụng đặt lịch khám, tư vấn sức khỏe online, theo dõi tình trạng sức khỏe, mang đến trải nghiệm chăm sóc y tế an toàn và tiện lợi cho người dùng.");
            fieldRepository.save(field3);

            Field field4 = new Field();
            field4.setFieldName("Tài chính & Ngân hàng");
            field4.setDescription("Các ứng dụng quản lý chi tiêu, ví điện tử, đầu tư và vay vốn, giúp người dùng kiểm soát tài chính cá nhân và doanh nghiệp hiệu quả.");
            fieldRepository.save(field4);

            Field field5 = new Field();
            field5.setFieldName("Du lịch & Giải trí");
            field5.setDescription("Ứng dụng đặt vé máy bay, khách sạn, khám phá điểm đến và sự kiện, giúp người dùng có những trải nghiệm du lịch thú vị và dễ dàng.");
            fieldRepository.save(field5);

            Field field6 = new Field();
            field6.setFieldName("Vận tải & Giao hàng");
            field6.setDescription("Ứng dụng gọi xe, đặt giao hàng nhanh, theo dõi vận chuyển, giúp tối ưu dịch vụ vận tải và tăng sự tiện lợi cho khách hàng.");
            fieldRepository.save(field6);

            Field field7 = new Field();
            field7.setFieldName("Doanh nghiệp & Quản lý công việc");
            field7.setDescription("Các ứng dụng quản lý dự án, chấm công, CRM và điều hành doanh nghiệp, giúp tối ưu năng suất làm việc và quy trình nội bộ.");
            fieldRepository.save(field7);
        }

        System.out.println(">>> END INIT DATABASE");
    }
}
