package vn.iotstar.service.impl;

import vn.iotstar.dao.UserDao;
import vn.iotstar.dao.impl.UserDaoImpl;
import vn.iotstar.model.User;
import vn.iotstar.service.UserService;

public class UserServiceImpl implements UserService {

    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        User user = userDao.get(username);
        if (user != null && password.equals(user.getPassword())) {
            return user;
        }
        return null;
    }

    @Override
    public User get(String username) {
        return userDao.get(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public void insert(User user) {
        userDao.insert(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void updateProfile(User user) {
        userDao.updateProfile(user);
    }
}
