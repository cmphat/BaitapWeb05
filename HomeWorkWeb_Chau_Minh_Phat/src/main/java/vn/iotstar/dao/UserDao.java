package vn.iotstar.dao;

import vn.iotstar.model.User;

public interface UserDao {
    User get(String username);
    User findByEmail(String email);
    User findById(int id);
    void insert(User user);
    void update(User user);
    void updateProfile(User user);
}
