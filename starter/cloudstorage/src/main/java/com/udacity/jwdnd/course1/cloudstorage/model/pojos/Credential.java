package com.udacity.jwdnd.course1.cloudstorage.model.pojos;

public class Credential {

    private int credentialid;
    private int userid;
    private String username;
    private String key;
    private String password;

    public int getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(int credentialid) {
        this.credentialid = credentialid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
