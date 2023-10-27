package BUS;

import DAO.ChiTietQuyenDAO;
import DTO.ChiTietQuyenDTO;
import java.sql.SQLException;
import java.util.List;

public class ChiTietQuyenBUS {
    private final ChiTietQuyenDAO ctqDAO = new ChiTietQuyenDAO();
    
    public ChiTietQuyenBUS() {
    }
    
    public List<ChiTietQuyenDTO> getAuthorization(int maNhomQuyen) throws SQLException {
        return ctqDAO.getAuthorization(maNhomQuyen);
    }
    
    public List<ChiTietQuyenDTO> getAllowedActions(int maNhomQuyen, String maChucNang) throws SQLException {
        return ctqDAO.getAllowedActions(maNhomQuyen, maChucNang);
    }
}
