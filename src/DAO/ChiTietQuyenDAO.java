package DAO;

import DTO.ChiTietQuyenDTO;
import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietQuyenDAO {
    public ChiTietQuyenDAO() {
    }
    
    public List<ChiTietQuyenDTO> getAuthorization(int maNhomQuyen) throws SQLException {
        List<ChiTietQuyenDTO> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String query = "SELECT * FROM chitietquyen WHERE manhomquyen = ? AND hanhdong = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maNhomQuyen);
            ps.setString(2, "view");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String maChucNang = rs.getString("machucnang");
                list.add(new ChiTietQuyenDTO(maNhomQuyen, maChucNang, "view"));
            }
            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            throw e;
        }
        return list;
    }
    
    public List<ChiTietQuyenDTO> getAllowedActions(int maNhomQuyen, String maChucNang) throws SQLException {
        List<ChiTietQuyenDTO> list = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String query = "SELECT * FROM chitietquyen WHERE manhomquyen = ? AND machucnang = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, maNhomQuyen);
            ps.setString(2, maChucNang);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String hanhDong = rs.getString("hanhdong");
                list.add(new ChiTietQuyenDTO(maNhomQuyen, maChucNang, hanhDong));
            }
            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            throw e;
        }
        return list;
    }
}
