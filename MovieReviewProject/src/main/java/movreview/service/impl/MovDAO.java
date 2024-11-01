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
import movreview.service.GenreVO;
import movreview.service.LikeVO;
import movreview.service.MemberVO;
import movreview.service.ReviewVO;

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
	// 관련 영화 검색
	public List<?> searchOverview(MovieVO vo) throws Exception {
		try {
			return list("movDAO.searchOverview", vo);
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
	// 리뷰 작성
	public ReviewVO insertReview(ReviewVO vo) throws Exception {
		return (ReviewVO) insert("movDAO.insertReview", vo);
	}
	// 리뷰 불러오기
	public List<?> selectReview(ReviewVO vo) throws Exception {
		return list("movDAO.selectReview", vo);
	}
	// 시리즈 댓글 작성
	public ReviewVO insertSeriesReview(ReviewVO vo) throws Exception {
		return (ReviewVO) insert("movDAO.insertSeriesReview", vo);
	}
	// 리뷰 불러오기
	public List<?> selectSeriesReview(ReviewVO vo) throws Exception {
		return list("movDAO.selectSeriesReview", vo);
	}
	// 즐겨찾기 추가
	public LikeVO insertLike(LikeVO vo) throws Exception {
		return (LikeVO) insert("movDAO.insertLike", vo);
	}
	// 즐겨찾기 제거
	public void deleteLike(LikeVO vo) throws Exception {
		delete("movDAO.deleteLike", vo);
	}
	// 즐겨찾기 확인
	public int selectLike(LikeVO vo) throws Exception {
		return (int) select("movDAO.selectLike", vo);
	}
	// 회원 가입
	public MemberVO registerMember(MemberVO vo) throws Exception {
		return (MemberVO) insert("movDAO.registerMember", vo);
	}
	// 인증 랜덤 번호 저장
	public int updateMailKey(MemberVO vo) throws Exception{
		return (int) update("movDAO.updateMailKey", vo);
	}
	// 메일 인증 업데이트
	public int updateMailAuth(MemberVO vo) throws Exception{
		return (int) update("movDAO.updateMailAuth", vo);
	}
	// 메일 인증 확인
	public int emailAuthFail(String id) throws Exception{
		return (int) select("movDAO.emailAuthFail", id);
	}
	// 인증 번호 확인
	public int verify(MemberVO vo) throws Exception{
		return (int) select("movDAO.verify", vo);
	}
	// 인증 여부 확인
	public int verifyCheck(String email) throws Exception{
		return (int) select("movDAO.verifyCheck", email);
	}
	// 비밀번호 업데이트
	public int updatePassword(MemberVO vo) throws Exception{
		return (int) update("movDAO.updatePassword", vo);
	}
	// 이메일 업데이트
	public int updateEmail(MemberVO vo) throws Exception{
		return (int) update("movDAO.updateEmail", vo);
	}
	// 계정 찾기
	public MemberVO searchAcc(MemberVO vo) throws Exception{
		return (MemberVO) select("movDAO.searchAcc", vo);
	}
	// 최근 리뷰 확인
	public List<?> checkReview(ReviewVO vo) throws Exception{
		return list("movDAO.checkReview", vo);
	}
	// 즐겨찾기 리스트
	public List<?> checkLike(LikeVO vo) throws Exception{
		return list("movDAO.checkLike", vo);
	}
	// 프로필 경로 조회
	public MemberVO profileRoot(MemberVO vo) throws Exception{
		return (MemberVO) select("movDAO.profileRoot", vo);
	}
	// 프로필 경로 업데이트
	public int updateProfile(MemberVO vo) throws Exception{
		return (int) update("movDAO.updateProfile", vo);
	}
	// 전체 영화 조회
	public List<?> selectAllMovie(MovieVO vo) throws Exception {
		return list("movDAO.selectAllMovie", vo);
	}
	// 장르 리스트 조회
	public List<?> getGenre(GenreVO vo) throws Exception {
		return list("movDAO.getGenre", vo);
	}
	// 장르 별 검색
	public List<?> searchByGenre(MovieVO vo) throws Exception {
		return list("movDAO.searchByGenre", vo);
	}
	// 시리즈 리스트 조회
	public List<?> seriesList(CollectionVO vo) throws Exception {
		return list("movDAO.seriesList", vo);
	}
}
