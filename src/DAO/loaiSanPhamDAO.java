/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.loaiSanPhamDTO;
import database.JDBCUtil;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.sql.ResultSet;

/**
 *
 * @author trant
 */
public class loaiSanPhamDAO {
    
    public static loaiSanPhamDAO getInstance() {
        return new loaiSanPhamDAO();
    }
    public ArrayList<loaiSanPhamDTO> getTypeOfProduct(){
        ArrayList<loaiSanPhamDTO> list = new ArrayList<loaiSanPhamDTO>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql ="SELECT * FROM loaisanpham WHERE trangthai = 1";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                loaiSanPhamDTO loaiSP = new loaiSanPhamDTO();
                loaiSP.setMaLoaiSanPham(rs.getInt("maloaisanpham"));
                loaiSP.setTenLoaiSanPham(rs.getString("tenloaisanpham"));
                list.add(loaiSP);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            
        }
        return list;
    }
    
    public boolean addTypeOfProduct(String name){
        boolean result = false;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO loaisanpham(tenloaisanpham,trangthai) VALUES('" + name + "',1)";
            Statement stmt = con.createStatement();
            if(stmt.executeUpdate(sql) >= 1)
                result = true;
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
    public boolean deleteTypeOfProduct(int id){
        boolean result = false;
        try {
             Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE loaisanpham SET trangthai = 0 WHERE maloaisanpham = " + id;
            Statement stmt = con.createStatement();
            if(stmt.executeUpdate(sql) >= 1)
                result = true;
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
        
    public boolean editTypeOfProduct(String name, int id){
        boolean result = false;
        try {
             Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE loaisanpham SET tenloaisanpham = '" + name + "' WHERE maloaisanpham = " + id;
            Statement stmt = con.createStatement();
            if(stmt.executeUpdate(sql) >= 1)
                result = true;
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
    public boolean hasTypeOfProduct(String name){
        boolean result = false;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT tenloaisanpham FROM loaisanpham WHERE trangthai = 1 and tenloaisanpham = '" + name + "'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
                result = true;
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
    public boolean hasTypeOfProductEdit(String name, int id){
        boolean result = false;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT tenloaisanpham FROM loaisanpham WHERE trangthai = 1 and tenloaisanpham = '" + name + "' and  maloaisanpham not in("+ id + ")";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
                result = true;
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
    
    public ArrayList<loaiSanPhamDTO> searchTatCa(String text) {
        ArrayList<loaiSanPhamDTO> result = new ArrayList<>();
        ArrayList<loaiSanPhamDTO> allLSP = loaiSanPhamDAO.getInstance().getTypeOfProduct();
        for (var lsp : allLSP) {

            if (String.valueOf(lsp.getMaLoaiSanPham()).toLowerCase().contains(text.toLowerCase()) || lsp.getTenLoaiSanPham().toLowerCase().contains(text.toLowerCase())) {
                    result.add(lsp);
            }
            
        }
        return result;
    }
    
    public ArrayList<loaiSanPhamDTO> searchTenLoaiSanPham(String text) {
        ArrayList<loaiSanPhamDTO> result = new ArrayList<>();
        ArrayList<loaiSanPhamDTO> allLSP = loaiSanPhamDAO.getInstance().getTypeOfProduct();
        for (var lsp : allLSP) {
         
                if (lsp.getTenLoaiSanPham().toLowerCase().contains(text.toLowerCase())) {
                    result.add(lsp);
                }
            
        }
        return result;
    }
    
    public ArrayList<loaiSanPhamDTO> searchMaLoaiSanPham(String text) {
        ArrayList<loaiSanPhamDTO> result = new ArrayList<>();
        ArrayList<loaiSanPhamDTO> allLSP = loaiSanPhamDAO.getInstance().getTypeOfProduct();
        for (var lsp : allLSP) {
         
                if (String.valueOf(lsp.getMaLoaiSanPham()).toLowerCase().contains(text.toLowerCase())) {
                    result.add(lsp);
                }
            
        }
        return result;
    }
        
    public String getNameOfType(int id) {
        String name = "";
        try {
             Connection con = JDBCUtil.getConnection();
             String sql = "SELECT tenloaisanpham FROM loaisanpham WHERE trangthai = 1 and maloaisanpham = " + id;
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             while(rs.next()){
                 name = rs.getString("tenloaisanpham");
             }
             JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return name;
    }
    
    public int getIDOfType(String name) {
        int id = -1;
        try {
             Connection con = JDBCUtil.getConnection();
             String sql = "SELECT maloaisanpham FROM loaisanpham WHERE trangthai = 1 and tenloaisanpham = '" + name + "'";
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
             while(rs.next()){
                 id = rs.getInt("maloaisanpham");
             }
             JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }
    
    public boolean hasProduct(int id){
        boolean result = false;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sanpham WHERE trangthai = 1 and maloaisanpham = " + id ;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next())
                result = true;
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }
}

