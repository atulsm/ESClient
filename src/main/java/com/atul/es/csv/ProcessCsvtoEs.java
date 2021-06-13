package com.atul.es.csv;

import com.atul.es.basic.Configuration;
import com.opencsv.CSVReader;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessCsvtoEs {
    public static void main(String[] args) throws Exception{
        long start = System.currentTimeMillis();
        ClientConfiguration clientConfiguration =
                ClientConfiguration.builder().connectedTo("localhost:9200").build();
        RestHighLevelClient client = RestClients.create(clientConfiguration).rest();

        //Get all data from csv
        List<LoginData> data = new ArrayList<LoginData>();
        try (CSVReader csvReader = new CSVReader(new FileReader("logindata.csv"));) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                data.add(new LoginData(values[0],values[1],values[2],values[3],values[4]));
            }
        }

        for (LoginData loginData : data) {
            //"sellerid","userloginid","ipaddress","userlogintype","login_date"
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("sellerid", loginData.getSellerid())
                    .field("userloginid", loginData.getUserloginid())
                    .field("ipaddress", loginData.getIpaddress())
                    .field("userlogintype", loginData.getUserlogintype())
                    .field("login_date", loginData.getLogin_date())
                    .field("country", loginData.getCountry())
                    .endObject();
            IndexRequest request = new IndexRequest("logindata", "login",
                    loginData.getSellerid()+loginData.getLogin_date().getTime());
            request.source(builder);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        }

        System.out.println("Indexed " + data.size() + " data to elasticsearch in "  + (System.currentTimeMillis() - start)/1000 + " secs");
        // on shutdown
        client.close();
    }
}
