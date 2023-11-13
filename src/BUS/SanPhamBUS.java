package BUS;

import DTO.SanPhamDTO;
import DAO.SanPhamDAO;
import java.util.ArrayList;

public class SanPhamBUS {
    SanPhamDAO spDAO = new SanPhamDAO();
    
    public ArrayList<SanPhamDTO> getlistProducts(){
        return spDAO.getlistProduct();
    }
}
