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
public class khoDAO  {
    
    public static khoDAO getInstance() {
        return new khoDAO();
    }
    
    public ArrayList<khoDTO> getListWareHouse(){
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
                kho.setDiaDiem(rs.getString("diachi"));
                listKho.add(kho);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return listKho;
    }
    
    public boolean  addWareHouse(khoDTO kho){
        boolean result = false;
        
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO kho(tenkho,diachi,trangthai) VALUES(?,?,1)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, kho.getTenKho());
            stmt.setString(2, kho.getDiaDiem());
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
            String sql = "SELECT diachi FROM kho WHERE trangthai = 1 and diachi = '" + kho.getDiaDiem() + "' and makho not in (" + kho.getMaKho() + ")";
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
            String sql = "UPDATE kho SET tenkho = '" + kho.getTenKho() + "' and diachi = '" + kho.getDiaDiem() + "' WHERE makho = " + kho.getMaKho();
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
                kho.setDiaDiem(rs.getString("diachi"));
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
}
