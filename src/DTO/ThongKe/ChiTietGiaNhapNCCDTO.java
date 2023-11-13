package DTO.ThongKe;

import java.time.LocalDateTime;

public class ChiTietGiaNhapNCCDTO {
    private int maPhieuNhap;
    private LocalDateTime thoiGianTao;
    private int soLuongNhap;
    private Long donGiaNhap;

    public ChiTietGiaNhapNCCDTO(int maPhieuNhap, LocalDateTime thoiGianTao, int soLuongNhap, Long donGiaNhap) {
        this.maPhieuNhap = maPhieuNhap;
        this.thoiGianTao = thoiGianTao;
        this.soLuongNhap = soLuongNhap;
        this.donGiaNhap = donGiaNhap;
    }

    public int getMaPhieuNhap() {
        return maPhieuNhap;
    }

    public void setMaPhieuNhap(int maPhieuNhap) {
        this.maPhieuNhap = maPhieuNhap;
    }

    public LocalDateTime getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(LocalDateTime thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public int getSoLuongNhap() {
        return soLuongNhap;
    }

    public void setSoLuongNhap(int soLuongNhap) {
        this.soLuongNhap = soLuongNhap;
    }

    public Long getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(Long donGiaNhap) {
        this.donGiaNhap = donGiaNhap;
    }
}
