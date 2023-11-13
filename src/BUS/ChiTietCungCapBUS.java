/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

/**
 *
 * @author downny
 */
import DAO.ChiTietCungCapDAO;
import DTO.ChiTietCungCapDTO;
import java.util.ArrayList;

public class ChiTietCungCapBUS {
    ChiTietCungCapDAO ctccDAO = new  ChiTietCungCapDAO();
    public ArrayList<ChiTietCungCapDTO> getListChiTietCungCap(int id){
        return ctccDAO.getListChiTietCungCap(id);
    }
    
    public String addChiTietCungCap(ChiTietCungCapDTO ctcc){
        if(ctccDAO.insert(ctcc))
            return "Thêm thành công !"; 
        return "Thêm thất bại !"; 
    }
    
    public String deleteChiTietCungCap(ChiTietCungCapDTO ctcc){
        if(ctccDAO.delete(ctcc))
            return "Xóa thành công!";
        return "Xóa thất bại!";
    }
    
    public String updateChiTietCungCap(ChiTietCungCapDTO ctcc){
        if(ctccDAO.update(ctcc))
            return "Cập nhật thành công!";
        return "Cập nhật thất bại!";
    }
}
