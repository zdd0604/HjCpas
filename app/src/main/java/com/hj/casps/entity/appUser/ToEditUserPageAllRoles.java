package com.hj.casps.entity.appUser;

import java.io.Serializable;

/**
 * Created by Admin on 2017/5/13.
 * 指定会员的所有备选角色，以及当前用户是否拥有此角色
 */

public class ToEditUserPageAllRoles implements Serializable {
    private static final long serialVersionUID = -3460802421456077954L;
    private String roleId;//string	角色ID
    private String roleName;//	string	角色名称
    private boolean isHasRole;//	string	当前用户是否拥有该角色

    public ToEditUserPageAllRoles(String roleId, String roleName, boolean isHasRole) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.isHasRole = isHasRole;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isHasRole() {
        return isHasRole;
    }

    public void setHasRole(boolean hasRole) {
        isHasRole = hasRole;
    }

    public ToEditUserPageAllRoles() {
    }
}
