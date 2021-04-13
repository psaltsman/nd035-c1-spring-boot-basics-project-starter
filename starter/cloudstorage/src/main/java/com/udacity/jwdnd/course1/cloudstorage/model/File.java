package com.udacity.jwdnd.course1.cloudstorage.model;

public class File {

    private int fileid;
    private int userid;
    private String filename;
    private String contenttype;
    private String filesize;
    private String filedata;

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    public String getFilesize() {
        return filesize;
    }

    public void setFilesize(String filesize) {
        this.filesize = filesize;
    }

    public String getFiledata() {
        return filedata;
    }

    public void setFiledata(String filedata) {
        this.filedata = filedata;
    }
}
