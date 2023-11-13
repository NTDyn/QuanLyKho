/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DTO.tonKhoDTO;
import database.JDBCUtil;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author trant
 */
public class tonKhoDAO {
    
    public static tonKhoDAO getInstance(){
        return new tonKhoDAO();
    }
    
    public ArrayList<tonKhoDTO> getTonKho(int makho){
        ArrayList<tonKhoDTO> list = new ArrayList<tonKhoDTO>();
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT DISTINCT K.makho, CTPN.masanpham, SP.tensanpham, soluongtonkho FROM phieunhap PN, chitietphieunhap CTPN, kho K, trangthaiphieunhap TTPN, chitietcungcap CTCC, sanpham SP WHERE PN.maphieunhap = CTPN.maphieunhap AND K.makho = PN.makho AND PN.trangthai = TTPN.matrangthai AND CTCC.masanpham = CTPN.masanpham AND CTCC.masanpham = SP.masanpham AND TTPN.tentrangthai LIKE '%delivered%' AND soluongtonkho > 0 AND K.makho = " +makho+ " ORDER BY masanpham;";
            Statement stmt =  con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                tonKhoDTO tonkho = new tonKhoDTO();
                tonkho.setMaKho(rs.getInt("K.makho"));
                tonkho.setMaSanPham(rs.getInt("CTPN.masanpham"));
                tonkho.setSoLuong(rs.getInt("soluongtonkho"));
                list.add(tonkho);
            }
        } catch (Exception e) {
        }
        return list;
    }
    
    public ArrayList<tonKhoDTO> getDetailTonKho(int masanpham, int makho ){
        ArrayList<tonKhoDTO> list = new ArrayList<tonKhoDTO>();
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT DISTINCT K.makho, PN.maphieunhap, CTPN.masanpham, SP.tensanpham, NCC.manhacungcap, soluongtonkho, CTPN.dongia FROM phieunhap PN, chitietphieunhap CTPN, kho K, trangthaiphieunhap TTPN, chitietcungcap CTCC, sanpham SP, nhacungcap NCC  WHERE PN.maphieunhap = CTPN.maphieunhap AND K.makho = PN.makho AND PN.trangthai = TTPN.matrangthai AND CTCC.masanpham = CTPN.masanpham AND CTCC.masanpham = SP.masanpham AND CTPN.manhacungcap = CTCC.manhacungcap AND CTCC.manhacungcap = NCC.manhacungcap AND TTPN.tentrangthai LIKE '%delivered%' AND soluongtonkho > 0 AND CTPN.masanpham = " + masanpham + " AND K.makho = " + makho + " ORDER BY masanpham;";
            Statement stmt =  con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                tonKhoDTO tonkho = new tonKhoDTO();
                tonkho.setMaKho(rs.getInt("K.makho"));
                tonkho.setMaSanPham(rs.getInt("CTPN.masanpham"));
                tonkho.setSoLuong(rs.getInt("soluongtonkho"));
                tonkho.setGiaNhap(rs.getInt("CTPN.dongia"));
                tonkho.setMaNhaCungCap(rs.getInt("NCC.manhacungcap"));
                list.add(tonkho);
            }
        } catch (Exception e) {
        }
        return list;
    }
}
