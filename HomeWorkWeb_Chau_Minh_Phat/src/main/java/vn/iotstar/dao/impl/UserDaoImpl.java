package vn.iotstar.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import vn.iotstar.config.JpaConfig;
import vn.iotstar.connection.DBConnection;
import vn.iotstar.dao.UserDao;
import vn.iotstar.model.User;

public class UserDaoImpl extends DBConnection implements UserDao {

    @Override
    public User get(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("fullname")
                    );
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setRoleid(rs.getInt("roleid"));
                    user.setActive(rs.getBoolean("active"));
                    user.setOtp(rs.getString("otp"));
                    java.sql.Timestamp ts = rs.getTimestamp("otp_expiry");
                    if (ts != null) {
                        user.setOtpExpiry(ts.toLocalDateTime());
                    }
                    try {
                        user.setImages(rs.getString("images"));
                    } catch (Exception ignored) {
                    }
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByEmail(String email) {
        String sql = "SELECT * FROM Users WHERE email = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("fullname")
                    );
                    user.setEmail(rs.getString("email"));
                    user.setPhone(rs.getString("phone"));
                    user.setRoleid(rs.getInt("roleid"));
                    user.setActive(rs.getBoolean("active"));
                    user.setOtp(rs.getString("otp"));
                    java.sql.Timestamp ts = rs.getTimestamp("otp_expiry");
                    if (ts != null) {
                        user.setOtpExpiry(ts.toLocalDateTime());
                    }
                    try {
                        user.setImages(rs.getString("images"));
                    } catch (Exception ignored) {
                    }
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findById(int id) {
        EntityManager em = JpaConfig.getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(User user) {
        String sql = "INSERT INTO Users (username, password, fullname, email, phone, roleid, active, otp, otp_expiry, images) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setInt(6, user.getRoleid());
            ps.setBoolean(7, user.isActive());
            ps.setString(8, user.getOtp());
            if (user.getOtpExpiry() != null) {
                ps.setTimestamp(9, java.sql.Timestamp.valueOf(user.getOtpExpiry()));
            } else {
                ps.setNull(9, java.sql.Types.TIMESTAMP);
            }
            ps.setString(10, user.getImages());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE Users SET password = ?, fullname = ?, email = ?, phone = ?, roleid = ?, active = ?, otp = ?, otp_expiry = ?, images = ? WHERE username = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getPassword());
            ps.setString(2, user.getFullname());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setInt(5, user.getRoleid());
            ps.setBoolean(6, user.isActive());
            ps.setString(7, user.getOtp());
            if (user.getOtpExpiry() != null) {
                ps.setTimestamp(8, java.sql.Timestamp.valueOf(user.getOtpExpiry()));
            } else {
                ps.setNull(8, java.sql.Types.TIMESTAMP);
            }
            ps.setString(9, user.getImages());
            ps.setString(10, user.getUsername());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfile(User user) {
        EntityManager em = JpaConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            User existing = em.find(User.class, user.getId());
            if (existing != null) {
                existing.setFullname(user.getFullname());
                existing.setPhone(user.getPhone());
                if (user.getImages() != null && !user.getImages().isBlank()) {
                    existing.setImages(user.getImages());
                }
                em.merge(existing);
            }
            trans.commit();
        } catch (Exception e) {
            if (trans.isActive()) {
                trans.rollback();
            }
            e.printStackTrace();
            throw e;
        } finally {
            em.close();
        }
    }
}
