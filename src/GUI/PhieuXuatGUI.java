/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GUI;

import BUS.ChiTietQuyenBUS;
import DTO.PhieuXuatDTO;
import BUS.PhieuXuatBUS;
import DAO.ChiTietPhieuXuatDAO;
import DTO.ChiTietQuyenDTO;
import DTO.NguoiDungDTO;
import DAO.PhieuXuatDAO;
import DTO.ChiTietPhieuXuatDTO;
import java.util.ArrayList;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import model.Account;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import view.PhieuXuatForm;

/**
 *
 * @author EV
 */
public class PhieuXuatGUI extends javax.swing.JFrame {

    /**
     * Creates new form PhieuXuatGUI
     */
    private final ChiTietQuyenBUS ctqBUS = new ChiTietQuyenBUS();
    private DefaultTableModel tblModel;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");

    public DecimalFormat getFormatter() {
        return formatter;
    }

    public SimpleDateFormat getFormatDate() {
        return formatDate;
    }

    public PhieuXuatGUI(NguoiDungDTO user) {
        initComponents();
//        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
//        ui.setNorthPane(null);
        tblPhieuXuat.setDefaultEditor(Object.class, null);
        initTable();
        jDateChooserFrom.setDateFormatString("dd/MM/yyyy");
        jDateChooserTo.setDateFormatString("dd/MM/yyyy");

        // Authorization
//        javax.swing.JButton[] buttons = {btnAdd};
//        disableAllButtons(buttons);
        authorizeAction(user);
//   authorizeAction("nam");
//        loadDataToTable();
    }

    public PhieuXuatGUI(Account accCur) {
        initComponents();
//        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
//        ui.setNorthPane(null);
        tblPhieuXuat.setDefaultEditor(Object.class, null);
        initTable();
//        loadDataToTable();
        jDateChooserFrom.setDateFormatString("dd/MM/yyyy");
        jDateChooserTo.setDateFormatString("dd/MM/yyyy");
        if (accCur.getRole().equals("Nhân viên xuất")) {
//            btnDelete.setEnabled(false);
//            btnEdit.setEnabled(false);
       
        }
    }

    protected ArrayList<PhieuXuatDTO> allPhieuXuat;
    protected static int MaPhieuXuat;
    protected static ArrayList<ChiTietPhieuXuatDTO> CTPhieuXuat;
//    private NguoiDungDTO user;

    public PhieuXuatGUI() {

        initComponents();
//        loadDataToTable();
        initTable();

        allPhieuXuat = PhieuXuatDAO.getInstance().selectAll();
        // Định dạng độ rộng

        loadDataToTable(allPhieuXuat);

//        tblSanPham.setDefaultEditor(Object.class, null);
//        tblNhapHang.setDefaultEditor(Object.class, null);
//        MaPhieuXuat = createId(PhieuXuatDAO.getInstance().selectAll());
//        txtMaPhieu.setText(String.valueOf(MaPhieuXuat));
//
        CTPhieuXuat = new ArrayList<ChiTietPhieuXuatDTO>();
//        txtNguoiTao.setFocusable(false);
//
////        this.user = user;
////        txtNguoiTao.setText(user.getHoTen());
//        txtNguoiTao.setText("tram");
    }

    public ChiTietPhieuXuatDTO findCTPhieu(int maMay) {
        if (maMay == ChiTietPhieuXuatDAO.getInstance().selectById(maMay).getMaPhieuXuat()) {
            return ChiTietPhieuXuatDAO.getInstance().selectById(maMay);
        }

        return null;
    }

    private void disableAllButtons(javax.swing.JButton[] buttons) {
        for (javax.swing.JButton btn : buttons) {
            btn.setEnabled(false);
        }
    }

    private void authorizeAction(NguoiDungDTO user) {
        // Get all allowed actions in this functionality
        List<ChiTietQuyenDTO> allowedActions = new ArrayList<>();
        try {
            allowedActions = ctqBUS.getAllowedActions(user.getMaNhomQuyen(), "phieuxuat");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(PhieuXuatGUI.this, "Lỗi kết nối cơ sở dữ liệu", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(PhieuXuatGUI.this, "Lỗi không xác định", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }

        for (ChiTietQuyenDTO ctq : allowedActions) {
            if (ctq.getHanhDong().equals("create")) {
//                btnAdd.setEnabled(true);
                continue;
            }
        }
    }

    public final void initTable() {
        tblModel = new DefaultTableModel();
        String[] headerTbl = new String[]{"STT", "Mã phiếu nhập", "Người tạo", "Thời gian tạo", "Tổng tiền"};
        tblModel.setColumnIdentifiers(headerTbl);
        tblPhieuXuat.setModel(tblModel);
        tblPhieuXuat.getColumnModel().getColumn(0).setPreferredWidth(5);
    }

    public void loadDataToTable(ArrayList<PhieuXuatDTO> arrPX) {
        try {
            int stt = 1;
            tblModel.setRowCount(0);
            for (var i : arrPX) {
//                System.out.println(i.getNguoiTao());
                tblModel.addRow(new Object[]{
                    stt++,
                    i.getMaPhieuXuat(), i.getNguoiTao(), i.getThoiGianTao(), i.getTongTien() + " đ"

//                    AccountDAO.getInstance().selectById(allPhieu.get(i).getNguoiTao()).getFullName(), 
//                    formatDate.format(allPhieu.get(i).getThoiGianTao()),
//                    formatter.format(allPhieu.get(i).getTongTien()) + "đ"              
//                       i.getMaSanPham(), i.getTenSanPham(), i.getSoLuong(), formatter.format(i.getGiaXuat()) + "đ"
                });
            }
        } catch (Exception e) {
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuXuat = new javax.swing.JTable();
        jToolBar1 = new javax.swing.JToolBar();
        btnDetail = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jPanel2 = new javax.swing.JPanel();
        jDateChooserFrom = new com.toedter.calendar.JDateChooser();
        jDateChooserTo = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        giaTu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        giaDen = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxS = new javax.swing.JComboBox<>();
        jTextFieldSearch = new javax.swing.JTextField();
        btnRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        tblPhieuXuat.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        tblPhieuXuat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã phiếu", "Thời gian tạo", "Người tạo", "Tổng tiền", "Trạng thái"
            }
        ));
        jScrollPane1.setViewportView(tblPhieuXuat);

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));
        jToolBar1.setRollover(true);

        btnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_eye_40px.png"))); // NOI18N
        btnDetail.setText("Xem chi tiết");
        btnDetail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDetail.setFocusable(false);
        btnDetail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDetail.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDetail);
        jToolBar1.add(jSeparator1);

        jPanel2.setBackground(java.awt.Color.white);
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm kiếm theo ngày", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jDateChooserFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserFromPropertyChange(evt);
            }
        });

        jDateChooserTo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserToPropertyChange(evt);
            }
        });

        jLabel1.setText("Từ ngày");

        jLabel2.setText("Đến ngày");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jDateChooserFrom, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jDateChooserTo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooserTo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jDateChooserFrom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm theo giá"));

        jLabel3.setText("Từ");

        giaTu.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                giaTuPropertyChange(evt);
            }
        });
        giaTu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                giaTuKeyReleased(evt);
            }
        });

        jLabel4.setText("Đến");

        giaDen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                giaDenMouseReleased(evt);
            }
        });
        giaDen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                giaDenKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(giaTu, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(giaDen, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(giaTu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(giaDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxS.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        jComboBoxS.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Mã phiếu", "Người tạo" }));
        jPanel3.add(jComboBoxS, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 210, 40));

        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased(evt);
            }
        });
        jPanel3.add(jTextFieldSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 310, 40));

        btnRefresh.setFont(new java.awt.Font("SF Pro Display", 0, 15)); // NOI18N
        btnRefresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        btnRefresh.setText("Làm mới");
        btnRefresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });
        jPanel3.add(btnRefresh, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 140, 40));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 328, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 750, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 512, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        // TODO add your handling code here:
        int i_row = tblPhieuXuat.getSelectedRow();
        if (i_row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xem !");
        } else {

            ChiTietPhieuXuatDTO mtl = findCTPhieu((int) tblPhieuXuat.getValueAt(i_row, 1));
            
            MaPhieuXuat = mtl.getMaPhieuXuat();
            
            
            
            ChiTietPhieuXuatDTO ctp = new ChiTietPhieuXuatDTO(mtl.getMaPhieuXuat(), mtl.getMaSanPham(), mtl.getSoLuong(), mtl.getDonGia());
            CTPhieuXuat.add(ctp);
            XemChiTietPhieuXuatGUI a = new XemChiTietPhieuXuatGUI();
            a.setVisible(true);
            CTPhieuXuat.clear();
////                            System.out.println(user);
//                            //System.out.println(PhieuXuatDAO.getInstance().getAllPhieuXuat().size());
//                            if (mtl != null) {
////
////                                System.out.println(findMayTinh((int) tblSanPham.getValueAt(i_row, 0)).getSoLuong());
//
//                                if (findMayTinh((int) tblSanPham.getValueAt(i_row, 0)).getSoLuong() < mtl.getSoLuong() + soluong) {
//                                    JOptionPane.showMessageDialog(this, "Số lượng máy không đủ !");
//
//                                } else {
//
//                                    mtl.setSoLuong(mtl.getSoLuong() + soluong);
//                                }
//                            } else {
//                                SanPhamDTO mt = SanPhamDAO.getInstance().selectByIdPX((int) tblSanPham.getValueAt(i_row, 0));
//                                ChiTietPhieuXuatDTO ctp = new ChiTietPhieuXuatDTO(MaPhieuXuat, mt.getMaSanPham(), soluong, mt.getGiaXuat());
//                                CTPhieuXuat.add(ctp);
//                            }
//
//                            loadDataToTableNhapHang();
//
//                            textTongTien.setText(formatter.format(tinhTongTien()) + "đ");
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng lớn hơn 0");
//                    }
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(this, "Vui lòng nhập số lượng ở dạng số nguyên!");
//                }
//            }
        }


    }//GEN-LAST:event_btnDetailActionPerformed

    private void jDateChooserFromPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserFromPropertyChange
        // TODO add your handling code here:
        searchAllCheck();
    }//GEN-LAST:event_jDateChooserFromPropertyChange

    private void jDateChooserToPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserToPropertyChange
        // TODO add your handling code here:
        searchAllCheck();
    }//GEN-LAST:event_jDateChooserToPropertyChange

    private void giaTuPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_giaTuPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_giaTuPropertyChange

    private void giaTuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giaTuKeyReleased
        // TODO add your handling code here:
        searchAllCheck();
    }//GEN-LAST:event_giaTuKeyReleased

    private void giaDenMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_giaDenMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_giaDenMouseReleased

    private void giaDenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giaDenKeyReleased
        // TODO add your handling code here:
        searchAllCheck();
    }//GEN-LAST:event_giaDenKeyReleased

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        // TODO add your handling code here:
        searchAllCheck();
    }//GEN-LAST:event_jTextFieldSearchKeyReleased

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        jComboBoxS.setSelectedIndex(0);
        jTextFieldSearch.setText("");
        jDateChooserFrom.setCalendar(null);
        jDateChooserTo.setCalendar(null);
        giaDen.setText("");
        giaTu.setText("");
        loadDataToTable(allPhieuXuat);
    }//GEN-LAST:event_btnRefreshActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PhieuXuatGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PhieuXuatGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PhieuXuatGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PhieuXuatGUI.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new PhieuXuatGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JTextField giaDen;
    private javax.swing.JTextField giaTu;
    private javax.swing.JComboBox<String> jComboBoxS;
    private com.toedter.calendar.JDateChooser jDateChooserFrom;
    private com.toedter.calendar.JDateChooser jDateChooserTo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tblPhieuXuat;
    // End of variables declaration//GEN-END:variables

    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public boolean checkDate(Date dateTest, Date star, Date end) {
        return dateTest.getTime() >= star.getTime() && dateTest.getTime() <= end.getTime();
    }

    public Date ChangeFrom(Date date) throws ParseException {
        SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy 00:00:00");
        String dateText = fm.format(date);
        SimpleDateFormat par = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date result = par.parse(dateText);
        return result;
    }

    public Date ChangeTo(Date date) throws ParseException {
        SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy 23:59:59");
        String dateText = fm.format(date);
        SimpleDateFormat par = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        Date result = par.parse(dateText);
        return result;
    }

    public void searchAllCheck() {
        String luaChon = jComboBoxS.getSelectedItem().toString();
        String content = jTextFieldSearch.getText();
        ArrayList<PhieuXuatDTO> result = null;
        if (content.length() > 0) {
            result = new ArrayList<>();
            switch (luaChon) {
                case "Tất cả":
                    result = searchTatCa(content);
                    break;
                case "Mã phiếu":
                    result = searchMaPhieu(content);
                    break;
                case "Người tạo":
                    result = searchNguoiTao(content);
                    break;
            }
        } else if (content.length() == 0) {
            result = PhieuXuatDAO.getInstance().selectAll();
        }

        Iterator<PhieuXuatDTO> itr = result.iterator();

        if (jDateChooserFrom.getDate() != null || jDateChooserTo.getDate() != null) {
            Date from;
            Date to;

            if (jDateChooserFrom.getDate() != null && jDateChooserTo.getDate() == null) {
                try {
                    from = ChangeFrom(jDateChooserFrom.getDate());
                    to = ChangeTo(new Date());
                    while (itr.hasNext()) {
                        PhieuXuatDTO phieu = itr.next();
                        if (!checkDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(phieu.getThoiGianTao()), from, to)) {
                            itr.remove();
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(PhieuXuatGUI.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else if (jDateChooserTo.getDate() != null && jDateChooserFrom.getDate() == null) {
                try {
                    String sDate1 = "01/01/2002";
                    from = ChangeFrom(new SimpleDateFormat("dd/MM/yyyy").parse(sDate1));
                    to = ChangeTo(jDateChooserTo.getDate());
                    while (itr.hasNext()) {
                        PhieuXuatDTO phieu = itr.next();
                        if (!checkDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(phieu.getThoiGianTao()), from, to)) {
                            itr.remove();

                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(PhieuXuatGUI.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    from = ChangeFrom(jDateChooserFrom.getDate());
                    to = ChangeTo(jDateChooserTo.getDate());
                    if (from.getTime() > to.getTime()) {
                        JOptionPane.showMessageDialog(this, "Thời gian không hợp lệ !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                        jDateChooserFrom.setCalendar(null);
                        jDateChooserTo.setCalendar(null);
                    } else {
                        while (itr.hasNext()) {
                            PhieuXuatDTO phieu = itr.next();
                            if (!checkDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(phieu.getThoiGianTao()), from, to)) {
                                itr.remove();

                            }
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(PhieuXuatGUI.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        ArrayList<PhieuXuatDTO> result1 = new ArrayList<>();

        if (giaTu.getText().length() > 0 || giaDen.getText().length() > 0) {
            double a;
            double b;

            if (giaTu.getText().length() > 0 && giaDen.getText().length() == 0) {
                a = Double.parseDouble(giaTu.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTongTien() >= a) {
                        result1.add(result.get(i));
                    }
                }
            } else if (giaTu.getText().length() == 0 && giaDen.getText().length() > 0) {
                b = Double.parseDouble(giaDen.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTongTien() <= b) {
                        result1.add(result.get(i));
                    }
                }
            } else if (giaTu.getText().length() > 0 && giaDen.getText().length() > 0) {
                a = Double.parseDouble(giaTu.getText());
                b = Double.parseDouble(giaDen.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTongTien() >= a && result.get(i).getTongTien() <= b) {
                        result1.add(result.get(i));
                    }
                }
            }
        }
        if (giaTu.getText().length() > 0 || giaDen.getText().length() > 0) {
            loadDataToTable(result1);
        } else {
            loadDataToTable(result);
        }
    }

    private boolean isNumeric(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public ArrayList<PhieuXuatDTO> searchTatCa(String text) {
        ArrayList<PhieuXuatDTO> result = new ArrayList<>();

        ArrayList<PhieuXuatDTO> armt = PhieuXuatDAO.getInstance().selectAll();

        for (var phieu : armt) {
            if (phieu.getNguoiTao().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }

            if (!text.isEmpty() && isNumeric(text) && phieu.getMaPhieuXuat() == Integer.parseInt(text)) {
                result.add(phieu);
            }
        }
        return result;
    }

    public ArrayList<PhieuXuatDTO> searchMaPhieu(String text) {
        ArrayList<PhieuXuatDTO> result = new ArrayList<>();
        ArrayList<PhieuXuatDTO> armt = PhieuXuatDAO.getInstance().selectAll();
        for (var phieu : armt) {
//            if (phieu.getMaPhieuXuat() == Integer.valueOf(text)) {
//                result.add(phieu);
//            }
            if (!text.isEmpty() && isNumeric(text) && phieu.getMaPhieuXuat() == Integer.parseInt(text)) {
                result.add(phieu);
            }
        }
        return result;
    }

    public ArrayList<PhieuXuatDTO> searchNguoiTao(String text) {
        ArrayList<PhieuXuatDTO> result = new ArrayList<>();
        ArrayList<PhieuXuatDTO> armt = PhieuXuatDAO.getInstance().selectAll();
        for (var phieu : armt) {

            if (phieu.getNguoiTao().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }

        }
        return result;
    }

}
