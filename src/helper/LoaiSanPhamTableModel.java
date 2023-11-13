package helper;

import DTO.ThongKe.ThongKeLoaiSanPhamDTO;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class LoaiSanPhamTableModel extends AbstractTableModel {
    private ArrayList<ThongKeLoaiSanPhamDTO> data;
    private String[] columnNames;

    public LoaiSanPhamTableModel(ArrayList<ThongKeLoaiSanPhamDTO> data, String[] columnNames) {
        this.data = data;
        this.columnNames = columnNames;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ThongKeLoaiSanPhamDTO lsp = data.get(rowIndex);
        switch(columnIndex) {
            case 0:
                return lsp.getMaLoaiSanPham();
            case 1:
                return lsp.getTenLoaiSanPham();
            case 2:
                return lsp.getSoLuong();
            default:
                return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public ArrayList<ThongKeLoaiSanPhamDTO> getData() {
        return data;
    }

    public void setData(ArrayList<ThongKeLoaiSanPhamDTO> data) {
        this.data = data;
    }
}
