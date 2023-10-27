/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author trant
 */
public class tonKhoDTO  {
    private int maKho;
    private int maSanPham;
    private int maNhaCungCap;
    private int soLuong;
    private int giaNhap;

    public tonKhoDTO(int maKho, int maSanPham, int maNhaCungCap, int soLuong, int giaNhap) {
        this.maKho = maKho;
        this.maSanPham = maSanPham;
        this.maNhaCungCap = maNhaCungCap;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
    }

    public tonKhoDTO() {
    }

    public int getMaKho() {
        return maKho;
    }

    public void setMaKho(int maKho) {
        this.maKho = maKho;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(int maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }
    
    
    
}
