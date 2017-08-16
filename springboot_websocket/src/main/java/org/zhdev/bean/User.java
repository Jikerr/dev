package org.zhdev.bean;

import java.security.Principal;

public class User implements Principal {
    private String userName;
    private String pwd;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public String getName() {
        return String.valueOf(this.id);
    }
}
