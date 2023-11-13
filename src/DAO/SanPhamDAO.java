/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.SanPhamDTO;
import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author trant
 */
public class SanPhamDAO {
    
    public static SanPhamDAO getInstance() {
        return new SanPhamDAO();
    }
    public ArrayList<SanPhamDTO> getlistProduct(){
        ArrayList<SanPhamDTO> list = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sanpham WHERE trangthai = 1";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                SanPhamDTO sp = new SanPhamDTO();
                sp.setMaSanPham( rs.getInt("masanpham"));
                sp.setLoaiSanPham(rs.getInt("maloaisanpham"));
                sp.setTenSanPham(rs.getString("tensanpham"));
                sp.setSoLuong( rs.getInt("soluong"));
                sp.setGiaXuat(rs.getInt("giaxuat"));
                sp.setCpu(rs.getString("cpu"));
                sp.setRam( rs.getString("ram"));
                sp.setVga( rs.getString("vga"));
                sp.setoCung( rs.getString("ocung"));
                sp.setManHinh( rs.getString("manhinh"));
                sp.setPin( rs.getString("pin"));
                sp.setMauSac( rs.getString("mausac"));
                sp.setTrongLuong( rs.getFloat("trongluong"));
                sp.setOs( rs.getString("os"));
                list.add(sp);
            }
            JDBCUtil.closeConnection(con);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public SanPhamDTO selectProductByID(int id){
        SanPhamDTO spDTO = null;
        
        try{
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sanpham WHERE trangthai = 1 and masanpham = " + id  ;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int maMay = id;
                String tenMay = rs.getString("tensanpham");
                int loaiSP = rs.getInt("maloaisanpham");
                int soLuong = rs.getInt("soluong");
                String cpu = rs.getString("cpu");
                String ram = rs.getString("ram");
                String vga = rs.getString("vga");
                int giaXuat = rs.getInt("giaxuat");
                String manHinh = rs.getString("manhinh");
                String pin = rs.getString("pin");
                String mauSac = rs.getString("mausac");
                String os = rs.getString("os");
                String oCung = rs.getString("ocung");
                Float trongLuong = rs.getFloat("trongluong");
                

                spDTO = new SanPhamDTO(maMay, tenMay, loaiSP, soLuong, giaXuat, cpu, ram, vga, oCung, manHinh, 
                        pin, trongLuong, mauSac, os);

            }
            JDBCUtil.closeConnection(con);
        } catch(Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return spDTO;
    }

    public SanPhamDTO selectProductByName(String name){
        SanPhamDTO spDTO = null;
        try{
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sanpham WHERE trangthai = 1 and  tensanpham = '" + name + "'"  ;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                int maMay = rs.getInt("masanpham");
                String tenMay = rs.getString("tensanpham");
                int loaiSP = rs.getInt("maloaisanpham");
                int soLuong = rs.getInt("soluong");
                String tenCpu = rs.getString("cpu");
                String ram = rs.getString("ram");
                String vga = rs.getString("vga");
                int gia = rs.getInt("giaxuat");
                String kichThuocMan = rs.getString("manhinh");
                String dungLuongPin = rs.getString("pin");
                String mausac = rs.getString("mausac");
                String os = rs.getString("os");
                String ocung = rs.getString("ocung");
                Float trongLuong = rs.getFloat("trongluong");
                int nhacungcap = 1;
                
                spDTO = new SanPhamDTO(maMay, tenMay, loaiSP, soLuong, gia, tenCpu, ram, vga, ocung, kichThuocMan, dungLuongPin, trongLuong, mausac, os);
            }
            JDBCUtil.closeConnection(con);
        } catch(Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return spDTO;
    }
    
    public boolean addProduct(SanPhamDTO sp){
        boolean result = false;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "INSERT INTO sanpham( `maloaisanpham`, `tensanpham`, `soluong`, `gianhap`, `giaxuat`, `cpu`, `ram`, `vga`, `ocung`, `manhinh`, `pin`, `trongluong`, `mausac`, `os`, `trangthai`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,1)";
            PreparedStatement presttm = con.prepareStatement(sql);
            presttm.setInt(1, sp.getLoaiSanPham());
            presttm.setString(2, sp.getTenSanPham());
            presttm.setInt(3, sp.getSoLuong());
            presttm.setInt(4, 0);
            presttm.setInt(5, sp.getGiaXuat());
            presttm.setString(6, sp.getCpu());
            presttm.setString(7, sp.getRam());
            presttm.setString(8, sp.getVga());
            presttm.setString(9, sp.getoCung());
            presttm.setString(10, sp.getManHinh());
            presttm.setString(11, sp.getPin());
            presttm.setFloat(12, sp.getTrongLuong());
            presttm.setString(13, sp.getMauSac());
            presttm.setString(14, sp.getOs());
            if(presttm.executeUpdate() >= 1){
                result = true;
            }
            
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    public int deleteProduct(int id) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE sanpham SET trangthai = 0 WHERE masanpham = ? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            ketQua = pst.executeUpdate();

            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
    
    public int updateProduct(SanPhamDTO sp) {
        int ketqua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "UPDATE sanpham SET tensanpham = ? , maloaisanpham = ?, giaxuat = ? , cpu = ?, ram = ?, mausac = ?, manhinh=  ?, vga = ?, ocung = ?, trongluong = ? , os = ? WHERE masanpham=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, sp.getTenSanPham());
            pst.setInt(2, sp.getLoaiSanPham());
            pst.setInt(3, sp.getGiaXuat());
            pst.setString(4, sp.getCpu());
            pst.setString(5, sp.getRam());
            pst.setString(6, sp.getMauSac());
            pst.setString(7, sp.getManHinh());
            pst.setString(8, sp.getVga());
            pst.setString(9, sp.getoCung());
            pst.setFloat(10, sp.getTrongLuong());
            pst.setString(11, sp.getOs());
            pst.setInt(12, sp.getMaSanPham());
            ketqua = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ketqua;
    }
    
    public String getNameByID(int masanpham) {
        String name = "";
        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT tensanpham FROM sanpham WHERE trangthai = 1 AND masanpham = " + masanpham;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                name = rs.getString("tensanpham");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
    
    
    
    
    public SanPhamDTO selectByIdPX(int t) {
        SanPhamDTO ketQua = null;
        ArrayList<SanPhamDTO> list = new ArrayList<SanPhamDTO>();

        try {
            Connection con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM sanpham WHERE masanpham = " + t;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {

                int setMaSanPham = rs.getInt("masanpham");
                int setMaLoaiSanPham = rs.getInt("maloaisanpham");
                String setTenSanPham = rs.getString("tensanpham");
                int setLoaiSanPham = rs.getInt("maloaisanpham");
                int setSoLuong = rs.getInt("soluong");
                int setGiaXuat = rs.getInt("giaxuat");
                String setCpu = rs.getString("cpu");
                String setRam = rs.getString("ram");
                String setVga = rs.getString("vga");
                String setoCung = rs.getString("ocung");
                String setManHinh = rs.getString("manhinh");
                String setPin = rs.getString("pin");
                String setMauSac = rs.getString("mausac");
                float setTrongLuong = rs.getFloat("trongluong");
                String setOs = rs.getString("os");

                ketQua = new SanPhamDTO(setMaSanPham, setMaLoaiSanPham, setTenSanPham, setLoaiSanPham, setSoLuong, setGiaXuat, setCpu, setRam, setVga, setoCung, setManHinh, setPin, setTrongLuong, setMauSac, setOs);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ketQua;
    }
    
      public int updateSoLuongPX(int maMay, int soluong) {
        int ketQua = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            //String sql = "INSERT INTO MayTinh (maMay, tenMay, soLuong, tenCpu, ram, cardManHinh, gia, dungLuongPin, dungLuongPin, dungLuongPin, loaiMay, rom) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            String sql = "UPDATE sanpham SET soluong=? WHERE masanpham=? ";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, soluong);
            pst.setInt(2, maMay);
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ketQua;
    }
    
    
}
