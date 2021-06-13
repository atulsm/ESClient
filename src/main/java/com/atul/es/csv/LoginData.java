package com.atul.es.csv;

import com.atul.es.ip2country.Ip2Country;
import com.atul.es.ip2country.Ip2CountryUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginData {
    private String sellerid;
    private String userloginid;
    private String ipaddress;
    private String userlogintype;
    private Date login_date;
    private String country;

    public LoginData(String sellerid, String userloginid, String ipaddress, String userlogintype, String login_dateStr) throws ParseException {
        this.sellerid = sellerid;
        this.userloginid = userloginid;
        this.ipaddress = ipaddress;
        this.userlogintype = userlogintype;
        this.login_date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(login_dateStr);

        Ip2Country ip2Country = Ip2CountryUtil.search(ipaddress);
        if(ip2Country != null){
            country = ip2Country.getCountry();
        }
    }

    public String getSellerid() {
        return sellerid;
    }

    public String getUserloginid() {
        return userloginid;
    }

    public String getIpaddress() {
        return ipaddress;
    }

    public String getUserlogintype() {
        return userlogintype;
    }

    public Date getLogin_date() {
        return login_date;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "sellerid='" + sellerid + '\'' +
                ", userloginid='" + userloginid + '\'' +
                ", ipaddress='" + ipaddress + '\'' +
                ", userlogintype='" + userlogintype + '\'' +
                ", country='" + country + '\'' +
                ", login_date=" + login_date +
                '}';
    }
}
