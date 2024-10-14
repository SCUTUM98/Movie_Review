package movreview.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import egovframework.example.sample.service.impl.EgovSampleServiceImpl;
import movreview.service.MovieService;
import movreview.service.MovieVO;
import movreview.service.impl.MovDAO;

@Service("movService")
public class MovieServiceImpl implements MovieService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);
	
	@Resource(name = "movDAO")
	private MovDAO movDAO;

}
