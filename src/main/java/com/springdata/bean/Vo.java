package com.springdata.bean;

import javax.persistence.Column;

public class Vo {

    private Long count;

    private String password;

    private String userName;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Vo(Long count, String password, String userName) {
        this.count = count;
        this.password = password;
        this.userName = userName;
    }
}
