package com.atul.es;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class ESStatusCheck {
	public static final String HTTP = "http://";
	
	public static void main(String[] args) {
		System.out.println(checkStatus(null));
		System.out.println(checkStatus("atul3:9200,atul2:9200"));
		System.out.println(checkStatus("atul3:9200"));
		System.out.println(checkStatus("atul2:9200"));
		System.out.println(checkStatus("atul4:9200,atul2:9200"));
	}
	
	public static boolean checkStatus(String hostNames) {
		if(hostNames==null){
			return false;
		}
		
		String[] hosts = hostNames.split(",");
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		
		for(String host:hosts){
			if(checkStatus(httpClient, host.trim())){
				return true;
			}
		}

		return false;
	}

	protected static boolean checkStatus(CloseableHttpClient httpClient, String host) {
		HttpGet httpGetRequest = new HttpGet(HTTP+host);
		try {
			httpClient.execute(httpGetRequest);
			System.out.println(host);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
