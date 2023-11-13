/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import DTO.ChiTietPhieuXuatDTO;
/**
 *
 * @author EV
 */
public class ChiTietPhieuXuatDAO {
    
    public static DAO.ChiTietPhieuXuatDAO getInstance() {
        return new DAO.ChiTietPhieuXuatDAO();
    }

  
    public int insert(ChiTietPhieuXuatDTO t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO chitietphieuxuat (maphieuxuat  , masanpham , soluong, dongia) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMaPhieuXuat());
            pst.setInt(2, t.getMaSanPham());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    public int update(ChiTietPhieuXuatDTO t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE ChiTietPhieuXuat SET maPhieu=?, maMay=?, soLuong=?, donGia = ?  WHERE maPhieu=? AND maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMaPhieuXuat());
            pst.setInt(2, t.getMaSanPham());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            pst.setInt(5, t.getMaPhieuXuat());
            pst.setInt(6, t.getMaSanPham());
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }


    public int delete(ChiTietPhieuXuatDTO t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "DELETE FROM ChiTietPhieuXuat WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMaPhieuXuat());
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

    
    
    public ArrayList<ChiTietPhieuXuatDTO> selectAll() {
        ArrayList<ChiTietPhieuXuatDTO> ketQua = new ArrayList<ChiTietPhieuXuatDTO>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM chitietphieuxuat";
            PreparedStatement pst = con.prepareStatement(sql);
//            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
             
            while (rs.next()) {
                int maPhieuXuat = rs.getInt("maphieuxuat");
                int maSanPham = rs.getInt("masanpham");
                int soLuong = rs.getInt("soluong");
                int donGia = rs.getInt("dongia");
                ChiTietPhieuXuatDTO ctp = new ChiTietPhieuXuatDTO(maPhieuXuat, maSanPham, soLuong, donGia);
                ketQua.add(ctp);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
    
    

    
    
//     public ArrayList<ChiTietPhieuXuatDTO> selectAll() {
//        ArrayList<ChiTietPhieuXuatDTO> ketQua = new ArrayList<ChiTietPhieuXuatDTO>();
//        try {
//            Connection con = JDBCUtil.getConnection();
//            String sql = "SELECT * FROM chitietphieuxuat WHERE maphieuxuat=?";
//            PreparedStatement pst = con.prepareStatement(sql);
////            pst.setString(1, t);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                int maPhieu = rs.getInt("maphieuxuat");
//                int maMay = rs.getInt("masanpham");
//                int soLuong = rs.getInt("soluong");
//                int donGia = rs.getInt("dongia");
//                ChiTietPhieuXuatDTO ctp = new ChiTietPhieuXuatDTO(maPhieu, maMay, soLuong, donGia);
//                ketQua.add(ctp);
//            }
//            JDBCUtil.closeConnection(con);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return ketQua;
//    }
    
    
    
    
    
    
    
    
//
//    public ArrayList<ChiTietPhieuXuatDTO> selectAll() {
//        ArrayList<ChiTietPhieuXuatDTO> ketQua = new ArrayList<ChiTietPhieuXuatDTO>();
//        try {
//            Connection con = JDBCUtil.getConnection();
//            String sql = "SELECT * FROM ChiTietPhieuXuat";
//            PreparedStatement pst = con.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                int maPhieu = rs.getInt("maPhieu");
//                int maMay = rs.getInt("maMay");
//                int soLuong = rs.getInt("soLuong");
//                int donGia = rs.getInt("donGia");
//                ChiTietPhieuXuatDTO ctp = new ChiTietPhieuXuatDTO(maPhieu, maMay, soLuong, donGia);
//                ketQua.add(ctp);
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return ketQua;
//    }

  
    public ChiTietPhieuXuatDTO selectById(int t) {
        ChiTietPhieuXuatDTO ketQua = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM chitietphieuxuat WHERE maphieuxuat=?" ;
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int maPhieu = rs.getInt("maphieuxuat");
                int maMay = rs.getInt("masanpham");
                int soLuong = rs.getInt("soluong");
                int donGia = rs.getInt("donGia");
                ketQua = new ChiTietPhieuXuatDTO(maPhieu, maMay, soLuong, donGia);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
}
