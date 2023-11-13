/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.ChiTietPhieuXuatDAO;
import DAO.SanPhamDAO;
import DAO.PhieuXuatDAO;
import DTO.ChiTietPhieuXuatDTO;
import DTO.PhieuXuatDTO;
import DTO.SanPhamDTO;
import GUI.PhieuXuatGUI;
import GUI.XemChiTietPhieuXuatGUI;
import OldDAO.AccountDAO;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import java.awt.Desktop;
import java.awt.FileDialog;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author EV
 */
public class PDFPhieuXuat {

    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");
    Document document = new Document();
    FileOutputStream file;
    JFrame jf = new JFrame();
    FileDialog fd = new FileDialog(jf, "Xuất pdf", FileDialog.SAVE);
    Font fontData;
    Font fontTitle;
    Font fontHeader;

    public PDFPhieuXuat() {
        try {
            fontData = new Font(BaseFont.createFont("lib/Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, Font.NORMAL);
            fontTitle = new Font(BaseFont.createFont("lib/Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 25, Font.NORMAL);
            fontHeader = new Font(BaseFont.createFont("lib/Roboto/Roboto-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED), 11, Font.NORMAL);
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(PDFPhieuXuat.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void chooseURL(String url) {
        try {
            document.close();
            document = new Document();
            file = new FileOutputStream(url);
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Khong tim thay duong dan file " + url);
        } catch (DocumentException ex) {
            JOptionPane.showMessageDialog(null, "Khong goi duoc document !");
        }
    }

    public void setTitle(String title) {
        try {
            Paragraph pdfTitle = new Paragraph(new Phrase(title, fontTitle));
            pdfTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(pdfTitle);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            ex.printStackTrace();
        }
    }

    private String getFile(String name) {
        fd.pack();
        fd.setSize(800, 600);
        fd.validate();
        Rectangle rect = jf.getContentPane().getBounds();
        double width = fd.getBounds().getWidth();
        double height = fd.getBounds().getHeight();
        double x = rect.getCenterX() - (width / 2);
        double y = rect.getCenterY() - (height / 2);
        Point leftCorner = new Point();
        leftCorner.setLocation(x, y);
        fd.setLocation(leftCorner);
        fd.setFile(name + ".pdf");
        fd.setVisible(true);
        String url = fd.getDirectory() + fd.getFile();
        if (url.equals("null")) {
            return null;
        }
        return url;
    }

    private void openFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public void writePhieuXuat(int mapn) {
        String url = "";

        try {
            fd.setTitle("In phiếu Xuất");
            fd.setLocationRelativeTo(null);
            url = getFile(Integer.toString(mapn));
            if (url == null) {
                return;
            }
            file = new FileOutputStream(url);
            document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, file);
            document.open();

            setTitle("THÔNG TIN PHIẾU XUẤT");

            
          
            
//            
//
//        if (mapn == ChiTietPhieuXuatDAO.getInstance().selectById(mapn).getMaPhieuXuat()) {
//             ChiTietPhieuXuatDAO.getInstance().selectById(mapn);   
//        }
//            System.out.println(ChiTietPhieuXuatDAO.getInstance().selectById(mapn).getMaPhieuXuat());
//            ChiTietPhieuXuatDTO mtl = ChiTietPhieuXuatDAO.getInstance().selectById(mapn);
//             System.out.println(mtl);
//            ChiTietPhieuXuatDTO ctp = new ChiTietPhieuXuatDTO(mtl.getMaPhieuXuat(), mtl.getMaSanPham(), mtl.getSoLuong(), mtl.getDonGia());
//            CTPhieuXuat.add(ctp);
//            XemChiTietPhieuXuatGUI a = new XemChiTietPhieuXuatGUI();
//            a.setVisible(true);
//            System.out.println(px);
//                if (PhieuXuatGUI.CTPhieuXuat.get(0).getMaPhieuXuat() == i.getMaPhieuXuat()) {
//
//                   
//
//                    tblCTPhieumd.addRow(new Object[]{
//                        i.getMaSanPham(),
//                        SanPhamDAO.getInstance().getNameByID(i.getMaSanPham()),
//                        i.getSoLuong(),
//                        formatter.format(i.getDonGia()) + "đ",
//                        formatter.format((i.getDonGia()) * (i.getSoLuong())) + "đ"
//
//                    });
//            System.out.println(ctp.getMaPhieuXuat());
//            for (var i : pn) {
//                if (i.getMaPhieuXuat() == mapn) {
//                    px = i;
//                }
//            }
//           System.out.println(px.getMaPhieuXuat());



            Paragraph para1 = new Paragraph(new Phrase("Mã phiếu: " + mapn, fontData));
            Paragraph para2 = new Paragraph(new Phrase("Thời gian tạo: " +  PhieuXuatDAO.getInstance().selectById(mapn).getThoiGianTao(), fontData));
            Paragraph para3 = new Paragraph(new Phrase("Người tạo: " + PhieuXuatDAO.getInstance().selectById(mapn).getNguoiTao(), fontData));
            para1.setIndentationLeft(40);
            para2.setIndentationLeft(40);
            para3.setIndentationLeft(40);
            document.add(para1);
            document.add(para2);
            document.add(para3);
            document.add(Chunk.NEWLINE);//add hang trong de tao khoang cach

            //Tao table cho cac chi tiet cua hoa don
            PdfPTable pdfTable = new PdfPTable(5);
            pdfTable.setWidths(new float[]{10f, 30f, 15f, 5f, 15f});
            PdfPCell cell;

            //Set headers cho table chi tiet
            pdfTable.addCell(new PdfPCell(new Phrase("Mã máy", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("Tên máy", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("Đơn giá", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("SL", fontHeader)));
            pdfTable.addCell(new PdfPCell(new Phrase("Tổng tiền", fontHeader)));

            for (int i = 0; i < 5; i++) {
                cell = new PdfPCell(new Phrase(""));
                pdfTable.addCell(cell);
            }

            //Truyen thong tin tung chi tiet vao table
//            for (ChiTietPhieuXuatDTO ctpn : ChiTietPhieuXuatDAO.getInstance().selectAll()) {
//                SanPhamDTO mt = SanPhamDAO.getInstance().selectByIdPX(ctpn.getMaPhieuXuat());
//
////                pdfTable.addCell(new PdfPCell(new Phrase(ctpn.getMaSanPham(),fontData );
//                pdfTable.addCell(new PdfPCell(new Phrase(mt.getTenSanPham(), fontData)));
//                pdfTable.addCell(new PdfPCell(new Phrase(mt.getTenSanPham(), fontData)));
//                pdfTable.addCell(new PdfPCell(new Phrase(formatter.format(mt.getGiaXuat()) + "đ", fontData)));
//                pdfTable.addCell(new PdfPCell(new Phrase(String.valueOf(ctpn.getSoLuong()), fontData)));
//                pdfTable.addCell(new PdfPCell(new Phrase(formatter.format(ctpn.getSoLuong() * mt.getGiaXuat()) + "đ", fontData)));
//            }
            ArrayList<ChiTietPhieuXuatDTO> pn = ChiTietPhieuXuatDAO.getInstance().selectAll();
            for (var i : pn) {
                if (i.getMaPhieuXuat() == mapn) {
                    pdfTable.addCell(new PdfPCell(new Phrase(formatter.format(i.getMaSanPham()), fontData)));
                    pdfTable.addCell(new PdfPCell(new Phrase(DAO.SanPhamDAO.getInstance().selectByIdPX(i.getMaSanPham()).getTenSanPham(), fontData)));
                    pdfTable.addCell(new PdfPCell(new Phrase(formatter.format(i.getDonGia()) + "đ", fontData)));
                    pdfTable.addCell(new PdfPCell(new Phrase(String.valueOf(i.getSoLuong()), fontData)));
                    pdfTable.addCell(new PdfPCell(new Phrase(formatter.format(i.getSoLuong() * i.getDonGia()) + "đ", fontData)));
                }
            }

            
            
            PhieuXuatDTO ttpx = PhieuXuatDAO.getInstance().selectById(mapn);
            System.out.println(ttpx);
            document.add(pdfTable);
            document.add(Chunk.NEWLINE);
            Paragraph paraTongThanhToan = new Paragraph(new Phrase("Tổng thanh toán: " + formatter.format(ttpx.getTongTien()) + "đ", fontData));
            paraTongThanhToan.setIndentationLeft(300);
            document.add(paraTongThanhToan);
            document.close();
            JOptionPane.showMessageDialog(null, "Ghi file thành công: " + url);
            openFile(url);

        } catch (DocumentException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Lỗi khi ghi file " + url);
        }

    }

}
