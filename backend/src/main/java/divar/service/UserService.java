package divar.service;

import divar.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User register(User user);

    Optional<User> login(String username, String password);

    Optional<User> findById(Long id);

    List<User> findAll();

    User update(User user);

    void delete(Long id);

}