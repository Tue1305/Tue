/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lexor.pos.web.Security;

/**
 *
 * @author vinh
 */
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;


@ManagedBean
@SessionScoped
public class UserManager implements Serializable {

    private static final long serialVersionUID = -9107952969237527819L;

    
    public static final String HOME_PAGE_REDIRECT
            = "/secured/home.xhtml?faces-redirect=true";
    public static final String LOGOUT_PAGE_REDIRECT
            = "/logout.xhtml?faces-redirect=true";

    private String userId;
    private String userPassword;

    private User currentUser;

    public String login() {
        // lookup the user based on user name and user password
        currentUser = find(userId, userPassword);

        if (currentUser != null) {


            return HOME_PAGE_REDIRECT;
        } else {
            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Login failed",
                            "Invalid or unknown credentials."));

            return null;
        }
    }

    public String logout() {
        String identifier = userId;

        // invalidate the session
        
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();

        
        return LOGOUT_PAGE_REDIRECT;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String isLoggedInForwardHome() {
        if (isLoggedIn()) {
            return HOME_PAGE_REDIRECT;
        }

        return null;
    }

    private User find(String userId, String password) {
        User result = null;

        // TODO to be replaced with actual retrieval of user
        if ("admin".equalsIgnoreCase(userId)
                && "admin".equals(password)) {
            result = new User(userId, "John", "Doe");
        }

        return result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    // do not provide a setter for currentUser!
}
