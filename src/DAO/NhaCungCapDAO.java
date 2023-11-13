/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.DAOInterface;
import DTO.NhaCungCapDTO;
import DTO.ChiTietCungCapDTO;
import com.sun.jdi.connect.spi.Connection;
import database.JDBCUtil;
import java.sql.Date;
import java.util.ArrayList;
import model.NhaCungCap;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import model.Phieu;
import org.apache.poi.hwmf.record.HwmfTernaryRasterOp;

/**
 *
 * @author kali
 */
public class NhaCungCapDAO{

    public static NhaCungCapDAO getInstance() {
        return new NhaCungCapDAO();
    }

    
    public boolean insert(NhaCungCapDTO t) {
        boolean ketQua = false;
        try {
            java.sql.Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO nhacungcap ( `tennhacungcap`, `sdt`, `diachi`)  VALUES (?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
              pst.setString(1, t.getTenNhaCungCap());
              pst.setString(2, t.getSdt());
              pst.setString(3, t.getDiaChi());
              if(pst.executeUpdate() >= 1){
                ketQua = true;
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;

    }

  
    public boolean update(NhaCungCapDTO t) {
        boolean ketQua = false;
        try {
            java.sql.Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE NhaCungCap SET maNhaCungCap=?, tenNhaCungCap=?, Sdt=?, diaChi=? WHERE maNhaCungCap=? and trangthai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, t.getMaNhaCungCap());
            pst.setString(2, t.getTenNhaCungCap());
            pst.setString(3, t.getSdt());
            pst.setString(4, t.getDiaChi());
            pst.setInt(5, t.getMaNhaCungCap());
            if(pst.executeUpdate() >= 1){
                ketQua = true;
            }
//            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;

    }

  
    public boolean delete(NhaCungCapDTO t) {
        boolean ketQua = false;
        try {
            java.sql.Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE NhaCungCap SET trangthai = 0 WHERE maNhaCungCap=?";
            PreparedStatement pst = con.prepareStatement(sql);
//            pst.setString(1, t.getMaNhaCungCap());
            pst.setInt(1, t.getMaNhaCungCap());
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


    public ArrayList<NhaCungCapDTO> selectAll() {
        ArrayList<NhaCungCapDTO> ketQua = new ArrayList<NhaCungCapDTO>();
        try {
            java.sql.Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM nhacungcap WHERE trangthai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {   
                NhaCungCapDTO ncc = new NhaCungCapDTO();
                ncc.setMaNhaCungCap(rs.getInt("manhacungcap"));
                ncc.setTenNhaCungCap(rs.getString("tennhacungcap"));
                ncc.setSdt(rs.getString("sdt"));
                ncc.setDiaChi(rs.getString("diachi"));
                ketQua.add(ncc);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }


    
    public NhaCungCapDTO selectById(String t) {
        NhaCungCapDTO ketQua = null;
        try {
            java.sql.Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM nhacungcap WHERE manhacungcap =? and trangthai = 1";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int maNhaCungCap = rs.getInt("manhacungcap");
                String tenNhaCungCap = rs.getString("tennhacungcap");
                String sdt = rs.getString("sdt");
                String diaChi = rs.getString("diachi");
//                ketQua = new NhaCungCapDTO(maNhaCungCap, tenNhaCungCap, sdt, diaChi);
                ketQua = new NhaCungCapDTO(maNhaCungCap, tenNhaCungCap, sdt, diaChi);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
    
    public boolean hasSDT(String sdt){
        boolean result = false;
        
            try {
                java.sql.Connection con = JDBCUtil.getConnection();
                String sql = "SELECT * FROM nhacungcap WHERE trangthai = 1 and sdt = '" + sdt + "'";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                result = rs.next();
                JDBCUtil.closeConnection(con);
            } catch (Exception e) {
                System.out.println(e);
            } 
        return result;
    }
    
    public boolean hasSDTException(NhaCungCapDTO ncc){
        boolean result = false;
        
            try {
                java.sql.Connection con = JDBCUtil.getConnection();
                String sql = "SELECT sdt FROM nhacungcap WHERE trangthai = 1 and sdt = '" + ncc.getSdt() + "' and manhacungcap not in (" + ncc.getMaNhaCungCap() + ")";
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery();
                result = rs.next();
                JDBCUtil.closeConnection(con);
            } catch (Exception e) {
                System.out.println(e);
            } 
        return result;
    }
    
    public boolean hasDiaChi(String diachi){
        boolean result = false;
        try {
            java.sql.Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM nhacungcap WHERE trangthai = 1 and diachi = '" + diachi + "'";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            result = rs.next();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.err.println(e);
        }
        return result;
        
    }
    
    public boolean hasDiaChiException(NhaCungCapDTO ncc){
         boolean result = false;
        try {
            java.sql.Connection con = JDBCUtil.getConnection();
            String sql = "SELECT diachi FROM nhacungcap WHERE trangthai = 1 and diachi = '" + ncc.getDiaChi() + "' and manhacungcap not in (" + ncc.getMaNhaCungCap() + ")";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            result = rs.next();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.err.println(e);
        }
        return result;
    }
    
    
}
