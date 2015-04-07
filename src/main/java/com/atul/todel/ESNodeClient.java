package com.atul.todel;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.node.Node;


/**
 * 
 * @author satul
 *
 */
public class ESNodeClient {

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
		Thread.sleep(50000);
		
		// on shutdown

		node.close();

	}

}
