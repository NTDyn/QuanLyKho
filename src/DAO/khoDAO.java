/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.khoDTO;
import database.JDBCUtil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author trant
 */
public class khoDAO {
    
    public static khoDAO getInstance() {
        return new khoDAO();
    }
    
    public ArrayList<khoDTO> getListWareHouse() {
        ArrayList<khoDTO> listKho = new ArrayList<khoDTO>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM kho WHERE trangthai = 1";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                khoDTO kho = new khoDTO();
                kho.setMaKho(rs.getInt("makho"));
                kho.setTenKho(rs.getString("tenkho"));
                kho.setDiaChi(rs.getString("diachi"));
                listKho.add(kho);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return listKho;
    }
    
    public boolean addWareHouse(khoDTO kho){
        boolean result = false;
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO kho(tenkho,diachi,trangthai) VALUES(?,?,1)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, kho.getTenKho());
            stmt.setString(2, kho.getDiaChi());
            if(stmt.executeUpdate() >= 1){
                result = true;
            }
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean updateAddressWareHouse(khoDTO kho){
        boolean result = false;
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT diachi FROM kho WHERE trangthai = 1 and diachi = '" + kho.getDiaChi() + "' and makho not in (" + kho.getMaKho() + ")";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                result = true;
            }
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean updateNameWareHouse(khoDTO kho){
        boolean result = false;
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT tenkho FROM kho WHERE trangthai = 1 and tenkho = '" + kho.getTenKho()+ "' and makho not in (" + kho.getMaKho() + ")";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()){
                result = true;
            }
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public boolean updateWareHouse(khoDTO kho){
        boolean result = false;
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE kho SET tenkho = '" + kho.getTenKho() + "', diachi = '" + kho.getDiaChi() + "' WHERE trangthai =1 and makho = " + kho.getMaKho();
            Statement stmt = con.createStatement();
            
            if(stmt.executeUpdate(sql) >= 1){
                result = true;
            }
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public boolean deleteWareHouse(int makho){
        boolean result = false;

        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE kho SET trangthai = 0   WHERE makho = " + makho;
            Statement stmt = con.createStatement();

            if(stmt.executeUpdate(sql) >= 1){
                result = true;
            }

            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public khoDTO getWareHouseByName(String name){
        khoDTO kho = new khoDTO();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM kho WHERE trangthai = 1 and tenkho = '" + name + "'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                kho.setMaKho(rs.getInt("makho"));
                kho.setDiaChi(rs.getString("diachi"));
                kho.setTenKho(rs.getString("tenkho"));
            }
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kho;
    }
    
    public String getWareHouseByID(int id){
        String name = "";
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT tenkho FROM kho WHERE trangthai = 1 and makho = " + id ;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                name = rs.getString("tenkho");
            }
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
    
    public int getNumberOfProduct(int makho){
        int number = 0;
        try{
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT COUNT(DISTINCT CTCC.masanpham) AS 'soluong'  FROM phieunhap PN, chitietphieunhap CTPN, kho K, trangthaiphieunhap TTPN, chitietcungcap CTCC, sanpham SP WHERE PN.maphieunhap = CTPN.maphieunhap AND K.makho = PN.makho AND PN.trangthai = TTPN.matrangthai AND CTCC.masanpham = CTPN.masanpham AND CTCC.masanpham = SP.masanpham AND TTPN.tentrangthai LIKE '%delivered%' AND soluongtonkho > 0 AND K.makho = " +makho;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
               number = rs.getInt("soluong");
            }

            JDBCUtil.closeConnection(con);
        } catch(Exception e) {
             e.printStackTrace();
        }
        return number;
    }
    
    public ArrayList<khoDTO> searchTatCa(String text) {
        ArrayList<khoDTO> result = new ArrayList<>();
        ArrayList<khoDTO> allkho = khoDAO.getInstance().getListWareHouse();
        for (var kho : allkho) {

            if (kho.getTenKho().toLowerCase().contains(text.toLowerCase())
                        || kho.getDiaChi().toLowerCase().contains(text.toLowerCase())) {
                    result.add(kho);
            }

        }
        return result;
    }

    public ArrayList<khoDTO> searchTenKho(String text) {
        ArrayList<khoDTO> result = new ArrayList<>();
        ArrayList<khoDTO> allkho = khoDAO.getInstance().getListWareHouse();
        for (var kho : allkho) {

            if (kho.getTenKho().toLowerCase().contains(text.toLowerCase()))
            {
                    result.add(kho);
            }

        }
        return result;
    }

    public ArrayList<khoDTO> searchDiaDiem(String text) {
        ArrayList<khoDTO> result = new ArrayList<>();
        ArrayList<khoDTO> allkho = khoDAO.getInstance().getListWareHouse();
        for (var kho : allkho) {

            if (kho.getDiaChi().toLowerCase().contains(text.toLowerCase())) {
                    result.add(kho);
            }

        }
        return result;
    }
}
