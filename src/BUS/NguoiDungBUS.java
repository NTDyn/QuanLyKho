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
}
