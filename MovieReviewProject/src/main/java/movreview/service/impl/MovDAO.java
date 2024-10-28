package movreview.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import egovframework.example.sample.service.impl.EgovSampleServiceImpl;
import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import movreview.service.MovieVO;
import movreview.service.ActorSnsVO;
import movreview.service.ActorVO;
import movreview.service.CollectionVO;
import movreview.service.MemberVO;

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
	// 연기자 검색
	public List<?> searchActor(ActorVO vo) throws Exception {
		try {
	        return list("movDAO.searchActor", vo);
	    } catch (Exception e) {
	        LOGGER.error("Error executing searchMovie query", e);
	        throw e;
	    }
	}
	// 시리즈 검색
	public List<?> searchCollection(CollectionVO vo) throws Exception {
		try {
	        return list("movDAO.searchCollection", vo);
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
	// 연기자 삽입
	public String insertActor(ActorVO vo) throws Exception {
		return (String) insert("movDAO.insertActor", vo);
	}
	// 연기자 sns 삽입
	public String insertSns(ActorSnsVO vo) throws Exception {
		return (String) insert("movDAO.insertSns", vo);
	}
	// 영화 상세 페이지
	public MovieVO selectMovie(MovieVO vo) throws Exception {
		return (MovieVO) select("movDAO.selectMovie", vo);
	}
	// 시리즈 상세 페이지
	public CollectionVO selectCollection(CollectionVO vo) throws Exception {
		return (CollectionVO) select("movDAO.selectCollection", vo);
	}
	// 연기자 상세 페이지
	public ActorVO actorDetail(ActorVO vo) throws Exception {
		return (ActorVO) select("movDAO.actorDetail", vo);
	}
	public ActorSnsVO snsDetail(ActorSnsVO vo) throws Exception {
		return (ActorSnsVO) select("movDAO.snsDetail", vo);
	}
	// 시리즈 중복 확인
	public CollectionVO checkCollection(CollectionVO vo) throws Exception {
		return (CollectionVO) select("movDAO.checkCollection", vo);
	}
	// 연기자 중복 확인
	public ActorVO checkActor(ActorVO vo) throws Exception {
		return (ActorVO) select("movDAO.checkActor", vo);
	}
	// 영화 중복 확인
	public int checkMovie(MovieVO vo) throws Exception {
		return (int) select("movDAO.checkMovie", vo);
	}
	// 최근 등록 영화
	public List<?> recentlyAdded(MovieVO vo) throws Exception {
		return list("movDAO.recentlyAdded", vo);
	}
	// 최근 등록 시리즈
	public List<?> recentlyCollected(CollectionVO vo) throws Exception {
		return list("movDAO.recentlyCollected", vo);
	}
	// 시리즈 소속 영화
	public List<?> collectionMovie(MovieVO vo) throws Exception {
		return list("movDAO.collectionMovie", vo);
	}
	// 회원 가입
	public MemberVO registerMember(MemberVO vo) throws Exception {
		return (MemberVO) insert("movDAO.registerMember", vo);
	}
}
