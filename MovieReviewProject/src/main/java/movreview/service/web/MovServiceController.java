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
import movreview.service.NaverService;
import movreview.service.MovieVO;
import movreview.service.ReviewVO;
import movreview.service.CollectionVO;
import movreview.service.GenreVO;
import movreview.service.LikeVO;
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
		System.out.println("UserName: " + username);

		model.addAttribute("username", username);

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

		return "board/search";
	}

	@RequestMapping(value = "/movieSearch.do", method = RequestMethod.POST)
	public String movieSearch(@RequestParam("searchKeyword") String title, Model model) throws Exception {
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

		MovieVO searchVO = new MovieVO();
		ActorVO actorVO = new ActorVO();
		CollectionVO collectionVO = new CollectionVO();
		MovieVO overviewVO = new MovieVO();
		searchVO.setTitleEn(searchKeyword);
		actorVO.setActName(searchKeyword);
		collectionVO.setName(searchKeyword);
		overviewVO.setTitleEn(searchKeyword);

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

			List<MovieVO> suggestVO = objectMapper.convertValue(resultsNode, new TypeReference<List<MovieVO>>() {
			});

			List<MovieVO> resultVO = objectMapper.convertValue(searchResultNode, new TypeReference<List<MovieVO>>() {
			});

			List<MovieVO> uniqueMovies = new ArrayList<>();

			for (MovieVO movie : resultVO) {
				int count = movService.checkMovie(movie);
				if (count == 0) {
					uniqueMovies.add(movie);
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

	@RequestMapping(value = "/detail.do")
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
	public String movieUpdate(@RequestParam("movieId") int movieId, Model model) throws Exception {
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

		if (checkCollection == null) {
			movService.insertMovie(movieVO);
			movService.insertCollection(collectionVO);
		} else if (checkCollection != null) {
			movService.insertMovie(movieVO);
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
	public String deleteComment(@RequestParam("type") String type, @RequestParam("reviewId") int reviewId, Model model) throws Exception {
		ReviewVO commentVO = new ReviewVO();
		commentVO.setReviewId(reviewId);
		System.out.println(commentVO.getReviewId());
		
		if(type.equals("movie")) {
			System.out.println(type);
			movService.deleteMovieComment(commentVO);
		}
		if(type.equals("series")) {
			System.out.println(type);
			movService.deleteSeriesComment(commentVO);
		}
		if(type.contentEquals("actor")) {
			System.out.println(type);
			movService.deleteActorComment(commentVO);
		}
		
		return "redirect:/commentDetail.do";
	}

	@RequestMapping("/registerMember.do")
	public String registerMember() throws Exception {

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
		} catch (Exception e) {
		    e.printStackTrace();
		    return "FAIL";
		}

		status.setComplete();

		return "redirect:/home.do";
	}

	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home() throws Exception {
		return "board/signin";
	}

	@RequestMapping(value = "/login_success.do", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String username = request.getUserPrincipal().getName();

		session.setAttribute("username", username);

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

		if (isTrue == 1) {
			int isVerify = movService.verifyCheck(email);
			System.out.println("isVerify: " + isVerify);
			if (isVerify == 1) {
				model.addAttribute("errorMessage", "이미 인증된 계정입니다.");
				return "forward:/verify.do";
			} else {
				movService.updateMailAuth(memberVO);
				model.addAttribute("errorMessage", "이메일 인증이 완료되었습니다. 이제 로그인이 가능합니다.");
				return "redirect:/main.do";
			}
		} else {
			model.addAttribute("errorMessage", "인증번호가 일치하지 않습니다. 다시 시도해 주세요.");
			return "forward:/verify.do";
		}
	}
	
    @RequestMapping("/movieList.do")
    public String movieList(Model model, HttpServletRequest request) throws Exception {
    	HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		session.setAttribute("username", username);
		
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

		MovieVO movieVO = new MovieVO();
		GenreVO genreVO = new GenreVO();
		
		List<?> genreList = movService.getGenre(genreVO);
		model.addAttribute("genreList", genreList);
		
		if (id.equals("g01")) {
			String genre = "SF";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g02")) {
			String genre = "TV 영화";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g03")) {
			String genre = "가족";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g04")) {
			String genre = "공포";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g05")) {
			String genre = "다큐멘터리";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g06")) {
			String genre = "드라마";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g07")) {
			String genre = "로맨스";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g08")) {
			String genre = "모험";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g09")) {
			String genre = "미스터리";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g10")) {
			String genre = "범죄";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g11")) {
			String genre = "서부";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g12")) {
			String genre = "스릴러";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g13")) {
			String genre = "애니메이션";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g14")) {
			String genre = "액션";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g15")) {
			String genre = "역사";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g16")) {
			String genre = "음악";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g17")) {
			String genre = "전쟁";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g18")) {
			String genre = "코미디";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
		}
		if (id.equals("g19")) {
			String genre = "판타지";
			movieVO.setGenreDB(genre);
			model.addAttribute("title", genre);
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

		ReviewVO reviewVO = new ReviewVO();
		LikeVO likeVO = new LikeVO();
		MemberVO memberVO = new MemberVO();
		reviewVO.setUserId(username);
		likeVO.setUserId(username);
		memberVO.setId(username);

		List<?> reviewList = movService.checkReview(reviewVO);
		List<?> likeList = movService.checkLike(likeVO);
		model.addAttribute("reviewList", reviewList);
		model.addAttribute("likeList", likeList);

		return "board/mypage";
	}

	@RequestMapping(value = "/addLike.do", method = RequestMethod.POST)
	public String addLike(@RequestParam("movieId") int movieId, Model model, HttpServletRequest request)
			throws Exception {
		HttpSession session = request.getSession();
		String username = request.getUserPrincipal().getName();

		session.setAttribute("username", username);

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
		
		return "board/checkPass";
	}

	@RequestMapping(value = "/updateInfo.do", method = RequestMethod.POST)
	public String updateInfo(@RequestParam("id") String id, @RequestParam("pw") String pass, Model model) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
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
			}
			else {
				model.addAttribute("errorMessage", "비밀번호가 일치하지 않습니다.");
			}
			
			return "forward:/identify.do";
		}
	}

	@RequestMapping(value = "/updatePass.do", method = RequestMethod.POST)
	public String updatePass(@RequestParam("id") String id, @RequestParam("pass") String pass, Model model,
			HttpServletRequest request) throws Exception {
		String encodPW = encoder.encode(pass);
		System.out.println("encode PW: " + encodPW);

		MemberVO memberVO = new MemberVO();
		memberVO.setId(id);
		memberVO.setPass(encodPW);

		movService.updatePassword(memberVO);
		System.out.println("DB update");
		model.addAttribute("errorMessage", "비밀번호가 변경되었습니다. 다시 로그인 해주세요.");

		HttpSession session = request.getSession(false);

		if (session != null) {
			System.out.println("Server is still alived!");
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

		memberVO.setId(id);
		memberVO.setEmail(email);
		memberVO.setMailKey(mailKey);
		memberVO.setMailAuth(auth);

		movService.updateEmail(memberVO);
		model.addAttribute("errorMessage", "이메일이 변경되었습니다. 메일 주소 인증 후 다시 로그인 해주세요.");
		
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
		} catch (Exception e) {
		    e.printStackTrace();
		    return "FAIL";
		}


		HttpSession session = request.getSession(false);

		if (session != null) {
			System.out.println("Server is still alived!");
			session.invalidate();
		}

		return "forward:/main.do";

	}
	
	@RequestMapping(value = "/commentDetail.do")
	public String commentDetail(Model model, HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		session.setAttribute("username", username);
		
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

}
