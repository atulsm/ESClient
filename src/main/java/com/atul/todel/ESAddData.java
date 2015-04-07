package com.atul.todel;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;


/**
 * 
 * @author satul
 *
 */
public class ESAddData {
	
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public static void main(String[] args) throws Exception{	
		
		Settings settings = ImmutableSettings.builder()
		        .put("discovery.zen.ping.multicast.enabled", "false")
		        .put("discovery.zen.ping.unicast.hosts", "164.99.175.163, 164.99.175.165")
		        .put("cluster.name", "atul.es.cluster")
		        .build();
			
		// Joing the cluster as a client
		Node node = nodeBuilder().client(true).loadConfigSettings(false).settings(settings).node();
		Client client = node.client();

		System.out.println("Joined a cluster");
		
		//get data
		String json = getData();
		
		
		//index Data
		IndexResponse response = client.prepareIndex("twitter", "tweet")
		        .setSource(json)
		        .execute()
		        .actionGet();
		
		// on shutdown
		node.close();

	}
	
	private static String getData(){
		String json = "{" +
		        "\"user\":\"kimchy\"," +
		        "\"postDate\":\"" + format.format(new Date()) + "\"," +
		        "\"message\":\"trying out Elasticsearch\"" +
		    "}";
		
		return json;
	}

}
