package com.atul.es.basic;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * 
 * @author satul
 *
 */
public class Searcher101 {
	public static void main(String[] args) throws Exception{
		ClientConfiguration clientConfiguration =
				ClientConfiguration.builder().connectedTo("localhost:9200").build();
		RestHighLevelClient client = RestClients.create(clientConfiguration).rest();

		System.out.println("Connected to elasticsearch");

		SearchSourceBuilder builder = new SearchSourceBuilder()
				.postFilter(QueryBuilders.rangeQuery("age").from(0).to(100));

		SearchRequest searchRequest = new SearchRequest(Configuration.INDEX);
		searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
		searchRequest.source(builder);


		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
		SearchHit[] searchHits = response.getHits().getHits();
		List<Person> results =
				Arrays.stream(searchHits)
						.map(hit -> JSON.parseObject(hit.getSourceAsString(), Person.class))
						.collect(Collectors.toList());

		results.stream().forEach(person -> System.out.println(person));
		client.close();
	}
	
}
