package DTO;

public class loaiSanPhamDTO {
    private int maLoaiSanPham;
    private String tenLoaiSanPham;
    private int trangThai;
    
    public loaiSanPhamDTO() {
        
    }

    public loaiSanPhamDTO(int maLoaiSanPham, String tenLoaiSanPham, int trangThai) {
        this.maLoaiSanPham = maLoaiSanPham;
        this.tenLoaiSanPham = tenLoaiSanPham;
        this.trangThai = trangThai;
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

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
