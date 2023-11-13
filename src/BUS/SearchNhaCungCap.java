package BUS;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

public class SearchNhaCungCap {
    public static SearchNhaCungCap getInstance() {
        return new SearchNhaCungCap();
    }

    public ArrayList<NhaCungCapDTO> searchTatCa(String text) {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        ArrayList<NhaCungCapDTO> armt = NhaCungCapDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (text.toLowerCase().contains(Integer.toString(ncc.getMaNhaCungCap()))
                    || ncc.getTenNhaCungCap().toLowerCase().contains(text.toLowerCase())
                    || ncc.getSdt().toLowerCase().contains(text.toLowerCase())
                    || ncc.getDiaChi().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<NhaCungCapDTO> searchTenNCC(String text) {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        ArrayList<NhaCungCapDTO> armt = NhaCungCapDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getTenNhaCungCap().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<NhaCungCapDTO> searchMaNCC(String text) {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        ArrayList<NhaCungCapDTO> armt = NhaCungCapDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (text.toLowerCase().contains(Integer.toString(ncc.getMaNhaCungCap()))) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<NhaCungCapDTO> searchDiaChi(String text) {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        ArrayList<NhaCungCapDTO> armt = NhaCungCapDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getDiaChi().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }

    public ArrayList<NhaCungCapDTO> searchSdt(String text) {
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();
        ArrayList<NhaCungCapDTO> armt = NhaCungCapDAO.getInstance().selectAll();
        for (var ncc : armt) {
            if (ncc.getSdt().toLowerCase().contains(text.toLowerCase())) {
                result.add(ncc);
            }
        }
        return result;
    }
}
