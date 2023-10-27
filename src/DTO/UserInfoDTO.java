package DTO;

import java.util.List;

public class UserInfoDTO {
    private NguoiDungDTO user;
    private NhomQuyenDTO permissionInfo;
    private List<ChiTietQuyenDTO> viewPermission;

    public UserInfoDTO(NguoiDungDTO user, NhomQuyenDTO permissionInfo, List<ChiTietQuyenDTO> viewPermission) {
        this.user = user;
        this.permissionInfo = permissionInfo;
        this.viewPermission = viewPermission;
    }

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

    public List<ChiTietQuyenDTO> getViewPermission() {
        return viewPermission;
    }

    public void setViewPermission(List<ChiTietQuyenDTO> viewPermission) {
        this.viewPermission = viewPermission;
    }
    
    
}
