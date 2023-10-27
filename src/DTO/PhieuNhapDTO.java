package DTO;

public class PhieuNhapDTO {
    private int maPhieuNhap;
    private String thoiGianTao;
    private String nguoiTao;
    private int maNhaCungCap;
    private int tongTien;
    private int trangThai;

    public PhieuNhapDTO(int maPhieuNhap, String thoiGianTao, String nguoiTao, int maNhaCungCap, int tongTien, int trangThai) {
        this.maPhieuNhap = maPhieuNhap;
        this.thoiGianTao = thoiGianTao;
        this.nguoiTao = nguoiTao;
        this.maNhaCungCap = maNhaCungCap;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public int getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(int maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public String getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(String thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public int getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(int maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
