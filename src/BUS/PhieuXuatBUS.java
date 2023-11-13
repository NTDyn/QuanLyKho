package BUS;

import DAO.PhieuXuatDAO;
import DTO.PhieuXuatDTO;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhieuXuatBUS {
    PhieuXuatDAO dao = new PhieuXuatDAO();

    public ArrayList<PhieuXuatDTO> getList() throws SQLException {
        return dao.selectAll();
    }

}
