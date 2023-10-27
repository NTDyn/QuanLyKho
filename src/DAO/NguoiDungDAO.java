package DAO;

import DTO.NguoiDungDTO;
import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NguoiDungDAO {
    public NguoiDungDAO() {
    }

    public NguoiDungDTO verifyLogin(String username) throws SQLException {
        NguoiDungDTO user = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String query = "SELECT * FROM nguoidung WHERE taikhoan = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String taiKhoan = rs.getString("taikhoan");
                String matKhau = rs.getString("matkhau");
                String hoTen = rs.getString("hoten");
                String email = rs.getString("email");
                int maNhomQuyen = rs.getInt("maNhomQuyen");
                int trangThai = rs.getInt("trangThai");
                user = new NguoiDungDTO(taiKhoan, matKhau, hoTen, email, maNhomQuyen, trangThai);
            }
            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            throw e;
        }
        return user;
    }
}
