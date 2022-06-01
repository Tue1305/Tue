/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lexor.pos.web.jsf;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
/**
 *
 * @author vinh
 */
@Named(value = "indexBean")
@ManagedBean
@ViewScoped
public class IndexBean implements Serializable{
    public String getMessage(){
        return "JSF Version: " + FacesContext.class.getPackage().getImplementationVersion();
    }
}
