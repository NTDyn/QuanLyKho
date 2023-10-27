package GUI;

import DTO.ChiTietQuyenDTO;
import DTO.NguoiDungDTO;
import DTO.NhomQuyenDTO;
import view.*;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.Account;

public class MainLayout extends javax.swing.JFrame {
    Color DefaultColor, ClickedColor;
    private Account currentAcc;

    private NguoiDungDTO user;
    private NhomQuyenDTO permissionInfo;
    private List<ChiTietQuyenDTO> permission;
    
    private MainLayout() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Account getCurrentAcc() {
        return currentAcc;
    }
    
    public MainLayout(NguoiDungDTO user, NhomQuyenDTO permissionInfo, List<ChiTietQuyenDTO> permission) throws UnsupportedLookAndFeelException {
        ImageIcon logo = new ImageIcon(getClass().getResource("/icon/logo.png"));
        setIconImage(logo.getImage());
        UIManager.setLookAndFeel(new FlatLightLaf());
        UIManager.put("Table.showVerticalLines", true);
        UIManager.put("Table.showHorizontalLines", true);
        initComponents();
        setLocationRelativeTo(null);
        DefaultColor = new Color(89, 168, 105);
        ClickedColor = new Color(26, 188, 156);
        this.user = user;
        this.permission = permission;
        this.permissionInfo = permissionInfo;
        javax.swing.JPanel[] panels = {SanPham, NhaCungCap, PhieuNhap, PhieuXuat, TonKho, TaiKhoan, ThongKe};
        hideAllControllers(panels);
        authorize(panels, permission);
        lblFullName.setText(user.getHoTen());
        lblRole.setText(permissionInfo.getTenNhomQuyen());
        setVisible(true);
    }
    
    private void hideAllControllers(javax.swing.JPanel[] panels) {
    	for (javax.swing.JPanel p : panels) {
    		p.setVisible(false);
    	}
    }
    
    private void authorize(javax.swing.JPanel[] panels, List<ChiTietQuyenDTO> permission) {
        javax.swing.JPanel firstPanelFound = null;
        for (ChiTietQuyenDTO ctq: permission) {
            for (javax.swing.JPanel panel: panels) {
                if (panel.getName().equalsIgnoreCase(ctq.getMaChucNang())) {
                    panel.setVisible(true);
                    
                    if (firstPanelFound == null) {
                        firstPanelFound = panel;
                    }
                    break;
                }
            }
        }
        
        switch (firstPanelFound.getName()) {
            case "sanpham":
                SanPhamMouseClicked(null);
                SanPhamMousePressed(null);
                break;
            case "nhacungcap":
                NhaCungCapMouseClicked(null);
                NhaCungCapMousePressed(null);
                break;
            case "phieunhap":
                PhieuNhapMouseClicked(null);
                PhieuNhapMousePressed(null);
                break;
            case "phieuxuat":
                PhieuXuatMouseClicked(null);
                PhieuXuatMousePressed(null);
                break;
            case "tonkho":
                TonKhoMouseClicked(null);
                TonKhoMousePressed(null);
                break;
            case "taikhoan":
                TaiKhoanMouseClicked(null);
                TaiKhoanMousePressed(null);
                break;
            case "thongke":
                ThongKeMouseClicked(null);
                ThongKeMousePressed(null);
                break;
            default:
                
        }
    }
    
    private void resetBackgroundAllPanels() {
        javax.swing.JPanel[] panels = {NhaCungCap, NhapHang, PhieuNhap, XuatHang, PhieuXuat, SanPham, TaiKhoan, ThongKe, TonKho};
        for (javax.swing.JPanel panel : panels) {
            panel.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        }
    }

    public MainLayout(Account t) throws UnsupportedLookAndFeelException {
        ImageIcon logo = new ImageIcon(getClass().getResource("/icon/logo.png"));
        setIconImage(logo.getImage());
        UIManager.setLookAndFeel(new FlatLightLaf());
        UIManager.put("Table.showVerticalLines", true);
        UIManager.put("Table.showHorizontalLines", true);
        initComponents();
        setLocationRelativeTo(null);
        this.currentAcc = t;
        ProductForm pf = new ProductForm();
        pMainContent.add(pf).setVisible(true);
//        pf.checkRole(currentAcc);
        DefaultColor = new Color(89, 168, 105);
        ClickedColor = new Color(26, 188, 156);
        pSidebar.setBackground(DefaultColor);
        SanPham.setBackground(ClickedColor);
        PhieuNhap.setBackground(DefaultColor);
        NhapHang.setBackground(DefaultColor);
        XuatHang.setBackground(DefaultColor);
        PhieuXuat.setBackground(DefaultColor);
        NhaCungCap.setBackground(DefaultColor);
        TonKho.setBackground(DefaultColor);
        TaiKhoan.setBackground(DefaultColor);
        ThongKe.setBackground(DefaultColor);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pSidebar = new javax.swing.JPanel();
        pUserInfo = new javax.swing.JPanel();
        pFullName = new javax.swing.JPanel();
        lblFullName = new javax.swing.JLabel();
        pRole = new javax.swing.JPanel();
        lblRole = new javax.swing.JLabel();
        pNav = new javax.swing.JPanel();
        NhaCungCap = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        NhapHang = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        PhieuNhap = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        XuatHang = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        PhieuXuat = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        SanPham = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TaiKhoan = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        TonKho = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        ThongKe = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        pSettings = new javax.swing.JPanel();
        Account = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        DangXuat = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        pMainContent = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý kho hàng máy tính");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pSidebar.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        pSidebar.setBorder(javax.swing.BorderFactory.createEmptyBorder(16, 1, 16, 1));
        pSidebar.setMinimumSize(new java.awt.Dimension(240, 690));
        pSidebar.setPreferredSize(new java.awt.Dimension(240, 730));
        pSidebar.setLayout(new java.awt.BorderLayout(0, 15));

        pUserInfo.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        pUserInfo.setLayout(new java.awt.GridLayout(0, 1));

        pFullName.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        pFullName.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        pFullName.setLayout(new java.awt.BorderLayout());

        lblFullName.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        lblFullName.setForeground(new java.awt.Color(255, 255, 255));
        lblFullName.setText("[fullname]");
        lblFullName.setToolTipText("");
        pFullName.add(lblFullName, java.awt.BorderLayout.CENTER);

        pUserInfo.add(pFullName);

        pRole.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        pRole.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        pRole.setLayout(new java.awt.BorderLayout());

        lblRole.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        lblRole.setForeground(new java.awt.Color(255, 255, 255));
        lblRole.setText("[role]");
        pRole.add(lblRole, java.awt.BorderLayout.PAGE_START);

        pUserInfo.add(pRole);

        pSidebar.add(pUserInfo, java.awt.BorderLayout.NORTH);

        pNav.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        pNav.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING, 0, 0));

        NhaCungCap.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        NhaCungCap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        NhaCungCap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        NhaCungCap.setMinimumSize(new java.awt.Dimension(240, 36));
        NhaCungCap.setName("nhacungcap"); // NOI18N
        NhaCungCap.setPreferredSize(new java.awt.Dimension(240, 36));
        NhaCungCap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NhaCungCapMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                NhaCungCapMousePressed(evt);
            }
        });
        NhaCungCap.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_supplier_25px.png"))); // NOI18N
        jLabel6.setText("Nhà cung cấp");
        NhaCungCap.add(jLabel6, java.awt.BorderLayout.CENTER);

        pNav.add(NhaCungCap);

        NhapHang.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        NhapHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        NhapHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        NhapHang.setMinimumSize(new java.awt.Dimension(240, 36));
        NhapHang.setName("nhaphang"); // NOI18N
        NhapHang.setPreferredSize(new java.awt.Dimension(240, 36));
        NhapHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NhapHangMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                NhapHangMousePressed(evt);
            }
        });
        NhapHang.setLayout(new java.awt.BorderLayout());

        jLabel2.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_import_25px.png"))); // NOI18N
        jLabel2.setText("Nhập hàng");
        NhapHang.add(jLabel2, java.awt.BorderLayout.CENTER);

        pNav.add(NhapHang);

        PhieuNhap.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        PhieuNhap.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        PhieuNhap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PhieuNhap.setMinimumSize(new java.awt.Dimension(240, 36));
        PhieuNhap.setName("phieunhap"); // NOI18N
        PhieuNhap.setPreferredSize(new java.awt.Dimension(240, 36));
        PhieuNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PhieuNhapMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PhieuNhapMousePressed(evt);
            }
        });
        PhieuNhap.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_add_file_25px_2.png"))); // NOI18N
        jLabel3.setText("Phiếu nhập");
        PhieuNhap.add(jLabel3, java.awt.BorderLayout.CENTER);

        pNav.add(PhieuNhap);

        XuatHang.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        XuatHang.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        XuatHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        XuatHang.setMinimumSize(new java.awt.Dimension(240, 36));
        XuatHang.setName("xuathang"); // NOI18N
        XuatHang.setPreferredSize(new java.awt.Dimension(240, 36));
        XuatHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                XuatHangMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                XuatHangMousePressed(evt);
            }
        });
        XuatHang.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_export_25px.png"))); // NOI18N
        jLabel4.setText("Xuất hàng");
        XuatHang.add(jLabel4, java.awt.BorderLayout.CENTER);

        pNav.add(XuatHang);

        PhieuXuat.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        PhieuXuat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        PhieuXuat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        PhieuXuat.setMinimumSize(new java.awt.Dimension(240, 36));
        PhieuXuat.setName("phieuxuat"); // NOI18N
        PhieuXuat.setPreferredSize(new java.awt.Dimension(240, 36));
        PhieuXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PhieuXuatMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PhieuXuatMousePressed(evt);
            }
        });
        PhieuXuat.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_database_daily_export_25px.png"))); // NOI18N
        jLabel9.setText("Phiếu xuất");
        PhieuXuat.add(jLabel9, java.awt.BorderLayout.CENTER);

        pNav.add(PhieuXuat);

        SanPham.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        SanPham.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        SanPham.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SanPham.setMinimumSize(new java.awt.Dimension(240, 36));
        SanPham.setName("sanpham"); // NOI18N
        SanPham.setPreferredSize(new java.awt.Dimension(240, 36));
        SanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SanPhamMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                SanPhamMousePressed(evt);
            }
        });
        SanPham.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_product_25px_2.png"))); // NOI18N
        jLabel1.setLabelFor(SanPham);
        jLabel1.setText("Sản phẩm");
        SanPham.add(jLabel1, java.awt.BorderLayout.LINE_START);

        pNav.add(SanPham);
        SanPham.getAccessibleContext().setAccessibleName("");

        TaiKhoan.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        TaiKhoan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        TaiKhoan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TaiKhoan.setMinimumSize(new java.awt.Dimension(240, 36));
        TaiKhoan.setName("taikhoan"); // NOI18N
        TaiKhoan.setPreferredSize(new java.awt.Dimension(240, 36));
        TaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TaiKhoanMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TaiKhoanMousePressed(evt);
            }
        });
        TaiKhoan.setLayout(new java.awt.BorderLayout());

        jLabel12.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_test_account_25px.png"))); // NOI18N
        jLabel12.setText("Tài khoản");
        TaiKhoan.add(jLabel12, java.awt.BorderLayout.CENTER);

        pNav.add(TaiKhoan);

        TonKho.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        TonKho.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        TonKho.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TonKho.setMinimumSize(new java.awt.Dimension(240, 36));
        TonKho.setName("tonkho"); // NOI18N
        TonKho.setPreferredSize(new java.awt.Dimension(240, 36));
        TonKho.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TonKhoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                TonKhoMousePressed(evt);
            }
        });
        TonKho.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-warehouse-25.png"))); // NOI18N
        jLabel10.setText("Tồn kho");
        TonKho.add(jLabel10, java.awt.BorderLayout.CENTER);

        pNav.add(TonKho);

        ThongKe.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        ThongKe.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        ThongKe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ThongKe.setMinimumSize(new java.awt.Dimension(240, 36));
        ThongKe.setName("thongke"); // NOI18N
        ThongKe.setPreferredSize(new java.awt.Dimension(240, 36));
        ThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ThongKeMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ThongKeMousePressed(evt);
            }
        });
        ThongKe.setLayout(new java.awt.BorderLayout());

        jLabel13.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/statisticals.png"))); // NOI18N
        jLabel13.setText("Thống kê");
        ThongKe.add(jLabel13, java.awt.BorderLayout.CENTER);

        pNav.add(ThongKe);

        pSidebar.add(pNav, java.awt.BorderLayout.CENTER);

        pSettings.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        pSettings.setLayout(new java.awt.GridLayout(0, 1));

        Account.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        Account.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        Account.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Account.setMinimumSize(new java.awt.Dimension(240, 36));
        Account.setPreferredSize(new java.awt.Dimension(240, 36));
        Account.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AccountMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                AccountMousePressed(evt);
            }
        });
        Account.setLayout(new java.awt.BorderLayout());

        jLabel14.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-information-25.png"))); // NOI18N
        jLabel14.setText("Cá nhân");
        Account.add(jLabel14, java.awt.BorderLayout.CENTER);

        pSettings.add(Account);

        DangXuat.setBackground(javax.swing.UIManager.getDefaults().getColor("Actions.Green"));
        DangXuat.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 24, 1, 1));
        DangXuat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        DangXuat.setMinimumSize(new java.awt.Dimension(240, 36));
        DangXuat.setPreferredSize(new java.awt.Dimension(240, 36));
        DangXuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DangXuatMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                DangXuatMousePressed(evt);
            }
        });
        DangXuat.setLayout(new java.awt.BorderLayout());

        jLabel5.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_shutdown_25px.png"))); // NOI18N
        jLabel5.setText("Đăng xuất");
        DangXuat.add(jLabel5, java.awt.BorderLayout.CENTER);

        pSettings.add(DangXuat);

        pSidebar.add(pSettings, java.awt.BorderLayout.SOUTH);

        getContentPane().add(pSidebar, java.awt.BorderLayout.WEST);

        pMainContent.setBackground(new java.awt.Color(255, 255, 255));
        pMainContent.setPreferredSize(new java.awt.Dimension(1180, 750));

        javax.swing.GroupLayout pMainContentLayout = new javax.swing.GroupLayout(pMainContent);
        pMainContent.setLayout(pMainContentLayout);
        pMainContentLayout.setHorizontalGroup(
            pMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1177, Short.MAX_VALUE)
        );
        pMainContentLayout.setVerticalGroup(
            pMainContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 862, Short.MAX_VALUE)
        );

        getContentPane().add(pMainContent, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SanPhamMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SanPhamMousePressed
        // TODO add your handling code here:
        resetBackgroundAllPanels();
        SanPham.setBackground(ClickedColor);
    }//GEN-LAST:event_SanPhamMousePressed

    private void NhaCungCapMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NhaCungCapMousePressed
        // TODO add your handling code here:
        resetBackgroundAllPanels();
        NhaCungCap.setBackground(ClickedColor);
    }//GEN-LAST:event_NhaCungCapMousePressed

    private void NhapHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NhapHangMousePressed
        // TODO add your handling code here:
        resetBackgroundAllPanels();
        NhapHang.setBackground(ClickedColor);
    }//GEN-LAST:event_NhapHangMousePressed

    private void PhieuNhapMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PhieuNhapMousePressed
        // TODO add your handling code here:
        resetBackgroundAllPanels();
        PhieuNhap.setBackground(ClickedColor);
    }//GEN-LAST:event_PhieuNhapMousePressed

    private void XuatHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_XuatHangMousePressed
        // TODO add your handling code here:
        resetBackgroundAllPanels();
        XuatHang.setBackground(ClickedColor);
    }//GEN-LAST:event_XuatHangMousePressed

    private void PhieuXuatMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PhieuXuatMousePressed
        // TODO add your handling code here:
        resetBackgroundAllPanels();
        PhieuXuat.setBackground(ClickedColor);
    }//GEN-LAST:event_PhieuXuatMousePressed

    private void SanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SanPhamMouseClicked
        // TODO add your handling code here:
        ProductForm productForm = new ProductForm(user);
        pMainContent.removeAll();
        pMainContent.add(productForm).setVisible(true);
    }//GEN-LAST:event_SanPhamMouseClicked

    private void TonKhoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TonKhoMousePressed
        // TODO add your handling code here:
        resetBackgroundAllPanels();
        TonKho.setBackground(ClickedColor);
    }//GEN-LAST:event_TonKhoMousePressed

    private void DangXuatMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DangXuatMousePressed
        // TODO add your handling code here:
        DangXuat.setBackground(ClickedColor);
    }//GEN-LAST:event_DangXuatMousePressed

    private void NhaCungCapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NhaCungCapMouseClicked
        // TODO add your handling code here:
        NhaCungCapForm nhaCungCapForm = new NhaCungCapForm(user);
        pMainContent.removeAll();
        pMainContent.add(nhaCungCapForm).setVisible(true);
    }//GEN-LAST:event_NhaCungCapMouseClicked

    private void TonKhoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TonKhoMouseClicked
        // TODO add your handling code here:
        TonKhoGUI tonKhoForm = new TonKhoGUI();
        pMainContent.removeAll();
        pMainContent.add(tonKhoForm).setVisible(true);

    }//GEN-LAST:event_TonKhoMouseClicked

    private void NhapHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_NhapHangMouseClicked
        // TODO add your handling code here:
        NhapHangForm nhapHangForm = new NhapHangForm(user);
//        nhaphang.setNguoiNhapHang(this.currentAcc.getUser());
        pMainContent.removeAll();
        pMainContent.add(nhapHangForm).setVisible(true);
    }//GEN-LAST:event_NhapHangMouseClicked

    private void PhieuNhapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PhieuNhapMouseClicked
        // TODO add your handling code here:
        PhieuNhapForm phieuNhapForm = new PhieuNhapForm(user);
        pMainContent.removeAll();
        pMainContent.add(phieuNhapForm).setVisible(true);
    }//GEN-LAST:event_PhieuNhapMouseClicked

    private void XuatHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_XuatHangMouseClicked
        // TODO add your handling code here:
        XuatHangForm xuatHangForm = new XuatHangForm(user);
//        xh.setNguoiTao(this.currentAcc.getFullName());
        pMainContent.removeAll();
        pMainContent.add(xuatHangForm).setVisible(true);
    }//GEN-LAST:event_XuatHangMouseClicked

    private void PhieuXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PhieuXuatMouseClicked
        // TODO add your handling code here:
        PhieuXuatForm phieuXuatForm = new PhieuXuatForm(user);
        pMainContent.removeAll();
        pMainContent.add(phieuXuatForm).setVisible(true);
    }//GEN-LAST:event_PhieuXuatMouseClicked

    private void DangXuatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DangXuatMouseClicked
        // TODO add your handling code here:
        int relly = JOptionPane.showConfirmDialog(
                null,
                "Bạn có chắc chắn muốn đăng xuất không?",
                "Đăng xuất",
                JOptionPane.YES_NO_OPTION);
        if (relly == JOptionPane.YES_OPTION) {
            setUser(null);
            setPermissionInfo(null);
            setPermission(null);
            this.dispose();
            Login a = new Login();
            a.setVisible(true);
        } else {
            DangXuat.setBackground(DefaultColor);
        }
    }//GEN-LAST:event_DangXuatMouseClicked

    private void TaiKhoanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TaiKhoanMouseClicked
        // TODO add your handling code here:
        AccountForm accountForm = new AccountForm(user);
        pMainContent.removeAll();
        pMainContent.add(accountForm).setVisible(true);
    }//GEN-LAST:event_TaiKhoanMouseClicked

    private void TaiKhoanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TaiKhoanMousePressed
        // TODO add your handling code here:
        resetBackgroundAllPanels();
        TaiKhoan.setBackground(ClickedColor);
    }//GEN-LAST:event_TaiKhoanMousePressed

    private void ThongKeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThongKeMouseClicked
        // TODO add your handling code here:
        ThongKeForm thongKeForm = new ThongKeForm();
        pMainContent.removeAll();
        pMainContent.add(thongKeForm).setVisible(true);
    }//GEN-LAST:event_ThongKeMouseClicked

    private void ThongKeMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ThongKeMousePressed
        // TODO add your handling code here:
        resetBackgroundAllPanels();
        ThongKe.setBackground(ClickedColor);
    }//GEN-LAST:event_ThongKeMousePressed

    private void AccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AccountMouseClicked
        // TODO add your handling code here:
        ChangePassword cp = new ChangePassword(this, rootPaneCheckingEnabled, user);
        cp.setVisible(true);
    }//GEN-LAST:event_AccountMouseClicked

    private void AccountMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AccountMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AccountMousePressed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        int resp = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn thoát ?", "Exit?", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        } else {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        }
    }//GEN-LAST:event_formWindowClosing

    public NguoiDungDTO getUser() {
        return user;
    }

    public void setUser(NguoiDungDTO user) {
        this.user = user;
    }

    public NhomQuyenDTO getPermissionInfo() {
        return permissionInfo;
    }

    public void setPermissionInfo(NhomQuyenDTO permissionInfo) {
        this.permissionInfo = permissionInfo;
    }

    public List<ChiTietQuyenDTO> getPermission() {
        return permission;
    }

    public void setPermission(List<ChiTietQuyenDTO> permission) {
        this.permission = permission;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainLayout().setVisible(true);
            }
        });
    }

    public void setName(String name) {
        this.lblFullName.setText(name);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Account;
    private javax.swing.JPanel DangXuat;
    private javax.swing.JPanel NhaCungCap;
    private javax.swing.JPanel NhapHang;
    private javax.swing.JPanel PhieuNhap;
    private javax.swing.JPanel PhieuXuat;
    private javax.swing.JPanel SanPham;
    private javax.swing.JPanel TaiKhoan;
    private javax.swing.JPanel ThongKe;
    private javax.swing.JPanel TonKho;
    private javax.swing.JPanel XuatHang;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel lblFullName;
    private javax.swing.JLabel lblRole;
    private javax.swing.JPanel pFullName;
    private javax.swing.JPanel pMainContent;
    private javax.swing.JPanel pNav;
    private javax.swing.JPanel pRole;
    private javax.swing.JPanel pSettings;
    private javax.swing.JPanel pSidebar;
    private javax.swing.JPanel pUserInfo;
    // End of variables declaration//GEN-END:variables
}
