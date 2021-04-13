package com.udacity.jwdnd.course1.cloudstorage.model.pojos;

public class Note {

    private int noteid;
    private int userid;
    private String notetitle;
    private String notedescription;

    public int getNoteid() {
        return noteid;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getNotetitle() {
        return notetitle;
    }

    public void setNotetitle(String notetitle) {
        this.notetitle = notetitle;
    }

    public String getNotedescription() {
        return notedescription;
    }

    public void setNotedescription(String notedescription) {
        this.notedescription = notedescription;
    }
}
