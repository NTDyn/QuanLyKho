/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ChiTietPhieuXuatDAO;
import DTO.ChiTietPhieuXuatDTO;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author EV
 */
public class ChiTietPhieuXuatBUS {
    ChiTietPhieuXuatDAO dao = new ChiTietPhieuXuatDAO();

    public ArrayList<ChiTietPhieuXuatDTO> getList() throws SQLException {
        return dao.selectAll();
    }

}
