package com.atul.todel;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.node.Node;


/**
 * 
 * @author satul
 *
 */
public class ESTransportClient {
	
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public static void main(String[] args) throws Exception{	
		
		//Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "atul.es.cluster").build();
		TransportClient transportClient = new TransportClient();
		transportClient = transportClient.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));			

		Client client = (Client) transportClient;

		System.out.println("Joined a cluster");
		
		//get data
		String json = getData();
		
		
		//index Data
		IndexResponse response = client.prepareIndex("partition_test", "event")
		        .setSource(json)
		        .execute()
		        .actionGet();
		
		// on shutdown
		client.close();

	}
	
	private static String getData(){
		String json = "{\"evt\":\"a\",\"msg\":\"b\"}";
		
		return json;
	}
}
