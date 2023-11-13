package DTO;

public class SanPhamDTO {
    private int maSanPham;
    private int maLoaiSanPham;
    private int loaiSanPham;
    private String tenSanPham;
    private int soLuong;
    private int giaXuat;
    private String cpu;
    private String ram;
    private String vga;
    private String oCung;
    private String manHinh;
    private String pin;
    private float trongLuong;
    private String mauSac;
    private String os;




    public SanPhamDTO() {
    }

    public SanPhamDTO(int maSanPham, String tenSanPham, int loaiSanPham, int soLuong, int giaXuat, String cpu, String ram, String vga, String oCung, String manHinh, String pin, float trongLuong, String mauSac, String os) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.loaiSanPham = loaiSanPham;
        this.soLuong = soLuong;
        this.giaXuat = giaXuat;
        this.cpu = cpu;
        this.ram = ram;
        this.vga = vga;
        this.oCung = oCung;
        this.manHinh = manHinh;
        this.pin = pin;
        this.trongLuong = trongLuong;
        this.mauSac = mauSac;
        this.os = os;

    }

    public SanPhamDTO(int maSanPham, int maLoaiSanPham,
            String tenSanPham, int loaiSanPham, int soLuong,
            int giaXuat, String cpu, String ram, String vga,
            String oCung, String manHinh, String pin, float trongLuong,
            String mauSac, String os) {
        this.maSanPham = maSanPham;
        this.maLoaiSanPham = maLoaiSanPham;
        this.tenSanPham = tenSanPham;
        this.loaiSanPham = loaiSanPham;
        this.soLuong = soLuong;
        this.giaXuat = giaXuat;
        this.cpu = cpu;
        this.ram = ram;
        this.vga = vga;
        this.oCung = oCung;
        this.manHinh = manHinh;
        this.pin = pin;
        this.trongLuong = trongLuong;
        this.mauSac = mauSac;
        this.os = os;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }


    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public int getMaLoaiSanPham() {
        return maLoaiSanPham;
    }

    public void setMaLoaiSanPham(int maLoaiSanPham) {
        this.maLoaiSanPham = maLoaiSanPham;
    }

    
    public int getLoaiSanPham() {
        return loaiSanPham;
    }

    public void setLoaiSanPham(int loaiSanPham) {
        this.loaiSanPham = loaiSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    
    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaXuat() {
        return giaXuat;
    }

    public void setGiaXuat(int giaXuat) {
        this.giaXuat = giaXuat;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getVga() {
        return vga;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }

    public String getoCung() {
        return oCung;
    }

    public void setoCung(String oCung) {
        this.oCung = oCung;
    }

    public String getManHinh() {
        return manHinh;
    }

    public void setManHinh(String manHinh) {
        this.manHinh = manHinh;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public float getTrongLuong() {
        return trongLuong;
    }

    public void setTrongLuong(float trongLuong) {
        this.trongLuong = trongLuong;
    }

    public String getMauSac() {
        return mauSac;
    }

    public void setMauSac(String mauSac) {
        this.mauSac = mauSac;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }


}
