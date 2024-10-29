package movreview.service.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import egovframework.example.sample.service.impl.EgovSampleServiceImpl;
import movreview.service.MovieService;
import movreview.service.TmdbService;
import movreview.service.impl.MovieServiceImpl;
import movreview.service.MovieVO;
import movreview.service.ProviderVO;
import movreview.service.CollectionVO;
import movreview.service.MemberVO;
import movreview.service.ActorVO;
import movreview.service.VideoVO;
import movreview.service.ActorSnsVO;

@Configuration
@PropertySource("classpath:api.properties")
@Controller
public class MovServiceController {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);
	
	@Value("${tmdb-api-key}")
	private String apiKey;
	
	@Resource(name = "movService")
	private MovieService movService;
	
	@Autowired
	private TmdbService tmdbService;
	
	@Inject
	BCryptPasswordEncoder encoder;
	
	@Autowired
	private MailHandler mailHandler;
	
	@RequestMapping(value="/main.do")
	public String mainPage(Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
	    String username = (String) session.getAttribute("username");

	    model.addAttribute("username", username);
	    
		MovieVO recentVO = new MovieVO();
		CollectionVO seriesVO = new CollectionVO();
		model.addAttribute("recentlyAdded", movService.recentlyAdded(recentVO));
		model.addAttribute("recentlyCollected", movService.recentlyCollected(seriesVO));
		
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
	public String searchPage(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
	    String username = (String) session.getAttribute("username");

	    model.addAttribute("username", username);
		
		return "board/search";
	}
	//API 테스트용
	@GetMapping("/searchMovie.do")
    public String searchMovie(HttpServletRequest request, Model model) throws Exception {
		String searchKeyword = "아이언맨";
        
        String movieData = tmdbService.searchByName(apiKey, searchKeyword);
        model.addAttribute("movieData", movieData);
        return "board/dataTest";
    }
	
	@RequestMapping(value="/movieSearch.do", method=RequestMethod.POST)
	public String movieSearch( @RequestParam("searchKeyword") String title, Model model) throws Exception {
	    MovieVO searchVO = new MovieVO();
	    ActorVO actorVO = new ActorVO();
		searchVO.setTitleEn(title);
		actorVO.setActName(title);
	    
	    List<?> searchList = movService.searchMovie(searchVO);
	    List<?> actorList = movService.searchActor(actorVO);
	    
	    model.addAttribute("searchList", searchList);
	    model.addAttribute("actorList", actorList);
	    
	    return "redirect:/result.do";
	}
	
	@RequestMapping(value="/result.do", method=RequestMethod.POST)
	public String searchResult(@RequestParam("searchKeyword") String searchKeyword, HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
	    String username = (String) session.getAttribute("username");

	    model.addAttribute("username", username);
		
		MovieVO searchVO = new MovieVO();
		ActorVO actorVO = new ActorVO();
		CollectionVO collectionVO = new CollectionVO();
	    searchVO.setTitleEn(searchKeyword);
	    actorVO.setActName(searchKeyword);
	    collectionVO.setName(searchKeyword);

	    List<?> searchList = movService.searchMovie(searchVO);
	    List<?> actorList = movService.searchActor(actorVO);
	    List<?> collectionList = movService.searchCollection(collectionVO);
	    
	    model.addAttribute("searchList", searchList);
	    model.addAttribute("actorList", actorList);
	    model.addAttribute("collectionList", collectionList);
		
		String suggestData = tmdbService.suggestMovie(apiKey);
		String searchResult = tmdbService.searchByName(apiKey, searchKeyword);
		
		if (suggestData == null || suggestData.isEmpty()) {
            throw new RuntimeException("Received null or empty response from the API");
        }

        if (searchResult == null || searchResult.isEmpty()) {
        	throw new RuntimeException("Received null or empty response from the API");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(suggestData);
            JsonNode resultsNode = jsonNode.get("results");
            JsonNode searchNode = objectMapper.readTree(searchResult);
            JsonNode searchResultNode = searchNode.get("results");
            
            if (resultsNode == null || !resultsNode.isArray()) {
                throw new RuntimeException("No results found in the response");
            }

            List<MovieVO> suggestVO = objectMapper.convertValue(
                resultsNode,
                new TypeReference<List<MovieVO>>() {}
            );
            
            List<MovieVO> resultVO = objectMapper.convertValue(
            		searchResultNode, 
            		new TypeReference<List<MovieVO>> () {});
            
            List<MovieVO> uniqueMovies = new ArrayList<>();

            for (MovieVO movie : resultVO) {
                int count = movService.checkMovie(movie);
                if (count == 0) {
                    uniqueMovies.add(movie);
                }
                else {
                	continue;
                }
            }

            model.addAttribute("suggestData", suggestVO);
            model.addAttribute("totalPages", jsonNode.get("total_pages").asInt());
            model.addAttribute("totalResults", jsonNode.get("total_results").asInt());
            model.addAttribute("resultData", uniqueMovies);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing the API response: " + e.getMessage());
        }
        
		return "board/searchResult";
	}
	
	@RequestMapping(value="/detail.do")
	public String movieDetail(@RequestParam("id") int id, HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
	    String username = (String) session.getAttribute("username");

	    model.addAttribute("username", username);
		
		LOGGER.debug("ID Value: " + id);
	    
	    String detailData = tmdbService.movieDetail(apiKey, id);
	    String recommendData = tmdbService.movieRecommend(apiKey, id);
	    String videoData = tmdbService.getVideo(apiKey, id);
	    
	    if (detailData == null || detailData.isEmpty()) {
	        throw new RuntimeException("Received null or empty response from the API");
	    }

	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        JsonNode jsonNode = objectMapper.readTree(detailData);
	        JsonNode videoNode = objectMapper.readTree(videoData);
	        JsonNode vidNode = videoNode.path("results");
	        
	        MovieVO detailVO = objectMapper.convertValue(jsonNode, MovieVO.class);
	        
	        if (recommendData != null && !recommendData.isEmpty()) {
	            JsonNode recommendNode = objectMapper.readTree(recommendData);
	            JsonNode recNode = recommendNode.path("results");
	            
	            List<Map<String, Object>> recList = new ArrayList<>();
	            for (JsonNode actor : recNode) {
	                Map<String, Object> actorMap = objectMapper.convertValue(actor, Map.class);
	                recList.add(actorMap);
	            }
	            LOGGER.debug("Recommend List: " + recList);

	            List<Map<String, Object>> uniqueRecList = new ArrayList<>();
	            List<Map<String, Object>> notUniqueRecList = new ArrayList<>();

	            for (Map<String, Object> recMovie : recList) {
	                int movieId = (Integer) recMovie.get("id");

	                MovieVO movieVO = new MovieVO();
	                movieVO.setMovieId(movieId);
	                
	                int count = movService.checkMovie(movieVO);

	                if (count == 0) {
	                    uniqueRecList.add(recMovie);
	                } else {
	                    notUniqueRecList.add(recMovie);
	                }
	            }
	            model.addAttribute("recommendData", uniqueRecList);
	            model.addAttribute("notUniqueData", notUniqueRecList);
	        }
	        
	        model.addAttribute("detailData", detailVO);
	        
	        // 장르 설정
	        List<String> genres = new ArrayList<>();
	        for (JsonNode genreNode : jsonNode.path("genres")) {
	            genres.add(genreNode.path("name").asText());
	        }
	        detailVO.setGenre(genres);
	        LOGGER.debug("Genre list: " + detailVO.getGenre());
	        
	        // 컬렉션 데이터 처리
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
	                model.addAttribute("collectionData", collectionVO);
	            }
	        }
	        
	        // 배우 데이터 처리
	        int movieId = jsonNode.path("id").asInt();
	        String actorData = tmdbService.searchActor(apiKey, movieId);

	        if (actorData != null && !actorData.isEmpty()) {
	            JsonNode actorNode = objectMapper.readTree(actorData);
	            JsonNode castNode = actorNode.path("cast");
	            
	            List<Map<String, Object>> actorList = new ArrayList<>();
	            for (JsonNode actor : castNode) {
	                Map<String, Object> actorMap = objectMapper.convertValue(actor, Map.class);
	                actorList.add(actorMap);
	            }
	            LOGGER.debug("Actor List: " + actorList);
	            model.addAttribute("actorData", actorList);
	        }

	        // 비디오 데이터 처리
	        if (vidNode.isArray()) {
	            for (JsonNode video : vidNode) {
	                VideoVO videoVO = objectMapper.convertValue(video, VideoVO.class);
	                LOGGER.debug("VIDEO KEY: " + videoVO.getKey());
	                model.addAttribute("videoData", videoVO);
	            }
	        } else {
	            LOGGER.warn("No video data found or not an array");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Error processing the API response: " + e.getMessage());
	    }
	    
	    return "board/detail";
	}

	
	@RequestMapping(value="/localDetail.do")
	public String localDetail(@RequestParam("id") int id, HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
	    String username = (String) session.getAttribute("username");

	    model.addAttribute("username", username);
		
		MovieVO selectVO = new MovieVO();
		CollectionVO collectVO = new CollectionVO();
		
	    selectVO.setMovieId(id);
	    movService.selectMovie(selectVO);
	    collectVO.setId(movService.selectMovie(selectVO).getCollectionId());

	    model.addAttribute("selectMovie", movService.selectMovie(selectVO));
	    model.addAttribute("collectionData", movService.checkCollection(collectVO));
	    
		LOGGER.debug("ID Value: " + id);
		
		String recommendData = tmdbService.movieRecommend(apiKey, id);
		int movieId = id;
        String actorData = tmdbService.searchActor(apiKey, movieId);
        String videoData = tmdbService.getVideo(apiKey, id);
        String providersData = tmdbService.movieProviders(apiKey, movieId);
        
        ObjectMapper objectMapper = new ObjectMapper();
        try {
        	JsonNode videoNode = objectMapper.readTree(videoData);
	        JsonNode vidNode = videoNode.path("results");
        	
        	if (actorData != null && !actorData.isEmpty()) {
                JsonNode actorNode = objectMapper.readTree(actorData);
                JsonNode providersNode = objectMapper.readTree(providersData);
                
                JsonNode castNode = actorNode.path("cast");
                JsonNode proResultsNode = providersNode.path("results");
                JsonNode koreaNode = proResultsNode.path("KR");
                JsonNode buyNode = koreaNode.findPath("buy");
                JsonNode rentNode = koreaNode.findPath("rent");
                JsonNode streamNode = koreaNode.findPath("flatrate");
                
                List<Map<String, Object>> actorList = new ArrayList<>();
                for (JsonNode actor : castNode) {
                    Map<String, Object> actorMap = objectMapper.convertValue(actor, Map.class);
                    actorList.add(actorMap);
                }
                LOGGER.debug("Actor List: " + actorList);
                model.addAttribute("actorData", actorList);
                
                List<Map<String, Object>> buyList = new ArrayList<>();
                List<Map<String, Object>> rentList = new ArrayList<>();
                List<Map<String, Object>> streamList = new ArrayList<>();
                for (JsonNode buy : buyNode) {
                	Map<String, Object> buyMap = objectMapper.convertValue(buy, Map.class);
                	buyList.add(buyMap);
                }
                for (JsonNode rent : rentNode) {
                	Map<String, Object> rentMap = objectMapper.convertValue(rent, Map.class);
                	rentList.add(rentMap);
                }
                for (JsonNode stream : streamNode) {
                	Map<String, Object> streamMap = objectMapper.convertValue(stream, Map.class);
                	streamList.add(streamMap);
                }
                model.addAttribute("buyList", buyList);
                LOGGER.debug("Buy List: " + buyList);
                System.out.println("Buy List: " + buyList);
                model.addAttribute("rentList", rentList);
                model.addAttribute("streamList", streamList);
                LOGGER.debug("Stream List: " + streamList);
                System.out.println("Stream List: " + streamList);
            }
        	
        	// 비디오 데이터 처리
	        if (vidNode.isArray()) {
	            for (JsonNode video : vidNode) {
	                VideoVO videoVO = objectMapper.convertValue(video, VideoVO.class);
	                LOGGER.debug("VIDEO KEY: " + videoVO.getKey());
	                model.addAttribute("videoData", videoVO);
	            }
	        } else {
	            LOGGER.warn("No video data found or not an array");
	        }
        	
        	if (recommendData != null && !recommendData.isEmpty()) {
	            JsonNode recommendNode = objectMapper.readTree(recommendData);
	            JsonNode recNode = recommendNode.path("results");
	            
	            List<Map<String, Object>> recList = new ArrayList<>();
	            for (JsonNode actor : recNode) {
	                Map<String, Object> actorMap = objectMapper.convertValue(actor, Map.class);
	                recList.add(actorMap);
	            }
	            LOGGER.debug("Recommend List: " + recList);

	            List<Map<String, Object>> uniqueRecList = new ArrayList<>();
	            List<Map<String, Object>> notUniqueRecList = new ArrayList<>();

	            for (Map<String, Object> recMovie : recList) {
	                int movieid = (Integer) recMovie.get("id");

	                MovieVO movieVO = new MovieVO();
	                movieVO.setMovieId(movieid);
	                
	                int count = movService.checkMovie(movieVO);

	                if (count == 0) {
	                    uniqueRecList.add(recMovie);
	                }
	                else {
	                	notUniqueRecList.add(recMovie);
	                }
	            }
	            model.addAttribute("recommendData", uniqueRecList);
	            model.addAttribute("notUniqueData", notUniqueRecList);
	        }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing the API response: " + e.getMessage());
        }
        
		return "board/localDetail";
	}
	
	@RequestMapping(value="/addMovie.do", method = RequestMethod.POST)
	public String addMovie(SessionStatus status,
							HttpServletRequest request,
							Model model,
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
	                        @RequestParam("actorIdList") String[] actorIds,
	                        @RequestParam("actNameList") String[] actorNames,
	                        @RequestParam("actProfilePathList") String[] actorProfilePaths) throws Exception {
		HttpSession session = request.getSession();
	    String username = (String) session.getAttribute("username");

	    model.addAttribute("username", username);
	    
		MovieVO movieVO = new MovieVO();
	    movieVO.setMovieId(movieId);
	    movieVO.setTitleKr(titleKr);
	    movieVO.setTitleEn(titleEn);
	    movieVO.setGenreDB(genreDB);
	    movieVO.setReleaseDate(releaseDate);
	    movieVO.setOverview(overview);
	    movieVO.setBackdropPath(backdropPath);
	    movieVO.setPosterPath(posterPath);
	    movieVO.setCollectionId(seriesId);
	    movieVO.setStatus(movieStatus);
	    movieVO.setTagline(tagline);
	    
	    CollectionVO collectionVO = new CollectionVO();
	    collectionVO.setId(seriesId);
	    collectionVO.setName(seriesName);
	    collectionVO.setBackdropPath(seriesDropPath);
	    collectionVO.setPosterPath(seriesPosterPath);
	    collectionVO.setOverview(seriesOverview);
	    
	    CollectionVO checkCollection = movService.checkCollection(collectionVO);
	    
	    if(checkCollection == null) {
	    	movService.insertMovie(movieVO);
		    movService.insertCollection(collectionVO);
	    }
	    else if (checkCollection != null) {
	    	movService.insertMovie(movieVO);
	    }
	    
	    for (int i = 0; i < actorIds.length; i++) {
	        ActorVO actorVO = new ActorVO();
	        actorVO.setActorId(actorIds[i]);
	        actorVO.setActName(actorNames[i]);
	        actorVO.setProfilePath(actorProfilePaths[i]);
	        
	        ActorVO checkActor = movService.checkActor(actorVO);
	        
	        if(checkActor == null) {
		    	String actorData = tmdbService.getActorDetail(apiKey, Integer.parseInt(actorIds[i]));
		    	String snsData = tmdbService.getActorSns(apiKey, Integer.parseInt(actorIds[i]));
		    	
		    	ObjectMapper objectMapper = new ObjectMapper();
		    	try {
		    		JsonNode actorNode = objectMapper.readTree(actorData);
		    		JsonNode snsNode = objectMapper.readTree(snsData);
		    		ActorVO actorDetail = objectMapper.convertValue(actorNode, ActorVO.class);
		    		ActorSnsVO snsVO = objectMapper.convertValue(snsNode, ActorSnsVO.class);
		    		
		    		movService.insertActor(actorDetail);
		    		movService.insertSns(snsVO);
		    		
		    	} catch (Exception e) {
		            e.printStackTrace();
		            throw new RuntimeException("Error processing the API response: " + e.getMessage());
		    	}
		    	
	        }
	        
	    }
	    
	    status.setComplete();
	    
	    MovieVO detailVO = new MovieVO();
	    detailVO.setMovieId(movieId);
	    
	    return "redirect:/localDetail.do?id=" + id;
	}
	
	@RequestMapping(value="/seriesDetail.do")
	public String seriesDetail(@RequestParam("collectionId") int collectionId, Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
	    String username = (String) session.getAttribute("username");

	    model.addAttribute("username", username);
		
		CollectionVO collectionVO  = new CollectionVO();
		collectionVO.setId(collectionId);
		MovieVO movieVO = new MovieVO();
		movieVO.setCollectionId(collectionId);
		List<?> movieList = movService.collectionMovie(movieVO);
		
		model.addAttribute("collectionList", movService.selectCollection(collectionVO));
		model.addAttribute("movieList", movieList);
		
		return "board/seriesDetail";
	}
	
	@RequestMapping(value="/actorDetail.do")
	public String actorDetail(@RequestParam("actorId") int actorId, Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
	    String username = (String) session.getAttribute("username");

	    model.addAttribute("username", username);
		
		ActorVO actorVO = new ActorVO();
		ActorSnsVO snsVO = new ActorSnsVO();
		
		String movieCredits = tmdbService.movieCredits(apiKey, actorId);
		
		actorVO.setActorId(String.valueOf(actorId));
		snsVO.setActorId(actorId);
		
		System.out.println("actorId: " + actorVO.getActorId());
		
		ActorVO tmp = movService.actorDetail(actorVO);
		System.out.println(tmp.getActorId());

		model.addAttribute("actorData", movService.actorDetail(actorVO));
		model.addAttribute("snsData", movService.snsDetail(snsVO));
		
		ObjectMapper objectMapper = new ObjectMapper();
        try {
        	JsonNode creditNode = objectMapper.readTree(movieCredits);
	        JsonNode credits = creditNode.path("cast");
	        
	        if (credits != null && !credits.isEmpty()) {
	        	List<Map<String, Object>> creditList = new ArrayList<>();
	        	for (JsonNode credit : credits) {
	        		Map<String, Object> creditMap = objectMapper.convertValue(credit, Map.class);
	        		creditList.add(creditMap);
	        	}
	        	model.addAttribute("creditData", creditList);
	        }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing the API response: " + e.getMessage());
        }
		
		return "board/actorDetail";
	}
	
	@RequestMapping("/registerMember.do")
	public String registerMember() throws Exception {
		
		return "board/signup";
	}
	
	@RequestMapping(value="/insertMember.do", method=RequestMethod.POST)
	public String insertMember(@RequestParam("id") String id
								, @RequestParam("pass") String pass
								, @RequestParam("name") String name
								, @RequestParam("email") String email
								, Model model
								, SessionStatus status) throws Exception {
		MemberVO memberVO = new MemberVO();
		
		String encodPW = encoder.encode(pass);
		String mailKey = new TempKey().getKey(30, false);
		System.out.println("난수: " + mailKey);
		String url = "localhost:8080/verify.do";
		
		memberVO.setId(id);
		memberVO.setEmail(email);
		memberVO.setName(name);
		memberVO.setPass(encodPW);
		memberVO.setMailKey(mailKey);
		
		System.out.println("id: " + id);
		
		movService.registerMember(memberVO);
		
		try {
			mailHandler.sendMail(email, "Film Report 회원가입 인증번호 입니다.", "접속 주소: " + url + "\n 인증번호: " + mailKey);
		} catch (Exception e) {
			e.printStackTrace();
			return "FAIL";			
		}
		
		status.setComplete();
		
		return "redirect:/home.do";
	}
	
	@RequestMapping(value="/home.do", method=RequestMethod.GET)
	public String home() throws Exception {
		return "board/signin";
	}
	
	@RequestMapping(value="/login_success.do", method=RequestMethod.GET)
	public String login(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = request.getUserPrincipal().getName();
		session.setAttribute("username", username);
		
		return "redirect:/main.do";
	}
	
	@RequestMapping(value="login_fail.do", method=RequestMethod.GET)
	public String login_fail() throws Exception {
		return "redirect:/home.do";
	}
	
	@RequestMapping(value="logout_After.do", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession(false);
		
		if(session != null) {
			System.out.println("Server is still alived!");
			session.invalidate();
			
			return "redirect:/main.do";
		}
		
		return "redirect:/main.do";
	}
	
	@RequestMapping(value="verify.do")
	public String verify() throws Exception {
		return("board/verify");
	}
	
	@RequestMapping(value="verifyTest.do")
	public String verifyTest(@RequestParam("email") String email, @RequestParam("mailKey") String mailKey, Model model) throws Exception {
		MemberVO memberVO = new MemberVO();
		
		memberVO.setEmail(email);
		memberVO.setMailKey(mailKey);
		
		int isTrue = movService.verify(memberVO);
		
		if (isTrue == 1) {
			movService.updateMailAuth(memberVO);
			return "redirect:/main.do";
		}
		else {
			return "redirect:/verify.do";
		}
	}

}
