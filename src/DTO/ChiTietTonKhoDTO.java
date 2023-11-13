package DTO;

public class ChiTietTonKhoDTO {
    private int maKho;
    private int maSanPham;
    private int soLuong;
    private int giaNhap;

    public ChiTietTonKhoDTO(int maKho, int maSanPham, int soLuong, int giaNhap) {
        this.maKho = maKho;
        this.maSanPham = maSanPham;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
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
