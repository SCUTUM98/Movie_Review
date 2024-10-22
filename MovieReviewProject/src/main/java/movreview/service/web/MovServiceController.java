package movreview.service.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import egovframework.example.sample.service.impl.EgovSampleServiceImpl;
import movreview.service.MovieService;
import movreview.service.TmdbService;
import movreview.service.impl.MovieServiceImpl;
import movreview.service.MovieVO;
import movreview.service.CollectionVO;
import movreview.service.LoginVO;
import movreview.service.ActorVO;

@Controller
public class MovServiceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);
	
	@Resource(name = "movService")
	private MovieService movService;
	
	@Autowired
	private TmdbService tmdbService;
	
	@RequestMapping(value="/main.do")
	public String mainPage(Model model) throws Exception {
		String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlOTFhNDM3OTVmMWRjMDMyNzk1OTA1NWJjN2FlOGJiOSIsIm5iZiI6MTcyODYwNTgwMS40Njk1NTMsInN1YiI6IjY3MDY0OTc4YTg4NjE0ZDZiMDhhZGRhNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.167LDdbBCOhEn0TosoOrME7mxJhmEq4T2Tq3lExAZ3Q";
		String suggestData = tmdbService.suggestMovie(apiKey);
		
		if (suggestData == null || suggestData.isEmpty()) {
            throw new RuntimeException("Received null or empty response from the API");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(suggestData);
            JsonNode resultsNode = jsonNode.get("results");
            if (resultsNode == null || !resultsNode.isArray()) {
                throw new RuntimeException("No results found in the response");
            }

            List<MovieVO> suggestVO = objectMapper.convertValue(
                resultsNode,
                new TypeReference<List<MovieVO>>() {}
            );
            
            LOGGER.debug("결과: " + suggestVO);
            model.addAttribute("movieData", suggestVO);
            model.addAttribute("totalPages", jsonNode.get("total_pages").asInt());
            model.addAttribute("totalResults", jsonNode.get("total_results").asInt());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing the API response: " + e.getMessage());
        }
        
		return "board/main";
	}
	
	@RequestMapping(value="/search.do")
	public String searchPage() throws Exception {
		
		
		return "board/search";
	}
	//API 테스트용
	@GetMapping("/searchMovie.do")
    public String searchMovie(HttpServletRequest request, Model model) throws Exception {
		String searchKeyword = "아이언맨";
        String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlOTFhNDM3OTVmMWRjMDMyNzk1OTA1NWJjN2FlOGJiOSIsIm5iZiI6MTcyODYwNTgwMS40Njk1NTMsInN1YiI6IjY3MDY0OTc4YTg4NjE0ZDZiMDhhZGRhNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.167LDdbBCOhEn0TosoOrME7mxJhmEq4T2Tq3lExAZ3Q";
        String movieData = tmdbService.searchByName(apiKey, searchKeyword);
        model.addAttribute("movieData", movieData);
        return "board/dataTest";
    }
	
	@RequestMapping(value="/movieSearch.do", method=RequestMethod.POST)
	public String movieSearch( @RequestParam("searchKeyword") String title, Model model) throws Exception {
	    MovieVO searchVO = new MovieVO();
		LOGGER.debug("Title: " + title);
		searchVO.setTitleEn(title);
	    LOGGER.debug("movieVO title: " + searchVO.getTitleEn());
	    
	    List<?> searchList = movService.searchMovie(searchVO);
	    System.out.println("올포랜드 : " + searchList);
	    
	    model.addAttribute("searchList", searchList);
	    
	    return "redirect:/result.do";
	}
	
	@RequestMapping(value="/result.do", method=RequestMethod.POST)
	public String searchResult(@RequestParam("searchKeyword") String searchKeyword, HttpServletRequest request, Model model) throws Exception {
		MovieVO searchVO = new MovieVO();
	    LOGGER.debug("Title: " + searchKeyword);
	    searchVO.setTitleEn(searchKeyword);
	    LOGGER.debug("movieVO title: " + searchVO.getTitleEn());

	    List<?> searchList = movService.searchMovie(searchVO);
	    
	    model.addAttribute("searchList", searchList);
		
		String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlOTFhNDM3OTVmMWRjMDMyNzk1OTA1NWJjN2FlOGJiOSIsIm5iZiI6MTcyODYwNTgwMS40Njk1NTMsInN1YiI6IjY3MDY0OTc4YTg4NjE0ZDZiMDhhZGRhNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.167LDdbBCOhEn0TosoOrME7mxJhmEq4T2Tq3lExAZ3Q";
		String suggestData = tmdbService.suggestMovie(apiKey);
		
		if (suggestData == null || suggestData.isEmpty()) {
            throw new RuntimeException("Received null or empty response from the API");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(suggestData);
            JsonNode resultsNode = jsonNode.get("results");
            if (resultsNode == null || !resultsNode.isArray()) {
                throw new RuntimeException("No results found in the response");
            }

            List<MovieVO> suggestVO = objectMapper.convertValue(
                resultsNode,
                new TypeReference<List<MovieVO>>() {}
            );

            model.addAttribute("suggestData", suggestVO);
            model.addAttribute("totalPages", jsonNode.get("total_pages").asInt());
            model.addAttribute("totalResults", jsonNode.get("total_results").asInt());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing the API response: " + e.getMessage());
        }
		
		return "board/searchResult";
	}
	
	@RequestMapping(value="/detail.do")
	public String movieDetail(@RequestParam("id") int id, HttpServletRequest request, Model model) throws Exception {
		LOGGER.debug("ID Value: " + id);
		String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlOTFhNDM3OTVmMWRjMDMyNzk1OTA1NWJjN2FlOGJiOSIsIm5iZiI6MTcyODYwNTgwMS40Njk1NTMsInN1YiI6IjY3MDY0OTc4YTg4NjE0ZDZiMDhhZGRhNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.167LDdbBCOhEn0TosoOrME7mxJhmEq4T2Tq3lExAZ3Q";
		String detailData = tmdbService.movieDetail(apiKey, id);
		String recommendData = tmdbService.movieRecommend(apiKey, id);
		
		if (detailData == null || detailData.isEmpty()) {
            throw new RuntimeException("Received null or empty response from the API");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(detailData);            
            MovieVO detailVO = objectMapper.convertValue(jsonNode, MovieVO.class);
            
            if (recommendData != null && !recommendData.isEmpty()) {
                JsonNode recommendNode = objectMapper.readTree(recommendData);
                JsonNode recNode = recommendNode.path("results");
                
                // JsonNode를 List로 변환하여 모델에 추가
                List<Map<String, Object>> recList = new ArrayList<>();
                for (JsonNode actor : recNode) {
                    Map<String, Object> actorMap = objectMapper.convertValue(actor, Map.class);
                    recList.add(actorMap);
                }
                LOGGER.debug("Recommend List: " + recList);
                model.addAttribute("recommendData", recList);
            }
            
            model.addAttribute("detailData", detailVO);
            
            List<String> genres = new ArrayList<>();
            for (JsonNode genreNode : jsonNode.path("genres")) {
                genres.add(genreNode.path("name").asText());
            }
            detailVO.setGenre(genres);
            LOGGER.debug("Genre list: " + detailVO.getGenre());
            
            if (jsonNode.has("belongs_to_collection")) {
                JsonNode collectionNode = jsonNode.path("belongs_to_collection");
                CollectionVO collectionVO = new CollectionVO();
                collectionVO.setId(collectionNode.path("id").asInt());
                collectionVO.setName(collectionNode.path("name").asText());
                collectionVO.setPosterPath(collectionNode.path("poster_path").asText());
                collectionVO.setBackdropPath(collectionNode.path("backdrop_path").asText());
                
                String collectionData = tmdbService.collectionDetail(apiKey, collectionVO.getName());
                LOGGER.debug("CollectionData: " + collectionData);
                JsonNode overviewNode = objectMapper.readTree(collectionData);
                CollectionVO resultVO = objectMapper.convertValue(overviewNode, CollectionVO.class);
                resultVO.setOverview(overviewNode.findPath("overview").asText());
                model.addAttribute("overviewData", resultVO);
                
                if (overviewNode.has("results")) {
                	JsonNode resultNode = overviewNode.findPath("result");
                    collectionVO.setOverview(resultNode.path("overview").asText());
                    LOGGER.debug("Collection Id: " + collectionVO.getId());
                    LOGGER.debug("Collection Poster Path: " + collectionVO.getPosterPath());
                    LOGGER.debug("Collection Overview: " + collectionVO.getOverview());
                    LOGGER.debug("Collection Overview: " + collectionVO.getOverview());
                    model.addAttribute("collectionData", collectionVO);
                }
                
            }
            
            int movieId = jsonNode.path("id").asInt();
            String actorData = tmdbService.searchActor(apiKey, movieId);

            if (actorData != null && !actorData.isEmpty()) {
                JsonNode actorNode = objectMapper.readTree(actorData);
                JsonNode castNode = actorNode.path("cast");
                
                // JsonNode를 List로 변환하여 모델에 추가
                List<Map<String, Object>> actorList = new ArrayList<>();
                for (JsonNode actor : castNode) {
                    Map<String, Object> actorMap = objectMapper.convertValue(actor, Map.class);
                    actorList.add(actorMap);
                }
                LOGGER.debug("Actor List: " + actorList);
                model.addAttribute("actorData", actorList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing the API response: " + e.getMessage());
        }
        
		return "board/detail";
	}
	
	@RequestMapping(value="/localDetail.do")
	public String localDetail(@RequestParam("id") int id, HttpServletRequest request, Model model) throws Exception {
		MovieVO selectVO = new MovieVO();
	    selectVO.setMovieId(id);
	    movService.selectMovie(selectVO);
	    LOGGER.debug("장르: " + movService.selectMovie(selectVO).getGenreDB());
	    model.addAttribute("selectMovie", movService.selectMovie(selectVO));
	    
		LOGGER.debug("ID Value: " + id);
		String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlOTFhNDM3OTVmMWRjMDMyNzk1OTA1NWJjN2FlOGJiOSIsIm5iZiI6MTcyODYwNTgwMS40Njk1NTMsInN1YiI6IjY3MDY0OTc4YTg4NjE0ZDZiMDhhZGRhNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.167LDdbBCOhEn0TosoOrME7mxJhmEq4T2Tq3lExAZ3Q";
		String recommendData = tmdbService.movieRecommend(apiKey, id);

        ObjectMapper objectMapper = new ObjectMapper();
        if (recommendData != null && !recommendData.isEmpty()) {
            JsonNode recommendNode = objectMapper.readTree(recommendData);
            JsonNode recNode = recommendNode.path("results");
            
            // JsonNode를 List로 변환하여 모델에 추가
            List<Map<String, Object>> recList = new ArrayList<>();
            for (JsonNode actor : recNode) {
                Map<String, Object> actorMap = objectMapper.convertValue(actor, Map.class);
                recList.add(actorMap);
            }
            LOGGER.debug("Recommend List: " + recList);
            model.addAttribute("recommendData", recList);
        }
        
            
		/*
		 * String collectionData = tmdbService.collectionDetail(apiKey,
		 * collectionVO.getName()); LOGGER.debug("CollectionData: " + collectionData);
		 * JsonNode overviewNode = objectMapper.readTree(collectionData); CollectionVO
		 * resultVO = objectMapper.convertValue(overviewNode, CollectionVO.class);
		 * resultVO.setOverview(overviewNode.findPath("overview").asText());
		 * model.addAttribute("overviewData", resultVO);
		 * 
		 * int movieId = jsonNode.path("id").asInt(); String actorData =
		 * tmdbService.searchActor(apiKey, movieId);
		 * 
		 * if (actorData != null && !actorData.isEmpty()) { JsonNode actorNode =
		 * objectMapper.readTree(actorData); JsonNode castNode = actorNode.path("cast");
		 * 
		 * // JsonNode를 List로 변환하여 모델에 추가 List<Map<String, Object>> actorList = new
		 * ArrayList<>(); for (JsonNode actor : castNode) { Map<String, Object> actorMap
		 * = objectMapper.convertValue(actor, Map.class); actorList.add(actorMap); }
		 * LOGGER.debug("Actor List: " + actorList); model.addAttribute("actorData",
		 * actorList); }
		 */
        
		return "board/localDetail";
	}
	
	@RequestMapping(value="/addMovie.do", method = RequestMethod.POST)
	public String addMovie(SessionStatus status,
	                        @RequestParam("id") int id,
	                        @RequestParam("movieId") int movieId,
	                        @RequestParam("titleKr") String titleKr,
	                        @RequestParam("titleEn") String titleEn,
	                        @RequestParam("genreDB") String genreDB,
	                        @RequestParam("releaseDate") String releaseDate,
	                        @RequestParam("overview") String overview,
	                        @RequestParam("backdropPath") String backdropPath,
	                        @RequestParam("posterPath") String posterPath,
	                        @RequestParam("collectionId") int collectionId,
	                        @RequestParam("status") String movieStatus,
	                        @RequestParam("tagline") String tagline,
	                        @RequestParam("seriesId") int seriesId,
	                        @RequestParam("seriesName") String seriesName,
	                        @RequestParam("seriesDropPath") String seriesDropPath,
	                        @RequestParam("seriesPosterPath") String seriesPosterPath,
	                        @RequestParam("seriesOverview") String seriesOverview,
	                        @RequestParam("actorIdList") String[] actorIds, // 배열로 수신
	                        @RequestParam("actNameList") String[] actorNames, // 배열로 수신
	                        @RequestParam("actProfilePathList") String[] actorProfilePaths) throws Exception {
	    
	    // MovieVO 생성 및 설정
	    MovieVO movieVO = new MovieVO();
	    movieVO.setMovieId(movieId);
	    movieVO.setTitleKr(titleKr);
	    movieVO.setTitleEn(titleEn);
	    movieVO.setGenreDB(genreDB);
	    movieVO.setReleaseDate(releaseDate);
	    movieVO.setOverview(overview);
	    movieVO.setBackdropPath(backdropPath);
	    movieVO.setPosterPath(posterPath);
	    movieVO.setCollectionId(collectionId);
	    movieVO.setStatus(movieStatus);
	    movieVO.setTagline(tagline);
	    
	    // CollectionVO 생성 및 설정
	    CollectionVO collectionVO = new CollectionVO();
	    collectionVO.setId(seriesId);
	    collectionVO.setName(seriesName);
	    collectionVO.setBackdropPath(seriesDropPath);
	    collectionVO.setPosterPath(seriesPosterPath);
	    collectionVO.setOverview(seriesOverview);
	    
	    // Movie, Collection 데이터 삽입
	    movService.insertMovie(movieVO);
	    movService.insertCollection(collectionVO);
	    
	    // Actor 데이터 삽입
	    for (int i = 0; i < actorIds.length; i++) {
	        ActorVO actorVO = new ActorVO();
	        actorVO.setActorId(actorIds[i]);
	        actorVO.setActName(actorNames[i]);
	        actorVO.setProfilePath(actorProfilePaths[i]);
	        LOGGER.debug("Actor ID: " + actorVO.getActorId() + "\nActor Name: " + actorVO.getActName());
	        
	        movService.insertActor(actorVO);
	    }
	    
	    status.setComplete();
	    
	    MovieVO detailVO = new MovieVO();
	    detailVO.setMovieId(movieId);
	    
	    return "redirect:/localDetail.do";
	}

}
