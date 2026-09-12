package vn.iotstar.util;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EmailUtil {

    private static final Logger LOGGER = Logger.getLogger(EmailUtil.class.getName());

    public static final String[] USERNAME_KEYS = {
        "EMAIL_USERNAME", "MAIL_USERNAME", "SMTP_USERNAME", "mail.smtp.user", "mail.user"
    };

    public static final String[] PASSWORD_KEYS = {
        "EMAIL_PASSWORD", "MAIL_PASSWORD", "SMTP_PASSWORD", "mail.smtp.password", "mail.password"
    };

    public static final String[] HOST_KEYS = {
        "SMTP_HOST", "MAIL_SMTP_HOST", "mail.smtp.host"
    };

    public static final String[] PORT_KEYS = {
        "SMTP_PORT", "MAIL_SMTP_PORT", "mail.smtp.port"
    };

    public static final String[] AUTH_KEYS = {
        "SMTP_AUTH", "MAIL_SMTP_AUTH", "mail.smtp.auth"
    };

    public static final String[] STARTTLS_KEYS = {
        "SMTP_STARTTLS_ENABLE", "MAIL_SMTP_STARTTLS_ENABLE", "mail.smtp.starttls.enable"
    };

    /**
     * Lấy giá trị cấu hình theo thứ tự ưu tiên:
     * 1. Java System Property (-Dkey=value)
     * 2. OS Environment Variable (System.getenv)
     * 3. File mail.properties trong resources (nếu có)
     */
    public static String getConfig(String... keys) {
        for (String key : keys) {
            String val = System.getProperty(key);
            if (val != null && !val.trim().isEmpty()) {
                return val.trim();
            }
            val = System.getenv(key);
            if (val != null && !val.trim().isEmpty()) {
                return val.trim();
            }
        }

        Properties fileProps = loadMailProperties();
        for (String key : keys) {
            String val = fileProps.getProperty(key);
            if (val != null && !val.trim().isEmpty()) {
                return val.trim();
            }
        }
        return null;
    }

    /**
     * Xác định nguồn gốc cấu hình (System Property, Environment Variable, mail.properties) để hỗ trợ debug an toàn.
     */
    public static String getConfigOrigin(String... keys) {
        for (String key : keys) {
            String val = System.getProperty(key);
            if (val != null && !val.trim().isEmpty()) {
                return "System Property (-D" + key + ")";
            }
            val = System.getenv(key);
            if (val != null && !val.trim().isEmpty()) {
                return "Environment Variable (" + key + ")";
            }
        }

        Properties fileProps = loadMailProperties();
        for (String key : keys) {
            String val = fileProps.getProperty(key);
            if (val != null && !val.trim().isEmpty()) {
                return "mail.properties file (" + key + ")";
            }
        }
        return "CHƯA THIẾT LẬP";
    }

    private static Properties loadMailProperties() {
        Properties props = new Properties();
        try (InputStream is = EmailUtil.class.getClassLoader().getResourceAsStream("mail.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (Exception ignored) {
        }
        return props;
    }

    /**
     * Ẩn một phần địa chỉ email để log an toàn, không để lộ thông tin nhạy cảm.
     */
    public static String maskEmail(String email) {
        if (email == null || email.isBlank()) {
            return "(chưa cấu hình)";
        }
        int atIdx = email.indexOf('@');
        if (atIdx <= 1) {
            return "***" + (atIdx >= 0 ? email.substring(atIdx) : "");
        }
        String name = email.substring(0, atIdx);
        String domain = email.substring(atIdx);
        if (name.length() <= 2) {
            return name.charAt(0) + "***" + domain;
        }
        return name.substring(0, 2) + "***" + domain;
    }

    /**
     * Kiểm tra xem cấu hình email đã sẵn sàng chưa.
     */
    public static boolean isConfigured() {
        String username = getConfig(USERNAME_KEYS);
        String password = getConfig(PASSWORD_KEYS);
        return username != null && !username.isBlank() && password != null && !password.isBlank();
    }

    public static boolean sendOtp(String recipient, String otp, String purpose) {
        String username = getConfig(USERNAME_KEYS);
        String password = getConfig(PASSWORD_KEYS);

        // Chuẩn hóa mật khẩu ứng dụng Google (loại bỏ toàn bộ khoảng trắng nếu người dùng copy nhóm 4 ký tự)
        if (password != null) {
            password = password.replaceAll("\\s+", "");
        }

        String host = getConfig(HOST_KEYS);
        if (host == null || host.isBlank()) {
            host = "smtp.gmail.com";
        }

        String port = getConfig(PORT_KEYS);
        if (port == null || port.isBlank()) {
            port = "587";
        }

        String auth = getConfig(AUTH_KEYS);
        if (auth == null || auth.isBlank()) {
            auth = "true";
        }

        String starttls = getConfig(STARTTLS_KEYS);
        if (starttls == null || starttls.isBlank()) {
            starttls = "true";
        }

        // Diagnostic log an toàn theo yêu cầu runtime
        java.net.URL resUrl = EmailUtil.class.getClassLoader().getResource("mail.properties");
        boolean resFound = (resUrl != null);
        boolean userConfigured = (username != null && !username.isBlank());
        boolean passConfigured = (password != null && !password.isBlank());

        System.out.println("[MAIL] EmailUtil runtime loaded");
        System.out.println("[MAIL] mail.properties resource found: " + resFound + (resFound ? " (" + resUrl + ")" : ""));
        System.out.println("[MAIL] username configured: " + userConfigured);
        System.out.println("[MAIL] password configured: " + passConfigured);
        System.out.println("[MAIL] smtp host: " + host);
        System.out.println("[MAIL] smtp port: " + port);

        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            System.err.println("[MAIL] LỖI: Thiếu thông tin xác thực gửi email SMTP (username hoặc password rỗng)!");
            LOGGER.severe("================================================================================");
            LOGGER.severe("[EMAIL OTP LỖI] THIẾU THÔNG TIN XÁC THỰC GỬI EMAIL SMTP!");
            LOGGER.severe("-> Danh sách biến username được hỗ trợ: EMAIL_USERNAME, MAIL_USERNAME, SMTP_USERNAME, mail.smtp.user");
            LOGGER.severe("-> Danh sách biến password được hỗ trợ: EMAIL_PASSWORD, MAIL_PASSWORD, SMTP_PASSWORD, mail.smtp.password");
            if (username == null || username.isBlank()) {
                LOGGER.severe("-> Trạng thái Username: CHƯA CẤU HÌNH (null/empty)");
            } else {
                LOGGER.info("-> Trạng thái Username: ĐÃ CẤU HÌNH [" + maskEmail(username) + "] từ " + getConfigOrigin(USERNAME_KEYS));
            }
            if (password == null || password.isBlank()) {
                LOGGER.severe("-> Trạng thái Password: CHƯA CẤU HÌNH (null/empty)");
            } else {
                LOGGER.info("-> Trạng thái Password: ĐÃ CẤU HÌNH [*** BẢO MẬT ***] từ " + getConfigOrigin(PASSWORD_KEYS));
            }
            LOGGER.severe("--------------------------------------------------------------------------------");
            LOGGER.severe("HƯỚNG DẪN THIẾT LẬP (chọn 1 trong 3 cách):");
            LOGGER.severe("1. Cách 1 (Eclipse Tomcat - VM Arguments):");
            LOGGER.severe("   Servers -> Double-click Tomcat -> Open launch configuration -> Arguments -> VM arguments:");
            LOGGER.severe("   -DEMAIL_USERNAME=\"your_email@gmail.com\" -DEMAIL_PASSWORD=\"your_16_char_app_password\"");
            LOGGER.severe("2. Cách 2 (File src/main/resources/mail.properties):");
            LOGGER.severe("   Tạo file 'mail.properties' (đã gitignore) với nội dung:");
            LOGGER.severe("   EMAIL_USERNAME=your_email@gmail.com");
            LOGGER.severe("   EMAIL_PASSWORD=your_16_char_app_password");
            LOGGER.severe("3. Cách 3 (PowerShell User Environment Variable):");
            LOGGER.severe("   [System.Environment]::SetEnvironmentVariable('EMAIL_USERNAME', 'your_email@gmail.com', 'User')");
            LOGGER.severe("   [System.Environment]::SetEnvironmentVariable('EMAIL_PASSWORD', 'your_16_char_app_password', 'User')");
            LOGGER.severe("   (Sau khi set biến môi trường, cần khởi động lại Eclipse/Tomcat để áp dụng)");
            LOGGER.severe("================================================================================");
            return false;
        }

        final String finalUsername = username;
        final String finalPassword = password;

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.3");
        props.put("mail.smtp.ssl.trust", host);

        // Timeout tránh treo luồng máy chủ khi mạng gián đoạn
        props.put("mail.smtp.connectiontimeout", "5000");
        props.put("mail.smtp.timeout", "5000");
        props.put("mail.smtp.writetimeout", "5000");

        String mailDebug = getConfig("MAIL_DEBUG", "mail.debug");
        if ("true".equalsIgnoreCase(mailDebug)) {
            props.put("mail.debug", "true");
        }

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(finalUsername, finalPassword);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(finalUsername, "Exercise Web System", "UTF-8"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

            String subject;
            String content;

            if ("ACTIVATE".equalsIgnoreCase(purpose)) {
                subject = "Xác nhận kích hoạt tài khoản";
                content = "Xin chào,\n\n"
                        + "Mã OTP kích hoạt tài khoản của bạn là: " + otp + "\n\n"
                        + "Mã có hiệu lực trong 5 phút.\n\n"
                        + "Trân trọng,\nĐội ngũ Exercise Web.";
            } else if ("RESET_PASSWORD".equalsIgnoreCase(purpose)) {
                subject = "OTP khôi phục mật khẩu";
                content = "Xin chào,\n\n"
                        + "Mã OTP khôi phục mật khẩu của bạn là: " + otp + "\n\n"
                        + "Mã có hiệu lực trong 5 phút.\n\n"
                        + "Nếu bạn không yêu cầu hành động này, vui lòng bỏ qua email.\n\n"
                        + "Trân trọng,\nĐội ngũ Exercise Web.";
            } else {
                subject = "Mã OTP xác thực";
                content = "Xin chào,\n\n"
                        + "Mã OTP của bạn là: " + otp + "\n\n"
                        + "Mã có hiệu lực trong 5 phút.\n\n"
                        + "Trân trọng,\nĐội ngũ Exercise Web.";
            }

            message.setSubject(subject, "UTF-8");
            message.setText(content, "UTF-8");

            LOGGER.info("Đang gửi OTP email (" + purpose + ") tới " + maskEmail(recipient) + " qua " + host + ":" + port + "...");
            Transport.send(message);
            LOGGER.info("Gửi email OTP thành công tới: " + maskEmail(recipient));
            System.out.println("[MAIL] OTP sent successfully");
            return true;

        } catch (jakarta.mail.AuthenticationFailedException e) {
            System.err.println("[MAIL] OTP send failed: 535 AuthenticationFailedException");
            LOGGER.severe("================================================================================");
            LOGGER.severe("[EMAIL OTP LỖI] 535 Authentication Failed: Xác thực Gmail SMTP thất bại!");
            LOGGER.severe("-> Tài khoản gửi: " + maskEmail(finalUsername));
            LOGGER.severe("-> Exception: " + e.getClass().getName() + " - " + e.getMessage());
            LOGGER.severe("-> Hướng dẫn khắc phục:");
            LOGGER.severe("   1. Bật 'Xác minh 2 bước' (2-Step Verification) trên tài khoản Google.");
            LOGGER.severe("   2. Tạo và sử dụng 'Mật khẩu ứng dụng' (Google App Password 16 ký tự).");
            LOGGER.severe("      Truy cập: https://myaccount.google.com/apppasswords");
            LOGGER.severe("   3. Tuyệt đối KHÔNG dùng mật khẩu đăng nhập Google/Gmail thông thường.");
            LOGGER.severe("================================================================================");
            return false;

        } catch (jakarta.mail.MessagingException e) {
            System.err.println("[MAIL] OTP send failed: MessagingException: " + e.getMessage());
            LOGGER.severe("================================================================================");
            String msg = e.getMessage() != null ? e.getMessage() : "";
            LOGGER.severe("[EMAIL OTP LỖI] MessagingException (" + e.getClass().getName() + "): " + msg);
            if (msg.contains("Could not connect") || (e.getCause() != null && e.getCause().toString().contains("ConnectException"))) {
                LOGGER.severe("-> Không thể kết nối tới máy chủ SMTP " + host + ":" + port + "!");
                LOGGER.severe("-> Nguyên nhân: Mạng Internet bị ngắt, hoặc tường lửa / Antivirus / ISP chặn cổng SMTP " + port + ".");
            }
            if (e.getNextException() != null) {
                Exception nextEx = e.getNextException();
                LOGGER.severe("-> Chi tiết lồng (" + nextEx.getClass().getName() + "): " + nextEx.getMessage());
            }
            LOGGER.severe("================================================================================");
            return false;

        } catch (Exception e) {
            System.err.println("[MAIL] OTP send failed: " + e.getClass().getName() + ": " + e.getMessage());
            LOGGER.log(Level.SEVERE, "[EMAIL OTP LỖI] Ngoại lệ không xác định (" + e.getClass().getName() + "): " + e.getMessage());
            return false;
        }
    }

    /**
     * Phương thức main hỗ trợ kiểm tra nhanh cấu hình email và kiểm thử gửi OTP từ dòng lệnh / IDE.
     */
    public static void main(String[] args) {
        System.out.println("=== KIỂM TRA CẤU HÌNH GỬI EMAIL OTP (SMTP) ===");
        String user = getConfig(USERNAME_KEYS);
        String pass = getConfig(PASSWORD_KEYS);
        String host = getConfig(HOST_KEYS);
        if (host == null || host.isBlank()) host = "smtp.gmail.com";
        String port = getConfig(PORT_KEYS);
        if (port == null || port.isBlank()) port = "587";
        String auth = getConfig(AUTH_KEYS);
        if (auth == null || auth.isBlank()) auth = "true";
        String starttls = getConfig(STARTTLS_KEYS);
        if (starttls == null || starttls.isBlank()) starttls = "true";

        System.out.println("1. SMTP Host                : " + host);
        System.out.println("2. SMTP Port                : " + port);
        System.out.println("3. mail.smtp.auth           : " + auth);
        System.out.println("4. mail.smtp.starttls.enable: " + starttls);
        System.out.println("5. Trạng thái Username      : " + (user != null && !user.isBlank() 
                ? "ĐÃ CẤU HÌNH [" + maskEmail(user) + "] (Nguồn: " + getConfigOrigin(USERNAME_KEYS) + ")" 
                : "CHƯA CẤU HÌNH (null)"));
        System.out.println("6. Trạng thái Password      : " + (pass != null && !pass.isBlank() 
                ? "ĐÃ CẤU HÌNH [*** BẢO MẬT ***] (Nguồn: " + getConfigOrigin(PASSWORD_KEYS) + ")" 
                : "CHƯA CẤU HÌNH (null)"));
        System.out.println("7. Sẵn sàng gửi OTP         : " + (isConfigured() ? "CÓ (ĐÃ SẴN SÀNG)" : "KHÔNG (THIẾU THÔNG TIN)"));

        if (args.length > 0 && args[0].contains("@")) {
            System.out.println("\nĐang gửi thử nghiệm mã OTP mẫu tới: " + maskEmail(args[0]) + " ...");
            boolean success = sendOtp(args[0], "123456", "ACTIVATE");
            System.out.println("\nKết quả thử nghiệm: " + (success ? "THÀNH CÔNG!" : "THẤT BẠI (Xem chi tiết log lỗi ở trên)"));
        } else {
            System.out.println("\n[GỢI Ý TEST NHANH]");
            System.out.println("Để kiểm tra gửi email OTP thực tế tới hộp thư của bạn, hãy chạy:");
            System.out.println("  java -cp \"target/classes;target/Exercise/WEB-INF/lib/*\" vn.iotstar.util.EmailUtil <dia_chi_email_nhan>");
        }
        System.out.println("==============================================");
    }
}

