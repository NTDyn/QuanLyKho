package DTO.ThongKe;

import java.time.LocalDateTime;

public class ChiTietGiaXuatSPDTO {
    private int maPhieuXuat;
    private LocalDateTime thoiGianTao;
    private int soLuongXuat;
    private Long donGiaXuat;

    public ChiTietGiaXuatSPDTO(int maPhieuXuat, LocalDateTime thoiGianTao, int soLuongXuat, Long donGiaXuat) {
        this.maPhieuXuat = maPhieuXuat;
        this.thoiGianTao = thoiGianTao;
        this.soLuongXuat = soLuongXuat;
        this.donGiaXuat = donGiaXuat;
    }

    public int getMaPhieuXuat() {
        return maPhieuXuat;
    }

    public void setMaPhieuXuat(int maPhieuXuat) {
        this.maPhieuXuat = maPhieuXuat;
    }

    public LocalDateTime getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(LocalDateTime thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public int getSoLuongXuat() {
        return soLuongXuat;
    }

    public void setSoLuongXuat(int soLuongXuat) {
        this.soLuongXuat = soLuongXuat;
    }

    public Long getDonGiaXuat() {
        return donGiaXuat;
    }

    public void setDonGiaXuat(Long donGiaXuat) {
        this.donGiaXuat = donGiaXuat;
    }
}
