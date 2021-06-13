package com.atul.es.ip2country;

import java.util.Date;

public class Ip2Country {

//    3757047552-3757047807,28.666670,77.216670,IN,India
    private long fromIp;
    private long toIp;
    private double latitude;
    private double longitude;
    private String countryCode;
    private String country;

    public Ip2Country(String fromToIpStr, String latitude, String longitude, String countryCode, String country) {
        String[] fromToIP = fromToIpStr.split("-");
        if(fromToIP.length ==2){
            this.fromIp = Long.parseLong(fromToIP[0]);
            this.toIp = Long.parseLong(fromToIP[1]);
        }else{
            System.out.println("Failed to parse fromtoIP " + fromToIpStr);
        }

        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.countryCode = countryCode;
        this.country = country;
    }

    public long getFromIp() {
        return fromIp;
    }

    public long getToIp() {
        return toIp;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Ip2Country{" +
                "fromIp=" + fromIp +
                ", toIp=" + toIp +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", countryCode='" + countryCode + '\'' +
                ", country=" + country +
                '}';
    }

    public static void main(String args[]){
        System.out.println(new Ip2Country("3757047552-3757047807","28.666670","77.216670","IN","India"));
    }
}
