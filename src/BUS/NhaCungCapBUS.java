/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

/**
 *
 * @author downny
 */
import DTO.NhaCungCapDTO;
import DAO.NhaCungCapDAO;
import java.util.ArrayList;

public class NhaCungCapBUS {
    NhaCungCapDAO nccDAO = new NhaCungCapDAO();
    
    public ArrayList<NhaCungCapDTO> selectAll(){
        return nccDAO.selectAll();
    }
    
    public String addNhaCungCap(NhaCungCapDTO ncc){
        if(nccDAO.insert(ncc))
            return "Thêm thành công !"; 
        return "Thêm thất bại !"; 
    }
    
    public String deleteNhaCungCap(NhaCungCapDTO ncc){
        if(nccDAO.delete(ncc))
            return "Xóa thành công!";
        return "Xóa thất bại!";
    }
    
    public String updateNhaCungCap(NhaCungCapDTO ncc){
        if(nccDAO.update(ncc))
           return "Cập nhật thành công!";
        return "Cập nhật thất bại!";
    }
}
