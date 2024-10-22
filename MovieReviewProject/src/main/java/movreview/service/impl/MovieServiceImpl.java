package movreview.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import egovframework.example.sample.service.impl.EgovSampleServiceImpl;
import movreview.service.ActorVO;
import movreview.service.CollectionVO;
import movreview.service.MovieService;
import movreview.service.MovieVO;
import movreview.service.impl.MovDAO;

@Service("movService")
public class MovieServiceImpl implements MovieService {
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);
	
	@Resource(name = "movDAO")
	private MovDAO movDAO;

	//영화 검색
	@Override
	public List<?> searchMovie(MovieVO vo) throws Exception {
		return movDAO.searchMovie(vo);
	}
	//영화 삽입
	@Override
	public String insertMovie(MovieVO vo) throws Exception{
		return movDAO.insertMovie(vo);
	}
	//시리즈 삽입
	@Override
	public String insertCollection(CollectionVO vo) throws Exception {
		return movDAO.insertCollection(vo);
	}
	// 연기자 삽입
	@Override
	public String insertActor(ActorVO vo) throws Exception {
		return movDAO.insertActor(vo);
	}
	//영화 상세 페이지
	@Override
	public MovieVO selectMovie(MovieVO vo) throws Exception {
		return movDAO.selectMovie(vo);
	}
	// 시리즈 중복 확인
	@Override
	public CollectionVO checkCollection(CollectionVO vo) throws Exception {
		return movDAO.checkCollection(vo);
	}
	// 연기자 중복 확인
	@Override
	public ActorVO checkActor(ActorVO vo) throws Exception {
		return movDAO.checkActor(vo);
	}

}
