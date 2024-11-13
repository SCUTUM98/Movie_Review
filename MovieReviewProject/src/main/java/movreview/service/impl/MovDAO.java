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
import movreview.service.LogVO;
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
	// 응원 글 작성
	public ReviewVO insertActorReview(ReviewVO vo) throws Exception {
		return (ReviewVO) insert("movDAO.insertActorReview", vo);
	}
	// 응원 글 불러오기
	public List<?> selectActorReview(ReviewVO vo) throws Exception {
		return list("movDAO.selectActorReview", vo);
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
	// 영화 업데이트
	public int movieUpdate(MovieVO vo) throws Exception {
		return (int) update("movDAO.movieUpdate", vo);
	}
	// 시리즈 업데이트
	public int seriesUpdate(CollectionVO vo) throws Exception {
		return (int) update("movDAO.seriesUpdate", vo);
	}
	// 배우 업데이트
	public int actorUpdate(ActorVO vo) throws Exception {
		return (int) update("movDAO.actorUpdate", vo);
	}
	public int actorSnsUpdate(ActorSnsVO vo) throws Exception {
		return (int) update("movDAO.actorSnsUpdate", vo);
	}
	// 시리즈 찾기
	public MovieVO findSeriesByName(MovieVO vo) throws Exception {
		return (MovieVO) select("movDAO.findSeriesByName", vo);
	}
	// 회원 가입
	public MemberVO registerMember(MemberVO vo) throws Exception {
		return (MemberVO) insert("movDAO.registerMember", vo);
	}
	// 아이디 중복 확인
	public int checkId(String id) throws Exception {
		return (int) select("movDAO.checkId", id);
	}
	// 이메일 중복 확인
	public int checkEmail(String email) throws Exception {
		return (int) select("movDAO.checkEmail", email);
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
	// 아이디 찾기
	public String findId(MemberVO vo) throws Exception{
		return (String) select("movDAO.findId", vo);
	}
	// 비밀번호 찾기
	public int findPass(MemberVO vo) throws Exception {
		return (int) select("movDAO.findPass", vo);
	}
	// 회원 탈퇴
	public int deleteAcc(String id) throws Exception {
		return delete("movDAO.deleteAcc", id);
	}
	// 최근 리뷰 확인
	public List<?> checkReview(ReviewVO vo) throws Exception{
		return list("movDAO.checkReview", vo);
	}
	public List<?> selectAllReview(ReviewVO vo) throws Exception{
		return list("movDAO.selectAllReview", vo);
	}
	public List<?> selectAllSeriesReview(ReviewVO vo) throws Exception{
		return list("movDAO.selectAllSeriesReview", vo);
	}
	public List<?> selectAllActorReview(ReviewVO vo) throws Exception{
		return list("movDAO.selectAllActorReview", vo);
	}
	// 즐겨찾기 리스트
	public List<?> checkLike(LikeVO vo) throws Exception{
		return list("movDAO.checkLike", vo);
	}
	public List<?> selectAllLike(LikeVO vo) throws Exception{
		return list("movDAO.selectAllLike", vo);
	}
	// 영화 댓글 삭제
	public void deleteMovieComment(ReviewVO vo) throws Exception {
		delete("movDAO.deleteMovieComment", vo);
	}
	// 시리즈 댓글 삭제
	public void deleteSeriesComment(ReviewVO vo) throws Exception {
		delete("movDAO.deleteSeriesComment", vo);
	}
	// 응원 글 삭제
	public void deleteActorComment(ReviewVO vo) throws Exception {
		delete("movDAO.deleteActorComment", vo);
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
	// 회원 목록 조회
	public List<?> searchAllUsers(MemberVO vo) throws Exception {
		return list("movDAO.searchAllUsers", vo);
	}
	// 관리자 계정 조회
	public List<?> searchAllAdmin(MemberVO vo) throws Exception {
		return list("movDAO.searchAllAdmin", vo);
	}
	// 회원 상세 조회
	public MemberVO searchUserDetail(MemberVO vo) throws Exception {
		return (MemberVO) select("movDAO.searchUserDetail", vo);
	}
	// 관리자 권한 부여
	public int upgradeToAdmin(String id) throws Exception {
		return (int) update("movDAO.upgradeToAdmin", id);
	}
	// 관리자 권한 회수
	public int downToUser(String id) throws Exception {
		return (int) update("movDAO.downToUser", id);
	}
	// 관리자 확인
	public int checkAdmin(String id) throws Exception {
		return (int) select("movDAO.checkAdmin", id);
	}
	// 로그 추가
	public LogVO insertLog(LogVO vo) throws Exception {
		return (LogVO) insert("movDAO.insertLog", vo);
	}
	// 계정 별 로그 조회
	public List<?> searchAccLog(String id) throws Exception {
		return list("movDAO.searchAccLog", id);
	}
	// 로그 가이드 조회
	public List<?> logCategory() throws Exception {
		return list("movDAO.logCategory");
	}
}
