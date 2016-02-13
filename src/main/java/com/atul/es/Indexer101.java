package com.atul.es;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

/**
 * 
 * @author satul
 *
 */
public class Indexer101 {
	private static final String[] NAMES = new String[]{"John","Jim","Thomas", "Tim"};
	
	public static void main(String[] args) throws Exception{	
		TransportClient transportClient = new TransportClient();
		transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress(Configuration.IP, Configuration.PORT));			
		Client client = (Client) transportClient;
		System.out.println("Connected to elasticsearch");
				
		for(int i=0;i<NAMES.length;i++){
			String json = getData(NAMES[i],i);			
					
			//index Data
			IndexResponse response = client.prepareIndex(Configuration.INDEX, Configuration.TYPE)
			        .setSource(json)
			        .execute()
			        .actionGet();
		}
		
		// on shutdown
		client.close();
		transportClient.close();
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
