package vn.iotstar.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "fullname", columnDefinition = "NVARCHAR(255)")
    private String fullname;

    @Column(name = "email", columnDefinition = "NVARCHAR(255)")
    private String email;

    @Column(name = "phone", columnDefinition = "NVARCHAR(20)")
    private String phone;

    @Column(name = "roleid")
    private int roleid;

    @Column(name = "active")
    private boolean active;

    @Column(name = "otp", length = 6)
    private String otp;

    @Column(name = "otp_expiry")
    private LocalDateTime otpExpiry;

    @Column(name = "images", columnDefinition = "NVARCHAR(500)")
    private String images;

    public User() {
    }

    public User(int id, String username, String password, String fullname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.roleid = 3;
        this.active = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(LocalDateTime otpExpiry) {
        this.otpExpiry = otpExpiry;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
