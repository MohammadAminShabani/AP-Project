package divar.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import divar.dto.request.LoginRequest;
import divar.dto.request.RegisterRequest;
import divar.dto.response.LoginResponse;
import divar.dto.response.UserResponse;
import divar.network.ApiClient;
import divar.network.ApiException;

import java.io.IOException;

public class AuthService {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Calls POST /users/login.
     * @throws ApiException if the backend rejects the credentials
     *         (wrong username/password, blocked user, ...).
     * @throws IOException if the request could not be sent at all
     *         (e.g. backend is not running).
     */
    public LoginResponse login(String username, String password)
            throws ApiException, IOException, InterruptedException {

        LoginRequest request = new LoginRequest(username, password);
        String requestJson = mapper.writeValueAsString(request);

        String responseJson = ApiClient.post("/users/login", requestJson);

        return mapper.readValue(responseJson, LoginResponse.class);
    }

    /**
     * Calls POST /users/register.
     * @throws ApiException if the backend rejects the registration
     *         (duplicate username/phone/email, invalid data, ...).
     * @throws IOException if the request could not be sent at all
     *         (e.g. backend is not running).
     */
    public UserResponse register(String fullName,
                                 String username,
                                 String password,
                                 String phoneNumber,
                                 String email)
            throws ApiException, IOException, InterruptedException {

        RegisterRequest request = new RegisterRequest(
                fullName, username, password, phoneNumber, email);

        String requestJson = mapper.writeValueAsString(request);

        String responseJson = ApiClient.post("/users/register", requestJson);

        return mapper.readValue(responseJson, UserResponse.class);
    }
}
