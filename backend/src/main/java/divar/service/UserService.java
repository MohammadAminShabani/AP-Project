package divar.service;

import divar.dto.request.LoginRequest;
import divar.dto.request.RegisterRequest;
import divar.dto.response.LoginResponse;
import divar.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse findById(Long id);

    List<UserResponse> findAll();

    UserResponse update(Long id, RegisterRequest request);

    void delete(Long id);
}