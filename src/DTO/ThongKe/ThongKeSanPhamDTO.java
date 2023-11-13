package DTO.ThongKe;

public class ThongKeSanPhamDTO {
    private int maSanPham;
    private String tenLoaiSanPham;
    private String tenSanPham;
    private int soLuongNhap;

    public ThongKeSanPhamDTO(int maSanPham, String tenLoaiSanPham, String tenSanPham, int soLuongNhap) {
        this.maSanPham = maSanPham;
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.tenSanPham = tenSanPham;
        this.soLuongNhap = soLuongNhap;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }
}
