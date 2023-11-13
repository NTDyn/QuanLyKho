package BUS;

import DAO.ChiTietQuyenDAO;
import DAO.NguoiDungDAO;
import DAO.NhomQuyenDAO;
import DTO.ChiTietQuyenDTO;
import DTO.NguoiDungDTO;
import DTO.NhomQuyenDTO;
import DTO.UserInfoDTO;
import helper.BCrypt;
import helper.Exception.AuthenticationException;
import helper.Exception.EmptyFieldException;
import helper.OTP;
import helper.SendEmailSMTP;
import helper.SendEmailSMTP.EmailSentListener;
import helper.Validation;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungBUS {
    private final NguoiDungDAO ndDAO = new NguoiDungDAO();
    private final NhomQuyenDAO nqDAO = new NhomQuyenDAO();
    private final ChiTietQuyenDAO ctqDAO = new ChiTietQuyenDAO();

    public NguoiDungBUS() {
    }
    
    public UserInfoDTO verifyLogin(String username, String password) throws Exception {
        // Input validation
        if (username.isEmpty()) {
            throw new EmptyFieldException("Bạn chưa nhập tài khoản", "username");
        }
        if (password.isEmpty()) {
            throw new EmptyFieldException("Bạn chưa nhập mật khẩu", "password");
        }
        if (!Validation.isValidUsername(username)) {
            throw new IllegalArgumentException("Tài khoản không hợp lệ");
        }
        
        // Authentication
        NguoiDungDTO user = null;
        try {
            user = ndDAO.verifyLogin(username);
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        
        if (user == null || !BCrypt.checkpw(password, user.getMatKhau())) {
            throw new AuthenticationException("Tài khoản này không tồn tại hoặc mật khẩu không đúng.");
        }
        
        if (user.getTrangThai() == 0) {
            throw new AuthenticationException("Tài khoản của bạn đã bị khoá.");
        }
        
        NhomQuyenDTO permissionInfo = null;
        try {
            permissionInfo = nqDAO.getPermissionById(user.getMaNhomQuyen());
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        
        if (permissionInfo.getTrangThai() == 0) {
            throw new AuthenticationException("Đăng nhập thất bại do tài khoản của bạn thuộc vào nhóm quyền không còn hiệu lực.");
        }
        
        // Authorization
        List<ChiTietQuyenDTO> viewPermission = new ArrayList<>();
        try {
            viewPermission = ctqDAO.getAuthorization(user.getMaNhomQuyen());
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        
        return new UserInfoDTO(user, permissionInfo, viewPermission);
    }
    
    public NguoiDungDTO verifyEmail(String email) throws Exception {
        // Input validation
        if (email.isEmpty()) {
            throw new EmptyFieldException("Bạn chưa nhập địa chỉ email", null);
        }
        if (!Validation.isValidEmail(email)) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        
        // Authentication
        NguoiDungDTO user = null;
        try {
            user = ndDAO.getInfoByEmail(email);
        } catch (SQLException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
        
        if (user == null) {
            throw new AuthenticationException("Không tìm thấy tài khoản nào được liên kết với địa chỉ email bạn đã nhập. Vui lòng thử lại.");
        }
        
        return user;
    }
    
    public void sendEmailOTP(String username, String email, EmailSentListener listener) throws Exception {
        String otp = OTP.generateOTP();
        SendEmailSMTP.sendOTPAsync(email, username, otp, listener);
    }
    
    public boolean handleConfirmOTP(String inputOtp, String otp) throws EmptyFieldException, IllegalArgumentException {
        if (inputOtp.isEmpty()) {
            throw new EmptyFieldException("Bạn chưa nhập mã xác nhận", null);
        }
        if (!Validation.isValidOTP(inputOtp)) {
            throw new IllegalArgumentException("Mã xác nhận không hợp lệ");
        }
        return otp.equals(inputOtp);
    }
    
    public boolean handleRecoverPassword(NguoiDungDTO user, String newPassword, String confirmNewPassword) throws Exception {
        if (newPassword.isEmpty()) {
            throw new EmptyFieldException("Bạn chưa nhập mật khẩu mới", "newPassword");
        }
        if (confirmNewPassword.isEmpty()) {
            throw new EmptyFieldException("Bạn chưa nhập xác nhận mật khẩu mới", "confirmNewPassword");
        }
        if (!newPassword.equals(confirmNewPassword)) {
            throw new IllegalArgumentException("Mật khẩu không chính xác, vui lòng thử lại");
        }
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt(12));
        int result;
        try {
            result = ndDAO.changePassword(user, hashedPassword);
        } catch (SQLException e) {
            throw e;
        }
        return result > 0;
    }
}
