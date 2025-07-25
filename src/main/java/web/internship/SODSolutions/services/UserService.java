package web.internship.SODSolutions.services;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.internship.SODSolutions.dto.request.ReqUserDTO;
import web.internship.SODSolutions.dto.response.ResUserDTO;
import web.internship.SODSolutions.mapper.UserMapper;
import web.internship.SODSolutions.model.User;
import web.internship.SODSolutions.repository.UserRepository;
import web.internship.SODSolutions.util.error.AppException;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserMapper userMapper;


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User getUserById(Long userId) {
        if(userId == null) {
            return null;
        }
        return userRepository.findById(userId).orElseThrow(()->
                new RuntimeException("User not found with id: " + userId));
    }

    public ResUserDTO createUser(ReqUserDTO rqUser) {
        String hashPassword = passwordEncoder.encode(rqUser.getPassword());
        User user = userMapper.toUser(rqUser);
        user.setPassword(hashPassword);
        user = userRepository.save(user);


        return userMapper.toResUserDTO(user);
    }

    public ResUserDTO changePassword(String oldPassword, String newPassword, Long id) throws AppException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException("User not found with id: " + id));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new AppException("Wrong password");
        }

        if(!isStrongPassword(newPassword)){
            throw new AppException("Password is too weak, change to a stronger password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user = userRepository.save(user);
        return userMapper.toResUserDTO(user);
    }

    public boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if (Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if ("!@#$%^&*()-_=+[]{}|;:'\",.<>?/`~".indexOf(c) >= 0) {
                hasSpecial = true;
            }
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}