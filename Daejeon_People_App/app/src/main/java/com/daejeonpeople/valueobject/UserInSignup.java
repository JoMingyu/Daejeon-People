package com.daejeonpeople.valueobject;

/**
 * Created by dsm2016 on 2017-07-04.
 */

public class UserInSignup {
    private String email;
    private boolean emailCertified;

    private String name;
    private boolean nameChecked = false;

    private String id;
    private boolean idChecked = false;

    private String password;
    private boolean passwordConfirmed = false;

    private String tel;
    private boolean telCertified;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailCertified() {
        return emailCertified;
    }

    public void setEmailCertified(boolean emailCertified) {
        this.emailCertified = emailCertified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isNameChecked() {
        return nameChecked;
    }

    public void setNameChecked(boolean nameChecked) {
        this.nameChecked = nameChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIdChecked() {
        return idChecked;
    }

    public void setIdChecked(boolean idChecked) {
        this.idChecked = idChecked;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordConfirmed() {
        return passwordConfirmed;
    }

    public void setPasswordConfirmed(boolean passwordConfirmed) {
        this.passwordConfirmed = passwordConfirmed;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public boolean isTelCertified() {
        return telCertified;
    }

    public void setTelCertified(boolean telCertified) {
        this.telCertified = telCertified;
    }
}
