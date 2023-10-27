package DTO;

public class ChucNangDTO {
    private String maChucNang;
    private String tenChucNang;
    private int trangThai;

    public ChucNangDTO(String maChucNang, String tenChucNang, int trangThai) {
        this.maChucNang = maChucNang;
        this.tenChucNang = tenChucNang;
        this.trangThai = trangThai;
    }

    public String getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(String maChucNang) {
        this.maChucNang = maChucNang;
    }

    public String getTenChucNang() {
        return tenChucNang;
    }

    public void setTenChucNang(String tenChucNang) {
        this.tenChucNang = tenChucNang;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
