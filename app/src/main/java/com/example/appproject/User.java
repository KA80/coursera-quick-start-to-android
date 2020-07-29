package com.example.appproject;

import java.io.Serializable;

public class User implements Serializable { // Класс пользователя
    private String mLogin;
    private String mPassword;
    private boolean mHasSuccessLogin;

    public User(String login, String password) {
        this.mLogin = login;
        this.mPassword = password;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String mLogin) {
        this.mLogin = mLogin;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String mPassword) {
        this.mPassword = mPassword;
    }

    public boolean hasSuccessLogin() {
        return mHasSuccessLogin;
    }

    public void setHasSuccessLogin(boolean hasSuccessLogin) {
        mHasSuccessLogin = hasSuccessLogin;
    }
}
