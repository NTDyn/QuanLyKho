package BUS;

import DAO.NhomQuyenDAO;
import DTO.NhomQuyenDTO;
import java.sql.SQLException;

public class NhomQuyenBUS {
    private final NhomQuyenDAO nqDAO = new NhomQuyenDAO();

    public NhomQuyenBUS() {
    }

    public NhomQuyenDTO getPermissionById(int maNhomQuyen) throws SQLException {
        return nqDAO.getPermissionById(maNhomQuyen);
    }
}
