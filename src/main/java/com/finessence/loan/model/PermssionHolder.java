/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.finessence.loan.model;


import com.finessence.loan.entities.Permission;
import java.util.List;

/**
 *
 * @author patrick
 */
public class PermssionHolder {
    
    private String module;
    private String icon;
    
    private List<Permission> permissions;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
}
