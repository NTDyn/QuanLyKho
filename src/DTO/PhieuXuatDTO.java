package DTO;

public class PhieuXuatDTO {
    private int maPhieuXuat;
    private String thoiGianTao;
    private String nguoiTao;
    private int tongTien;
    private int trangThai;

    public PhieuXuatDTO(int maPhieuXuat, String thoiGianTao, String nguoiTao, int tongTien, int trangThai) {
        this.maPhieuXuat = maPhieuXuat;
        this.thoiGianTao = thoiGianTao;
        this.nguoiTao = nguoiTao;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public int getMaPhieuXuat() {
        return maPhieuXuat;
    }

    public void setMaPhieuXuat(int maPhieuXuat) {
        this.maPhieuXuat = maPhieuXuat;
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
