package vn.iotstar.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.iotstar.model.User;

public interface UserService {

    User login(String username, String password);

    User get(String username);

    User findByEmail(String email);

    User findById(int id);

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    Page<User> searchUsers(String keyword, Pageable pageable);

    void insert(User user);

    void update(User user);

    void updateProfile(User user);

    void delete(int id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameExceptId(String username, int id);

    boolean existsByEmailExceptId(String email, int id);
}
