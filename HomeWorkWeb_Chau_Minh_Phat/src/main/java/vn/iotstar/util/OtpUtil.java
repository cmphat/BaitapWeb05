package vn.iotstar.util;

import java.security.SecureRandom;

public class OtpUtil {
    private static final SecureRandom secureRandom = new SecureRandom();
    
    public static String generateOtp() {
        int otp = secureRandom.nextInt(1000000); // 0 to 999999
        return String.format("%06d", otp);
    }
}
