package DTO;

public class ChiTietCungCapDTO {
    private int maNhaCungCap;
    private int maSanPham;
    private int giaNhap;
    private int trangThai;
    private String tenSanPham;
    
    public ChiTietCungCapDTO() {
        
    }

    public ChiTietCungCapDTO(int maNhaCungCap, int maSanPham, int giaNhap, int trangThai) {
        this.maNhaCungCap = maNhaCungCap;
        this.maSanPham = maSanPham;
        this.giaNhap = giaNhap;
        this.trangThai = trangThai;
    }

    public int getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(int maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public int getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(int giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }
}
