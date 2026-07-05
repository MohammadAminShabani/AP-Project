package divar.service.impl;

import divar.dto.request.LoginRequest;
import divar.dto.request.RegisterRequest;
import divar.dto.response.LoginResponse;
import divar.dto.response.UserResponse;
import divar.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserResponse register(RegisterRequest request) {
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
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