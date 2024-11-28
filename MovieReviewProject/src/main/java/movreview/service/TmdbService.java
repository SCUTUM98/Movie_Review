package movreview.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    
    public String tvTrending(String apiKey) {
    	String url = String.format("https://api.themoviedb.org/3/trending/tv/day?language=ko-KR");
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String newestAniKR(String apiKey) {
    	LocalDate now = LocalDate.now();
    	int year = now.getYear();
    	
    	String url = String.format("https://api.themoviedb.org/3/discover/tv?first_air_date_year=" + year + "&include_adult=false&include_null_first_air_dates=false&language=ko-KR&page=1&sort_by=first_air_date.desc&with_genres=16&with_origin_country=KR", year);
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String newestAniJP(String apiKey) {
    	LocalDate now = LocalDate.now();
    	int year = now.getYear();
    	
    	String url = String.format("https://api.themoviedb.org/3/discover/tv?first_air_date_year=" + year + "&include_adult=false&include_null_first_air_dates=false&language=ko-KR&page=1&sort_by=first_air_date.desc&with_genres=16&with_origin_country=JP");
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String newestAniUS(String apiKey) {
    	LocalDate now = LocalDate.now();
    	int year = now.getYear();
    	
    	String url = String.format("https://api.themoviedb.org/3/discover/tv?first_air_date_year=" + year + "&include_adult=false&include_null_first_air_dates=false&language=ko-KR&page=1&sort_by=first_air_date.desc&with_genres=16&with_origin_country=US");
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String popularRealityKR(String apiKey) {
    	String url = String.format("https://api.themoviedb.org/3/discover/tv?include_adult=false&include_null_first_air_dates=false&language=ko-KR&page=1&sort_by=popularity.desc&with_genres=10764&with_origin_country=KR");
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String popularRealityJP(String apiKey) {
    	String url = String.format("https://api.themoviedb.org/3/discover/tv?include_adult=false&include_null_first_air_dates=false&language=ko-KR&page=1&sort_by=popularity.desc&with_genres=10764&with_origin_country=JP");
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String popularRealityUS(String apiKey) {
    	String url = String.format("https://api.themoviedb.org/3/discover/tv?include_adult=false&include_null_first_air_dates=false&language=ko-KR&page=1&sort_by=popularity.desc&with_genres=10764&with_origin_country=US");
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String popularDramaKR(String apiKey) {
    	LocalDate now = LocalDate.now();
    	int year = now.getYear();
    	
    	String url = String.format("https://api.themoviedb.org/3/discover/tv?first_air_date_year=" + year + "&includeadult=false&include_null_first_air_dates=false&language=ko-KR&page=1&sort_by=popularity.desc&with_genres=18&with_origin_country=KR");
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String popularDramaJP(String apiKey) {
    	LocalDate now = LocalDate.now();
    	int year = now.getYear();
    	
    	String url = String.format("https://api.themoviedb.org/3/discover/tv?first_air_date_year=" + year + "&includeadult=false&include_null_first_air_dates=false&language=ko-KR&page=1&sort_by=popularity.desc&with_genres=18&with_origin_country=JP");
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String popularDramaUS(String apiKey) {
    	LocalDate now = LocalDate.now();
    	int year = now.getYear();
    	
    	String url = String.format("https://api.themoviedb.org/3/discover/tv?first_air_date_year=" + year + "&includeadult=false&include_null_first_air_dates=false&language=ko-KR&page=1&sort_by=popularity.desc&with_genres=18&with_origin_country=US");
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String searchTv(String apiKey, String searchKeyword) {
    	String url = String.format("https://api.themoviedb.org/3/search/tv?query=%s&include_adult=false&language=ko-KR&page=1", searchKeyword);

    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String detailTv(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/tv/%s?language=ko-KR", id);
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String searchTvActor(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/tv/%s/aggregate_credits?language=ko-KR", id);
    	
    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String getTvTrailer(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/tv/%s/videos?language=ko-KR", id);

    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String TvRecommendation(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/tv/%s/recommendations?language=ko-KR&page=1", id);

    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
    
    public String getSeasonInfo(String apiKey, int id) {
    	String url = String.format("https://api.themoviedb.org/3/tv/%s?language=ko-KR", id);

    	HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        
        LOGGER.debug("Response Body: " + response.getBody());
        
        return response.getBody();
    }
}
