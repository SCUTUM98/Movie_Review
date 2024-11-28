package movreview.service.web;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.hsqldb.rights.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import egovframework.example.sample.service.impl.EgovSampleServiceImpl;
import movreview.service.MovieService;
import movreview.service.TmdbService;
import movreview.service.TvSeasonVO;
import movreview.service.TvVO;
import movreview.service.NaverService;
import movreview.service.MovieVO;
import movreview.service.ReviewVO;
import movreview.service.CollectionVO;
import movreview.service.GenreVO;
import movreview.service.LikeVO;
import movreview.service.LogChartVO;
import movreview.service.LogVO;
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
	
	@Value("${google-api-key}")
	private String mapApi;
	
	@Value("${naver-client-key}")
	private String client;
	
	@Value("${naver-secret-key}")
	private String secret;

	@Resource(name = "movService")
	private MovieService movService;

	@Autowired
	private TmdbService tmdbService;
	
	@Autowired
	private NaverService naverService;

	@Inject
	BCryptPasswordEncoder encoder;

	@Autowired
	private MailHandler mailHandler;

	@RequestMapping(value = "/main.do")
	public String mainPage(Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		
		logVO.setLogType("move");
		logVO.setLogDetail("main.do");
		
		movService.insertLog(logVO);

		MovieVO recentVO = new MovieVO();
		CollectionVO seriesVO = new CollectionVO();
		model.addAttribute("recentlyAdded", movService.recentlyAdded(recentVO));
		model.addAttribute("recentlyCollected", movService.recentlyCollected(seriesVO));

		String suggestData = tmdbService.movieTrends(apiKey);
		String upComing = tmdbService.upComing(apiKey);
		System.out.println("MOVIE DATA: " + suggestData);

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

			List<MovieVO> suggestVO = objectMapper.convertValue(resultsNode, new TypeReference<List<MovieVO>>() {
			});
			
			
			LOGGER.debug("결과: " + suggestVO);
			model.addAttribute("movieData", suggestVO);
			model.addAttribute("totalPages", jsonNode.get("total_pages").asInt());
			model.addAttribute("totalResults", jsonNode.get("total_results").asInt());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error processing the API response: " + e.getMessage());
		}
		
		if (upComing == null || upComing.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API");
		}
		
		try {
			JsonNode comingNode = objectMapper.readTree(upComing);
			JsonNode comingResult = comingNode.get("results");
			
			List<MovieVO> upComingVO = objectMapper.convertValue(comingResult, new TypeReference<List<MovieVO>>() {});
			model.addAttribute("upComingData", upComingVO);
		} catch(Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error processing the API response: " + e.getMessage());
		}

		return "board/main";
	}

	@RequestMapping(value = "/search.do")
	public String searchPage(HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		logVO.setLogType("move");
		logVO.setLogDetail("search.do");
		
		movService.insertLog(logVO);

		return "board/search";
	}

	@RequestMapping(value = "/movieSearch.do", method = RequestMethod.POST)
	public String movieSearch(@RequestParam("searchKeyword") String title, Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		
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

	@RequestMapping(value = "/result.do", method = RequestMethod.POST)
	public String searchResult(@RequestParam("searchKeyword") String searchKeyword, HttpServletRequest request,
			Model model) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		logVO.setLogType("search");
		logVO.setLogDetail(searchKeyword);
		movService.insertLog(logVO);
		
		logVO.setLogType("move");
		logVO.setLogDetail("result.do");
		movService.insertLog(logVO);

		MovieVO searchVO = new MovieVO();
		ActorVO actorVO = new ActorVO();
		CollectionVO collectionVO = new CollectionVO();
		MovieVO overviewVO = new MovieVO();
		TvVO tvVO = new TvVO();
		searchVO.setTitleEn(searchKeyword);
		actorVO.setActName(searchKeyword);
		collectionVO.setName(searchKeyword);
		overviewVO.setTitleEn(searchKeyword);
		tvVO.setName(searchKeyword);

		List<?> searchList = movService.searchMovie(searchVO);
		List<?> actorList = movService.searchActor(actorVO);
		List<?> collectionList = movService.searchCollection(collectionVO);
		List<?> overviewList = movService.searchOverview(overviewVO);

		model.addAttribute("searchList", searchList);
		model.addAttribute("actorList", actorList);
		model.addAttribute("collectionList", collectionList);
		model.addAttribute("overviewList", overviewList);

		List<MovieVO> newOverviewList = new ArrayList<>();

		Set<MovieVO> searchSet = new HashSet<>();
		for (Object obj : searchList) {
			if (obj instanceof MovieVO) {
				searchSet.add((MovieVO) obj);
			}
		}

		for (Object obj : overviewList) {
			if (obj instanceof MovieVO) {
				MovieVO movie = (MovieVO) obj;
				if (!searchSet.contains(movie)) {
					newOverviewList.add(movie);
				}
			}
		}

		model.addAttribute("newOverviewList", newOverviewList);

		String suggestData = tmdbService.suggestMovie(apiKey);
		String searchResult = tmdbService.searchByName(apiKey, searchKeyword);
		String tvList = tmdbService.searchTv(apiKey, searchKeyword);

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
			JsonNode tvNode = objectMapper.readTree(tvList);
			JsonNode tvResult = tvNode.get("results");

			if (resultsNode == null || !resultsNode.isArray()) {
				throw new RuntimeException("No results found in the response");
			}

			List<MovieVO> suggestVO = objectMapper.convertValue(resultsNode, new TypeReference<List<MovieVO>>() {
			});

			List<MovieVO> resultVO = objectMapper.convertValue(searchResultNode, new TypeReference<List<MovieVO>>() {
			});
			
			List<TvVO> tvResultVO = objectMapper.convertValue(tvResult, new TypeReference<List<TvVO>>() {
			});

			List<MovieVO> uniqueMovies = new ArrayList<>();
			List<TvVO> uniqueTV = new ArrayList<>();

			for (MovieVO movie : resultVO) {
				int count = movService.checkMovie(movie);
				if (count == 0) {
					uniqueMovies.add(movie);
				}
			}
			
			for (TvVO tv : tvResultVO) {
				int count = movService.checkTV(tv);
				if (count == 0) {
					uniqueTV.add(tv);
				}
			}

			model.addAttribute("suggestData", suggestVO);
			model.addAttribute("totalPages", jsonNode.get("total_pages").asInt());
			model.addAttribute("totalResults", jsonNode.get("total_results").asInt());
			model.addAttribute("resultData", uniqueMovies);
			model.addAttribute("tvResult", uniqueTV);

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error processing the API response: " + e.getMessage());
		}

		return "board/searchResult";
	}

	@RequestMapping(value = "/detail.do")
	public String movieDetail(@RequestParam("id") int id, HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		logVO.setLogType("move");
		logVO.setLogDetail("detail.do");
		movService.insertLog(logVO);
		
		logVO.setLogType("load tmdb");
		logVO.setLogDetail(Integer.toString(id));
		movService.insertLog(logVO);

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
					model.addAttribute("collectionData", collectionVO);
				}
			}

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

	@RequestMapping(value = "/localDetail.do")
	public String localDetail(@RequestParam("id") int id, HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		logVO.setLogType("move");
		logVO.setLogDetail("localDetail.do");
		movService.insertLog(logVO);
		
		logVO.setLogType("load Film Report");
		logVO.setLogDetail(Integer.toString(id));
		movService.insertLog(logVO);

		MovieVO selectVO = new MovieVO();
		CollectionVO collectVO = new CollectionVO();
		ReviewVO reviewVO = new ReviewVO();
		LikeVO likeVO = new LikeVO();

		selectVO.setMovieId(id);
		movService.selectMovie(selectVO);
		collectVO.setId(movService.selectMovie(selectVO).getCollectionId());
		reviewVO.setMovieId(id);
		likeVO.setMovieId(id);
		likeVO.setUserId(username);

		List<?> reviewList = movService.selectReview(reviewVO);
		int liked = movService.selectLike(likeVO);

		System.out.println(liked);

		model.addAttribute("selectMovie", movService.selectMovie(selectVO));
		model.addAttribute("collectionData", movService.checkCollection(collectVO));
		model.addAttribute("reviews", reviewList);
		model.addAttribute("liked", liked);

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
					} else {
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
	
	@RequestMapping(value="movieUpdate.do")
	public String movieUpdate(@RequestParam("movieId") int movieId, Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		logVO.setLogType("update movie");
		logVO.setLogDetail(Integer.toString(movieId));
		movService.insertLog(logVO);
		
		String detailData = tmdbService.movieDetail(apiKey, movieId);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(detailData);
		MovieVO detailVO = objectMapper.convertValue(jsonNode, MovieVO.class);
		movService.movieUpdate(detailVO);
		
		MovieVO seriesName = new MovieVO();
		seriesName.setMovieId(movieId);
		
		if(jsonNode.has("belongs_to_collection")) {
			JsonNode collectionNode = jsonNode.path("belongs_to_collection");
			CollectionVO collectionVO = new CollectionVO();
			collectionVO.setId(collectionNode.path("id").asInt());
			collectionVO.setName(collectionNode.path("name").asText());
			collectionVO.setPosterPath(collectionNode.path("poster_path").asText());
			collectionVO.setBackdropPath(collectionNode.path("backdrop_path").asText());

			String collectionData = tmdbService.collectionDetail(apiKey, collectionVO.getName());
			LOGGER.debug("CollectionData: " + collectionData);
			JsonNode overviewNode = objectMapper.readTree(collectionData);
			
			collectionVO.setOverview(overviewNode.findPath("overview").asText());
			movService.seriesUpdate(collectionVO);
		}
		
		return "redirect:/localDetail.do?id=" + movieId;
	}

	@RequestMapping(value = "/addReview.do", method = RequestMethod.POST)
	public String addReview(SessionStatus status, HttpServletRequest request, Model model,
			@RequestParam("movieId") int movieId, @RequestParam("userId") String userId, @RequestParam("rate") int rate,
			@RequestParam("detail") String detail) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}

		logVO.setLogType("movie review");
		logVO.setLogDetail(Integer.toString(movieId));
		String tmp = Integer.toString(rate) + "점: " + detail;
		logVO.setLogDetail2(tmp);
		movService.insertLog(logVO);

		System.out.println("UserId: " + userId);

		ReviewVO reviewVO = new ReviewVO();

		reviewVO.setMovieId(movieId);
		reviewVO.setUserId(userId);
		reviewVO.setRate(rate);
		reviewVO.setDetail(detail);

		movService.insertReview(reviewVO);

		return "redirect:/localDetail.do?id=" + movieId;
	}
	
	@RequestMapping(value = "/addSeriesReview.do", method = RequestMethod.POST)
	public String addSeriesReview(SessionStatus status, HttpServletRequest request, Model model,
			@RequestParam("seriesId") int seriesId, @RequestParam("userId") String userId, @RequestParam("rate") int rate,
			@RequestParam("detail") String detail) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}

		logVO.setLogType("series review");
		logVO.setLogDetail(Integer.toString(seriesId));
		logVO.setLogDetail2(detail);
		movService.insertLog(logVO);


		ReviewVO reviewVO = new ReviewVO();

		reviewVO.setSeriesId(seriesId);
		reviewVO.setUserId(userId);
		reviewVO.setRate(rate);
		reviewVO.setDetail(detail);

		movService.insertSeriesReview(reviewVO);

		return "redirect:/seriesDetail.do?collectionId=" + seriesId;
	}
	
	@RequestMapping(value = "/addActorReview.do", method = RequestMethod.POST)
	public String addActorReview(SessionStatus status, HttpServletRequest request, Model model,
			@RequestParam("actorId") String actorId, @RequestParam("userId") String userId, @RequestParam("detail") String detail) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}

		logVO.setLogType("actor review");
		logVO.setLogDetail(actorId);
		logVO.setLogDetail2(detail);
		movService.insertLog(logVO);
		
		ReviewVO reviewVO = new ReviewVO();

		reviewVO.setActorId(actorId);
		reviewVO.setUserId(userId);
		reviewVO.setDetail(detail);
		
		movService.insertActorReview(reviewVO);
		
		return "redirect:/actorDetail.do?actorId=" + actorId;
	}

	@RequestMapping(value = "/addMovie.do", method = RequestMethod.POST)
	public String addMovie(SessionStatus status, HttpServletRequest request, Model model, @RequestParam("id") int id,
			@RequestParam("movieId") int movieId, @RequestParam("titleKr") String titleKr,
			@RequestParam("titleEn") String titleEn, @RequestParam("genreDB") String genreDB,
			@RequestParam("releaseDate") String releaseDate, @RequestParam("overview") String overview,
			@RequestParam("backdropPath") String backdropPath, @RequestParam("posterPath") String posterPath,
			@RequestParam("collectionId") int collectionId, @RequestParam("status") String movieStatus,
			@RequestParam("tagline") String tagline, @RequestParam("seriesId") int seriesId,
			@RequestParam("seriesName") String seriesName, @RequestParam("seriesDropPath") String seriesDropPath,
			@RequestParam("seriesPosterPath") String seriesPosterPath,
			@RequestParam("seriesOverview") String seriesOverview, @RequestParam("actorIdList") String[] actorIds,
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
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}

		if (checkCollection == null) {
			movService.insertMovie(movieVO);
			logVO.setLogType("add movie");
			logVO.setLogDetail(Integer.toString(movieId));
			movService.insertLog(logVO);
			movService.insertCollection(collectionVO);
			logVO.setLogType("add series");
			logVO.setLogDetail(Integer.toString(seriesId));
			movService.insertLog(logVO);
		} else if (checkCollection != null) {
			movService.insertMovie(movieVO);
			logVO.setLogType("add movie");
			logVO.setLogDetail(Integer.toString(movieId));
			movService.insertLog(logVO);
		}

		for (int i = 0; i < actorIds.length; i++) {
			ActorVO actorVO = new ActorVO();
			actorVO.setActorId(actorIds[i]);
			actorVO.setActName(actorNames[i]);
			actorVO.setProfilePath(actorProfilePaths[i]);

			ActorVO checkActor = movService.checkActor(actorVO);

			if (checkActor == null) {
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

	@RequestMapping(value = "/seriesDetail.do")
	public String seriesDetail(@RequestParam("collectionId") int collectionId, Model model, HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		logVO.setLogType("load series");
		logVO.setLogDetail(Integer.toString(collectionId));
		movService.insertLog(logVO);

		CollectionVO collectionVO = new CollectionVO();
		collectionVO.setId(collectionId);
		MovieVO movieVO = new MovieVO();
		movieVO.setCollectionId(collectionId);
		List<?> movieList = movService.collectionMovie(movieVO);
		
		ReviewVO reviewVO = new ReviewVO();
		reviewVO.setSeriesId(collectionId);
		System.out.println("collectionId: " + reviewVO.getSeriesId());
		List<?> reviewList = movService.selectSeriesReview(reviewVO);
		model.addAttribute("reviews", reviewList);

		model.addAttribute("collectionList", movService.selectCollection(collectionVO));
		model.addAttribute("movieList", movieList);

		return "board/seriesDetail";
	}

	@RequestMapping(value = "/actorDetail.do")
	public String actorDetail(@RequestParam("actorId") int actorId, Model model, HttpServletRequest request) throws Exception {
	    model.addAttribute("googleAPI", mapApi);
	    
	    HttpSession session = request.getSession();
	    String username = (String) session.getAttribute("username");

	    model.addAttribute("username", username);
	    
	    LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		logVO.setLogType("load actor");
		logVO.setLogDetail(Integer.toString(actorId));
		movService.insertLog(logVO);

	    ActorVO actorVO = new ActorVO();
	    ActorSnsVO snsVO = new ActorSnsVO();

	    String movieCredits = tmdbService.movieCredits(apiKey, actorId);

	    actorVO.setActorId(String.valueOf(actorId));
	    snsVO.setActorId(actorId);

	    ActorVO tmp = movService.actorDetail(actorVO);
	    model.addAttribute("actorData", tmp);
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
	    
	    String news = naverService.searchNews(client, secret, tmp.getActName());
	    
	    ObjectMapper newsMapper = new ObjectMapper();
	    try {
	        JsonNode newsItem = newsMapper.readTree(news);
	        JsonNode newsNode = newsItem.path("items");
	        
	        if (newsNode != null && !newsNode.isEmpty()) {
	            List<Map<String, Object>> newsList = new ArrayList<>();
	            for (JsonNode newsN : newsNode) {
	                Map<String, Object> newsMap = newsMapper.convertValue(newsN, Map.class);
	                
	                // pubDate 문자열 변환
	                String pubDateStr = (String) newsMap.get("pubDate");
	                if (pubDateStr != null && !pubDateStr.isEmpty()) {
	                    try {
	                        // 입력 형식과 출력 형식 정의
	                        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
	                        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
	                        Date pubDate = inputFormat.parse(pubDateStr); // 문자열을 Date로 변환
	                        String formattedDate = outputFormat.format(pubDate); // 원하는 형식으로 변환
	                        newsMap.put("pubDate", formattedDate); // 변환된 날짜로 업데이트
	                    } catch (ParseException e) {
	                        e.printStackTrace(); // 예외 처리
	                        System.err.println("Failed to parse date: " + pubDateStr); // 문제를 로그에 남김
	                        newsMap.put("pubDate", "N/A"); // 변환 실패 시 N/A 표시
	                    }
	                } else {
	                    newsMap.put("pubDate", "N/A"); // 날짜가 없을 경우 표시
	                }
	                
	                newsList.add(newsMap);
	            }
	            model.addAttribute("newsData", newsList);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new RuntimeException("Error processing the API response: " + e.getMessage());
	    }
	    
	    ReviewVO reviewVO = new ReviewVO();
		reviewVO.setActorId(Integer.toString(actorId));
		System.out.println("collectionId: " + reviewVO.getSeriesId());
		List<?> reviewList = movService.selectActorReview(reviewVO);
		model.addAttribute("reviews", reviewList);

	    return "board/actorDetail";
	}

	@RequestMapping(value="/deleteComment.do")
	public String deleteComment(@RequestParam("type") String type, @RequestParam("reviewId") int reviewId, Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
	    String username = (String) session.getAttribute("username");
		
		ReviewVO commentVO = new ReviewVO();
		commentVO.setReviewId(reviewId);
		System.out.println(commentVO.getReviewId());
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}

		if(type.equals("movie")) {
			System.out.println(type);
			movService.deleteMovieComment(commentVO);
			logVO.setLogType("delete movie comment");
			logVO.setLogDetail(Integer.toString(reviewId));
			movService.insertLog(logVO);
		}
		if(type.equals("series")) {
			System.out.println(type);
			movService.deleteSeriesComment(commentVO);
			logVO.setLogType("delete series comment");
			logVO.setLogDetail(Integer.toString(reviewId));
			movService.insertLog(logVO);
		}
		if(type.contentEquals("actor")) {
			System.out.println(type);
			movService.deleteActorComment(commentVO);
			logVO.setLogType("delete actor comment");
			logVO.setLogDetail(Integer.toString(reviewId));
			movService.insertLog(logVO);
		}
		
		return "redirect:/commentDetail.do";
	}

	@RequestMapping("/registerMember.do")
	public String registerMember() throws Exception {
		LogVO logVO = new LogVO();
		logVO.setUserId("A traveler");
		logVO.setLogType("move");
		logVO.setLogDetail("registerMember.do");
		movService.insertLog(logVO);

		return "board/signup";
	}

	@RequestMapping(value = "/insertMember.do", method = RequestMethod.POST)
	public String insertMember(@RequestParam("id") String id, @RequestParam("pass") String pass,
			@RequestParam("name") String name, @RequestParam("email") String email, Model model, SessionStatus status)
			throws Exception {
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
		
		LogVO logVO = new LogVO();
		logVO.setUserId(id);
		logVO.setLogType("register");
		logVO.setLogDetail("try reigster");
		movService.insertLog(logVO);

		String htmlContent = "<html>" +
		        "<head>" +
		        "<style>" +
		        "body { font-family: Arial, sans-serif; }" +
		        ".container { max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }" +
		        ".header { background-color: #f2f2f2; padding: 10px; text-align: center; }" +
		        ".content { margin: 20px 0; }" +
		        ".footer { font-size: 12px; text-align: center; color: #777; }" +
		        "</style>" +
		        "</head>" +
		        "<body>" +
		        "<div class='container'>" +
		        "<div class='header'>" +
		        "<h2>Film Report 회원가입 인증</h2>" +
		        "</div>" +
		        "<div class='content'>" +
		        "<p>안녕하세요!</p>" +
		        "<p>아래 링크를 클릭하여 인증을 완료해 주세요:</p>" +
		        "<p><a href='" + url + "'>접속 주소</a></p>" +
		        "<p>인증번호: <strong>" + mailKey + "</strong></p>" +
		        "</div>" +
		        "<div class='footer'>" +
		        "<p>감사합니다!</p>" +
		        "<p>Film Report 팀</p>" +
		        "</div>" +
		        "</div>" +
		        "</body>" +
		        "</html>";

		try {
		    mailHandler.sendMail(email, "Film Report 회원가입 인증번호 입니다.", htmlContent, "text/html");
		    logVO.setLogType("send mail success");
			logVO.setLogDetail(mailKey);
			movService.insertLog(logVO);
		} catch (Exception e) {
		    e.printStackTrace();
		    logVO.setLogType("send mail fail");
			logVO.setLogDetail(mailKey);
			movService.insertLog(logVO);
		    return "FAIL";
		}

		status.setComplete();
		
		logVO.setLogType("register");
		logVO.setLogDetail("register success");
		movService.insertLog(logVO);

		return "redirect:/home.do";
	}

	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home() throws Exception {
		LogVO logVO = new LogVO();
		logVO.setUserId("A traveler");
		logVO.setLogType("move");
		logVO.setLogDetail("home.do");
		movService.insertLog(logVO);
		
		return "board/signin";
	}

	@RequestMapping(value = "/login_success.do", method = RequestMethod.GET)
	public String login(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String username = request.getUserPrincipal().getName();

		session.setAttribute("username", username);
		
		LogVO logVO = new LogVO();
		logVO.setUserId(username);
		logVO.setLogType("log-in success");
		logVO.setLogDetail("로그인 성공");
		
		movService.insertLog(logVO);

		return "redirect:/main.do";
	}

	@RequestMapping(value = "/login_fail.do", method = RequestMethod.GET)
	public String login_fail(Model model) throws Exception {
		model.addAttribute("errorMessage", "아이디 비밀번호를 확인하십시오.");
		

		return "forward:/home.do";
	}

	@RequestMapping(value = "/logout_After.do", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) throws IOException {
		HttpSession session = request.getSession(false);

		if (session != null) {
			System.out.println("Server is still alived!");
			session.invalidate();
		}

		return "redirect:/main.do";
	}

	@RequestMapping(value = "/verify.do")
	public String verify() throws Exception {
		return ("board/verify");
	}

	@RequestMapping(value = "/verifyTest.do")
	public String verifyTest(@RequestParam("email") String email, @RequestParam("mailKey") String mailKey, Model model)
			throws Exception {
		MemberVO memberVO = new MemberVO();

		memberVO.setEmail(email);
		memberVO.setMailKey(mailKey);

		int isTrue = movService.verify(memberVO);
		
		LogVO logVO = new LogVO();
		logVO.setUserId("A traveler");

		if (isTrue == 1) {
			int isVerify = movService.verifyCheck(email);
			System.out.println("isVerify: " + isVerify);
			if (isVerify == 1) {
				model.addAttribute("errorMessage", "이미 인증된 계정입니다.");
				logVO.setLogType("verify fail(already verified)");
				logVO.setLogDetail(email);
				movService.insertLog(logVO);
				return "forward:/verify.do";
			} else {
				movService.updateMailAuth(memberVO);
				model.addAttribute("errorMessage", "이메일 인증이 완료되었습니다. 이제 로그인이 가능합니다.");
				logVO.setLogType("verify success");
				logVO.setLogDetail(email);
				movService.insertLog(logVO);
				return "redirect:/main.do";
			}
		} else {
			model.addAttribute("errorMessage", "인증번호가 일치하지 않습니다. 다시 시도해 주세요.");
			logVO.setLogType("verify fail(not correct)");
			logVO.setLogDetail(email);
			movService.insertLog(logVO);
			return "forward:/verify.do";
		}
	}
	
    @RequestMapping("/movieList.do")
    public String movieList(Model model, HttpServletRequest request) throws Exception {
    	HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		session.setAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		logVO.setLogType("move");
		logVO.setLogDetail("movieList.do");
		movService.insertLog(logVO);
		
		MovieVO movieVO = new MovieVO();
		GenreVO genreVO = new GenreVO();
		
		List<?> movieList = movService.selectAllMovie(movieVO);
		List<?> genreList = movService.getGenre(genreVO);
		
		model.addAttribute("movieList", movieList);
		model.addAttribute("genreList", genreList);
		model.addAttribute("title", "no");

    	return "board/movieList";
    }
    
    @RequestMapping("/genre.do")
    public String genreSearch(@RequestParam("id") String id, Model model, HttpServletRequest request) throws Exception {
    	HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		session.setAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		logVO.setLogType("move");
		logVO.setLogDetail("genre.do");
		movService.insertLog(logVO);

		MovieVO movieVO = new MovieVO();
		GenreVO genreVO = new GenreVO();
		
		List<?> genreList = movService.getGenre(genreVO);
		model.addAttribute("genreList", genreList);
		
		if (id.equals("g01")) {
			String genre = "SF";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g02")) {
			String genre = "TV 영화";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g03")) {
			String genre = "가족";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g04")) {
			String genre = "공포";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g05")) {
			String genre = "다큐멘터리";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g06")) {
			String genre = "드라마";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g07")) {
			String genre = "로맨스";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g08")) {
			String genre = "모험";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g09")) {
			String genre = "미스터리";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g10")) {
			String genre = "범죄";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g11")) {
			String genre = "서부";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g12")) {
			String genre = "스릴러";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g13")) {
			String genre = "애니메이션";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g14")) {
			String genre = "액션";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g15")) {
			String genre = "역사";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g16")) {
			String genre = "음악";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g17")) {
			String genre = "전쟁";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g18")) {
			String genre = "코미디";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		if (id.equals("g19")) {
			String genre = "판타지";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
			logVO.setLogType("select genre");
			logVO.setLogDetail(genre);
			movService.insertLog(logVO);
		}
		
		List<?> movieList = movService.searchByGenre(movieVO);
		model.addAttribute("movieList", movieList);
		
		
		return "board/movieList";	
    }
    
    @RequestMapping(value="/seriesList.do")
    public String seriesList(Model model, HttpServletRequest request) throws Exception {
    	HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		session.setAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		logVO.setLogType("move");
		logVO.setLogDetail("seriesList.do");
		movService.insertLog(logVO);
		
		CollectionVO collectionVO = new CollectionVO();
		
		List<?> seriesList = movService.seriesList(collectionVO);
		model.addAttribute("seriesList", seriesList);
		
		return "board/seriesList";
    }

	@RequestMapping(value = "/mypage.do")
	public String mypage(Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		session.setAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		logVO.setLogType("move");
		logVO.setLogDetail("mypage.do");
		movService.insertLog(logVO);

		ReviewVO reviewVO = new ReviewVO();
		LikeVO likeVO = new LikeVO();
		MemberVO memberVO = new MemberVO();
		reviewVO.setUserId(username);
		likeVO.setUserId(username);
		memberVO.setId(username);

		List<?> reviewList = movService.checkReview(reviewVO);
		List<?> likeList = movService.checkLike(likeVO);
		int result = movService.checkAdmin(username);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("likeList", likeList);
		model.addAttribute("result", result);

		return "board/mypage";
	}

	@RequestMapping(value = "/addLike.do", method = RequestMethod.POST)
	public String addLike(@RequestParam("movieId") int movieId, Model model, HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		String username = request.getUserPrincipal().getName();

		session.setAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		logVO.setLogType("like");
		logVO.setLogDetail(Integer.toString(movieId));
		movService.insertLog(logVO);

		LikeVO likeVO = new LikeVO();
		likeVO.setMovieId(movieId);
		likeVO.setUserId(username);

		movService.insertLike(likeVO);

		return "redirect:/localDetail.do?id=" + movieId;
	}

	@RequestMapping(value = "/deleteLike.do", method = RequestMethod.POST)
	public String deleteLike(@RequestParam("movieId") int movieId, Model model, HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		String username = request.getUserPrincipal().getName();

		session.setAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		logVO.setLogType("unlike");
		logVO.setLogDetail(Integer.toString(movieId));
		movService.insertLog(logVO);

		LikeVO likeVO = new LikeVO();
		likeVO.setMovieId(movieId);
		likeVO.setUserId(username);

		movService.deleteLike(likeVO);

		return "redirect:/localDetail.do?id=" + movieId;
	}
	
	@RequestMapping(value = "/identify.do")
	public String identify(Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String username = request.getUserPrincipal().getName();
		session.setAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		logVO.setLogType("move");
		logVO.setLogDetail("identify.do");
		movService.insertLog(logVO);
		
		return "board/checkPass";
	}

	@RequestMapping(value = "/updateInfo.do", method = RequestMethod.POST)
	public String updateInfo(@RequestParam("id") String id, @RequestParam("pw") String pass, Model model, HttpServletRequest request) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		HttpSession session = request.getSession();
		String username = request.getUserPrincipal().getName();
		session.setAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		logVO.setLogType("move");
		logVO.setLogDetail("updateInfo.do");
		movService.insertLog(logVO);
		
		MemberVO userVO = new MemberVO();
		userVO.setId(id);
		String pw = movService.searchAcc(userVO).getPass();
		
		if(encoder.matches(pass, pw)) {
			MemberVO memberVO = new MemberVO();
			memberVO.setId(id);

			model.addAttribute("userInfo", movService.searchAcc(memberVO));

			return "board/updateMember";
		}
		
		else {
			if (pw == null) {
				model.addAttribute("errorMessage", "비밀번호를 입력해주세요.");
				logVO.setLogType("move fail");
				logVO.setLogDetail("updateInfo.do 비밀번호 미입력");
				movService.insertLog(logVO);
			}
			else {
				model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
				logVO.setLogType("move fail");
				logVO.setLogDetail("updateInfo.do 비밀번호 미일치");
				movService.insertLog(logVO);
			}
			
			return "forward:/identify.do";
		}
	}

	@RequestMapping(value = "/updatePass.do", method = RequestMethod.POST)
	public String updatePass(@RequestParam("id") String id, @RequestParam("pass") String pass, Model model,
			HttpServletRequest request) throws Exception {
		
		String username = request.getUserPrincipal().getName();
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		logVO.setLogType("move");
		logVO.setLogDetail("updatePass.do");
		movService.insertLog(logVO);
		
		String encodPW = encoder.encode(pass);
		System.out.println("encode PW: " + encodPW);

		MemberVO memberVO = new MemberVO();
		memberVO.setId(id);
		memberVO.setPass(encodPW);

		movService.updatePassword(memberVO);
		System.out.println("DB update");
		model.addAttribute("errorMessage", "비밀번호가 변경되었습니다. 다시 로그인 해주세요.");

		HttpSession session = request.getSession(false);
		logVO.setLogType("update");
		logVO.setLogDetail("PW update");
		movService.insertLog(logVO);

		if (session != null) {
			System.out.println("Server is still alived!");
			logVO.setLogType("log-out");
			logVO.setLogDetail(username);
			movService.insertLog(logVO);
			session.invalidate();
		}

		return "forward:/main.do";
	}

	@RequestMapping(value = "/updateEmail.do", method = RequestMethod.POST)
	public String updateEmail(@RequestParam("id") String id, @RequestParam("email") String email, Model model, HttpServletRequest request) throws Exception {
		MemberVO memberVO = new MemberVO();

		String mailKey = new TempKey().getKey(30, false);
		System.out.println("난수: " + mailKey);
		String url = "localhost:8080/verify.do";
		String auth = "0";
		String username = request.getUserPrincipal().getName();
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}

		memberVO.setId(id);
		memberVO.setEmail(email);
		memberVO.setMailKey(mailKey);
		memberVO.setMailAuth(auth);

		movService.updateEmail(memberVO);
		model.addAttribute("errorMessage", "이메일이 변경되었습니다. 메일 주소 인증 후 다시 로그인 해주세요.");
		logVO.setLogType("update");
		logVO.setLogDetail("PW email");
		
		String htmlContent = "<html>" +
		        "<head>" +
		        "<style>" +
		        "body { font-family: Arial, sans-serif; }" +
		        ".container { max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }" +
		        ".header { background-color: #f2f2f2; padding: 10px; text-align: center; }" +
		        ".content { margin: 20px 0; }" +
		        ".footer { font-size: 12px; text-align: center; color: #777; }" +
		        "</style>" +
		        "</head>" +
		        "<body>" +
		        "<div class='container'>" +
		        "<div class='header'>" +
		        "<h2>Film Report 회원가입 인증</h2>" +
		        "</div>" +
		        "<div class='content'>" +
		        "<p>안녕하세요!</p>" +
		        "<p>아래 링크를 클릭하여 인증을 완료해 주세요:</p>" +
		        "<p><a href='" + url + "'>접속 주소</a></p>" +
		        "<p>인증번호: <strong>" + mailKey + "</strong></p>" +
		        "</div>" +
		        "<div class='footer'>" +
		        "<p>감사합니다!</p>" +
		        "<p>Film Report 팀</p>" +
		        "</div>" +
		        "</div>" +
		        "</body>" +
		        "</html>";

		try {
		    mailHandler.sendMail(email, "Film Report 회원가입 인증번호 입니다.", htmlContent, "text/html");
		    logVO.setLogType("send mail success");
			logVO.setLogDetail(mailKey);
			movService.insertLog(logVO);
		    
		} catch (Exception e) {
		    e.printStackTrace();
		    logVO.setLogType("send mail fail");
			logVO.setLogDetail(mailKey);
			movService.insertLog(logVO);
		    return "FAIL";
		}

		HttpSession session = request.getSession(false);

		if (session != null) {
			System.out.println("Server is still alived!");
			logVO.setLogType("log-out");
			logVO.setLogDetail(username);
			movService.insertLog(logVO);
			session.invalidate();
		}

		return "forward:/main.do";

	}
	
	@RequestMapping(value = "/commentDetail.do")
	public String commentDetail(Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		session.setAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		logVO.setLogType("move");
		logVO.setLogDetail("commentDetail.do");
		movService.insertLog(logVO);
		
		ReviewVO reviewVO = new ReviewVO();
		reviewVO.setUserId(username);

		List<?> reviewList = movService.selectAllReview(reviewVO);
		model.addAttribute("reviewList", reviewList);
		
		return "board/commentDetail";
	}
	
	@RequestMapping(value = "/commentCategory.do")
	public String commentCategory(@RequestParam("selectedValue") String selectedValue, Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		session.setAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		logVO.setLogType("move");
		logVO.setLogDetail("commentCategory.do");
		movService.insertLog(logVO);
		
		ReviewVO reviewVO = new ReviewVO();
		ReviewVO seriesReviewVO = new ReviewVO();
		ReviewVO actorReviewVO = new ReviewVO();
		
		reviewVO.setUserId(username);
		seriesReviewVO.setUserId(username);
		actorReviewVO.setUserId(username);
		
		List<?> reviewList = movService.selectAllReview(reviewVO);
		List<?> seriesList = movService.selectAllSeriesReview(seriesReviewVO);
		List<?> actorList = movService.selectAllActorReview(actorReviewVO);
		
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("seriesList", seriesList);
		model.addAttribute("actorList", actorList);
		
		return "board/commentDetail";
	}
	
	@RequestMapping(value = "/favoritesDetail.do")
	public String favoritesDetail(Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		session.setAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		logVO.setLogType("move");
		logVO.setLogDetail("favoritesDetail.do");
		movService.insertLog(logVO);
		
		LikeVO likeVO = new LikeVO();
		
		likeVO.setUserId(username);
		
		List<?> reviewList = movService.selectAllLike(likeVO);
		
		model.addAttribute("movieList", reviewList);
		
		return "board/favoriteDetail";
	}
	
	@RequestMapping(value = "/idCheck.do", method = RequestMethod.POST)
    @ResponseBody // JSON으로 응답
    public Map<String, Integer> idCheck(@RequestParam("id") String id) throws Exception {
        int cnt = movService.checkId(id);
        System.out.println("cnt: " + cnt);
        
        Map<String, Integer> response = new HashMap<>();
        response.put("cnt", cnt);
        return response;
    }
	
	@RequestMapping(value = "/movieCheck.do", method = RequestMethod.POST)
    @ResponseBody // JSON으로 응답
    public Map<String, Integer> movieCheck(@RequestParam("id") int id) throws Exception {
		MovieVO movieVO = new MovieVO();
		movieVO.setMovieId(id);
		
        int cnt = movService.checkMovie(movieVO);
        System.out.println("cnt: " + cnt);
        
        Map<String, Integer> response = new HashMap<>();
        response.put("cnt", cnt); 
        return response;
    }

	@RequestMapping(value = "/emailCheck.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> emailCheck(@RequestParam("email") String email) throws Exception {
        int cnt = movService.checkEmail(email);
        System.out.println("cnt: " + cnt);
        
        Map<String, Integer> response = new HashMap<>();
        response.put("cnt", cnt);
        return response;
    }
	
	@RequestMapping(value = "/deleteAcc.do", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> deleteAcc(@RequestParam("id") String id) throws Exception {
		int result = movService.deleteAcc(id);
		System.out.println("result: " + result);
		
		Map<String, Integer> response = new HashMap<>();
		response.put("result", result);

		return response;
	}
	
	@RequestMapping("/findId.do")
	public String findId() throws Exception {
		
		return "board/login";
	}
	
	@RequestMapping(value="findIdResult.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> findIdResult(@RequestParam("name") String name, @RequestParam("email") String email) throws Exception {
		System.out.println("name: " + name + "email: " + email);
		MemberVO memberVO = new MemberVO();
		memberVO.setName(name);
		memberVO.setEmail(email);
		
		String id = movService.findId(memberVO);
		System.out.println("id: " + id);
		
		Map<String, String> response = new HashMap<>();
		response.put("id", id);
		return response;
	}
	
	@RequestMapping("/findPass.do")
	public String findPass() throws Exception {
		
		return "board/findPass";
	}
	
	@RequestMapping(value="findPassResult.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> findPassResult(@RequestParam("id") String id, @RequestParam("email") String email) throws Exception {
		System.out.println("id: " + id + "email: " + email);
		MemberVO memberVO = new MemberVO();
		memberVO.setId(id);
		memberVO.setEmail(email);
		
		int cnt = movService.findPass(memberVO);
		
		if (cnt != 0) {
			String mailKey = new TempKey().getKey(30, false);
			String url = "localhost:8080/home.do";
			
			String htmlContent = "<html>" +
			        "<head>" +
			        "<style>" +
			        "body { font-family: Arial, sans-serif; }" +
			        ".container { max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }" +
			        ".header { background-color: #f2f2f2; padding: 10px; text-align: center; }" +
			        ".content { margin: 20px 0; }" +
			        ".footer { font-size: 12px; text-align: center; color: #777; }" +
			        "</style>" +
			        "</head>" +
			        "<body>" +
			        "<div class='container'>" +
			        "<div class='header'>" +
			        "<h2>Film Report 임시 비밀번호</h2>" +
			        "</div>" +
			        "<div class='content'>" +
			        "<p>안녕하세요!</p>" +
			        "<p>아래 링크를 클릭하여 로그인 후 비밀번호를 변경해주세요:</p>" +
			        "<p><a href='" + url + "'>접속 주소</a></p>" +
			        "<p>임시 비밀번호: <strong>" + mailKey + "</strong></p>" +
			        "</div>" +
			        "<div class='footer'>" +
			        "<p>감사합니다!</p>" +
			        "<p>Film Report 팀</p>" +
			        "</div>" +
			        "</div>" +
			        "</body>" +
			        "</html>";

			mailHandler.sendMail(email, "Film Report 임시 비밀번호 입니다.", htmlContent, "text/html");
			String encodPW = encoder.encode(mailKey);
			memberVO.setPass(encodPW);
			movService.updatePassword(memberVO);
		}
		
		Map<String, Integer> response = new HashMap<>();
		response.put("cnt", cnt);
		return response;
	}

	@PostMapping("/updateProfile.do")
    public ResponseEntity<?> updateProfile(@RequestParam("id") String userId,
                                           @RequestParam("file") MultipartFile file) {
        MemberVO member = new MemberVO();
        member.setId(userId);

        try {
            String profilePath = uploadProfileImage(member, file);
            return ResponseEntity.ok().body("{\"success\": true, \"profilePath\": \"" + profilePath + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }

	public String uploadProfileImage(MemberVO member, MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어 있습니다.");
        }

        String fileName = member.getId() + "_Profile." + getFileExtension(file.getOriginalFilename());
        String root = "C:\\Users\\admin\\Desktop\\upload\\profile";
        File uploadFile = new File(root, fileName);

        try {
            file.transferTo(uploadFile);
            member.setProfile(uploadFile.getAbsolutePath());
            movService.updateProfile(member);
            return uploadFile.getAbsolutePath();
        } catch (IOException e) {
            LOGGER.error("파일 업로드 실패", e);
            throw new Exception("파일 업로드 실패");
        }
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
    
    @RequestMapping("/adminMain.do")
    public String adminMain(Model model) throws Exception {
    	LogChartVO chartVO = movService.logCnt();
    	List<?> loginCnt = movService.loginCnt();
    	List<?> mainLogTable = movService.mainLogTable();
    	
    	ObjectMapper om = new ObjectMapper();

    	String userList = om.writeValueAsString(loginCnt);
    	System.out.println(userList);
    	
    	model.addAttribute("chartData", chartVO);
    	model.addAttribute("loginCnt", loginCnt);
    	model.addAttribute("log", mainLogTable);
    	
    	return "board/admin";
    }
    
    @RequestMapping("/adminAccTable.do")
    public String adminAccTable(Model model) throws Exception {
    	MemberVO member = new MemberVO();
    	List<?> memberList = movService.searchAllUsers(member);
    	
    	model.addAttribute("memberList", memberList);
    	
    	return "board/accountTable";
    }
    
    @RequestMapping("/adminAccList.do")
    public String adminAccList(Model model) throws Exception {
    	MemberVO member = new MemberVO();
    	List<?> memberList = movService.searchAllAdmin(member);
    	
    	model.addAttribute("memberList", memberList);
    	
    	return "board/adminList";
    }
    
    @RequestMapping("/adminUserDetail.do")
    public String adminUserDetail(@RequestParam("id") String id, Model model) throws Exception {
    	MemberVO member = new MemberVO();
    	List<?> activityCnt = movService.userActivityCnt(id);
    	List<?> loadCnt = movService.userLoadCnt(id);
    	member.setId(id);
    	
    	ObjectMapper om = new ObjectMapper();

    	String userList = om.writeValueAsString(loadCnt);
    	System.out.println(userList);
    	
    	model.addAttribute("userData", movService.searchUserDetail(member));
    	model.addAttribute("logList", movService.searchAccLog(id));
    	model.addAttribute("activityCnt", activityCnt);
    	model.addAttribute("loadCnt", loadCnt);
    	
    	return "board/userDetail";
    }
    
    @RequestMapping("/adminLogGuide.do")
    public String adminLogGuide(Model model) throws Exception {
    	
    	model.addAttribute("logCategory", movService.logCategory());
    	
    	return "board/logGuide";
    }
    
    @RequestMapping("/adminMovie.do")
    public String adminMovie(Model model) throws Exception {
    	MovieVO movieVO = new MovieVO();
    	
    	model.addAttribute("movieList", movService.selectAllMovie(movieVO));
    	
    	return "board/adminMovie";
    }
    
    @RequestMapping("/adminLogList.do")
    public String adminLogList(Model model) throws Exception {
    	List<?> logList = movService.selectLog();
    	
    	model.addAttribute("logList", logList);
    	
    	return "board/logList";
    }
    
    @RequestMapping("/adminSearchCard.do")
    public String adminSearchCard(Model model) throws Exception {
    	LogChartVO chartVO = movService.logCnt();
    	LogChartVO genreVO = movService.genreCnt();
    	List<?> searchLogTable = movService.searchLogTable();
    	
    	model.addAttribute("chartData", chartVO);
    	model.addAttribute("genreData", genreVO);
    	model.addAttribute("log", searchLogTable);
    	
    	return "board/adminSearchCard";
    }
    
    @RequestMapping("/adminDetailCard.do")
    public String adminDetailCard(Model model) throws Exception {
    	LogChartVO chartVO = movService.contentsCnt();
    	LogChartVO callChartVO = movService.contentsCall();
    	
    	model.addAttribute("chartData", chartVO);
    	model.addAttribute("callData", callChartVO);
    	
    	return "board/adminDetailCard";
    }
    
    @RequestMapping("/adminReviewCard.do")
    public String adminReviewCard(Model model) throws Exception {
    	LogChartVO chartVO = movService.reviewCnt();
    	List<?> reviewCnt = movService.reviewBarChart();
    	List<?> reviewLogTable = movService.reviewLogTable();
    	
    	ObjectMapper om = new ObjectMapper();

    	String userList = om.writeValueAsString(reviewCnt);
    	System.out.println(userList);
    	
    	model.addAttribute("chartData", chartVO);
    	model.addAttribute("reviewCnt", reviewCnt);
    	model.addAttribute("log", reviewLogTable);
    	
    	return "board/adminReviewCard";
    }
    
    @RequestMapping("/adminInsertCard.do")
    public String adminInsertCard(Model model) throws Exception {
    	LogChartVO chartVO = movService.logCnt();
    	List<?> insertLogTable = movService.insertLogTable();
    	
    	model.addAttribute("chartData", chartVO);
    	model.addAttribute("log", insertLogTable);
    	
    	return "board/adminInsertCard";
    }
    
    @RequestMapping("/adminLogPop.do")
    public String adminLogPop(@RequestParam("logId") int logId, Model model) throws Exception {
    	LogVO logVO = movService.logDetail(logId);
    	
    	String userId = logVO.getUserId();
    	String logType = logVO.getLogType();
    	String logDetail = logVO.getLogDetail();
    	
    	String tmp = logVO.getReportTime();
    	String[] arr = tmp.split("[.]");
    	String eventTime = arr[0];

    	LogVO logDescription = movService.logByName(logType);
    	int loggId = logDescription.getId();
    	System.out.println(loggId);
    	
    	if(loggId == 4 || loggId == 5 || loggId == 6 || loggId == 7 || loggId == 8 || loggId == 9 || loggId == 19 || loggId == 20 || loggId == 21 || loggId == 22 || loggId == 23 || loggId == 24 || loggId == 25 || loggId == 28 || loggId == 29 || loggId == 32 || loggId == 33) {
    		model.addAttribute("log", logVO);
    		model.addAttribute("logDescription", logDescription);
    		String[] logIds = {"4", "5", "6", "7", "8", "9", "19", "20", "21", "22", "23", "24", "25", "28", "29", "32", "33"};
    		model.addAttribute("logIds", logIds);
    	}
    	if(loggId == 10 || loggId == 11 || loggId == 12) {
    		MovieVO movieVO = new MovieVO();
    		movieVO.setMovieId(Integer.valueOf(logDetail));
    		
    		if(loggId == 10 || loggId == 12) {
    			String detailData = tmdbService.movieDetail(apiKey, Integer.valueOf(logDetail));
    			ObjectMapper objectMapper = new ObjectMapper();
    			JsonNode jsonNode = objectMapper.readTree(detailData);
    			MovieVO detailVO = objectMapper.convertValue(jsonNode, MovieVO.class);
    			model.addAttribute("movieDetail", detailVO);
    		}
    		else {
    			model.addAttribute("movieDetail", movService.selectMovie(movieVO));
    		}
    		
    		model.addAttribute("log", logVO);
    		model.addAttribute("logDescription", logDescription);
    	}
    	if(loggId == 13 || loggId == 14 || loggId == 15) {
    		model.addAttribute("log", logVO);
    		model.addAttribute("logDescription", logDescription);
    		
    		ReviewVO reviewVO = new ReviewVO();
    		
			reviewVO.setUserId(userId);
			reviewVO.setSubmitTime(eventTime);
    		
    		if(loggId == 13) {
    			MovieVO movieVO = new MovieVO();
    			
        		movieVO.setMovieId(Integer.valueOf(logDetail));
        		reviewVO.setMovieId(Integer.valueOf(logDetail));
        		
        		model.addAttribute("movieDetail", movService.selectMovie(movieVO));
        		model.addAttribute("reviewDetail", movService.findReviewByTime(reviewVO));
    		}
    		if(loggId == 14) {
    			CollectionVO seriesVO = new CollectionVO();
    			
    			seriesVO.setId(Integer.valueOf(logDetail));
    			System.out.println(seriesVO.getId());
    			reviewVO.setSeriesId(Integer.valueOf(logDetail));
    			
    			model.addAttribute("seriesDetail", movService.selectCollection(seriesVO));
    			model.addAttribute("reviewDetail", movService.seriesReviewByTime(reviewVO));
    		}
    		if(loggId == 15) {
    			ActorVO actorVO = new ActorVO();
    			
    			actorVO.setActorId(logDetail);
    			reviewVO.setActorId(logDetail);
    			
    			model.addAttribute("actorDetail", movService.actorDetail(actorVO));
    			model.addAttribute("reviewDetail", movService.actorReviewByTime(reviewVO));
    		}
    	}
    	if(loggId == 16 || loggId == 17) {
    		model.addAttribute("log", logVO);
    		model.addAttribute("logDescription", logDescription);
    		
    		if(loggId == 16) {
    			MovieVO movieVO = new MovieVO();
    			movieVO.setMovieId(Integer.valueOf(logDetail));
    			
    			model.addAttribute("movieDetail", movService.selectMovie(movieVO));
    		}
    		if(loggId == 17) {
    			CollectionVO seriesVO = new CollectionVO();
    			seriesVO.setId(Integer.valueOf(logDetail));
    			
    			model.addAttribute("seriesDetail", movService.selectCollection(seriesVO));
    		}
    	}
    	if(loggId == 30 || loggId == 31) {
    		model.addAttribute("log", logVO);
    		model.addAttribute("logDescription", logDescription);
    		
    		MovieVO movieVO = new MovieVO();
			movieVO.setMovieId(Integer.valueOf(logDetail));
			
			model.addAttribute("movieDetail", movService.selectMovie(movieVO));
    	}
    	if(loggId == 34) {
    		CollectionVO seriesVO = new CollectionVO();
    		seriesVO.setId(Integer.valueOf(logDetail));
    		
    		seriesVO = movService.selectCollection(seriesVO);
    		
			model.addAttribute("seriesDetail", seriesVO);
    		
    		model.addAttribute("log", logVO);
    		model.addAttribute("logDescription", logDescription);
    	}
    	if(loggId == 35) {
    		ActorVO actorVO = new ActorVO();
    		actorVO.setActorId(logDetail);
    		
    		actorVO =  movService.actorDetail(actorVO);
    		
			model.addAttribute("actorDetail", actorVO);
    		
    		model.addAttribute("log", logVO);
    		model.addAttribute("logDescription", logDescription);
    	}
    	
    	return "board/logPop";
    }
    
    @RequestMapping(value="/adminMoviePop.do")
    public String moviePopup(@RequestParam("id") int id, Model model) throws Exception {
    	MovieVO movieVO = new MovieVO();
    	CollectionVO collectionVO = new CollectionVO();
    	ReviewVO reviewVO = new ReviewVO();
    	
    	movieVO.setMovieId(id);
    	
    	MovieVO movieDetail = movService.selectMovie(movieVO);
    	
    	if (movieDetail.getCollectionId() != 0) {
    		collectionVO.setId(movieDetail.getCollectionId());
    		CollectionVO seriesDetail = movService.checkCollection(collectionVO);
    		model.addAttribute("seriesDetail", seriesDetail);
    	}
    	
    	reviewVO.setMovieId(id);
    	List<?> reviewDetail = movService.selectReview(reviewVO);
    	
    	model.addAttribute("movieDetail", movieDetail);
    	model.addAttribute("reviewDetail", reviewDetail);
    	
    	return "board/moviePopup";
    }
    
    @RequestMapping(value="/adminGrant.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> adminGrant(@RequestParam("id") String id) throws Exception {

		int result = movService.upgradeToAdmin(id);
		System.out.println("result: " + result);
		
		Map<String, Integer> response = new HashMap<>();
		response.put("result", result);
		return response;
	}
    
    @RequestMapping(value="/adminRevoke.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> adminRevoke(@RequestParam("id") String id) throws Exception {

		int result = movService.downToUser(id);
		System.out.println("result: " + result);
		
		Map<String, Integer> response = new HashMap<>();
		response.put("result", result);
		return response;
	}
    
    
    // TV 프로그램
    @RequestMapping("/tvMain.do")
    public String tvMain(Model model, HttpServletRequest request) throws Exception {
    	HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		
		logVO.setLogType("move");
		logVO.setLogDetail("tvMain.do");
		movService.insertLog(logVO);
		
		String trendingData = tmdbService.tvTrending(apiKey);
		String newestAniKR = tmdbService.newestAniKR(apiKey);
		String newestAniJP = tmdbService.newestAniJP(apiKey);
		String newestAniUS = tmdbService.newestAniUS(apiKey);
		String popularRealityKR = tmdbService.popularRealityKR(apiKey);
		String popularRealityJP = tmdbService.popularRealityJP(apiKey);
		String popularRealityUS = tmdbService.popularRealityUS(apiKey);
		String popularDramaKR = tmdbService.popularDramaKR(apiKey);
		String popularDramaJP = tmdbService.popularDramaJP(apiKey);
		String popularDramaUS = tmdbService.popularDramaUS(apiKey);
		
		if (trendingData == null || trendingData.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API: trendingData");
		}
		if (newestAniKR == null || newestAniKR.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API: newestAniKR");
		}
		if (newestAniJP == null || newestAniJP.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API: newestAniJP");
		}
		if (newestAniUS == null || newestAniUS.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API: newestAniUS");
		}
		if (popularRealityKR == null || popularRealityKR.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API: popularReality");
		}
		if (popularRealityJP == null || popularRealityJP.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API: popularReality");
		}
		if (popularRealityUS == null || popularRealityUS.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API: popularReality");
		}
		if (popularDramaKR == null || popularDramaKR.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API: popularDramaKR");
		}
		if (popularDramaJP == null || popularDramaJP.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API: popularDramaJP");
		}
		if (popularDramaUS == null || popularDramaUS.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API: popularDramaUS");
		}

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode KRNode = objectMapper.readTree(popularRealityKR);
			JsonNode reKRNode = KRNode.get("results");
			
			JsonNode JPNode = objectMapper.readTree(popularRealityJP);
			JsonNode reJPNode = JPNode.get("results");
			
			JsonNode USNode = objectMapper.readTree(popularRealityUS);
			JsonNode reUSNode = USNode.get("results");
			
			if (reKRNode == null || !reKRNode.isArray()) {
				throw new RuntimeException("No results found in the response: reKRNode");
			}
			if (reJPNode == null || !reJPNode.isArray()) {
				throw new RuntimeException("No results found in the response: reJPNode");
			}
			if (reUSNode == null || !reUSNode.isArray()) {
				throw new RuntimeException("No results found in the response: reUSNode");
			}
			
			List<TvVO> reKRVO = objectMapper.convertValue(reKRNode, new TypeReference<List<TvVO>>() {
			});
			List<TvVO> reJPVO = objectMapper.convertValue(reJPNode, new TypeReference<List<TvVO>>() {
			});
			List<TvVO> reUSVO = objectMapper.convertValue(reUSNode, new TypeReference<List<TvVO>>() {
			});
			
			model.addAttribute("reKR", reKRVO);
			model.addAttribute("reJP", reJPVO);
			model.addAttribute("reUS", reUSVO);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error processing the API response: " + e.getMessage());
		}
		
		try {
			JsonNode KRNode = objectMapper.readTree(popularDramaKR);
			JsonNode dramaKRNode = KRNode.get("results");
			
			JsonNode JPNode = objectMapper.readTree(popularDramaJP);
			JsonNode dramaJPNode = JPNode.get("results");
			
			JsonNode USNode = objectMapper.readTree(popularDramaUS);
			JsonNode dramaUSNode = USNode.get("results");
			
			if (dramaKRNode == null || !dramaKRNode.isArray()) {
				throw new RuntimeException("No results found in the response: dramaKRNode");
			}
			if (dramaJPNode == null || !dramaJPNode.isArray()) {
				throw new RuntimeException("No results found in the response: dramaJPNode");
			}
			if (dramaUSNode == null || !dramaUSNode.isArray()) {
				throw new RuntimeException("No results found in the response: dramaUSNode");
			}
			
			List<TvVO> dramaKRVO = objectMapper.convertValue(dramaKRNode, new TypeReference<List<TvVO>>() {
			});
			List<TvVO> dramaJPVO = objectMapper.convertValue(dramaJPNode, new TypeReference<List<TvVO>>() {
			});
			List<TvVO> dramaUSVO = objectMapper.convertValue(dramaUSNode, new TypeReference<List<TvVO>>() {
			});
			
			model.addAttribute("dramaKR", dramaKRVO);
			model.addAttribute("dramaJP", dramaJPVO);
			model.addAttribute("dramaUS", dramaUSVO);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error processing the API response: " + e.getMessage());
		}
		
		
		try {
			JsonNode jsonNode = objectMapper.readTree(trendingData);
			JsonNode trendingNode = jsonNode.get("results");
			
			JsonNode KRNode = objectMapper.readTree(newestAniKR);
			JsonNode aniKRNode = KRNode.get("results");
			
			JsonNode JPNode = objectMapper.readTree(newestAniJP);
			JsonNode aniJPNode = JPNode.get("results");
			
			JsonNode USNode = objectMapper.readTree(newestAniUS);
			JsonNode aniUSNode = USNode.get("results");
			
			if (trendingNode == null || !trendingNode.isArray()) {
				throw new RuntimeException("No results found in the response: trendingData");
			}
			if (aniKRNode == null || !aniKRNode.isArray()) {
				throw new RuntimeException("No results found in the response: newestAniKR");
			}
			if (aniJPNode == null || !aniJPNode.isArray()) {
				throw new RuntimeException("No results found in the response: newestAniJP");
			}
			if (aniUSNode == null || !aniUSNode.isArray()) {
				throw new RuntimeException("No results found in the response: newestAniUS");
			}

			List<TvVO> trendingVO = objectMapper.convertValue(trendingNode, new TypeReference<List<TvVO>>() {
			});
			List<TvVO> aniKRVO = objectMapper.convertValue(aniKRNode, new TypeReference<List<TvVO>>() {
			});
			List<TvVO> aniJPVO = objectMapper.convertValue(aniJPNode, new TypeReference<List<TvVO>>() {
			});
			List<TvVO> aniUSVO = objectMapper.convertValue(aniUSNode, new TypeReference<List<TvVO>>() {
			});
			
			model.addAttribute("trendingData", trendingVO);
			model.addAttribute("aniKR", aniKRVO);
			model.addAttribute("aniJP", aniJPVO);
			model.addAttribute("aniUS", aniUSVO);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error processing the API response: " + e.getMessage());
		}
    	
    	
    	return "board/tvShowMain";
    }
    
    @RequestMapping(value = "/detailTv.do")
	public String tvDetail(@RequestParam("id") int id, HttpServletRequest request, Model model) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		else {
			logVO.setUserId("A traveler");
		}
		logVO.setLogType("move");
		logVO.setLogDetail("detailTv.do");
		movService.insertLog(logVO);
		
		logVO.setLogType("load tmdb");
		logVO.setLogDetail(Integer.toString(id));
		movService.insertLog(logVO);

		String detailData = tmdbService.detailTv(apiKey, id);
		String videoData = tmdbService.getTvTrailer(apiKey, id);
		String recoData = tmdbService.TvRecommendation(apiKey, id);
		String seasonData = tmdbService.getSeasonInfo(apiKey, id);
		System.out.println("DetailData: " + detailData);
		System.out.println("SeasonData: " + seasonData);

		if (detailData == null || detailData.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API");
		}
		if (seasonData == null || seasonData.isEmpty()) {
			throw new RuntimeException("Received null or empty response from the API: seasonData");
		}

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			JsonNode jsonNode = objectMapper.readTree(detailData);
			JsonNode videoNode = objectMapper.readTree(videoData);
			JsonNode vidNode = videoNode.path("results");
			JsonNode recoNode = objectMapper.readTree(recoData);
			JsonNode recNode = recoNode.findPath("results");
			JsonNode seasonNode = objectMapper.readTree(seasonData);
			JsonNode seasonInfo = seasonNode.findPath("seasons");

			TvVO detailVO = objectMapper.convertValue(jsonNode, TvVO.class);

			model.addAttribute("detailData", detailVO);

			List<String> genres = new ArrayList<>();
			for (JsonNode genreNode : jsonNode.path("genres")) {
				genres.add(genreNode.path("name").asText());
			}
			detailVO.setGenre(genres);
			
			List<Map<String, Object>> recList = new ArrayList<>();
			for (JsonNode actor : recNode) {
				Map<String, Object> actorMap = objectMapper.convertValue(actor, Map.class);
				recList.add(actorMap);
			}
			System.out.println(recList);
			model.addAttribute("recommendData", recList);
			
			List<Map<String, Object>> seasonList = new ArrayList<>();
			for (JsonNode actor : seasonInfo) {
				Map<String, Object> actorMap = objectMapper.convertValue(actor, Map.class);
				seasonList.add(actorMap);
			}
			System.out.println("SeasonList: " + seasonList);
			model.addAttribute("seasonList", seasonList);

			int seriesId = jsonNode.path("id").asInt();
			String actorData = tmdbService.searchTvActor(apiKey, seriesId);

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

		return "board/tvDetail";
	}
    
    @RequestMapping(value = "/addTvSeries.do", method = RequestMethod.POST)
    public String addTvSeries(SessionStatus status, HttpServletRequest request, Model model
    		, @RequestParam("programId") int programId
    		, @RequestParam("programName") String programName
    		, @RequestParam("programOriginalName") String programOriginalName
    		, @RequestParam("programOverview") String programOverview
    		, @RequestParam("programBackdropPath") String programBackdropPath
    		, @RequestParam("programPosterPath") String programPosterPath
    		, @RequestParam("programAdult") boolean programAdult
    		, @RequestParam("programGenre") String programGenre
    		, @RequestParam("programFirstAirDate") String programFirstAirDate
    		, @RequestParam("programOriginCountry") String[] programOriginCountry
    		, @RequestParam("seasonId") String[] seasonIds
    		, @RequestParam("seriesId") String[] seriesId
    		, @RequestParam("seasonName") String[] seasonNameList
    		, @RequestParam("seasonAirDate") String[] seasonAirDateList
    		, @RequestParam("seasonEpisodeCount") String[] seasonEpisodeCountList
    		, @RequestParam("seasonPosterPath") String[] seasonPosterPathList) throws Exception {
    	
    	HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		model.addAttribute("username", username);
		
		LogVO logVO = new LogVO();
		if (username != null) {
			logVO.setUserId(username);
		}
		logVO.setLogType("add TV Series");
		logVO.setLogDetail(Integer.toString(programId));
		movService.insertLog(logVO);
		
		TvVO tvVO = new TvVO();
		
		tvVO.setId(programId);
		tvVO.setName(programName);
		tvVO.setOriginalName(programOriginalName);
		tvVO.setOverview(programOverview);
		tvVO.setBackdropPath(programBackdropPath);
		tvVO.setPosterPath(programPosterPath);
		tvVO.setAdult(programAdult);
		tvVO.setGenreDB(programGenre);
		tvVO.setFirstAirDate(programFirstAirDate);
		tvVO.setOriginCountryDB(programOriginCountry[0]);
		
		movService.insertTvSeries(tvVO);
		
		for (int i = 0; i < seasonIds.length; i++) {
			TvSeasonVO seasonVO = new TvSeasonVO();
			seasonVO.setId(Integer.parseInt(seasonIds[i]));
			seasonVO.setSeriesId(programId);
			seasonVO.setName(seasonNameList[i]);
			seasonVO.setAirDate(seasonAirDateList[i]);
			seasonVO.setEpisodeCount(Integer.parseInt(seasonEpisodeCountList[i]));
			seasonVO.setPosterPath(seasonPosterPathList[i]);
			
			logVO.setLogType("add Season");
			logVO.setLogDetail(programId + "(" + seasonIds[i] + ")");
			movService.insertLog(logVO);
			
			movService.insertTvSeason(seasonVO);
		}
    	
    	return "fuck";
    }
    
    @RequestMapping(value = "/tvSeriesCheck.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> tvSeriesCheck(@RequestParam("id") int id) throws Exception {
		
        int cnt = movService.tvSeriesCheck(id);
        System.out.println("cnt: " + cnt);
        
        Map<String, Integer> response = new HashMap<>();
        response.put("cnt", cnt); 
        return response;
    }

}
