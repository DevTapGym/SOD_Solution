package web.internship.SODSolutions.config;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import lombok.experimental.FieldDefaults;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import web.internship.SODSolutions.model.Permission;
import web.internship.SODSolutions.model.Role;
import web.internship.SODSolutions.model.User;
import web.internship.SODSolutions.services.UserService;
import web.internship.SODSolutions.util.SecurityUtil;
import web.internship.SODSolutions.util.error.PermissionException;

import java.util.List;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PermissionInterceptor implements HandlerInterceptor {

    AntPathMatcher pathMatcher = new AntPathMatcher();
    private final UserService userService;

    public PermissionInterceptor(UserService userService) {
        this.userService = userService;
    }
    @Override
    @Transactional
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {

        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        System.out.println(">>> RUN preHandle");
        System.out.println(">>> path= " + path);
        System.out.println(">>> httpMethod= " + httpMethod);
        System.out.println(">>> requestURI= " + requestURI);

        // check permission
        String email = SecurityUtil.getCurrentUserLogin().isPresent()
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";

        System.out.println(">>> Current user email: " + email);

        if (!email.isEmpty()) {
            User user = this.userService.getUserByEmail(email);
            if (user != null) {
                Role role = user.getRole();
                if (role != null) {
                    List<Permission> permissions = role.getPermissions();
                    boolean isAllow = permissions.stream().anyMatch(item ->
                            pathMatcher.match(item.getApiPath(), requestURI) && item.getMethod().equals(httpMethod));

                    if (!isAllow) {
                        throw new PermissionException("Bạn không có quyền truy cập endpoint này.");
                    }
                } else {
                    throw new PermissionException("Bạn không có quyền truy cập endpoint này.");
                }
            }

        }
        return true;
    }
}
