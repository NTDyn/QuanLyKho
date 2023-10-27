/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view;

import BUS.ChiTietQuyenBUS;
import BUS.SanPhamBUS;
import DTO.ChiTietQuyenDTO;
import DTO.NguoiDungDTO;
import DTO.SanPhamDTO;
import DAO.SanPhamDAO;
import GUI.loaiSanPhamGUI;
import controller.SearchProduct;
import OldDAO.LaptopDAO;
import OldDAO.MayTinhDAO;
import OldDAO.PCDAO;
import java.awt.Desktop;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.Account;
import model.MayTinh;
import model.PC;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Robot
 */
public class ProductForm extends javax.swing.JInternalFrame {
    SanPhamBUS spBUS = new SanPhamBUS();
    private DefaultTableModel tblModel;
    DefaultTableCellRenderer renderer;
    DecimalFormat formatter = new DecimalFormat("###, ###, ###");
    private final ChiTietQuyenBUS ctqBUS = new ChiTietQuyenBUS();
    
    public ProductForm(NguoiDungDTO user) {
        initComponents();
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        tblSanPham.setDefaultEditor(Object.class, null);
        initTable();
        loadDataToTable();
        changeTextFind();
        
        // Authorization
        javax.swing.JButton[] buttons = {btnAdd, btnDelete, btnEdit};
        disableAllButtons(buttons);
        authorizeAction(user);

        // loadDataToTable();
    }
    
    public ProductForm() {
        initComponents();
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        tblSanPham.setDefaultEditor(Object.class, null);
        initTable();
        loadDataToTable();
        changeTextFind();
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
            allowedActions = ctqBUS.getAllowedActions(user.getMaNhomQuyen(), "sanpham");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ProductForm.this, "Lỗi kết nối cơ sở dữ liệu", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ProductForm.this, "Lỗi không xác định", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return;
        }
        
        for (ChiTietQuyenDTO ctq : allowedActions) {
            if (ctq.getHanhDong().equals("create")) {
                btnAdd.setEnabled(true);
                continue;
            }
            if (ctq.getHanhDong().equals("update")) {
                btnEdit.setEnabled(true);
                continue;
            }
            if (ctq.getHanhDong().equals("delete")) {
                btnDelete.setEnabled(true);
                continue;
            }
        }
    }
    
    public void checkRole(Account t) {
        if(t.getRole().equals("Nhân viên nhập") || t.getRole().equals("Nhân viên xuất")) {
            btnAdd.setEnabled(false);
            btnDelete.setEnabled(false);
            btnEdit.setEnabled(false);
            btnXuatExcel.setEnabled(false);
            btnNhapExcel.setEnabled(false);
        } else {
            System.out.println("abcdjad");
        }
    }

    public final void initTable() {
        tblModel = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        String[] headerTbl = new String[]{"Mã máy", "Tên máy", "Số lượng", "Đơn giá"};
        tblModel.setColumnIdentifiers(headerTbl);
        tblSanPham.setModel(tblModel);
        tblSanPham.getColumnModel().getColumn(0).setPreferredWidth(5);
        tblSanPham.getColumnModel().getColumn(1).setPreferredWidth(200);
        tblSanPham.getColumnModel().getColumn(2).setPreferredWidth(5);

        
        renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblSanPham.setDefaultRenderer(String.class, renderer);
        tblSanPham.addMouseListener(new MouseAdapter(){
            private ActionEvent evt;
            public void mousePressed(MouseEvent mouseEvent){
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if(mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1 ){
                           btnDetailActionPerformed(evt); 
                }
            }
        });
        
    }

    public void loadDataToTable() {
        ArrayList<SanPhamDTO> arr = new ArrayList<SanPhamDTO>();
        arr = spBUS.getlistProducts();
        tblModel.setRowCount(0);
        
        for(int i = 0; i< arr.size() ; i++){
            SanPhamDTO spDTO = arr.get(i);
            int maSP = spDTO.getMaSanPham();
            String tenSP = spDTO.getTenSanPham();
            int soluong = spDTO.getSoLuong();
            int giaBan = spDTO.getGiaXuat();
            Object [] row = {maSP, tenSP, soluong, formatter.format( giaBan) + " đ"  };
            tblModel.addRow(row);
        }
        
        for(int i = 0; i < tblSanPham.getColumnCount(); i++){
            tblSanPham.getColumnModel().getColumn(i).setCellRenderer(renderer);
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
        jToolBar1 = new javax.swing.JToolBar();
        btnAdd = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDetail = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        btnXuatExcel = new javax.swing.JButton();
        btnNhapExcel = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxLuaChon = new javax.swing.JComboBox<>();
        jTextFieldSearch = new javax.swing.JTextField();
        btnLamMoi = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSanPham = new javax.swing.JTable();

        setBorder(null);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));
        jToolBar1.setRollover(true);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_add_40px.png"))); // NOI18N
        btnAdd.setText("Thêm");
        btnAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAdd.setFocusable(false);
        btnAdd.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAdd.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAdd);

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_delete_40px.png"))); // NOI18N
        btnDelete.setText("Xoá");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDelete.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDelete);

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_edit_40px.png"))); // NOI18N
        btnEdit.setText("Sửa");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.setFocusable(false);
        btnEdit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEdit.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEdit);

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

        btnXuatExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_spreadsheet_file_40px.png"))); // NOI18N
        btnXuatExcel.setText("Xuất Excel");
        btnXuatExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXuatExcel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnXuatExcel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });
        jToolBar1.add(btnXuatExcel);

        btnNhapExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_xls_40px.png"))); // NOI18N
        btnNhapExcel.setText("Nhập Excel");
        btnNhapExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNhapExcel.setFocusable(false);
        btnNhapExcel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnNhapExcel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnNhapExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNhapExcelActionPerformed(evt);
            }
        });
        jToolBar1.add(btnNhapExcel);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon_laptop.jpg"))); // NOI18N
        jButton2.setText("Thương hiệu");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxLuaChon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Mã máy", "Tên máy", "Số lượng", "Đơn giá", "Thương hiệu", "RAM", "CPU", "Dung lượng", "Card màn hình", "Màu sắc", "Pin" }));
        jComboBoxLuaChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLuaChonActionPerformed(evt);
            }
        });
        jComboBoxLuaChon.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBoxLuaChonPropertyChange(evt);
            }
        });
        jPanel3.add(jComboBoxLuaChon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 150, 40));

        jTextFieldSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSearchActionPerformed(evt);
            }
        });
        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased(evt);
            }
        });
        jPanel3.add(jTextFieldSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 30, 360, 40));

        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        btnLamMoi.setText("Làm mới");
        btnLamMoi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });
        jPanel3.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 140, 40));

        tblSanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblSanPham.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblSanPhamAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblSanPhamMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblSanPham);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 722, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1180, 750));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed

        AddProduct a = new  AddProduct(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
        a.setVisible(true);

    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        if (tblSanPham.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xoá");
        } else {
            xoaMayTinhSelect();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        if (tblSanPham.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa");
        } else {

            UpdateProduct a = new UpdateProduct(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
            a.setVisible(true);
            loadDataToTable();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();
            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Product");

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < tblSanPham.getColumnCount(); i++) {
                    Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tblSanPham.getColumnName(i));
                }

                for (int j = 0; j < tblSanPham.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tblSanPham.getColumnCount(); k++) {
                        Cell cell = row.createCell(k);
                        if (tblSanPham.getValueAt(j, k) != null) {
                            cell.setCellValue(tblSanPham.getValueAt(j, k).toString());
                        }

                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                openFile(saveFile.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void btnNhapExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNhapExcelActionPerformed
        // TODO add your handling code here:
//        File excelFile;
//        FileInputStream excelFIS = null;
//        BufferedInputStream excelBIS = null;
//        XSSFWorkbook excelJTableImport = null;
//        ArrayList<SanPhamDTO> listAccExcel = new ArrayList<SanPhamDTO>();
//        JFileChooser jf = new JFileChooser();
//        int result = jf.showOpenDialog(null);
//        jf.setDialogTitle("Open file");
//        Workbook workbook = null;
//        if (result == JFileChooser.APPROVE_OPTION) {
//            try {
//                excelFile = jf.getSelectedFile();
//                excelFIS = new FileInputStream(excelFile);
//                excelBIS = new BufferedInputStream(excelFIS);
//                excelJTableImport = new XSSFWorkbook(excelBIS);
//                XSSFSheet excelSheet = excelJTableImport.getSheetAt(0);
//                for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
//                    XSSFRow excelRow = excelSheet.getRow(row);
//                    String maMay = excelRow.getCell(0).getStringCellValue();
//                    String tenMay = excelRow.getCell(1).getStringCellValue();
//                    int soLuong = (int) excelRow.getCell(2).getNumericCellValue();
//                    String giaFomat = excelRow.getCell(3).getStringCellValue().replaceAll(",", "");
//                    int viTri = giaFomat.length() - 1;
//                    String giaoke = giaFomat.substring(0, viTri) + giaFomat.substring(viTri + 1);
//                    double donGia = Double.parseDouble(giaoke);
//                    String boXuLi = excelRow.getCell(4).getStringCellValue();
//                    String ram = excelRow.getCell(5).getStringCellValue();
//                    String boNho = excelRow.getCell(6).getStringCellValue();
//                    MayTinh mt = new MayTinh(maMay, tenMay, soLuong, donGia, boXuLi, ram, "", "", boNho, 1);
//                    listAccExcel.add(mt);
//                    DefaultTableModel table_acc = (DefaultTableModel) tblSanPham.getModel();
//                    table_acc.setRowCount(0);
//                    loadDataToTableSearch(listAccExcel);
//                }
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (IOException ex) {
//                Logger.getLogger(ProductForm.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        for (int i = 0; i < listAccExcel.size(); i++) {
//            SanPhamDTO spDTO = listAccExcel.get(i);
//            if (mayTinh.getMaMay().contains("LP")) {
//                Laptop lapNew = new Laptop(0, "", mayTinh.getMaMay(),
//                        mayTinh.getTenMay(), mayTinh.getSoLuong(), mayTinh.getGia(), mayTinh.getTenCpu(),
//                        mayTinh.getRam(), mayTinh.getXuatXu(), mayTinh.getCardManHinh(), mayTinh.getRom(), 1);
//                LaptopDAO.getInstance().insert(lapNew);
//            } else if (mayTinh.getMaMay().contains("PC")) {
//                PC pcNew = new PC("", 0, mayTinh.getMaMay(), mayTinh.getTenMay(), mayTinh.getSoLuong(),
//                        mayTinh.getGia(), mayTinh.getTenCpu(), mayTinh.getRam(), mayTinh.getXuatXu(), mayTinh.getCardManHinh(),
//                        mayTinh.getRom(), mayTinh.getTrangThai());
//                PCDAO.getInstance().insert(pcNew);
//            } else {
//                JOptionPane.showMessageDialog(this, "Mã máy " + mayTinh.getMaMay() + " không phù hợp !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
//            }
//        }
    }//GEN-LAST:event_btnNhapExcelActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        // TODO add your handling code here:
        jComboBoxLuaChon.setSelectedIndex(0);
        loadDataToTable();
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        // TODO add your handling code here:
        if (tblSanPham.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm !");
        } else {
            DetailProduct a = new DetailProduct(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
            a.setVisible(true);
        }
    }//GEN-LAST:event_btnDetailActionPerformed

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        // TODO add your handling code here:
        String luaChon = jComboBoxLuaChon.getSelectedItem().toString();
        String content = jTextFieldSearch.getText();
        ArrayList<SanPhamDTO> result = searchFn(luaChon, content);
        loadDataToTableSearch(result);
    }//GEN-LAST:event_jTextFieldSearchKeyReleased

    private void jComboBoxLuaChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLuaChonActionPerformed
        // TODO add your handling code here:
        String luaChon = jComboBoxLuaChon.getSelectedItem().toString();
        String content = jTextFieldSearch.getText();
        ArrayList<SanPhamDTO> result = searchFn(luaChon, content);
        loadDataToTableSearch(result);
    }//GEN-LAST:event_jComboBoxLuaChonActionPerformed

    private void jTextFieldSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextFieldSearchKeyPressed

    private void jComboBoxLuaChonPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBoxLuaChonPropertyChange
        // TODO add your handling code here:
        String luaChon = jComboBoxLuaChon.getSelectedItem().toString();
        String content = jTextFieldSearch.getText();
        ArrayList<SanPhamDTO> result = searchFn(luaChon, content);
        loadDataToTableSearch(result);
    }//GEN-LAST:event_jComboBoxLuaChonPropertyChange

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        loaiSanPhamGUI lsp = new loaiSanPhamGUI();
        lsp.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextFieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSearchActionPerformed

    private void tblSanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSanPhamMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamMouseClicked

    private void tblSanPhamAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblSanPhamAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tblSanPhamAncestorAdded

    public ArrayList<SanPhamDTO> searchFn(String luaChon, String content) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        SearchProduct searchPr = new SearchProduct();
        switch (luaChon) {
            case "Tất cả":
                result = searchPr.searchTatCa(content);
                break;
            case "Mã máy":
                result = searchPr.searchMaSanPham(content);
                break;
            case "Tên máy":
                result = searchPr.searchTenSanPham(content);
                break;
            case "Thương hiệu":
                result = searchPr.searchThuongHieu(content);
                break;
            case "Số lượng":
                result = searchPr.searchSoLuong(content);
                break;
            case "Đơn giá":
                result = searchPr.searchDonGia(content);
                break;
            case "RAM":
                result = searchPr.searchRam(content);
                break;
            case "CPU":
                result = searchPr.searchCpu(content);
                break;
            case "Pin":
                result = searchPr.searchPin(content);
                break;
            case "Card màn hình":
                result = searchPr.searchManHinh(content);
                break;
            case "Màu sắc":
                result = searchPr.searchMauSac(content);
                break;
            case "VGA":
                result = searchPr.searchVGA(content);
        }
        return result;
    }

    public boolean checklap() {
        if (LaptopDAO.getInstance().isLaptop(getMayTinhSelect().getMaMay()) == true) {
            return true;
        } else {
            return false;
        }
    }

    public SanPhamDTO getDetailSanPham() {
        int row = tblSanPham.getSelectedRow();
        int id = Integer.parseInt(tblModel.getValueAt(row, 0).toString());
        SanPhamDTO spDTO = SanPhamDAO.getInstance().selectProductByID(id);
        return spDTO;
    }

    public PC getDetailPC() {
        PC a = PCDAO.getInstance().selectById(getMayTinhSelect().getMaMay());
        return a;
    }

    public void xoaMayTinhSelect() {
        DefaultTableModel table_acc = (DefaultTableModel) tblSanPham.getModel();
        int row = tblSanPham.getSelectedRow();
        int luaChon = JOptionPane.showConfirmDialog(this, "Bạn có muốn xoá sản phẩm này?", "Xoá sản phẩm",
                JOptionPane.YES_NO_OPTION);
        if (luaChon == JOptionPane.YES_OPTION) {
            SanPhamDTO remove = getSanPhamSelect();
            SanPhamDAO.getInstance().deleteProduct(remove.getMaSanPham());
        }
        loadDataToTable();
    }

    public MayTinh getMayTinhSelect() {
        int i_row = tblSanPham.getSelectedRow();
        MayTinh acc = MayTinhDAO.getInstance().selectById(tblModel.getValueAt(i_row, 0).toString());
        return acc;
    }

    public SanPhamDTO getSanPhamSelect(){
        int row = tblSanPham.getSelectedRow();
        int id = Integer.parseInt(tblModel.getValueAt(row, 0).toString());
        SanPhamDTO spDTO = SanPhamDAO.getInstance().selectProductByID(id);
        return spDTO;
    }
    
    public void loadDataToTableSearch(ArrayList<SanPhamDTO> result) {
        try {
            tblModel.setRowCount(0);
            for (SanPhamDTO i : result) {
                
                tblModel.addRow(new Object[]{
                    i.getMaSanPham(), i.getTenSanPham(), i.getSoLuong(), formatter.format(i.getGiaXuat()) + "đ"
                });
            }
        } catch (Exception e) {
        }
    }

    public void changeTextFind() {
        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                /* do nothing */
                if (jTextFieldSearch.getText().length() == 0) {
                    loadDataToTable();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                /* do nothing */

            }
        });
    }
    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnNhapExcel;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBoxLuaChon;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tblSanPham;
    // End of variables declaration//GEN-END:variables

    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
