package DTO.ThongKe;

public class ThongKeLoaiSanPhamDTO {
    private int maLoaiSanPham;
    private String tenLoaiSanPham;
    private int soLuong;

    public ThongKeLoaiSanPhamDTO(int maLoaiSanPham, String tenLoaiSanPham, int soLuong) {
        this.maLoaiSanPham = maLoaiSanPham;
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.soLuong = soLuong;
    }

    public int getMaLoaiSanPham() {
        return maLoaiSanPham;
    }

    public void setMaLoaiSanPham(int maLoaiSanPham) {
        this.maLoaiSanPham = maLoaiSanPham;
    }

    public String getTenLoaiSanPham() {
        return tenLoaiSanPham;
    }

    public void setTenLoaiSanPham(String tenLoaiSanPham) {
        this.tenLoaiSanPham = tenLoaiSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
