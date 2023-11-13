package helper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SendEmailSMTP {
    public static void sendOTP(String recipient, String username, String otp) throws Exception {
        String email = "qlkmt1@gmail.com";
        String password = "ccvzqgpratefwxct";
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject("Yêu cầu thay đổi mật khẩu");
            message.setText("Xin chào " + username + ",\n"
                    + "\n"
                    + "Ai đó đã yêu cầu đặt lại mật khẩu cho tài khoản của bạn. Nếu đây không phải là bạn, vui lòng bỏ qua email này.\n"
                    + "\n"
                    + "Hãy sử dụng mã xác nhận này để khôi phục mật khẩu của bạn: " + otp
                    + "\n\n"
                    + "Vui lòng không chia sẻ mã xác nhận này cho bất kỳ ai.\n"
                    + "\n"
                    + "Trân trọng,\n"
                    + "Phần mềm quản lý kho máy tính\n");
            Transport.send(message);
        } catch (Exception e) {
            throw e;
        }
    }
    
    public static void sendOTPAsync(String recipient, String username, String otp, EmailSentListener listener) throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                sendOTP(recipient, username, otp);
                listener.onEmailSent(otp); // callback
            } catch (Exception e) {
                listener.onEmailSendError(e);
            } finally {
                executor.shutdown();
            }
        });
    }
    
    public interface EmailSentListener {
        public void onEmailSent(String otp);
        public void onEmailSendError(Exception e);
    }
}
