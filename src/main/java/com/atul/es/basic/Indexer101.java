package com.atul.es.basic;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

import java.util.Date;

/**
 * 
 * @author satul
 *
 */
public class Indexer101 {
	private static final String[] NAMES = new String[]{"John","Jim","Thomas", "Tim"};
	
	public static void main(String[] args) throws Exception{
		ClientConfiguration clientConfiguration =
				ClientConfiguration.builder().connectedTo("localhost:9200").build();
		RestHighLevelClient client = RestClients.create(clientConfiguration).rest();
		System.out.println("Connected to elasticsearch");
				
		for(int i=0;i<NAMES.length;i++){
			//String json = getData(NAMES[i],i);
			XContentBuilder builder = XContentFactory.jsonBuilder()
					.startObject()
					.field("fullName", NAMES[i])
					.field("dateOfBirth", new Date())
					.field("age", "10")
					.endObject();
			IndexRequest request = new IndexRequest(Configuration.INDEX, Configuration.TYPE);
			request.source(builder);
			IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		}
		
		// on shutdown
		client.close();
	}
	
	private static String getData(String name, int i){
		return new JsonBuilder().add("name",name).add("pos", String.valueOf(i)).finish();
	}
	
	private static class JsonBuilder{	
		private StringBuilder ret = new StringBuilder().append("{");

		public JsonBuilder add(String key, String val){
			ret.append("\"").append(key).append("\":\"").append(val).append("\",");
			return this;
		}
	
		public String finish(){
			return ret.substring(0, ret.length()-1).toString()+"}";
		}
	}
}
