package DTO;

public class PhieuNhapDTO {
    private int maPhieuNhap;
    private String thoiGianTao;
    private int maKho;
    private String nguoiTao;
    private int tongTien;
    private int trangThai;

    public PhieuNhapDTO(int maPhieuNhap, String thoiGianTao, int maKho, String nguoiTao, int tongTien, int trangThai) {
        this.maPhieuNhap = maPhieuNhap;
        this.thoiGianTao = thoiGianTao;
        this.maKho = maKho;
        this.nguoiTao = nguoiTao;
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
    
    public int getMaKho() {
        return maKho;
    }

    public void setMaKho(int maKho) {
        this.maKho = maKho;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
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
