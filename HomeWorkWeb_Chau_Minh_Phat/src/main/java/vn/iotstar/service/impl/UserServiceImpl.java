package vn.iotstar.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.iotstar.model.User;
import vn.iotstar.repository.UserRepository;
import vn.iotstar.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User login(String username, String password) {
        if (username == null || password == null) {
            return null;
        }
        User user = get(username.trim());
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public User get(String username) {
        if (username == null) {
            return null;
        }
        return userRepository.findByUsername(username.trim()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        if (email == null) {
            return null;
        }
        return userRepository.findByEmail(email.trim()).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> searchUsers(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return userRepository.findAll(pageable);
        }
        return userRepository.searchUsers(keyword.trim(), pageable);
    }

    @Override
    public void insert(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateProfile(User user) {
        User existing = findById(user.getId());
        if (existing != null) {
            existing.setFullname(user.getFullname());
            existing.setPhone(user.getPhone());
            existing.setImages(user.getImages());
            userRepository.save(existing);
        }
    }

    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        if (username == null) return false;
        return userRepository.existsByUsernameIgnoreCase(username.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        if (email == null) return false;
        return userRepository.existsByEmailIgnoreCase(email.trim());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsernameExceptId(String username, int id) {
        if (username == null) return false;
        return userRepository.existsByUsernameIgnoreCaseAndIdNot(username.trim(), id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmailExceptId(String email, int id) {
        if (email == null) return false;
        return userRepository.existsByEmailIgnoreCaseAndIdNot(email.trim(), id);
    }
}
