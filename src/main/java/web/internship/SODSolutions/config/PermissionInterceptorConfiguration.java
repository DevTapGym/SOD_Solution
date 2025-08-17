package web.internship.SODSolutions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import web.internship.SODSolutions.services.UserService;

@Configuration
public class PermissionInterceptorConfiguration implements WebMvcConfigurer {

    final UserService userService;

    public PermissionInterceptorConfiguration(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PermissionInterceptor permissionInterceptor() {
        return new PermissionInterceptor(userService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] whiteList = {
                "/", "/api/v1/auth/**", "/storage/**",  "/api/v1/files", "/api/v1/forms/**"
        };
        registry.addInterceptor(permissionInterceptor())
                .excludePathPatterns(whiteList);
    }
}
