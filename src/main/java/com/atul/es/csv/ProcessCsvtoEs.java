package com.atul.es.csv;

import com.opencsv.CSVReader;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
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
import java.util.Date;
import java.util.List;

public class ProcessCsvtoEs {
    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        ClientConfiguration clientConfiguration =
                ClientConfiguration.builder().connectedTo("localhost:9200").build();
        RestHighLevelClient client = RestClients.create(clientConfiguration).rest();

        //Get all data from csv
        try (CSVReader csvReader = new CSVReader(new FileReader("09b8f40fea8fc16f1d0ae0275825c27a_h3.csv"))) {

            String[] values = null;
            csvReader.readNext(); //For skipping header
            int i = 0;
            BulkRequest bulkRequest = new BulkRequest();
            while ((values = csvReader.readNext()) != null) {
                if(values.length != 5){
                    continue;
                }

                LoginData loginData = new LoginData(values[0], values[1], values[2], values[3], values[4]);

                //"sellerid","userloginid","ipaddress","userlogintype","login_date"
                XContentBuilder builder = XContentFactory.jsonBuilder()
                        .startObject()
                        .field("sellerid", loginData.getSellerid())
                        //.field("userloginid", loginData.getUserloginid())
                        .field("ipaddress", loginData.getIpaddress())
                        //.field("userlogintype", loginData.getUserlogintype())
                        .field("login_date", loginData.getLogin_date())
                        .field("country", loginData.getCountry())
                        .field("countryCode", loginData.getCountryCode())
                        .field("location", loginData.getGeoLocation())
                        .endObject();
                IndexRequest request = new IndexRequest("logindata", "login",
                        loginData.getSellerid() + loginData.getIpaddress() +  loginData.getLogin_date().getTime());
                request.source(builder);
                //IndexResponse response = client.index(request, RequestOptions.DEFAULT);

                bulkRequest.add(request);

                if (++i % 1000 == 0) {
                    BulkResponse response = client.bulk(bulkRequest,RequestOptions.DEFAULT);
                    if(response.hasFailures()){
                        System.out.println(response.buildFailureMessage());
                    }
                    System.out.println(new Date() + " indexed " + i + " records from csv");
                    bulkRequest = new BulkRequest();
                }
            }

            System.out.println("Indexed " + i + " data to elasticsearch in " + (System.currentTimeMillis() - start) / 1000 + " secs");
            // on shutdown
            client.close();
        }
    }
}
