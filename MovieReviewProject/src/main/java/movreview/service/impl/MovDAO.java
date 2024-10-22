package movreview.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import egovframework.example.sample.service.impl.EgovSampleServiceImpl;
import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import movreview.service.MovieVO;
import movreview.service.ActorVO;
import movreview.service.CollectionVO;
import movreview.service.LoginVO;

@Repository("movDAO")
public class MovDAO extends EgovAbstractDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSampleServiceImpl.class);
	
	// 영화 검색
	public List<?> searchMovie(MovieVO vo) throws Exception {
	    try {
	        return list("movDAO.searchMovie", vo);
	    } catch (Exception e) {
	        LOGGER.error("Error executing searchMovie query", e);
	        throw e;
	    }
	}
	// 영화 삽입
	public String insertMovie(MovieVO vo) throws Exception {
		return (String) insert("movDAO.insertMovie", vo);
	}
	// 시리즈 삽입
	public String insertCollection(CollectionVO vo) throws Exception {
		return (String) insert("movDAO.insertCollection", vo);
	}
	// 연기자 삽닙
	public String insertActor(ActorVO vo) throws Exception {
		return (String) insert("movDAO.insertActor", vo);
	}
	// 영화 상세 페이지
	public MovieVO selectMovie(MovieVO vo) throws Exception {
		return (MovieVO) select("movDAO.selectMovie", vo);
	}
}
