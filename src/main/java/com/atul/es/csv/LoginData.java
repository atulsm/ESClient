package com.atul.es.csv;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginData {
    private String sellerid;
    private String userloginid;
    private String ipaddress;
    private String userlogintype;
    private Date login_date;

    public LoginData(String sellerid, String userloginid, String ipaddress, String userlogintype, String login_dateStr) throws ParseException {
        this.sellerid = sellerid;
        this.userloginid = userloginid;
        this.ipaddress = ipaddress;
        this.userlogintype = userlogintype;
        this.login_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(login_dateStr);
    }

    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getUserloginid() {
        return userloginid;
    }

    public void setUserloginid(String userloginid) {
        this.userloginid = userloginid;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    public String getUserlogintype() {
        return userlogintype;
    }

    public void setUserlogintype(String userlogintype) {
        this.userlogintype = userlogintype;
    }

    public Date getLogin_date() {
        return login_date;
    }

    public void setLogin_date(Date login_date) {
        this.login_date = login_date;
    }


    @Override
    public String toString() {
        return "LoginData{" +
                "sellerid='" + sellerid + '\'' +
                ", userloginid='" + userloginid + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                ", userlogintype='" + userlogintype + '\'' +
                ", login_date=" + login_date +
                '}';
    }
}
