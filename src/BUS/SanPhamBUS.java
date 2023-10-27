/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DTO.SanPhamDTO;
import DAO.SanPhamDAO;
import java.util.ArrayList;

/**
 *
 * @author trant
 */
public class SanPhamBUS {
    SanPhamDAO spDAO = new SanPhamDAO();
    
    public ArrayList<SanPhamDTO> getlistProducts(){
        return spDAO.getlistProduct();
    }
}
