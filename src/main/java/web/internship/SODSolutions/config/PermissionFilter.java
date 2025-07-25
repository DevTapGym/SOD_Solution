package web.internship.SODSolutions.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import web.internship.SODSolutions.model.Permission;
import web.internship.SODSolutions.repository.PermissionRepository;

import java.io.IOException;
import java.util.List;

@Component
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PermissionFilter extends OncePerRequestFilter {
     PermissionRepository permissionRepository;
     AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();

        // Whitelist phải khớp với SecurityConfiguaration
        String[] whiteList = {
                "/", "/api/v1/auth/login",
                "/api/v1/auth/register", "/api/v1/auth/check-code",
                "/api/v1/auth/resend-code", "/api/v1/auth/retry-password",
                "/api/v1/auth/change-password-retry", "/api/v1/auth/login-google",
                "/api/v1/auth/profile",
                "/storage/**"
        };

        // Bỏ qua các endpoint trong whitelist
        for (String path : whiteList) {
            if (pathMatcher.match(path, requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // Kiểm tra xác thực
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not authenticated");
            return;
        }

        // Lấy danh sách quyền từ Authentication
        List<String> userPermissions = authentication.getAuthorities().stream()
                .map(Object::toString)
                .toList();

        // Lấy tất cả permissions từ cơ sở dữ liệu
        List<Permission> allPermissions = permissionRepository.findAll();

        // Kiểm tra quyền
        boolean isAllowed = allPermissions.stream()
                .filter(permission -> userPermissions.contains(permission.getName()))
                .anyMatch(permission -> pathMatcher.match(permission.getApiPath(), requestURI)
                        && permission.getMethod().equals(httpMethod));

        if (!isAllowed) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied: No permission for this endpoint");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
