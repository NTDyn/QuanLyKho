package DAO;

import DTO.DateRangeDTO;
import DTO.ThongKe.*;
import database.JDBCUtil;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ThongKeDAO {
    private final DateTimeFormatter sqlDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public ThongKeDAO() {
    }
    
    public ArrayList<ThongKeDoanhThuDTO> thongKeDoanhThu7NgayQua() throws SQLException {
        ArrayList<ThongKeDoanhThuDTO> result = new ArrayList<>();
        try {
            Connection conn = JDBCUtil.getConnection();
            String query = """
                           WITH RECURSIVE dates(date) AS (
                             SELECT DATE_SUB(CURDATE(), INTERVAL 6 DAY)
                             UNION ALL
                             SELECT DATE_ADD(date, INTERVAL 1 DAY)
                             FROM dates
                             WHERE date < CURDATE()
                           )
                           SELECT 
                             dates.date AS ngay,
                             COALESCE(SUM(PN.tongtien), 0) AS chiphi,
                             COALESCE(SUM(PX.tongtien), 0) AS doanhthu
                           FROM dates
                           LEFT JOIN phieunhap PN ON dates.date = DATE(PN.thoigiantao)
                           LEFT JOIN phieuxuat PX ON dates.date = DATE(PX.thoigiantao)
                           LEFT JOIN trangthaiphieunhap TTPN ON PN.trangthai = TTPN.matrangthai
                           WHERE (TTPN.tentrangthai LIKE '%delivered%')
                              OR (TTPN.matrangthai IS NULL)
                           GROUP BY dates.date
                           ORDER BY dates.date;
                           """;
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Date ngay = rs.getDate("ngay");
                Long chiPhi = rs.getLong("chiphi");
                Long doanhThu = rs.getLong("doanhthu");
                Long loiNhuan = doanhThu - chiPhi;
                result.add(new ThongKeDoanhThuDTO(ngay, chiPhi, doanhThu, loiNhuan));
            }
            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            throw e;
        }
        return result;
    }
    
    public ArrayList<ThongKeTonKhoDTO> thongKeTonKho(DateRangeDTO dateRange, String productName) throws SQLException {
        ArrayList<ThongKeTonKhoDTO> result = new ArrayList<>();
        String fromDate = dateRange.getFromDate().format(sqlDateFormatter);
        String toDate = dateRange.getToDate().format(sqlDateFormatter);
        try {
            Connection conn = JDBCUtil.getConnection();
            String query = """
                           WITH nhaptrongky AS (
                           	SELECT masanpham, SUM(soluongnhap) AS soluongnhap
                           	FROM phieunhap PN JOIN chitietphieunhap CTPN ON PN.maphieunhap = CTPN.maphieunhap JOIN trangthaiphieunhap TTPN ON PN.trangthai = TTPN.matrangthai
                           	WHERE DATE(PN.thoigiantao) BETWEEN ? AND ? AND tentrangthai LIKE '%delivered%'
                           	GROUP BY masanpham
                           ), xuattrongky AS (
                           	SELECT masanpham, SUM(soluong) AS soluongxuat
                           	FROM phieuxuat PX JOIN chitietphieuxuat CTPX ON PX.maphieuxuat = CTPX.maphieuxuat
                           	WHERE DATE(PX.thoigiantao) BETWEEN ? AND ? AND PX.trangthai = 1
                           	GROUP BY masanpham
                           ), nhapdauky AS (
                           	SELECT masanpham, SUM(soluongnhap) AS soluongnhap
                           	FROM phieunhap PN JOIN chitietphieunhap CTPN ON PN.maphieunhap = CTPN.maphieunhap
                           	WHERE DATE(PN.thoigiantao) < ?
                           	GROUP BY masanpham
                           ), xuatdauky AS (
                           	SELECT masanpham, SUM(soluong) AS soluongxuat
                           	FROM phieuxuat PX JOIN chitietphieuxuat CTPX ON PX.maphieuxuat = CTPX.maphieuxuat
                           	WHERE DATE(PX.thoigiantao) < ?
                           	GROUP BY masanpham
                           ), dauky AS (
                           	SELECT SP.masanpham, COALESCE(NDK.soluongnhap, 0) - COALESCE(XDK.soluongxuat, 0) AS tondauky
                           	FROM sanpham SP
                               LEFT JOIN chitietcungcap CTCC ON SP.masanpham = CTCC.masanpham
                           	LEFT JOIN nhapdauky NDK ON CTCC.masanpham = NDK.masanpham
                           	LEFT JOIN xuatdauky XDK ON CTCC.masanpham = XDK.masanpham
                           ), tonkho AS (
                           	SELECT SP.masanpham, tensanpham, tondauky, COALESCE(NTK.soluongnhap, 0) AS nhaptrongky, COALESCE(XTK.soluongxuat, 0) AS xuattrongky, (tondauky + COALESCE(NTK.soluongnhap, 0) - COALESCE(XTK.soluongxuat, 0)) AS toncuoiky
                           	FROM dauky DK
                           	LEFT JOIN nhaptrongky NTK ON DK.masanpham = NTK.masanpham
                           	LEFT JOIN xuattrongky XTK ON DK.masanpham = XTK.masanpham
                           	JOIN sanpham SP ON SP.masanpham = DK.masanpham
                           )
                           SELECT DISTINCT * FROM tonkho
                           WHERE tensanpham LIKE ?
                           ORDER BY masanpham
                           """;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            ps.setString(3, fromDate);
            ps.setString(4, toDate);
            ps.setString(5, fromDate);
            ps.setString(6, fromDate);
            ps.setString(7, "%" + productName + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                int tonDauKy = rs.getInt("tondauky");
                int nhapTrongKy = rs.getInt("nhaptrongky");
                int xuatTrongKy = rs.getInt("xuattrongky");
                int tonCuoiKy = rs.getInt("toncuoiky");
                result.add(new ThongKeTonKhoDTO(maSanPham, tenSanPham, tonDauKy, nhapTrongKy, xuatTrongKy, tonCuoiKy));
            }
            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            throw e;
        }
        return result;
    }
    
    public ArrayList<ThongKeSanPhamDTO> thongKeSanPham(DateRangeDTO dateRange, String productName) throws SQLException {
        ArrayList<ThongKeSanPhamDTO> result = new ArrayList<>();
        String fromDate = dateRange.getFromDate().format(sqlDateFormatter);
        String toDate = dateRange.getToDate().format(sqlDateFormatter);
        try {
            Connection conn = JDBCUtil.getConnection();
            String query = """
                           WITH slnhap AS (
                               SELECT masanpham, SUM(soluongnhap) AS soluongnhap
                               FROM phieunhap PN
                               JOIN chitietphieunhap CTPN ON PN.maphieunhap = CTPN.maphieunhap
                               JOIN trangthaiphieunhap TTPN ON TTPN.matrangthai = PN.trangthai
                               WHERE tentrangthai LIKE '%delivered%' AND DATE(PN.thoigiantao) BETWEEN ? AND ?
                               GROUP BY masanpham
                           ), temp_table AS (
                               SELECT SP.masanpham, tenloaisanpham, tensanpham, COALESCE(soluongnhap, 0) AS soluongnhap
                               FROM sanpham SP JOIN slnhap SLN ON SP.masanpham = SLN.masanpham
                               JOIN loaisanpham LSP ON SP.maloaisanpham = LSP.maloaisanpham
                           )
                           SELECT * FROM temp_table
                           WHERE tensanpham LIKE ?
                           ORDER BY soluongnhap DESC, masanpham
                           """;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            ps.setString(3, "%" + productName + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenLoaiSanPham = rs.getString("tenloaisanpham");
                String tenSanPham = rs.getString("tensanpham");
                int soLuongNhap = rs.getInt("soluongnhap");
                result.add(new ThongKeSanPhamDTO(maSanPham, tenLoaiSanPham, tenSanPham, soLuongNhap));
            }
            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            throw e;
        }
        return result;
    }
    
    public ArrayList<ChiTietSanPhamNhapDTO> thongKeChiTietSanPhamNhap(DateRangeDTO dateRange, int productId) throws SQLException {
        ArrayList<ChiTietSanPhamNhapDTO> result = new ArrayList<>();
        String fromDate = dateRange.getFromDate().format(sqlDateFormatter);
        String toDate = dateRange.getToDate().format(sqlDateFormatter);
        try {
            Connection conn = JDBCUtil.getConnection();
            String query = """
                            WITH tongnhap AS (
                                SELECT manhacungcap, SUM(soluongnhap) AS tongsoluongnhap
                                FROM phieunhap PN
                                JOIN chitietphieunhap CTPN ON PN.maphieunhap = CTPN.maphieunhap
                                JOIN trangthaiphieunhap TTPN ON TTPN.matrangthai = PN.trangthai
                                WHERE tentrangthai LIKE '%delivered%' AND DATE(PN.thoigiantao) BETWEEN ? AND ? AND masanpham = ?
                                GROUP BY manhacungcap
                                ORDER BY tongsoluongnhap
                            ), temp_table AS (
                                    SELECT TN.manhacungcap, tennhacungcap, tongsoluongnhap
                                FROM tongnhap TN JOIN nhacungcap NCC ON NCC.manhacungcap = TN.manhacungcap
                            )
                            SELECT * FROM temp_table
                            ORDER BY tongsoluongnhap DESC, manhacungcap
                           """;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            ps.setInt(3, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int maNhaCungCap = rs.getInt("manhacungcap");
                String tenNhaCungCap = rs.getString("tennhacungcap");
                int tongSoLuongNhap = rs.getInt("tongsoluongnhap");
                result.add(new ChiTietSanPhamNhapDTO(maNhaCungCap, tenNhaCungCap, tongSoLuongNhap));
            }
            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            throw e;
        }
        return result;
    }
    
    public ArrayList<ChiTietGiaNhapNCCDTO> chiTietGiaNhapNCC (int productId, int providerId, DateRangeDTO dateRange) throws SQLException {
        ArrayList<ChiTietGiaNhapNCCDTO> result = new ArrayList<>();
        String fromDate = dateRange.getFromDate().format(sqlDateFormatter);
        String toDate = dateRange.getToDate().format(sqlDateFormatter);
        try {
            Connection conn = JDBCUtil.getConnection();
            String query = """
                            SELECT CTPN.maphieunhap, PN.thoigiantao, soluongnhap, dongia AS dongianhap
                            FROM phieunhap PN
                            JOIN chitietphieunhap CTPN ON PN.maphieunhap = CTPN.maphieunhap
                            JOIN trangthaiphieunhap TTPN ON TTPN.matrangthai = PN.trangthai
                            WHERE tentrangthai LIKE '%delivered%' AND DATE(PN.thoigiantao) BETWEEN ? AND ? AND masanpham = ? AND CTPN.manhacungcap = ?
                            ORDER BY thoigiantao DESC, soluongnhap DESC, dongia
                           """;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            ps.setInt(3, productId);
            ps.setInt(4, providerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int maPhieuNhap = rs.getInt("maphieunhap");
                Timestamp timestamp = rs.getTimestamp("thoigiantao");
                LocalDateTime thoiGianTao = timestamp.toLocalDateTime();
                int soLuongNhap = rs.getInt("soluongnhap");
                Long donGiaNhap = rs.getLong("dongianhap");
                result.add(new ChiTietGiaNhapNCCDTO(maPhieuNhap, thoiGianTao, soLuongNhap, donGiaNhap));
            }
            ps.close();
            rs.close();
            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            throw e;
        }
        return result;
    }
    
    public ArrayList<ThongKeLoaiSanPhamDTO> thongKeLoaiSanPham(DateRangeDTO dateRange, String productType) throws SQLException {
        ArrayList<ThongKeLoaiSanPhamDTO> result = new ArrayList<>();
        String fromDate = dateRange.getFromDate().format(sqlDateFormatter);
        String toDate = dateRange.getToDate().format(sqlDateFormatter);
        try {
            Connection conn = JDBCUtil.getConnection();
            String query = """
                            SELECT LSP.maloaisanpham, tenloaisanpham, SUM(CTPX.soluong) AS soluong
                            FROM chitietphieuxuat CTPX
                            JOIN phieuxuat PX ON CTPX.maphieuxuat = PX.maphieuxuat
                            JOIN sanpham SP ON CTPX.masanpham = SP.masanpham
                            JOIN loaisanpham LSP ON LSP.maloaisanpham = SP.maloaisanpham
                            WHERE PX.trangthai = 1 AND DATE(PX.thoigiantao) BETWEEN ? AND ? AND tenloaisanpham LIKE ?
                            GROUP BY LSP.maloaisanpham
                            ORDER BY soluong DESC, maloaisanpham
                           """;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            ps.setString(3, "%" + productType + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int maLoaiSanPham = rs.getInt("maloaisanpham");
                String tenLoaiSanPham = rs.getString("tenloaisanpham");
                int soLuong = rs.getInt("soluong");
                result.add(new ThongKeLoaiSanPhamDTO(maLoaiSanPham, tenLoaiSanPham, soLuong));
            }
            ps.close();
            rs.close();
            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            throw e;
        }
        return result;
    }
    
    public ArrayList<ChiTietLoaiSanPhamDTO> chiTietLoaiSanPham(DateRangeDTO dateRange, int productTypeId) throws SQLException {
        ArrayList<ChiTietLoaiSanPhamDTO> result = new ArrayList<>();
        String fromDate = dateRange.getFromDate().format(sqlDateFormatter);
        String toDate = dateRange.getToDate().format(sqlDateFormatter);
        try {
            Connection conn = JDBCUtil.getConnection();
            String query = """
                            SELECT CTPX.masanpham, tensanpham, SUM(CTPX.soluong) AS soluong
                            FROM chitietphieuxuat CTPX
                            JOIN phieuxuat PX ON CTPX.maphieuxuat = PX.maphieuxuat
                            JOIN sanpham SP ON SP.masanpham = CTPX.masanpham
                            JOIN loaisanpham LSP ON LSP.maloaisanpham = SP.maloaisanpham
                            WHERE PX.trangthai = 1 AND DATE(PX.thoigiantao) BETWEEN ? AND ? AND LSP.maloaisanpham = ?
                            GROUP BY CTPX.masanpham
                            ORDER BY soluong DESC, masanpham DESC
                           """;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            ps.setInt(3, productTypeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int maSanPham = rs.getInt("masanpham");
                String tenSanPham = rs.getString("tensanpham");
                int soLuong = rs.getInt("soluong");
                result.add(new ChiTietLoaiSanPhamDTO(maSanPham, tenSanPham, soLuong));
            }
            ps.close();
            rs.close();
            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            throw e;
        }
        return result;
    }

    public ArrayList<ChiTietGiaXuatSPDTO> chiTietGiaXuatSanPham (int productId, DateRangeDTO dateRange) throws SQLException {
        ArrayList<ChiTietGiaXuatSPDTO> result = new ArrayList<>();
        String fromDate = dateRange.getFromDate().format(sqlDateFormatter);
        String toDate = dateRange.getToDate().format(sqlDateFormatter);
        try {
            Connection conn = JDBCUtil.getConnection();
            String query = """
                            SELECT CTPX.maphieuxuat, thoigiantao, soluong AS soluongxuat, dongia AS dongiaxuat
                            FROM chitietphieuxuat CTPX
                            JOIN phieuxuat PX ON CTPX.maphieuxuat = PX.maphieuxuat
                            WHERE PX.trangthai = 1 AND DATE(PX.thoigiantao) BETWEEN ? AND ? AND masanpham = ?
                            ORDER BY thoigiantao DESC, soluongxuat DESC, dongiaxuat
                           """;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            ps.setInt(3, productId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int maPhieuXuat = rs.getInt("maphieuxuat");
                Timestamp timestamp = rs.getTimestamp("thoigiantao");
                LocalDateTime thoiGianTao = timestamp.toLocalDateTime();
                int soLuongXuat = rs.getInt("soluongxuat");
                Long donGiaXuat = rs.getLong("dongiaxuat");
                result.add(new ChiTietGiaXuatSPDTO(maPhieuXuat, thoiGianTao, soLuongXuat, donGiaXuat));
            }
            ps.close();
            rs.close();
            JDBCUtil.closeConnection(conn);
        } catch (SQLException e) {
            throw e;
        }
        return result;
    }
}
