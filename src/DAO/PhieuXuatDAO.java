/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.JDBCUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import DTO.PhieuXuatDTO;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import DTO.PhieuXuatDTO;

/**
 *
 * @author EV
 */
public class PhieuXuatDAO {

//    public static PhieuXuatDAO getInstance() {
//        return new PhieuXuatDAO();
//    }
//
//    public ArrayList<PhieuXuatDTO> getAllPhieuXuat() {
//        ArrayList<PhieuXuatDTO> list = new ArrayList<PhieuXuatDTO>();
//        try {
//            Connection con = JDBCUtil.getConnection();
//            String sql = "SELECT * FROM phieuxuat WHERE trangthai = 1";
//            Statement stmt = con.createStatement();
//            ResultSet rs = stmt.executeQuery(sql);
//            while (rs.next()) {
//                int maphieuxuat = rs.getInt("maphieuxuat");
//                String thoigiantao = rs.getString("thoigiantao");
//                String nguoitao = rs.getString("nguoitao");
//                int tongtien = rs.getInt("tongtien");
//                int trangthai = rs.getInt("trangthai");
//                PhieuXuatDTO phieuxuat = new PhieuXuatDTO(maphieuxuat, thoigiantao, nguoitao, tongtien, trangthai);
//                list.add(phieuxuat);
//            }
//            JDBCUtil.closeConnection(con);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }
//
//    
////    	public boolean checkExistById(int id) throws SQLException {
////		boolean isExist = false;
////		 Connection con = JDBCUtil.getConnection();
////		PreparedStatement psm = con
////				.prepareStatement("SELECT * FROM phieuxuat WHERE product_id = ? AND trangthai = 1;");
////		psm.setInt(1, id);
////		ResultSet rs = psm.executeQuery();
////		if (rs.next()) {
////			isExist = true;
////		}
////		return isExist;
////	}
//
//    
//    public boolean create(PhieuXuatDTO newObj) throws SQLException {
//          Connection con = JDBCUtil.getConnection();
//          
//          
//            System.out.println(newObj);
//          
//          
//        try {
//            String query = "INSERT INTO phieuxuat(thoigiantao, nguoitao , tongtien)"
//                    + "VALUES (?, ?, ?);";
//            PreparedStatement ptm = con.prepareStatement(query);
//            ptm.setString(1, newObj.getThoiGianTao());
//            ptm.setString(2, newObj.getNguoiTao());
//            ptm.setInt(3, newObj.getTongTien());
//
//            int result = ptm.executeUpdate();
//            JDBCUtil.closeConnection(con);
//            return result > 0;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
////     public static void main(String[] args){
////        PhieuXuatDAO phieuxuatdao = new PhieuXuatDAO();
////        ArrayList<PhieuXuatDTO> l = new ArrayList<>();
////        l = phieuxuatdao.getAllPhieuXuat();
////         System.out.println(  l);
////        
////     }
//    
//    
//    public int insert(PhieuXuatDTO t) {
//        int ketQua = 0;
//        try {
//            Connection con = JDBCUtil.getConnection();
//            String sql = "INSERT INTO PhieuXuat (maphieuxuat , thoigiantao, nguoitao , tongtien) VALUES (?,?,?,?)";
//            PreparedStatement pst = con.prepareStatement(sql);
//            pst.setInt(1, t.getMaPhieuXuat());
//            pst.setString(2, t.getThoiGianTao());
//            pst.setString(3, t.getNguoiTao());
//            pst.setDouble(4, t.getTongTien());
//            ketQua = pst.executeUpdate();
//            JDBCUtil.closeConnection(con);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return ketQua;
//    }
    
    public static DAO.PhieuXuatDAO getInstance() {
        return new DAO.PhieuXuatDAO();
    }
    
    public ArrayList<PhieuXuatDTO> selectAll() {
        ArrayList<PhieuXuatDTO> list = new ArrayList<PhieuXuatDTO>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM phieuxuat WHERE trangthai = 1";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int maphieuxuat = rs.getInt("maphieuxuat");
                String thoigiantao = rs.getString("thoigiantao");
                String nguoitao = rs.getString("nguoitao");
                int tongtien = rs.getInt("tongtien");
                int trangthai = rs.getInt("trangthai");
                PhieuXuatDTO phieuxuat = new PhieuXuatDTO(maphieuxuat, thoigiantao, nguoitao, tongtien, trangthai);
                list.add(phieuxuat);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public int insert(PhieuXuatDTO t) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO phieuxuat (maphieuxuat , thoigiantao, nguoitao , tongtien) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMaPhieuXuat());
            pst.setString(2, t.getThoiGianTao());
            pst.setString(3, t.getNguoiTao());
            pst.setDouble(4, t.getTongTien());
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }

        public PhieuXuatDTO selectById(int t) {
        PhieuXuatDTO ketQua = null;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM phieuxuat WHERE maphieuxuat=" + t;       
             Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int maPhieu = rs.getInt("maphieuxuat");
                String thoiGianTao = rs.getString("thoigiantao");
                String nguoiTao = rs.getString("nguoitao");
                int tongTien = rs.getInt("tongtien");
                ketQua = new PhieuXuatDTO(maPhieu, thoiGianTao, nguoiTao , tongTien, 1);
            }
             JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
    
 
    
    
//    public int update(PhieuXuatDTO t) {
//        int ketQua = 0;
//        try {
//            Connection con = JDBCUtil.getConnection();
//            String sql = "UPDATE phieuxuat SET maphieuxuat=?, thoigiantao=?, nguoitao=?, tongtien = ? WHERE maphieuxuat=?";
//            PreparedStatement pst = con.prepareStatement(sql);
//            pst.setInt(1, t.getMaPhieuXuat());
//            pst.setString(2, t.getThoiGianTao());
//            pst.setString(3, t.getNguoiTao());
//            pst.setDouble(4, t.getTongTien());
//            pst.setInt(5, t.getMaPhieuXuat());
//            ketQua = pst.executeUpdate();
//            JDBCUtil.closeConnection(con);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return ketQua;
//    }

//    public int delete(PhieuXuatDTO t) {
//        int ketQua = 0;
//        try {
//            Connection con = JDBCUtil.getConnection();
//            String sql = "DELETE FROM PhieuXuat WHERE maPhieu=?";
//            PreparedStatement pst = con.prepareStatement(sql);
//            pst.setInt(1, t.getMaPhieuXuat());
//            ketQua = pst.executeUpdate();
//            JDBCUtil.closeConnection(con);
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return ketQua;
//    }


      
    
    
    
    
    
    
    
    
    
    
//    public ArrayList<PhieuXuatDTO> selectAll() {
//        ArrayList<PhieuXuatDTO> ketQua = new ArrayList<PhieuXuatDTO>();
//        try {
//
//            Connection con = JDBCUtil.getConnection();
//            String sql = "SELECT * FROM phieuxuat ORDER BY thoiGianTao DESC";
//            PreparedStatement pst = con.prepareStatement(sql);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                int maPhieu = rs.getInt("maphieuxuat");
//                String thoiGianTao = rs.getString("thoigiantao");
//                String nguoiTao = rs.getString("nguoitao");
//                int tongTien = rs.getInt("tongtien");
//                int trangthai = rs.getInt("trangthai");
//
//                PhieuXuatDTO phieuxuat = new PhieuXuatDTO(maPhieu, thoiGianTao, nguoiTao, tongTien, trangthai);
////                PhieuXuatDTO p = new PhieuXuatDTO(maPhieu, thoiGianTao,
////                        nguoiTao, 
////                        DAO.ChiTietPhieuXuatDAO.getInstance().selectAll(String.valueOf(maPhieu))),
////                        tongTien);
////                ketQua.add(p);
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return ketQua;
//    }

//    public PhieuXuatDTO selectById(int t) {
//        PhieuXuatDTO ketQua = null;
//        try {
//            Connection con = JDBCUtil.getConnection();
//            String sql = "SELECT * FROM phieuxuat WHERE maPhieu=?";
//            PreparedStatement pst = con.prepareStatement(sql);
//            pst.setInt(1, t);
//            ResultSet rs = pst.executeQuery();
//            while (rs.next()) {
//                int maPhieu = rs.getInt("maphieuxuat");
//                String thoiGianTao = rs.getString("thoigiantao");
//                String nguoiTao = rs.getString("nguoitao");
//                double tongTien = rs.getDouble("tongtien");
////                ketQua = new PhieuXuatDTO(maPhieu, thoiGianTao, nguoiTao, DAO.ChiTietPhieuXuatDAO.getInstance().selectById(String.valueOf(maPhieu)), tongTien);
//            }
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//        }
//        return ketQua;
//    }

}
