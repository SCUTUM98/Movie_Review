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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

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
	public String mainPage() throws Exception {
		return "board/main";
	}
	
	@RequestMapping(value="/search.do")
	public String searchPage() throws Exception {
		return "board/search";
	}
	
	@RequestMapping(value="/result.do", method=RequestMethod.GET)
	public String searchResult(HttpServletRequest request, Model model) throws Exception {
		//MovieVO suggestVO = new MovieVO(); // 영화 등록 제안
		
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

            // 모델에 추가
            model.addAttribute("suggestData", suggestVO);
            model.addAttribute("totalPages", jsonNode.get("total_pages").asInt());
            model.addAttribute("totalResults", jsonNode.get("total_results").asInt());
            //model.addAttribute("backdropPath", jsonNode.get("backdrop_path").asInt());

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing the API response: " + e.getMessage());
        }
		
		return "board/searchResult";
	}
	
	@GetMapping("/searchMovie.do")
    public String searchMovie(HttpServletRequest request, Model model) throws Exception {
		String title2 = "아이언맨";
        String apiKey = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlOTFhNDM3OTVmMWRjMDMyNzk1OTA1NWJjN2FlOGJiOSIsIm5iZiI6MTcyODYwNTgwMS40Njk1NTMsInN1YiI6IjY3MDY0OTc4YTg4NjE0ZDZiMDhhZGRhNiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.167LDdbBCOhEn0TosoOrME7mxJhmEq4T2Tq3lExAZ3Q";
        String movieData = tmdbService.searchByName(apiKey, title2);
        model.addAttribute("movieData", movieData); // 결과를 모델에 추가
        return "board/dataTest"; // 결과를 보여줄 JSP 페이지로 이동
    }
	
}
