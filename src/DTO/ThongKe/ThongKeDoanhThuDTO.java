package DTO.ThongKe;

import java.util.Date;

public class ThongKeDoanhThuDTO {
    private Date ngay;
    private Long chiPhi;
    private Long doanhThu;
    private Long loiNhuan;
    
    public ThongKeDoanhThuDTO() {
    }

    public ThongKeDoanhThuDTO(Date ngay, Long chiPhi, Long doanhThu, Long loiNhuan) {
        this.ngay = ngay;
        this.chiPhi = chiPhi;
        this.doanhThu = doanhThu;
        this.loiNhuan = loiNhuan;
    }

    public Date getNgay() {
        return ngay;
    }

    public void setNgay(Date ngay) {
        this.ngay = ngay;
    }

    public Long getChiPhi() {
        return chiPhi;
    }

    public void setChiPhi(Long chiPhi) {
        this.chiPhi = chiPhi;
    }

    public Long getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(Long doanhThu) {
        this.doanhThu = doanhThu;
    }

    public Long getLoiNhuan() {
        return loiNhuan;
    }

    public void setLoiNhuan(Long loiNhuan) {
        this.loiNhuan = loiNhuan;
    }
}
