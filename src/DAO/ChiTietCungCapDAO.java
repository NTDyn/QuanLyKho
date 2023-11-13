/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.ChiTietCungCapDTO;
import database.JDBCUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author downny
 */
public class ChiTietCungCapDAO {
     public static ChiTietCungCapDAO getInstance() {
        return new ChiTietCungCapDAO();
    }
    public ArrayList<ChiTietCungCapDTO> getListChiTietCungCap(int id){
        ArrayList<ChiTietCungCapDTO> list = new ArrayList<ChiTietCungCapDTO>();
        try {
            java.sql.Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM chitietcungcap WHERE trangthai = 1 and manhacungcap = " + id;
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {   
                ChiTietCungCapDTO ccsp = new ChiTietCungCapDTO();
                ccsp.setMaSanPham(rs.getInt("masanpham"));
                ccsp.setGiaNhap(rs.getInt("gianhap"));
                list.add(ccsp);
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return list;
    }
    
    public boolean insert(ChiTietCungCapDTO ct){
        boolean ketQua = false;
        try {
            java.sql.Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO chitietcungcap VALUES(?,?,?,1) ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, ct.getMaNhaCungCap());
            pst.setInt(2, ct.getMaSanPham());
            pst.setInt(3, ct.getGiaNhap());
            if(pst.executeUpdate() >= 1){
                ketQua = true;
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;  
    }
    
    public boolean delete(ChiTietCungCapDTO ct) {
        boolean ketQua = false;
        try {
            java.sql.Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE chitietcungcap SET trangthai = 0 WHERE manhacungcap = ? and masanpham = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, ct.getMaNhaCungCap());
            pst.setInt(2, ct.getMaSanPham());
            if(pst.executeUpdate() >= 1){
                ketQua = true;
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
    
    public boolean update(ChiTietCungCapDTO ct){
        boolean ketQua = false;
        try {
            java.sql.Connection con = JDBCUtil.getConnection();
            String sql ="UPDATE chitietcungcap SET gianhap = ? where manhacungcap = ? and masanpham = ? and trangthai = 1;";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, ct.getGiaNhap());
            pst.setInt(2, ct.getMaNhaCungCap());
            pst.setInt(3, ct.getMaSanPham());
            if(pst.executeUpdate() >= 1){
                ketQua = true;
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
}
