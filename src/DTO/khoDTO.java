/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;

/**
 *
 * @author trant
 */
public class khoDTO {
    private int maKho;
    private String tenKho;
    private String diaDiem ;

    public khoDTO() {
    }

    
    public khoDTO(int maKho, String tenKho, String diaDiem) {
        this.maKho = maKho;
        this.tenKho = tenKho;
        this.diaDiem = diaDiem;
    }

    public int getMaKho() {
        return maKho;
    }

    public void setMaKho(int maKho) {
        this.maKho = maKho;
    }

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
    }

    public String getDiaDiem() {
        return diaDiem;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    
}
