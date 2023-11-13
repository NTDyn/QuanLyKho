package GUI.Dialog;

import BUS.ThongKeBUS;
import DTO.DateRangeDTO;
import DTO.ThongKe.ChiTietSanPhamNhapDTO;
import DTO.ThongKe.ThongKeSanPhamDTO;
import GUI.Chart.PieChart.ModelPieChart;
import com.formdev.flatlaf.FlatLightLaf;
import helper.ChartColor;
import java.util.ArrayList;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ChiTietSanPhamNhapDialog extends javax.swing.JDialog {
    private final ThongKeBUS tkBUS = new ThongKeBUS();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private JInternalFrame parent;
    private DefaultTableModel dtm;
    private DefaultTableCellRenderer dtcr;
    private ArrayList<ChiTietSanPhamNhapDTO> arr;
    private ThongKeSanPhamDTO product;
    private DateRangeDTO dateRange;
    
    public ChiTietSanPhamNhapDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initTable();
    }
    
    public ChiTietSanPhamNhapDialog(JInternalFrame parent, javax.swing.JFrame owner, boolean modal, DateRangeDTO dateRange, ThongKeSanPhamDTO product) {
        super(owner, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.parent = parent;
        this.product = product;
        this.dateRange = dateRange;
        initTable();
        displayInfo(product, dateRange);
        this.arr = thongKeChiTietSanPhamNhap(dateRange, product.getMaSanPham());
    }
    
    private void initTable() {
        dtm = new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhà cung cấp", "Nhà cung cấp", "Số lượng nhập"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };
        table.setModel(dtm);
        table.getColumnModel().getColumn(1).setPreferredWidth(400);
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(0));
        dtcr = new DefaultTableCellRenderer();
        dtcr.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private void displayInfo(ThongKeSanPhamDTO product, DateRangeDTO dateRange) {
        lblTenSanPham.setText("Tên sản phẩm: " + product.getTenSanPham());
        lblLoaiSanPham.setText("Loại sản phẩm: " + product.getTenLoaiSanPham());
        lblThoiGian.setText("Thời gian: " + dateRange.getFromDate().format(formatter) + " - " + dateRange.getToDate().format(formatter));
        lblTongSoLuongNhap.setText("Tổng số lượng nhập: " + product.getSoLuongNhap());
    }
    
    private void addDataToChart(ArrayList<ChiTietSanPhamNhapDTO> arr) {
        boolean paintAll = arr.size() <= 7;
        int valueOther = 0;
        for (int i = 0; i < arr.size(); ++i) {
            if (paintAll || i < 6) {
                pieChart.addData(new ModelPieChart(arr.get(i).getTenNhaCungCap(), arr.get(i).getTongSoLuongNhap(), ChartColor.chartColor[i]));
            } else {
                valueOther += arr.get(i).getTongSoLuongNhap();
            }
        }
        if (!paintAll) {
            pieChart.addData(new ModelPieChart("Khác", valueOther, ChartColor.chartColor[ChartColor.chartColor.length - 1]));
        }
    }
    
    private ArrayList<ChiTietSanPhamNhapDTO> thongKeChiTietSanPhamNhap(DateRangeDTO dateRange, int productId) {
        ArrayList<ChiTietSanPhamNhapDTO> arr = new ArrayList<>();
        try {
            arr = tkBUS.thongKeChiTietSanPhamNhap(dateRange, productId);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(ChiTietSanPhamNhapDialog.this, "Lỗi kết nối cơ sở dữ liệu", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return arr;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(ChiTietSanPhamNhapDialog.this, "Lỗi không xác định", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return arr;
        }
        dtm.setRowCount(0);
        if (arr.size() == 0) return arr;
        
        for (int i = 0; i < arr.size(); ++i) {
            ChiTietSanPhamNhapDTO ctspnDTO = arr.get(i);
            int maNhaCungCap = ctspnDTO.getMaNhaCungCap();
            String tenNhaCungCap = ctspnDTO.getTenNhaCungCap();
            int tongSoLuongNhap = ctspnDTO.getTongSoLuongNhap();
            Object [] row = {maNhaCungCap, tenNhaCungCap, tongSoLuongNhap};
            dtm.addRow(row);
        }
        for (int i = 0; i < table.getColumnCount(); ++i) {
            table.getColumnModel().getColumn(i).setCellRenderer(dtcr);
        }
        addDataToChart(arr);
        return arr;
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
        scrollPane = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        lblTenSanPham = new javax.swing.JLabel();
        lblLoaiSanPham = new javax.swing.JLabel();
        lblTongSoLuongNhap = new javax.swing.JLabel();
        lblThoiGian = new javax.swing.JLabel();
        pieChart = new GUI.Chart.PieChart.PieChart();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Chi tiết nhập hàng");
        setResizable(false);

        pContainer.setBackground(new java.awt.Color(255, 255, 255));

        table.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhà cung cấp", "Nhà cung cấp", "Số lượng nhập"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        scrollPane.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setResizable(false);
            table.getColumnModel().getColumn(1).setResizable(false);
            table.getColumnModel().getColumn(2).setResizable(false);
        }

        lblTenSanPham.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTenSanPham.setForeground(new java.awt.Color(0, 0, 0));
        lblTenSanPham.setText("Tên sản phẩm: [tensanpham]");

        lblLoaiSanPham.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblLoaiSanPham.setForeground(new java.awt.Color(0, 0, 0));
        lblLoaiSanPham.setText("Loại sản phẩm: [loaisanpham]");

        lblTongSoLuongNhap.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblTongSoLuongNhap.setForeground(new java.awt.Color(0, 0, 0));
        lblTongSoLuongNhap.setText("Tổng số lượng nhập: xxxx");

        lblThoiGian.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblThoiGian.setForeground(new java.awt.Color(0, 0, 0));
        lblThoiGian.setText("Thời gian: dd/MM/yyyy - dd/MM/yyyy");

        javax.swing.GroupLayout pContainerLayout = new javax.swing.GroupLayout(pContainer);
        pContainer.setLayout(pContainerLayout);
        pContainerLayout.setHorizontalGroup(
            pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pContainerLayout.createSequentialGroup()
                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pContainerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pContainerLayout.createSequentialGroup()
                                .addComponent(lblThoiGian)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                                .addComponent(lblTongSoLuongNhap)
                                .addGap(60, 60, 60))
                            .addGroup(pContainerLayout.createSequentialGroup()
                                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTenSanPham)
                                    .addComponent(lblLoaiSanPham))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pContainerLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)))
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pContainerLayout.setVerticalGroup(
            pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                    .addGroup(pContainerLayout.createSequentialGroup()
                        .addComponent(lblTenSanPham)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLoaiSanPham)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblThoiGian)
                            .addComponent(lblTongSoLuongNhap))
                        .addGap(18, 18, 18)
                        .addComponent(pieChart, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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

    private void handleViewGiaNhap(int row) {
        ChiTietSanPhamNhapDTO provider = arr.get(row);
        ChiTietGiaNhapNCCDialog chiTietGiaNhap = new ChiTietGiaNhapNCCDialog(this, true, this.product, provider, this.dateRange);
        chiTietGiaNhap.setVisible(true);
    }
    
    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount() == 2 && table.getSelectedRow() != -1) {
            int row = table.getSelectedRow();
            handleViewGiaNhap(row);
        }
    }//GEN-LAST:event_tableMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
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
            java.util.logging.Logger.getLogger(ChiTietSanPhamNhapDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChiTietSanPhamNhapDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChiTietSanPhamNhapDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChiTietSanPhamNhapDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        UIManager.setLookAndFeel(new FlatLightLaf());
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChiTietSanPhamNhapDialog dialog = new ChiTietSanPhamNhapDialog(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblLoaiSanPham;
    private javax.swing.JLabel lblTenSanPham;
    private javax.swing.JLabel lblThoiGian;
    private javax.swing.JLabel lblTongSoLuongNhap;
    private javax.swing.JPanel pContainer;
    private GUI.Chart.PieChart.PieChart pieChart;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
