package controller;

import DAO.SanPhamDAO;
import DAO.loaiSanPhamDAO;
import DTO.SanPhamDTO;
import OldDAO.MayTinhDAO;
import java.util.ArrayList;
import model.MayTinh;

/**
 *
 * @author sinh
 */
public class SearchProduct {

    public static SearchProduct getInstance() {
        return new SearchProduct();
    }

    public ArrayList<SanPhamDTO> searchTatCa(String text) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
             String lSP = loaiSanPhamDAO.getInstance().getNameOfType(sp.getLoaiSanPham());
            if (String.valueOf(sp.getMaSanPham()).toLowerCase().contains(text.toLowerCase()) || sp.getTenSanPham().toLowerCase().contains(text.toLowerCase())
                        || String.valueOf(sp.getCpu()).toLowerCase().contains(text.toLowerCase())
                        || String.valueOf(sp.getManHinh()).toLowerCase().contains(text.toLowerCase())
                        || String.valueOf(sp.getRam()).toLowerCase().contains(text.toLowerCase())
                        || lSP.toLowerCase().contains(text.toLowerCase())) {
                    result.add(sp);
            }
            
        }
        return result;
    }

    public ArrayList<SanPhamDTO> searchMaSanPham(String text) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
            
                if (String.valueOf(sp.getMaSanPham()).toLowerCase().contains(text.toLowerCase())) {
                    result.add(sp);
                }
            
        }
        return result;
    }

    public ArrayList<SanPhamDTO> searchTenSanPham(String text) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
            
                if (sp.getTenSanPham().toLowerCase().contains(text.toLowerCase())) {
                    result.add(sp);
                }
            
        }
        return result;
    }

    public ArrayList<SanPhamDTO> searchSoLuong(String text) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
            
                if (text.length() != 0) {
                    if (sp.getSoLuong() > Integer.parseInt(text)) {
                        result.add(sp);
                    }
                } else {
                    result.add(sp);
                }
            
        }
        return result;
    }

    public ArrayList<SanPhamDTO> searchDonGia(String text) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
            

                if (text.length() != 0) {
                    if (sp.getGiaXuat() > Integer.parseInt(text)) {
                        result.add(sp);
                    }
                }
                else {
                    result.add(sp);
                }
            
        }
        return result;
    }

    public ArrayList<SanPhamDTO> searchRam(String text) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
            if (sp.getRam().toLowerCase().contains(text.toLowerCase())) {
                result.add(sp);
            }
        }
        return result;
    }

    public ArrayList<SanPhamDTO> searchCpu(String text) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
            if (sp.getCpu().toLowerCase().contains(text.toLowerCase())) {
                result.add(sp);
            }
        }
        return result;
    }

    public ArrayList<SanPhamDTO> searchVGA(String text) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
            if (sp.getVga().toLowerCase().contains(text.toLowerCase())) {
                result.add(sp);
            }
        }
        return result;
    }

    public ArrayList<SanPhamDTO> searchPin(String text) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
            if (sp.getPin().toLowerCase().contains(text.toLowerCase())) {
                result.add(sp);
            }
        }
        return result;
    }


    public ArrayList<SanPhamDTO> searchManHinh(String text) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
            if (sp.getManHinh().toLowerCase().contains(text.toLowerCase())) {
                result.add(sp);
            }
        }
        return result;
    }

    public ArrayList<SanPhamDTO> searchMauSac(String text) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
         
                if (sp.getMauSac().toLowerCase().contains(text.toLowerCase())) {
                    result.add(sp);
                }
            
        }
        return result;
    }

    public MayTinh searchId(String text) {
        SanPhamDTO result = new SanPhamDTO();
        ArrayList<MayTinh> allsp = MayTinhDAO.getInstance().selectAll();
        for (var sp : allsp) {
            if (String.valueOf(sp.getMaMay()).toLowerCase().contains(text.toLowerCase())) {
                return sp;
            }
        }
        return null;
    }
        public SanPhamDTO searchMaSanPham(int text) {
        SanPhamDTO result = new SanPhamDTO();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
            if (String.valueOf(sp.getMaSanPham()).toLowerCase().contains(String.valueOf(text).toLowerCase())) {
                return sp;
            }
        }
        return null;
    }
    
    public ArrayList<SanPhamDTO> searchThuongHieu(String text) {
        ArrayList<SanPhamDTO> result = new ArrayList<>();
        ArrayList<SanPhamDTO> allsp = SanPhamDAO.getInstance().getlistProduct();
        for (var sp : allsp) {
                String lSP = loaiSanPhamDAO.getInstance().getNameOfType(sp.getLoaiSanPham());
                if (lSP.toLowerCase().contains(text.toLowerCase())) {
                    result.add(sp);
                }
            
        }
        return result;
    }
}
