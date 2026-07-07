package divar.service.impl;

import divar.dto.request.LoginRequest;
import divar.dto.request.RegisterRequest;
import divar.dto.response.LoginResponse;
import divar.dto.response.UserResponse;
import divar.entity.User;
import divar.enums.UserRole;
import divar.enums.UserStatus;
import divar.exception.*;
import divar.repository.UserRepository;
import divar.security.JwtService;
import divar.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository , JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    public UserResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists.");        }

        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new PhoneNumberAlreadyExistsException("Phone number already exists.");
        }
        if(request.getPassword() == null || request.getPassword().isEmpty()){
            throw new PasswordMustNotBeEmptyException("Password must not be empty.");
        }
        if (request.getEmail() != null &&
                !request.getEmail().isBlank() &&
                userRepository.existsByEmail(request.getEmail())) {

            throw new EmailAlreadyExistsException("Email already exists.");
        }

        User user = new User();

        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setEmail(request.getEmail());

        user.setRole(UserRole.USER);
        user.setStatus(UserStatus.ACTIVE);

        user.setAverageRating(0);
        user.setRatingCount(0);

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setFullName(savedUser.getFullName());
        response.setUsername(savedUser.getUsername());
        response.setPhoneNumber(savedUser.getPhoneNumber());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());

        return response;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid username or password."));

        if (!user.getPassword().equals(request.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password.");
        }

        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new UserNotActiveException("User account is not active.");
        }

        LoginResponse response = new LoginResponse();

        String token = jwtService.generateToken(user);
        response.setToken(token);
        response.setUserId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());

        return response;
    }
    @Override
    public UserResponse findById(Long id) {
        return null;
    }

    @Override
    public List<UserResponse> findAll() {
        return List.of();
    }

    @Override
    public UserResponse update(Long id, RegisterRequest request) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}