package DAO;

import DTO.NhomQuyenDTO;
import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NhomQuyenDAO {
    public NhomQuyenDAO() {
    }
    
    public NhomQuyenDTO getPermissionById(int maNhomQuyen) throws SQLException {
        NhomQuyenDTO info = null;
        try {
            Connection conn = JDBCUtil.getConnection();
            String query = "SELECT * FROM nhomquyen WHERE manhomquyen = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maNhomQuyen);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String tenNhomQuyen = rs.getString("tennhomquyen");
                int trangThai = rs.getInt("trangthai");
                info = new NhomQuyenDTO(maNhomQuyen, tenNhomQuyen, trangThai);
            }
            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            throw e;
        }
        return info;
    }
}
