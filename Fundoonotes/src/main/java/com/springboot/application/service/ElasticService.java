package com.springboot.application.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.application.config.ElasticConfig;
import com.springboot.application.model.Note;

@Service
public class ElasticService {

	@Autowired
	private ElasticConfig config;
	@Autowired
	private ObjectMapper objectmapper;
	// index in elastic search means database
	private String INDEX = "springboot";
	// type in elastic search means table
	private String TYPE = "note_details";

	@SuppressWarnings("unchecked")
	public String CreateNote(Note note) throws Exception {
		Map<String, Object> notemapper = objectmapper.convertValue(note, Map.class);
		IndexRequest indexrequest = new IndexRequest(INDEX, TYPE, String.valueOf(note.getId())).source(notemapper);
		IndexResponse indexResponse = config.client().index(indexrequest, RequestOptions.DEFAULT);
		System.out.println(indexrequest);
		System.out.println(indexResponse);
		System.out.println( indexResponse.getResult().name());
		return indexResponse.getResult().name();
	}

	@SuppressWarnings("unchecked")
	public String UpdateNote(Note note) throws Exception {
		Map<String, Object> notemapper = objectmapper.convertValue(note, Map.class);
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(note.getId()));
		UpdateResponse updateResponse = config.client().update(updateRequest, RequestOptions.DEFAULT);
		return updateResponse.getResult().name();
	}

	@SuppressWarnings("unchecked")
	public String DeleteNote(Note note) throws Exception {
		Map<String, Object> notemapper = objectmapper.convertValue(note, Map.class);
		DeleteRequest deleterequest = new DeleteRequest(INDEX, TYPE, String.valueOf(note.getId()));
		DeleteResponse deleteResponse = config.client().delete(deleterequest, RequestOptions.DEFAULT);
		return deleteResponse.getResult().name();
	}

	public List<Note> searchbytitle(String title) {
		System.out.println(title);
		SearchRequest searchrequest = new SearchRequest("springboot");
		SearchSourceBuilder searchsource = new SearchSourceBuilder();
		System.out.println(searchrequest);
		
		searchsource.query(QueryBuilders.matchQuery("title",title));
		searchrequest.source(searchsource);
		SearchResponse searchresponse = null;
		try {
			searchresponse = config.client().search(searchrequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(getResult(searchresponse).toString());
		return getResult(searchresponse);
	}

	private List<Note> getResult(SearchResponse searchresponse) {
		SearchHit[] searchhits = searchresponse.getHits().getHits();
		List<Note> notes = new ArrayList<>();
		if (searchhits.length > 0) {
			Arrays.stream(searchhits)
					.forEach(hit -> notes.add(objectmapper.convertValue(hit.getSourceAsMap(), Note.class)));
		}
		return notes;
	}
}
