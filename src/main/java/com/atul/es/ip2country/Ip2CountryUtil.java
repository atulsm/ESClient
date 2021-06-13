package com.atul.es.ip2country;

import com.atul.es.csv.LoginData;
import com.opencsv.CSVReader;
import inet.ipaddr.IPAddress;
import inet.ipaddr.IPAddressString;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ip2CountryUtil {

    private static Ip2Country[] ip2country;
    static{
        try {
            loadIpToCountry("IpToCountry_filtered.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadIpToCountry(String csvFile) throws Exception {
        //Get all data from csv
        List<Ip2Country> data = new ArrayList<Ip2Country>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFile));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                data.add(new Ip2Country(values[0],values[1],values[2],values[3],values[4]));
            }
        }
        Collections.sort(data, new Comparator<Ip2Country>() {
            @Override
            public int compare(Ip2Country o1, Ip2Country o2) {
                return (int) (o1.getFromIp() - o2.getFromIp());
            }
        });

        ip2country = new Ip2Country[data.size()];
        data.toArray(ip2country);
    }

    public static Ip2Country search(String ip){
        IPAddress addr = new IPAddressString(ip).getAddress();
        if(addr == null){
            return  null;
        }

        long value = addr.getValue().longValue();

        int n = ip2country.length;
        return binarySearch(0, n - 1, value);

    }

    private static Ip2Country binarySearch(int l, int r, long x) {
        if (r >= l) {
            int mid = l + (r - l) / 2;
            if (ip2country[mid].getFromIp() <= x &&  ip2country[mid].getToIp() >= x)
                return ip2country[mid];

            if (ip2country[mid].getFromIp() > x)
                return binarySearch(l, mid - 1, x);

            return binarySearch(mid + 1, r, x);
        }

        // We reach here when element is not present
        // in array
        return null;
    }

    public static void main(String args[]){
        long start = System.currentTimeMillis();

        Ip2Country data = search("1.179.127.255");  //28542676
        System.out.println(data);

        data = search("1.179.128.0");  //28542676
        System.out.println(data);

        data = search("1.179.128.1");  //28542676
        System.out.println(data);

        System.out.println("Finished in "  + (System.currentTimeMillis() - start)/1000 + " secs");

    }


}
