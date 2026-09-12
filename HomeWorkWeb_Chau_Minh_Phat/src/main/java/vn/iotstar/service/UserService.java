package vn.iotstar.service;

import vn.iotstar.model.User;

public interface UserService {
    User login(String username, String password);
    User get(String username);
    User findByEmail(String email);
    User findById(int id);
    void insert(User user);
    void update(User user);
    void updateProfile(User user);
}
