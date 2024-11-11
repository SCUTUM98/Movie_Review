package movreview.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import egovframework.example.sample.service.impl.EgovSampleServiceImpl;

import org.springframework.stereotype.Service;

@Service
public class TmdbService {
    
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);

    public TmdbService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String searchByName(String apiKey, String query) {
        String url = String.format("https://api.themoviedb.org/3/search/movie?query=%s&language=ko-KR&page=1", query);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        return response.getBody();
    }
    
    public String searchById(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/movie/movie_id=%s&language=ko-KR", id);
    	
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        return response.getBody();
    }
    
    public String searchActor(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/movie/%s/credits?language=ko-KR", id);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String suggestMovie(String apiKey) {
    	String url = String.format("https://api.themoviedb.org/3/movie/top_rated?language=ko-KR&page=1");

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        return response.getBody();
    }
    
    public String movieDetail(String apiKey, int id) {
    	LOGGER.debug("Input ID: " + id);
    	String url = String.format("https://api.themoviedb.org/3/movie/%s?language=ko-KR", id);
    	LOGGER.debug("Input URL: " + url);
    	
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String collectionDetail(String apiKey, String name) {
    	String url = String.format("https://api.themoviedb.org/3/search/collection?query=%s&include_adult=false&language=ko-KR&page=1", name);
    	
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String movieRecommend(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/movie/%s/recommendations?language=ko-KR&page=1", id);
    	
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String getVideo(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/movie/%s/videos?language=ko-KR", id);
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String getActorDetail(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/person/%s?language=ko-KR", id);
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String getActorSns(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/person/%s/external_ids", id);
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String movieCredits(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/person/%s/movie_credits?language=ko-KR", id);
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String movieProviders(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/movie/%s/watch/providers", id);
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String movieTrends(String apiKey) {
    	String url = String.format("https://api.themoviedb.org/3/trending/movie/day?language=ko-KR");
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String upComing(String apiKey) {
    	String url = String.format("https://api.themoviedb.org/3/movie/upcoming?language=ko-KR&page=1");
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
}
