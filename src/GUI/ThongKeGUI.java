package GUI;

import BUS.ThongKeBUS;
import DTO.DateRangeDTO;
import DTO.NguoiDungDTO;
import DTO.ThongKe.*;
import GUI.Chart.ModelChart;
import GUI.Dialog.ChiTietLoaiSanPhamDialog;
import GUI.Dialog.ChiTietSanPhamNhapDialog;
import GUI.Dialog.SelectDateDialog;
import helper.LoaiSanPhamTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ThongKeGUI extends javax.swing.JInternalFrame {
    private final ThongKeBUS tkBUS = new ThongKeBUS();
    private ArrayList<ThongKeLoaiSanPhamDTO> arrLoaiSanPham;
    private DefaultTableModel dtmOverview;
    private DefaultTableModel dtmTonKho;
    private DefaultTableModel dtmSanPham;
    private DefaultTableModel dtmLoaiSanPham;
    private DefaultTableCellRenderer dtcrOverview;
    private DefaultTableCellRenderer dtcrTonKho;
    private DefaultTableCellRenderer dtcrSanPham;
    private DefaultTableCellRenderer dtcrLoaiSanPham;
    private LoaiSanPhamTableModel tmLoaiSanPham;
    
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final LocalDateTime nowDateTime = LocalDateTime.now();
    private final LocalDateTime lastMonthDateTime = nowDateTime.minusMonths(1);
    private final LocalDateTime last2MonthDateTime = nowDateTime.minusMonths(2);
    private final int curYear = nowDateTime.getYear();
    private final int curMonth = nowDateTime.getMonthValue();
    
    private final String CB_VALUE_LAST_7_DAYS = "7 ngày qua";
    private final String CB_VALUE_LAST_30_DAYS = "30 ngày qua";
    private final String CB_VALUE_LAST_90_DAYS = "90 ngày qua";
    private final String CB_VALUE_LAST_365_DAYS = "365 ngày qua";
    private final String CB_VALUE_LIFETIME = "Toàn thời gian";
    private final String CB_VALUE_CURRENT_YEAR = Integer.toString(curYear);
    private final String CB_VALUE_LAST_YEAR = Integer.toString(curYear - 1);
    private final String CB_VALUE_CURRENT_MONTH = "Tháng " + curMonth;
    private final String CB_VALUE_LAST_MONTH = "Tháng " + lastMonthDateTime.getMonthValue() + (lastMonthDateTime.getYear() == curYear ? "" : "/" + lastMonthDateTime.getYear());
    private final String CB_VALUE_LAST_2_MONTHS = "Tháng " + last2MonthDateTime.getMonthValue() + (last2MonthDateTime.getYear() == curYear ? "" : "/" + last2MonthDateTime.getYear());
    private final String CB_VALUE_CUSTOM = "Tuỳ chỉnh";
    
    private DateRangeDTO drTonKho;
    private DateRangeDTO drDoanhThu;
    private DateRangeDTO drSanPham;
    private DateRangeDTO drLoaiSanPham;
    
    private String queryTonKho;
    private String querySanPham;
    private String queryLoaiSanPham;
    
    private boolean isLoadingTonKho;
    private boolean isLoadingSanPham;
    private boolean isLoadingLoaiSanPham;
    
    /**
     * Creates new form ThongKeGUI
     */
    public ThongKeGUI(NguoiDungDTO user) {
        initComponents();
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        initList();
        initTable();
        initChart();
        initDateRange();
        setModelComboBox();
        initLoadingState();
        initQueryString();
        
        thongKeDoanhThu7NgayQua();
        thongKeTonKho(drTonKho, queryTonKho);
        thongKeSanPham(drSanPham, querySanPham);
        thongKeLoaiSanPham(drLoaiSanPham, queryLoaiSanPham);
        // Authorize
        // ...
    }
    
    public ThongKeGUI() {
        initComponents();
    }
    
    public boolean isIsLoadingTonKho() {
        return isLoadingTonKho;
    }

    public void setIsLoadingTonKho(boolean isLoadingTonKho) {
        this.isLoadingTonKho = isLoadingTonKho;
    }

    public String getQueryTonKho() {
        return queryTonKho;
    }

    public void setQueryTonKho(String queryTonKho) {
        this.queryTonKho = queryTonKho;
    }

    public String getQuerySanPham() {
        return querySanPham;
    }

    public void setQuerySanPham(String querySanPham) {
        this.querySanPham = querySanPham;
    }

    public boolean isIsLoadingSanPham() {
        return isLoadingSanPham;
    }

    public void setIsLoadingSanPham(boolean isLoadingSanPham) {
        this.isLoadingSanPham = isLoadingSanPham;
    }

    public String getQueryLoaiSanPham() {
        return queryLoaiSanPham;
    }

    public void setQueryLoaiSanPham(String queryLoaiSanPham) {
        this.queryLoaiSanPham = queryLoaiSanPham;
    }

    public boolean isIsLoadingLoaiSanPham() {
        return isLoadingLoaiSanPham;
    }

    public void setIsLoadingLoaiSanPham(boolean isLoadingLoaiSanPham) {
        this.isLoadingLoaiSanPham = isLoadingLoaiSanPham;
    }
    
    private void initList() {
        this.arrLoaiSanPham = new ArrayList<>();
    }
    
    private void initTable() {
        dtmOverview = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ngày", "Chi phí", "Doanh thu", "Lợi nhuận"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        tbOverview.setModel(dtmOverview);
        dtcrOverview = new DefaultTableCellRenderer();
        dtcrOverview.setHorizontalAlignment(SwingConstants.CENTER);
        
        dtmTonKho = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Tồn đầu kỳ", "Nhập trong kỳ", "Xuất trong kỳ", "Tồn cuối kỳ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        tbTonKho.setModel(dtmTonKho);
        tbTonKho.getColumnModel().getColumn(1).setPreferredWidth(400);
        dtcrTonKho = new DefaultTableCellRenderer();
        dtcrTonKho.setHorizontalAlignment(SwingConstants.CENTER);
        
        dtmSanPham = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Loại sản phẩm", "Tên sản phẩm", "Số lượng nhập"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        
        tbSanPham.setModel(dtmSanPham);
        tbSanPham.getColumnModel().getColumn(2).setPreferredWidth(400);
        dtcrSanPham = new DefaultTableCellRenderer();
        dtcrSanPham.setHorizontalAlignment(SwingConstants.CENTER);
        
//        dtmLoaiSanPham = new DefaultTableModel(
//            new Object [][] {
//
//            },
//            new String [] {
//                "Mã", "Loại sản phẩm", "Số lượng xuất"
//            }
//        ) {
//            boolean[] canEdit = new boolean [] {
//                false, false, false
//            };
//
//            public boolean isCellEditable(int rowIndex, int columnIndex) {
//                return canEdit [columnIndex];
//            }
//        };
        
//        tbLoaiSanPham.setModel(dtmLoaiSanPham);
//        tbLoaiSanPham.getColumnModel().removeColumn(tbLoaiSanPham.getColumnModel().getColumn(0));

        String[] tbLoaiSanPhamColumnNames = {"Mã", "Loại sản phẩm", "Số lượng xuất"};
        this.tmLoaiSanPham = new LoaiSanPhamTableModel(this.arrLoaiSanPham, tbLoaiSanPhamColumnNames);
        tbLoaiSanPham.setModel(tmLoaiSanPham);
        tbLoaiSanPham.getColumnModel().getColumn(0).setMinWidth(0);
        tbLoaiSanPham.getColumnModel().getColumn(0).setMaxWidth(0);
        tbLoaiSanPham.getColumnModel().getColumn(0).setWidth(0);
        tbLoaiSanPham.getColumnModel().getColumn(0).setPreferredWidth(0);
        dtcrLoaiSanPham = new DefaultTableCellRenderer();
        dtcrLoaiSanPham.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private void initChart() {
        chartOverview.addLegend("Chi phí", new Color(245, 189, 135));
        chartOverview.addLegend("Doanh thu", new Color(135, 189, 245));
        chartOverview.addLegend("Lợi nhuận", new Color(189, 135, 245));
    }
    
    private void initDateRange() {
        // Set fromDate to 7 days ago, toDate to today
        LocalDateTime fromDate = LocalDateTime.now().minusDays(6);
        DateRangeDTO dateRange = new DateRangeDTO(fromDate, LocalDateTime.now());
        drTonKho = dateRange;
        drDoanhThu = dateRange;
        drSanPham = dateRange;
        drLoaiSanPham = dateRange;
    }
    
    private void setModelComboBox() {
        javax.swing.JComboBox[] comboBox = {cbTonKhoDate, cbDoanhThuDate, cbSanPhamDate, cbLoaiSanPhamDate};
        for (javax.swing.JComboBox cb : comboBox) {
            cb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { CB_VALUE_LAST_7_DAYS, CB_VALUE_LAST_30_DAYS, CB_VALUE_LAST_90_DAYS, 
                CB_VALUE_LAST_365_DAYS, CB_VALUE_LIFETIME, CB_VALUE_CURRENT_YEAR, CB_VALUE_LAST_YEAR, CB_VALUE_CURRENT_MONTH, CB_VALUE_LAST_MONTH, 
                CB_VALUE_LAST_2_MONTHS, CB_VALUE_CUSTOM }));
            cb.setSelectedIndex(0);
        }
    }

    private void initLoadingState() {
        setIsLoadingTonKho(false);
        setIsLoadingSanPham(false);
        setIsLoadingLoaiSanPham(false);
    }
    
    private void initQueryString() {
        setQueryTonKho("");
        setQuerySanPham("");
        setQueryLoaiSanPham("");
    }
    
    public void setDateRange(String dateRangeName, DateRangeDTO dateRange) {
        if (dateRangeName.equals("tonkho")) {
            drTonKho = dateRange;
            displayDateRangeToLabel(drTonKho, lblTonKhoDate);
        } else if (dateRangeName.equals("sanpham")) {
            drSanPham = dateRange;
            displayDateRangeToLabel(drSanPham, lblSanPhamDate);
        } else if (dateRangeName.equals("loaisanpham")) {
            drLoaiSanPham = dateRange;
            displayDateRangeToLabel(drLoaiSanPham, lblLoaiSanPhamDate);
        }
        // else if..
    }
    
    private void setIsLoading(String name, boolean value) {
        if (name.equals("tonkho")) {
            setIsLoadingTonKho(value);
        } else if (name.equals("sanpham")) {
            setIsLoadingSanPham(value);
        } else if (name.equals("loaisanpham")) {
            setIsLoadingLoaiSanPham(value);
        }
    }
    
    private void displayDateRangeToLabel(DateRangeDTO dateRange, javax.swing.JLabel label) {
        label.setText(dateRange.getFromDate().format(formatter) + " - " + dateRange.getToDate().format(formatter));
    }

    private void handleComboBoxChanged(String name, String value, DateRangeDTO dateRange, javax.swing.JLabel label) {
        if (value.equals(CB_VALUE_LIFETIME)) {
            // Reset 2 date range
            label.setText(CB_VALUE_LIFETIME);
            return;
        } else if (value.equals(CB_VALUE_CUSTOM)) {
            SelectDateDialog selectDate = new SelectDateDialog(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled, name, dateRange, 0, false);
            selectDate.setVisible(true);
            return;
        }
        if (value.equals(CB_VALUE_LAST_7_DAYS)) {
            dateRange.setFromDate(nowDateTime.minusDays(6));
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(CB_VALUE_LAST_30_DAYS)) {
            dateRange.setFromDate(nowDateTime.minusDays(29));
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(CB_VALUE_LAST_90_DAYS)) {
            dateRange.setFromDate(nowDateTime.minusDays(89));
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(CB_VALUE_LAST_365_DAYS)) {
            dateRange.setFromDate(nowDateTime.minusDays(364));
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(CB_VALUE_CURRENT_YEAR)) {
            dateRange.setFromDate(LocalDateTime.of(curYear, 1, 1, 0, 0));
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(CB_VALUE_LAST_YEAR)) {
            LocalDateTime firstDayOfCurYear = LocalDateTime.of(curYear, 1, 1, 0, 0, 0);
            LocalDateTime lastDayOfLastYear = firstDayOfCurYear.minusDays(1);
            dateRange.setFromDate(LocalDateTime.of(curYear - 1, 1, 1, 0, 0));
            dateRange.setToDate(lastDayOfLastYear);
        } else if (value.equals(CB_VALUE_CURRENT_MONTH)) {
            dateRange.setFromDate(LocalDateTime.of(curYear, nowDateTime.getMonthValue(), 1, 0, 0));
            dateRange.setToDate(nowDateTime);
        } else if (value.equals(CB_VALUE_LAST_MONTH)) {
            LocalDateTime firstDayOfCurMonth = LocalDateTime.of(curYear, nowDateTime.getMonthValue(), 1, 0, 0);
            LocalDateTime lastDayOfLastMonth = firstDayOfCurMonth.minusDays(1);
            dateRange.setFromDate(LocalDateTime.of(lastMonthDateTime.getYear(), lastMonthDateTime.getMonthValue(), 1, 0, 0));
            dateRange.setToDate(lastDayOfLastMonth);
        } else if (value.equals(CB_VALUE_LAST_2_MONTHS)) {
            LocalDateTime firstDayOfLastMonth = LocalDateTime.of(lastMonthDateTime.getYear(), lastMonthDateTime.getMonthValue(), 1, 0, 0);
            LocalDateTime lastDayOfLast2Months = firstDayOfLastMonth.minusDays(1);
            dateRange.setFromDate(LocalDateTime.of(last2MonthDateTime.getYear(), last2MonthDateTime.getMonthValue(), 1, 0, 0));
            dateRange.setToDate(lastDayOfLast2Months);
        }
        
        setDateRange(name, dateRange);
        setIsLoading(name, true);
    }
    
    private void handleReload(javax.swing.JButton btnReload, String name, DateRangeDTO dateRange, String query) {
        // Disable button
        btnReload.setEnabled(false);
        
        thongKe(name, dateRange, query);
        
        // Enable button after 2 seconds
        int timeoutMs = 2000;
        Timer timer = new Timer(timeoutMs, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnReload.setEnabled(true);
                btnReload.requestFocus();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
    
    private void handleViewProductDetail(int row) {
        int maSanPham = Integer.parseInt(tbSanPham.getValueAt(row, 0).toString());
        String loaiSanPham = tbSanPham.getValueAt(row, 1).toString();
        String tenSanPham = tbSanPham.getValueAt(row, 2).toString();
        int soLuongNhap = Integer.parseInt(tbSanPham.getValueAt(row, 3).toString());
        ThongKeSanPhamDTO product = new ThongKeSanPhamDTO(maSanPham, loaiSanPham, tenSanPham, soLuongNhap);
        ChiTietSanPhamNhapDialog detailDialog = new ChiTietSanPhamNhapDialog(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled, drSanPham, product);
        detailDialog.setVisible(true);
    }
    
    private void handleViewProductTypeDetail(int row) {
        int maLoaiSanPham = Integer.parseInt(tbLoaiSanPham.getValueAt(row, 0).toString());
        String tenLoaiSanPham = tbLoaiSanPham.getValueAt(row, 1).toString();
        int soLuong = Integer.parseInt(tbLoaiSanPham.getValueAt(row, 2).toString());
        ThongKeLoaiSanPhamDTO productType = new ThongKeLoaiSanPhamDTO(maLoaiSanPham, tenLoaiSanPham, soLuong);
        ChiTietLoaiSanPhamDialog detailDialog = new ChiTietLoaiSanPhamDialog(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled, drLoaiSanPham, productType);
        detailDialog.setVisible(true);
    }
    
    private void thongKeDoanhThu7NgayQua() {
        ArrayList<ThongKeDoanhThuDTO> arr = new ArrayList<>();
        try {
            arr = tkBUS.thongKeDoanhThu7NgayQua();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ThongKeGUI.this, "Lỗi kết nối cơ sở dữ liệu", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ThongKeGUI.this, "Lỗi không xác định", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        
        dtmOverview.setRowCount(0);
        for (int i = 0; i < arr.size(); ++i) {
            ThongKeDoanhThuDTO tkdtDTO = arr.get(i);
            Date ngay = tkdtDTO.getNgay();
            Long chiPhi = tkdtDTO.getChiPhi();
            Long doanhThu = tkdtDTO.getDoanhThu();
            Long loiNhuan = tkdtDTO.getLoiNhuan();
            Object [] row = {ngay, chiPhi, doanhThu, loiNhuan};
            dtmOverview.addRow(row);
            chartOverview.addData(new ModelChart(ngay.toString(), new double[]{chiPhi, doanhThu, loiNhuan}));
        }
        
        for (int i = 0; i < tbOverview.getColumnCount(); ++i) {
            tbOverview.getColumnModel().getColumn(i).setCellRenderer(dtcrOverview);
        }
    }
    
    private void thongKeTonKho(DateRangeDTO dateRange, String productName) {
        ArrayList<ThongKeTonKhoDTO> arr = new ArrayList<>();
        try {
            arr = tkBUS.thongKeTonKho(dateRange, productName);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ThongKeGUI.this, "Lỗi kết nối cơ sở dữ liệu", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ThongKeGUI.this, "Lỗi không xác định", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        dtmTonKho.setRowCount(0);
        for (int i = 0; i < arr.size(); ++i) {
            ThongKeTonKhoDTO tktkDTO = arr.get(i);
            int maSanPham = tktkDTO.getMaSanPham();
            String tenSanPham = tktkDTO.getTenSanPham();
            int tonDauKy = tktkDTO.getTonDauKy();
            int nhapTrongKy = tktkDTO.getNhapTrongKy();
            int xuatTrongKy = tktkDTO.getXuatTrongKy();
            int tonCuoiKy = tktkDTO.getTonCuoiKy();
            Object [] row = {maSanPham, tenSanPham, tonDauKy, nhapTrongKy, xuatTrongKy, tonCuoiKy};
            dtmTonKho.addRow(row);
        }
        for (int i = 0; i < tbTonKho.getColumnCount(); ++i) {
            tbTonKho.getColumnModel().getColumn(i).setCellRenderer(dtcrTonKho);
        }
        setIsLoadingTonKho(false);
    }
    
    private void thongKeSanPham(DateRangeDTO dateRange, String productName) {
        ArrayList<ThongKeSanPhamDTO> arr = new ArrayList<>();
        try {
            arr = tkBUS.thongKeSanPham(dateRange, productName);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ThongKeGUI.this, "Lỗi kết nối cơ sở dữ liệu", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ThongKeGUI.this, "Lỗi không xác định", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        dtmSanPham.setRowCount(0);
        for (int i = 0; i < arr.size(); ++i) {
            ThongKeSanPhamDTO tkspDTO = arr.get(i);
            int maSanPham = tkspDTO.getMaSanPham();
            String tenLoaiSanPham = tkspDTO.getTenLoaiSanPham();
            String tenSanPham = tkspDTO.getTenSanPham();
            int soLuongNhap = tkspDTO.getSoLuongNhap();
            Object [] row = {maSanPham, tenLoaiSanPham, tenSanPham, soLuongNhap};
            dtmSanPham.addRow(row);
        }
        for (int i = 0; i < tbSanPham.getColumnCount(); ++i) {
            tbSanPham.getColumnModel().getColumn(i).setCellRenderer(dtcrSanPham);
        }
        setIsLoadingSanPham(false);
    }
    
    private void thongKe(String name, DateRangeDTO dateRange, String query) {
        if (name.equals("tonkho")) {
            thongKeTonKho(dateRange, query);
        } else if (name.equals("sanpham")) {
            thongKeSanPham(dateRange, query);
        } else if (name.equals("loaisanpham")) {
            thongKeLoaiSanPham(dateRange, query);
        }
    }
    
    private void thongKeLoaiSanPham(DateRangeDTO dateRange, String productType) {
        ArrayList<ThongKeLoaiSanPhamDTO> arr = new ArrayList<>();
        try {
            arr = tkBUS.thongKeLoaiSanPham(dateRange, productType);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ThongKeGUI.this, "Lỗi kết nối cơ sở dữ liệu", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ThongKeGUI.this, "Lỗi không xác định", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        tmLoaiSanPham.setData(arr);
        tmLoaiSanPham.fireTableDataChanged();
//        dtmLoaiSanPham.setRowCount(0);
//        for (int i = 0; i < arr.size(); ++i) {
//            ThongKeLoaiSanPhamDTO lsp = arr.get(i);
//            int maLoaiSanPham = lsp.getMaLoaiSanPham();
//            String tenLoaiSanPham = lsp.getTenLoaiSanPham();
//            int soLuong = lsp.getSoLuong();
//            Object [] row = {maLoaiSanPham, tenLoaiSanPham, soLuong};
//            dtmLoaiSanPham.addRow(row);
//        }
        for (int i = 0; i < tbLoaiSanPham.getColumnCount(); ++i) {
            tbLoaiSanPham.getColumnModel().getColumn(i).setCellRenderer(dtcrLoaiSanPham);
        }
        setIsLoadingLoaiSanPham(false);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pContainer = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        pOverview = new javax.swing.JPanel();
        pSoLuongNhap = new javax.swing.JPanel();
        txtSoLuongNhap = new javax.swing.JLabel();
        lblNhap = new javax.swing.JLabel();
        iconSanPham = new javax.swing.JLabel();
        pSoLuongXuat = new javax.swing.JPanel();
        txtSoLuongXuat = new javax.swing.JLabel();
        lblXuat = new javax.swing.JLabel();
        iconSanPham1 = new javax.swing.JLabel();
        pSoLuongTonKho = new javax.swing.JPanel();
        txtTonKho = new javax.swing.JLabel();
        lblTonKho = new javax.swing.JLabel();
        iconSanPham2 = new javax.swing.JLabel();
        pChartOverview = new javax.swing.JPanel();
        chartOverview = new GUI.Chart.Chart();
        scrollPane1 = new javax.swing.JScrollPane();
        tbOverview = new javax.swing.JTable();
        lblThongKe7NgayQua = new javax.swing.JLabel();
        pTonKho = new javax.swing.JPanel();
        pFilterTonKho = new javax.swing.JPanel();
        inputTonKho = new javax.swing.JTextField();
        cbTonKhoDate = new javax.swing.JComboBox<>();
        lblSearchTonKho = new javax.swing.JLabel();
        btnReloadTonKho = new javax.swing.JButton();
        btnExportExcelTonKho = new javax.swing.JButton();
        lblTonKhoDate = new javax.swing.JLabel();
        scrollPane2 = new javax.swing.JScrollPane();
        tbTonKho = new javax.swing.JTable();
        pDoanhThu = new javax.swing.JPanel();
        pFilterDoanhThu = new javax.swing.JPanel();
        cbDoanhThuDate = new javax.swing.JComboBox<>();
        lblDoanhThuDate = new javax.swing.JLabel();
        toolbarDoanhThu = new javax.swing.JToolBar();
        btnReloadTonKho1 = new javax.swing.JButton();
        btnExportExcelTonKho1 = new javax.swing.JButton();
        scrollPane3 = new javax.swing.JScrollPane();
        tbDoanhThu = new javax.swing.JTable();
        chartDoanhThu = new GUI.Chart.Chart();
        pSanPham = new javax.swing.JPanel();
        pFilterSanPham = new javax.swing.JPanel();
        lblSearchSanPham = new javax.swing.JLabel();
        inputSanPham = new javax.swing.JTextField();
        lblSanPhamDate = new javax.swing.JLabel();
        cbSanPhamDate = new javax.swing.JComboBox<>();
        btnChiTietSanPham = new javax.swing.JButton();
        btnExportExcelSanPham = new javax.swing.JButton();
        btnReloadSanPham = new javax.swing.JButton();
        scrollPane4 = new javax.swing.JScrollPane();
        tbSanPham = new javax.swing.JTable();
        pLoaiSanPham = new javax.swing.JPanel();
        chartLoaiSanPham = new GUI.Chart.Chart();
        pFilterLoaiSanPham = new javax.swing.JPanel();
        lblSearchLoaiSanPham = new javax.swing.JLabel();
        inputLoaiSanPham = new javax.swing.JTextField();
        lblLoaiSanPhamDate = new javax.swing.JLabel();
        cbLoaiSanPhamDate = new javax.swing.JComboBox<>();
        btnChiTietLoaiSanPham = new javax.swing.JButton();
        btnExportExcelLoaiSanPham = new javax.swing.JButton();
        btnReloadLoaiSanPham = new javax.swing.JButton();
        scrollPane5 = new javax.swing.JScrollPane();
        tbLoaiSanPham = new javax.swing.JTable();

        setBorder(null);

        pOverview.setBackground(new java.awt.Color(255, 255, 255));

        pSoLuongNhap.setBackground(new java.awt.Color(255, 204, 0));
        pSoLuongNhap.setPreferredSize(new java.awt.Dimension(359, 102));

        txtSoLuongNhap.setFont(new java.awt.Font("SF Pro Display", 1, 36)); // NOI18N
        txtSoLuongNhap.setForeground(new java.awt.Color(255, 255, 255));
        txtSoLuongNhap.setText("0");

        lblNhap.setFont(new java.awt.Font("SF Pro Display", 0, 18)); // NOI18N
        lblNhap.setForeground(new java.awt.Color(255, 255, 255));
        lblNhap.setText("Sản phẩm đã nhập");

        iconSanPham.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-monitor-80.png"))); // NOI18N

        javax.swing.GroupLayout pSoLuongNhapLayout = new javax.swing.GroupLayout(pSoLuongNhap);
        pSoLuongNhap.setLayout(pSoLuongNhapLayout);
        pSoLuongNhapLayout.setHorizontalGroup(
            pSoLuongNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pSoLuongNhapLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(iconSanPham)
                .addGap(18, 18, 18)
                .addGroup(pSoLuongNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNhap)
                    .addComponent(txtSoLuongNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        pSoLuongNhapLayout.setVerticalGroup(
            pSoLuongNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pSoLuongNhapLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(pSoLuongNhapLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconSanPham)
                    .addGroup(pSoLuongNhapLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txtSoLuongNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNhap)))
                .addGap(10, 10, 10))
        );

        pSoLuongXuat.setBackground(new java.awt.Color(255, 204, 0));
        pSoLuongXuat.setPreferredSize(new java.awt.Dimension(359, 102));

        txtSoLuongXuat.setFont(new java.awt.Font("SF Pro Display", 1, 36)); // NOI18N
        txtSoLuongXuat.setForeground(new java.awt.Color(255, 255, 255));
        txtSoLuongXuat.setText("0");

        lblXuat.setFont(new java.awt.Font("SF Pro Display", 0, 18)); // NOI18N
        lblXuat.setForeground(new java.awt.Color(255, 255, 255));
        lblXuat.setText("Sản phẩm đã xuất");

        iconSanPham1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-monitor-80.png"))); // NOI18N

        javax.swing.GroupLayout pSoLuongXuatLayout = new javax.swing.GroupLayout(pSoLuongXuat);
        pSoLuongXuat.setLayout(pSoLuongXuatLayout);
        pSoLuongXuatLayout.setHorizontalGroup(
            pSoLuongXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pSoLuongXuatLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(iconSanPham1)
                .addGap(18, 18, 18)
                .addGroup(pSoLuongXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblXuat)
                    .addComponent(txtSoLuongXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(117, Short.MAX_VALUE))
        );
        pSoLuongXuatLayout.setVerticalGroup(
            pSoLuongXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pSoLuongXuatLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(pSoLuongXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconSanPham1)
                    .addGroup(pSoLuongXuatLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txtSoLuongXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblXuat)))
                .addGap(10, 10, 10))
        );

        pSoLuongTonKho.setBackground(new java.awt.Color(255, 204, 0));

        txtTonKho.setFont(new java.awt.Font("SF Pro Display", 1, 36)); // NOI18N
        txtTonKho.setForeground(new java.awt.Color(255, 255, 255));
        txtTonKho.setText("0");

        lblTonKho.setFont(new java.awt.Font("SF Pro Display", 0, 18)); // NOI18N
        lblTonKho.setForeground(new java.awt.Color(255, 255, 255));
        lblTonKho.setText("Tồn kho");

        iconSanPham2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-monitor-80.png"))); // NOI18N

        javax.swing.GroupLayout pSoLuongTonKhoLayout = new javax.swing.GroupLayout(pSoLuongTonKho);
        pSoLuongTonKho.setLayout(pSoLuongTonKhoLayout);
        pSoLuongTonKhoLayout.setHorizontalGroup(
            pSoLuongTonKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pSoLuongTonKhoLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(iconSanPham2)
                .addGap(18, 18, 18)
                .addGroup(pSoLuongTonKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTonKho)
                    .addComponent(txtTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(154, Short.MAX_VALUE))
        );
        pSoLuongTonKhoLayout.setVerticalGroup(
            pSoLuongTonKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pSoLuongTonKhoLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(pSoLuongTonKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(iconSanPham2)
                    .addGroup(pSoLuongTonKhoLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txtTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTonKho)))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout pChartOverviewLayout = new javax.swing.GroupLayout(pChartOverview);
        pChartOverview.setLayout(pChartOverviewLayout);
        pChartOverviewLayout.setHorizontalGroup(
            pChartOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chartOverview, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        pChartOverviewLayout.setVerticalGroup(
            pChartOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(chartOverview, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
        );

        tbOverview.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Ngày", "Chi phí", "Doanh thu", "Lợi nhuận"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane1.setViewportView(tbOverview);
        if (tbOverview.getColumnModel().getColumnCount() > 0) {
            tbOverview.getColumnModel().getColumn(0).setResizable(false);
            tbOverview.getColumnModel().getColumn(1).setResizable(false);
            tbOverview.getColumnModel().getColumn(2).setResizable(false);
            tbOverview.getColumnModel().getColumn(3).setResizable(false);
        }

        lblThongKe7NgayQua.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblThongKe7NgayQua.setText("Thống kê doanh thu 7 ngày qua");

        javax.swing.GroupLayout pOverviewLayout = new javax.swing.GroupLayout(pOverview);
        pOverview.setLayout(pOverviewLayout);
        pOverviewLayout.setHorizontalGroup(
            pOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane1)
                    .addGroup(pOverviewLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(pChartOverview, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pOverviewLayout.createSequentialGroup()
                        .addComponent(pSoLuongNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pSoLuongXuat, javax.swing.GroupLayout.PREFERRED_SIZE, 385, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pSoLuongTonKho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pOverviewLayout.createSequentialGroup()
                .addGap(451, 451, 451)
                .addComponent(lblThongKe7NgayQua)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pOverviewLayout.setVerticalGroup(
            pOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pOverviewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pOverviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pSoLuongXuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pSoLuongTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pSoLuongNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblThongKe7NgayQua)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pChartOverview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPane.addTab("Tổng quan", pOverview);

        pTonKho.setBackground(new java.awt.Color(255, 255, 255));

        pFilterTonKho.setBackground(new java.awt.Color(255, 255, 255));
        pFilterTonKho.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pFilterTonKho.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        inputTonKho.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        inputTonKho.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputTonKhoKeyPressed(evt);
            }
        });
        pFilterTonKho.add(inputTonKho, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 280, 40));

        cbTonKhoDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbTonKhoDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTonKhoDateActionPerformed(evt);
            }
        });
        pFilterTonKho.add(cbTonKhoDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 280, -1));

        lblSearchTonKho.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblSearchTonKho.setForeground(new java.awt.Color(0, 0, 0));
        lblSearchTonKho.setText("Tìm kiếm sản phẩm");
        pFilterTonKho.add(lblSearchTonKho, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        btnReloadTonKho.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnReloadTonKho.setText("Làm mới");
        btnReloadTonKho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReloadTonKho.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReloadTonKho.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReloadTonKho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadTonKhoActionPerformed(evt);
            }
        });
        pFilterTonKho.add(btnReloadTonKho, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        btnExportExcelTonKho.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnExportExcelTonKho.setText("Xuất Excel");
        btnExportExcelTonKho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportExcelTonKho.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportExcelTonKho.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pFilterTonKho.add(btnExportExcelTonKho, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, -1));

        lblTonKhoDate.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTonKhoDate.setForeground(new java.awt.Color(0, 0, 0));
        lblTonKhoDate.setText("dd/mm/yyyy - dd/mm/yyyy");
        pFilterTonKho.add(lblTonKhoDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        tbTonKho.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbTonKho.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên sản phẩm", "Tồn đầu kỳ", "Nhập trong kỳ", "Xuất trong kỳ", "Tồn cuối kỳ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane2.setViewportView(tbTonKho);
        if (tbTonKho.getColumnModel().getColumnCount() > 0) {
            tbTonKho.getColumnModel().getColumn(0).setResizable(false);
            tbTonKho.getColumnModel().getColumn(1).setResizable(false);
            tbTonKho.getColumnModel().getColumn(2).setResizable(false);
            tbTonKho.getColumnModel().getColumn(3).setResizable(false);
            tbTonKho.getColumnModel().getColumn(4).setResizable(false);
            tbTonKho.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout pTonKhoLayout = new javax.swing.GroupLayout(pTonKho);
        pTonKho.setLayout(pTonKhoLayout);
        pTonKhoLayout.setHorizontalGroup(
            pTonKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTonKhoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pFilterTonKho, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE)
                .addContainerGap())
        );
        pTonKhoLayout.setVerticalGroup(
            pTonKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pTonKhoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pTonKhoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pFilterTonKho, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabbedPane.addTab("Tồn kho", pTonKho);

        pDoanhThu.setBackground(new java.awt.Color(255, 255, 255));

        pFilterDoanhThu.setBackground(new java.awt.Color(255, 255, 255));
        pFilterDoanhThu.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        pFilterDoanhThu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pFilterDoanhThu.add(cbDoanhThuDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 50, 210, -1));

        lblDoanhThuDate.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblDoanhThuDate.setForeground(new java.awt.Color(0, 0, 0));
        lblDoanhThuDate.setText("dd/mm/yyyy - dd/mm/yyyy");
        pFilterDoanhThu.add(lblDoanhThuDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, -1, -1));

        toolbarDoanhThu.setBackground(new java.awt.Color(255, 255, 255));
        toolbarDoanhThu.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));
        toolbarDoanhThu.setRollover(true);

        btnReloadTonKho1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        btnReloadTonKho1.setText("Làm mới");
        btnReloadTonKho1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReloadTonKho1.setFocusable(false);
        btnReloadTonKho1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReloadTonKho1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbarDoanhThu.add(btnReloadTonKho1);

        btnExportExcelTonKho1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_spreadsheet_file_40px.png"))); // NOI18N
        btnExportExcelTonKho1.setText("Xuất Excel");
        btnExportExcelTonKho1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportExcelTonKho1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportExcelTonKho1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbarDoanhThu.add(btnExportExcelTonKho1);

        tbDoanhThu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "[]", "Chi phí", "Doanh thu", "Lợi nhuận"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane3.setViewportView(tbDoanhThu);
        if (tbDoanhThu.getColumnModel().getColumnCount() > 0) {
            tbDoanhThu.getColumnModel().getColumn(0).setResizable(false);
            tbDoanhThu.getColumnModel().getColumn(1).setResizable(false);
            tbDoanhThu.getColumnModel().getColumn(2).setResizable(false);
            tbDoanhThu.getColumnModel().getColumn(3).setResizable(false);
            tbDoanhThu.getColumnModel().getColumn(3).setHeaderValue("Lợi nhuận");
        }

        javax.swing.GroupLayout pDoanhThuLayout = new javax.swing.GroupLayout(pDoanhThu);
        pDoanhThu.setLayout(pDoanhThuLayout);
        pDoanhThuLayout.setHorizontalGroup(
            pDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pDoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(scrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pDoanhThuLayout.createSequentialGroup()
                        .addComponent(pFilterDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(toolbarDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(chartDoanhThu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pDoanhThuLayout.setVerticalGroup(
            pDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDoanhThuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pDoanhThuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(toolbarDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pFilterDoanhThu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chartDoanhThu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPane.addTab("Doanh thu", pDoanhThu);

        pSanPham.setBackground(new java.awt.Color(255, 255, 255));

        pFilterSanPham.setBackground(new java.awt.Color(255, 255, 255));
        pFilterSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pFilterSanPham.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblSearchSanPham.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblSearchSanPham.setForeground(new java.awt.Color(0, 0, 0));
        lblSearchSanPham.setText("Tìm kiếm sản phẩm");
        pFilterSanPham.add(lblSearchSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        inputSanPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputSanPhamKeyPressed(evt);
            }
        });
        pFilterSanPham.add(inputSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 280, 40));

        lblSanPhamDate.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblSanPhamDate.setForeground(new java.awt.Color(0, 0, 0));
        lblSanPhamDate.setText("dd/mm/yyyy - dd/mm/yyyy");
        pFilterSanPham.add(lblSanPhamDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        cbSanPhamDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbSanPhamDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbSanPhamDateActionPerformed(evt);
            }
        });
        pFilterSanPham.add(cbSanPhamDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 280, -1));

        btnChiTietSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnChiTietSanPham.setText("Chi tiết");
        btnChiTietSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChiTietSanPham.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChiTietSanPham.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnChiTietSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietSanPhamActionPerformed(evt);
            }
        });
        pFilterSanPham.add(btnChiTietSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        btnExportExcelSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnExportExcelSanPham.setText("Xuất Excel");
        btnExportExcelSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportExcelSanPham.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportExcelSanPham.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pFilterSanPham.add(btnExportExcelSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, -1, -1));

        btnReloadSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnReloadSanPham.setText("Làm mới");
        btnReloadSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReloadSanPham.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReloadSanPham.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReloadSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadSanPhamActionPerformed(evt);
            }
        });
        pFilterSanPham.add(btnReloadSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, -1));

        tbSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tbSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Loại sản phẩm", "Tên sản phẩm", "Số lượng nhập"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbSanPhamMouseClicked(evt);
            }
        });
        scrollPane4.setViewportView(tbSanPham);
        if (tbSanPham.getColumnModel().getColumnCount() > 0) {
            tbSanPham.getColumnModel().getColumn(0).setResizable(false);
            tbSanPham.getColumnModel().getColumn(1).setResizable(false);
            tbSanPham.getColumnModel().getColumn(2).setResizable(false);
            tbSanPham.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout pSanPhamLayout = new javax.swing.GroupLayout(pSanPham);
        pSanPham.setLayout(pSanPhamLayout);
        pSanPhamLayout.setHorizontalGroup(
            pSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pFilterSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE)
                .addContainerGap())
        );
        pSanPhamLayout.setVerticalGroup(
            pSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pFilterSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(scrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 703, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabbedPane.addTab("Sản phẩm", pSanPham);

        pLoaiSanPham.setBackground(new java.awt.Color(255, 255, 255));

        pFilterLoaiSanPham.setBackground(new java.awt.Color(255, 255, 255));
        pFilterLoaiSanPham.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pFilterLoaiSanPham.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblSearchLoaiSanPham.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblSearchLoaiSanPham.setForeground(new java.awt.Color(0, 0, 0));
        lblSearchLoaiSanPham.setText("Tìm kiếm loại sản phẩm");
        pFilterLoaiSanPham.add(lblSearchLoaiSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        inputLoaiSanPham.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                inputLoaiSanPhamKeyPressed(evt);
            }
        });
        pFilterLoaiSanPham.add(inputLoaiSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 280, 40));

        lblLoaiSanPhamDate.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblLoaiSanPhamDate.setForeground(new java.awt.Color(0, 0, 0));
        lblLoaiSanPhamDate.setText("dd/mm/yyyy - dd/mm/yyyy");
        pFilterLoaiSanPham.add(lblLoaiSanPhamDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        cbLoaiSanPhamDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbLoaiSanPhamDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbLoaiSanPhamDateActionPerformed(evt);
            }
        });
        pFilterLoaiSanPham.add(cbLoaiSanPhamDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 280, -1));

        btnChiTietLoaiSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnChiTietLoaiSanPham.setText("Chi tiết");
        btnChiTietLoaiSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChiTietLoaiSanPham.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChiTietLoaiSanPham.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnChiTietLoaiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietLoaiSanPhamActionPerformed(evt);
            }
        });
        pFilterLoaiSanPham.add(btnChiTietLoaiSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        btnExportExcelLoaiSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnExportExcelLoaiSanPham.setText("Xuất Excel");
        btnExportExcelLoaiSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportExcelLoaiSanPham.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportExcelLoaiSanPham.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        pFilterLoaiSanPham.add(btnExportExcelLoaiSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, -1, -1));

        btnReloadLoaiSanPham.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnReloadLoaiSanPham.setText("Làm mới");
        btnReloadLoaiSanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReloadLoaiSanPham.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReloadLoaiSanPham.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReloadLoaiSanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadLoaiSanPhamActionPerformed(evt);
            }
        });
        pFilterLoaiSanPham.add(btnReloadLoaiSanPham, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 160, -1, -1));

        tbLoaiSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã", "Loại sản phẩm", "Số lượng xuất"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbLoaiSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbLoaiSanPhamMouseClicked(evt);
            }
        });
        scrollPane5.setViewportView(tbLoaiSanPham);
        if (tbLoaiSanPham.getColumnModel().getColumnCount() > 0) {
            tbLoaiSanPham.getColumnModel().getColumn(0).setResizable(false);
            tbLoaiSanPham.getColumnModel().getColumn(1).setResizable(false);
            tbLoaiSanPham.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout pLoaiSanPhamLayout = new javax.swing.GroupLayout(pLoaiSanPham);
        pLoaiSanPham.setLayout(pLoaiSanPhamLayout);
        pLoaiSanPhamLayout.setHorizontalGroup(
            pLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLoaiSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chartLoaiSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 1168, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pLoaiSanPhamLayout.createSequentialGroup()
                        .addComponent(pFilterLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPane5)))
                .addContainerGap())
        );
        pLoaiSanPhamLayout.setVerticalGroup(
            pLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pLoaiSanPhamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chartLoaiSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pLoaiSanPhamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pFilterLoaiSanPham, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                    .addComponent(scrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabbedPane.addTab("Loại sản phẩm", pLoaiSanPham);

        javax.swing.GroupLayout pContainerLayout = new javax.swing.GroupLayout(pContainer);
        pContainer.setLayout(pContainerLayout);
        pContainerLayout.setHorizontalGroup(
            pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pContainerLayout.createSequentialGroup()
                .addComponent(tabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 1180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pContainerLayout.setVerticalGroup(
            pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPane)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbTonKhoDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTonKhoDateActionPerformed
        // TODO add your handling code here:
        handleComboBoxChanged("tonkho", String.valueOf(cbTonKhoDate.getSelectedItem()), drTonKho, lblTonKhoDate);
        if (isLoadingTonKho) {
            thongKeTonKho(drTonKho, queryTonKho);
        }
    }//GEN-LAST:event_cbTonKhoDateActionPerformed

    private void btnReloadTonKhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadTonKhoActionPerformed
        // TODO add your handling code here:
        handleReload(btnReloadTonKho, "tonkho", drTonKho, queryTonKho);
    }//GEN-LAST:event_btnReloadTonKhoActionPerformed

    private void inputTonKhoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputTonKhoKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (inputTonKho.getText().trim().toLowerCase().equals(queryTonKho.toLowerCase()))
                return;
            setQueryTonKho(inputTonKho.getText().trim());
            thongKeTonKho(drTonKho, queryTonKho);
        }
    }//GEN-LAST:event_inputTonKhoKeyPressed
    
    private void btnChiTietSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietSanPhamActionPerformed
        // TODO add your handling code here:
        int row = tbSanPham.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm");
            return;
        }
        handleViewProductDetail(row);
    }//GEN-LAST:event_btnChiTietSanPhamActionPerformed

    private void btnReloadSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadSanPhamActionPerformed
        // TODO add your handling code here:
        handleReload(btnReloadSanPham, "sanpham", drSanPham, querySanPham);
    }//GEN-LAST:event_btnReloadSanPhamActionPerformed

    private void cbSanPhamDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbSanPhamDateActionPerformed
        // TODO add your handling code here:
        handleComboBoxChanged("sanpham", String.valueOf(cbSanPhamDate.getSelectedItem()), drSanPham, lblSanPhamDate);
        if (isLoadingSanPham) {
            thongKeSanPham(drSanPham, querySanPham);
        }
    }//GEN-LAST:event_cbSanPhamDateActionPerformed

    private void inputSanPhamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputSanPhamKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (inputSanPham.getText().trim().toLowerCase().equals(querySanPham.toLowerCase()))
                return;
            setQuerySanPham(inputSanPham.getText().trim());
            thongKeSanPham(drSanPham, querySanPham);
        }
    }//GEN-LAST:event_inputSanPhamKeyPressed

    private void tbSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbSanPhamMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2 && tbSanPham.getSelectedRow() != -1) {
            int row = tbSanPham.getSelectedRow();
            handleViewProductDetail(row);
        }
    }//GEN-LAST:event_tbSanPhamMouseClicked

    private void inputLoaiSanPhamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_inputLoaiSanPhamKeyPressed
        // TODO add your handling code here:
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (inputLoaiSanPham.getText().trim().toLowerCase().equals(queryLoaiSanPham.toLowerCase()))
                return;
            setQueryLoaiSanPham(inputLoaiSanPham.getText().trim());
            thongKeLoaiSanPham(drLoaiSanPham, queryLoaiSanPham);
        }
    }//GEN-LAST:event_inputLoaiSanPhamKeyPressed

    private void cbLoaiSanPhamDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbLoaiSanPhamDateActionPerformed
        // TODO add your handling code here:
        handleComboBoxChanged("loaisanpham", String.valueOf(cbLoaiSanPhamDate.getSelectedItem()), drLoaiSanPham, lblLoaiSanPhamDate);
        if (isLoadingLoaiSanPham) {
            thongKeLoaiSanPham(drLoaiSanPham, queryLoaiSanPham);
        }
    }//GEN-LAST:event_cbLoaiSanPhamDateActionPerformed

    private void btnChiTietLoaiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietLoaiSanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnChiTietLoaiSanPhamActionPerformed

    private void btnReloadLoaiSanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadLoaiSanPhamActionPerformed
        // TODO add your handling code here:
        handleReload(btnReloadLoaiSanPham, "loaisanpham", drLoaiSanPham, queryLoaiSanPham);
    }//GEN-LAST:event_btnReloadLoaiSanPhamActionPerformed

    private void tbLoaiSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbLoaiSanPhamMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2 && tbLoaiSanPham.getSelectedRow() != -1) {
            int row = tbLoaiSanPham.getSelectedRow();
            handleViewProductTypeDetail(row);
        }
    }//GEN-LAST:event_tbLoaiSanPhamMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChiTietLoaiSanPham;
    private javax.swing.JButton btnChiTietSanPham;
    private javax.swing.JButton btnExportExcelLoaiSanPham;
    private javax.swing.JButton btnExportExcelSanPham;
    private javax.swing.JButton btnExportExcelTonKho;
    private javax.swing.JButton btnExportExcelTonKho1;
    private javax.swing.JButton btnReloadLoaiSanPham;
    private javax.swing.JButton btnReloadSanPham;
    private javax.swing.JButton btnReloadTonKho;
    private javax.swing.JButton btnReloadTonKho1;
    private javax.swing.JComboBox<String> cbDoanhThuDate;
    private javax.swing.JComboBox<String> cbLoaiSanPhamDate;
    private javax.swing.JComboBox<String> cbSanPhamDate;
    private javax.swing.JComboBox<String> cbTonKhoDate;
    private GUI.Chart.Chart chartDoanhThu;
    private GUI.Chart.Chart chartLoaiSanPham;
    private GUI.Chart.Chart chartOverview;
    private javax.swing.JLabel iconSanPham;
    private javax.swing.JLabel iconSanPham1;
    private javax.swing.JLabel iconSanPham2;
    private javax.swing.JTextField inputLoaiSanPham;
    private javax.swing.JTextField inputSanPham;
    private javax.swing.JTextField inputTonKho;
    private javax.swing.JLabel lblDoanhThuDate;
    private javax.swing.JLabel lblLoaiSanPhamDate;
    private javax.swing.JLabel lblNhap;
    private javax.swing.JLabel lblSanPhamDate;
    private javax.swing.JLabel lblSearchLoaiSanPham;
    private javax.swing.JLabel lblSearchSanPham;
    private javax.swing.JLabel lblSearchTonKho;
    private javax.swing.JLabel lblThongKe7NgayQua;
    private javax.swing.JLabel lblTonKho;
    private javax.swing.JLabel lblTonKhoDate;
    private javax.swing.JLabel lblXuat;
    private javax.swing.JPanel pChartOverview;
    private javax.swing.JPanel pContainer;
    private javax.swing.JPanel pDoanhThu;
    private javax.swing.JPanel pFilterDoanhThu;
    private javax.swing.JPanel pFilterLoaiSanPham;
    private javax.swing.JPanel pFilterSanPham;
    private javax.swing.JPanel pFilterTonKho;
    private javax.swing.JPanel pLoaiSanPham;
    private javax.swing.JPanel pOverview;
    private javax.swing.JPanel pSanPham;
    private javax.swing.JPanel pSoLuongNhap;
    private javax.swing.JPanel pSoLuongTonKho;
    private javax.swing.JPanel pSoLuongXuat;
    private javax.swing.JPanel pTonKho;
    private javax.swing.JScrollPane scrollPane1;
    private javax.swing.JScrollPane scrollPane2;
    private javax.swing.JScrollPane scrollPane3;
    private javax.swing.JScrollPane scrollPane4;
    private javax.swing.JScrollPane scrollPane5;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable tbDoanhThu;
    private javax.swing.JTable tbLoaiSanPham;
    private javax.swing.JTable tbOverview;
    private javax.swing.JTable tbSanPham;
    private javax.swing.JTable tbTonKho;
    private javax.swing.JToolBar toolbarDoanhThu;
    private javax.swing.JLabel txtSoLuongNhap;
    private javax.swing.JLabel txtSoLuongXuat;
    private javax.swing.JLabel txtTonKho;
    // End of variables declaration//GEN-END:variables
}
