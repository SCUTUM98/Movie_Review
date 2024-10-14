package movreview.service.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URI;

import javax.annotation.Resource;

import egovframework.example.sample.service.impl.EgovSampleServiceImpl;
import movreview.service.MovieService;
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
	
	@RequestMapping(value="/main.do")
	public String mainPage() {
		return "board/main";
	}
	
	@RequestMapping(value="/search.do")
	public String searchPage() {
		return "board/search";
	}
	
	@RequestMapping(value="/result.do")
	public String searchResult() {
		
		
		return "board/searchResult";
	}
	
}
