package com.springboot.application.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.application.model.Note;

@Service
public class ElasticService {

	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private ObjectMapper objectmapper;
	private String INDEX = "springboot";
	private String TYPE = "note_details";

	public String CreateNote(Note note) throws Exception {
		Map<String, Object> notemapper = objectmapper.convertValue(note, Map.class);
		IndexRequest indexrequest = new IndexRequest(INDEX, TYPE, String.valueOf(note.getId())).source(notemapper);
		IndexResponse indexResponse = client.index(indexrequest, RequestOptions.DEFAULT);
		return indexResponse.getResult().name();
	}
	public String UpdateNote(Note note) throws Exception {
		Map<String, Object> notemapper = objectmapper.convertValue(note, Map.class);
		IndexRequest indexrequest = new IndexRequest(INDEX, TYPE, String.valueOf(note.getId())).source(notemapper);
		IndexResponse indexResponse = client.index(indexrequest, RequestOptions.DEFAULT);
		return indexResponse.getResult().name();
	}
	public String DeleteNote(Note note) throws Exception {
		Map<String, Object> notemapper = objectmapper.convertValue(note, Map.class);
		IndexRequest indexrequest = new IndexRequest(INDEX, TYPE, String.valueOf(note.getId())).source(notemapper);
		IndexResponse indexResponse = client.index(indexrequest, RequestOptions.DEFAULT);
		return indexResponse.getResult().name();
	}

	public List<Note> searchbytitle(String title) {
		SearchRequest searchrequest = new SearchRequest();
		SearchSourceBuilder searchsource = new SearchSourceBuilder();
		QueryBuilder querybuilder = QueryBuilders.boolQuery()
				.should(QueryBuilders.queryStringQuery(title).lenient(true).field("title").field("description"))
				.should(QueryBuilders.queryStringQuery("*" + title + "*").lenient(true).field("title")
						.field("discription"));
		searchsource.query(querybuilder);
		searchrequest.source(searchsource);
		SearchResponse searchresponse=null;
		try {
			searchresponse=client.search(searchrequest, RequestOptions.DEFAULT);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return getResult(searchresponse);
	}

	private List<Note> getResult(SearchResponse searchresponse) {
		SearchHit[] searchhits= searchresponse.getHits().getHits();
		List<Note> notes = new ArrayList<>();
		if(searchhits.length>0)
		{
			Arrays.stream(searchhits).forEach(hit->notes.add(objectmapper.convertValue(hit.getSourceAsMap(),Note.class)));
		}
		return null;
	}
}
