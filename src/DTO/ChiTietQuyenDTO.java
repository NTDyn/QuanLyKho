package DTO;

public class ChiTietQuyenDTO {
    private int maNhomQuyen;
    private String maChucNang;
    private String hanhDong;
    private String hanChe;

    public ChiTietQuyenDTO(int maNhomQuyen, String maChucNang, String hanhDong) {
        this.maNhomQuyen = maNhomQuyen;
        this.maChucNang = maChucNang;
        this.hanhDong = hanhDong;
    }

    public ChiTietQuyenDTO(int maNhomQuyen, String maChucNang, String hanhDong, String hanChe) {
        this.maNhomQuyen = maNhomQuyen;
        this.maChucNang = maChucNang;
        this.hanhDong = hanhDong;
        this.hanChe = hanChe;
    }

    public int getMaNhomQuyen() {
        return maNhomQuyen;
    }

    public void setMaNhomQuyen(int maNhomQuyen) {
        this.maNhomQuyen = maNhomQuyen;
    }

    public String getMaChucNang() {
        return maChucNang;
    }

    public void setMaChucNang(String maChucNang) {
        this.maChucNang = maChucNang;
    }

    public String getHanhDong() {
        return hanhDong;
    }

    public void setHanhDong(String hanhDong) {
        this.hanhDong = hanhDong;
    }

    public String getHanChe() {
        return hanChe;
    }

    public void setHanChe(String hanChe) {
        this.hanChe = hanChe;
    }
}
