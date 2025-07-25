package web.internship.SODSolutions.config;

import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import web.internship.SODSolutions.model.Permission;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class CustomUserDetails implements UserDetails {
    String email;
    String password;
    boolean isActive;
    List<GrantedAuthority> authorities;

    @Getter
    List<Permission> permissions;

    public CustomUserDetails(String email, String password, boolean isActive, List<Permission> permissions) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.permissions = permissions;
        this.authorities = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
