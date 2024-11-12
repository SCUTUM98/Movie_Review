package movreview.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import egovframework.example.sample.service.impl.EgovSampleServiceImpl;
import movreview.service.ActorSnsVO;
import movreview.service.ActorVO;
import movreview.service.CollectionVO;
import movreview.service.GenreVO;
import movreview.service.LikeVO;
import movreview.service.MemberVO;
import movreview.service.MovieService;
import movreview.service.MovieVO;
import movreview.service.ReviewVO;

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
	// 관련 영화 검색
	@Override
	public List<?> searchOverview(MovieVO vo) throws Exception {
		return movDAO.searchOverview(vo);
	}
	// 연기자 검색
	@Override
	public List<?> searchActor(ActorVO vo) throws Exception {
		return movDAO.searchActor(vo);
	}
	// 시리즈 검색
	@Override
	public List<?> searchCollection(CollectionVO vo) throws Exception {
		return movDAO.searchCollection(vo);
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
	// 연기자 sns 삽입
	@Override
	public String insertSns(ActorSnsVO vo) throws Exception {
		return movDAO.insertSns(vo);
	}
	//영화 상세 페이지
	@Override
	public MovieVO selectMovie(MovieVO vo) throws Exception {
		return movDAO.selectMovie(vo);
	}
	// 시리즈 상세 페이지
	@Override
	public CollectionVO selectCollection(CollectionVO vo) throws Exception {
		return movDAO.selectCollection(vo);
	}
	// 연기자 상세 페이지
	@Override
	public ActorVO actorDetail(ActorVO vo) throws Exception {
		return movDAO.actorDetail(vo);
	}
	@Override
	public ActorSnsVO snsDetail(ActorSnsVO vo) throws Exception {
		return movDAO.snsDetail(vo);
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
	// 영화 중복 확인
	@Override
	public int checkMovie(MovieVO vo) throws Exception {
		return movDAO.checkMovie(vo);
	}
	// 최근 등록 영화
	@Override
	public List<?> recentlyAdded(MovieVO vo) throws Exception {
		return movDAO.recentlyAdded(vo);
	}
	// 최근 등록 시리즈
	@Override
	public List<?> recentlyCollected(CollectionVO vo) throws Exception {
		return movDAO.recentlyCollected(vo);
	}
	// 시리즈 소속 영화
	@Override
	public List<?> collectionMovie(MovieVO vo) throws Exception {
		return movDAO.collectionMovie(vo);
	}
	// 리뷰 작성
	@Override
	public ReviewVO insertReview(ReviewVO vo) throws Exception {
		return movDAO.insertReview(vo);
	}
	// 리뷰 불러오기
	@Override
	public List<?> selectSeriesReview(ReviewVO vo) throws Exception {
		return movDAO.selectSeriesReview(vo);
	}
	// 시리즈 댓글 작성
	@Override
	public ReviewVO insertSeriesReview(ReviewVO vo) throws Exception {
		return movDAO.insertSeriesReview(vo);
	}
	// 시리즈 댓글 불러오기
	@Override
	public List<?> selectReview(ReviewVO vo) throws Exception {
		return movDAO.selectReview(vo);
	}
	// 응원 글 작성
	@Override
	public ReviewVO insertActorReview(ReviewVO vo) throws Exception {
		return movDAO.insertActorReview(vo);
	}
	// 응원 글 불러오기
	@Override
	public List<?> selectActorReview(ReviewVO vo) throws Exception {
		return movDAO.selectActorReview(vo);
	}
	// 즐겨찾기 추가
	@Override
	public LikeVO insertLike(LikeVO vo) throws Exception {
		return movDAO.insertLike(vo);
	}
	// 즐겨찾기 제거
	@Override
	public void deleteLike(LikeVO vo) throws Exception {
		movDAO.deleteLike(vo);
	}
	// 즐겨찾기 확인
	@Override 
	public int selectLike(LikeVO vo) throws Exception {
		return movDAO.selectLike(vo);
	}
	// 영화 업데이트
	@Override
	public int movieUpdate(MovieVO vo) throws Exception {
		return movDAO.movieUpdate(vo);
	}
	// 시리즈 업데이트
	@Override
	public int seriesUpdate(CollectionVO vo) throws Exception {
		return movDAO.seriesUpdate(vo);
	}
	// 배우 업데이트
	@Override
	public int actorUpdate(ActorVO vo) throws Exception {
		return movDAO.actorUpdate(vo);
	}
	@Override
	public int actorSnsUpdate(ActorSnsVO vo) throws Exception {
		return movDAO.actorSnsUpdate(vo);
	}
	// 시리즈 찾기
	@Override
	public MovieVO findSeriesByName(MovieVO vo) throws Exception {
		return movDAO.findSeriesByName(vo);
	}
	// 회원 가입
	@Override
	public MemberVO registerMember(MemberVO vo) throws Exception {
		return movDAO.registerMember(vo);
	}
	// 아이디 중복 확인
	@Override
	public int checkId(String id) throws Exception {
		return movDAO.checkId(id);
	}
	// 이메일 중복 확인
	@Override
	public int checkEmail(String email) throws Exception {
		return movDAO.checkEmail(email);
	}
	// 인증 랜덤 번호 저장
	@Override
	public int updateMailKey(MemberVO vo) throws Exception{
		return movDAO.updateMailKey(vo);
	}
	// 메일 인증 업데이트
	@Override
	public int updateMailAuth(MemberVO vo) throws Exception{
		return movDAO.updateMailAuth(vo);
	}
	// 메일 인증 확인
	@Override
	public int emailAuthFail(String id) throws Exception{
		return movDAO.emailAuthFail(id);
	}
	// 인증 번호 확인
	@Override
	public int verify(MemberVO vo) throws Exception{
		return movDAO.verify(vo);
	}
	// 인증 여부 확인
	@Override
	public int verifyCheck(String email) throws Exception {
		return movDAO.verifyCheck(email);
	}
	// 비밀번호 업데이트
	@Override
	public int updatePassword(MemberVO vo) throws Exception {
		return movDAO.updatePassword(vo);
	}
	// 이메일 업데이트
	@Override
	public int updateEmail(MemberVO vo) throws Exception {
		return movDAO.updateEmail(vo);
	}
	// 계정 확인
	@Override
	public MemberVO searchAcc(MemberVO vo) throws Exception {
		return movDAO.searchAcc(vo);
	}
	// 아이디 찾기
	@Override
	public String findId(MemberVO vo) throws Exception {
		return movDAO.findId(vo);
	}
	// 비밀번호 찾기
	@Override
	public int findPass(MemberVO vo) throws Exception {
		return movDAO.findPass(vo);
	}
	// 회원 탈퇴
	@Override
	public int deleteAcc(String id) throws Exception {
		return movDAO.deleteAcc(id);
	}
	// 최근 리뷰 확인
	@Override
	public List<?> checkReview(ReviewVO vo) throws Exception {
		return movDAO.checkReview(vo);
	}
	@Override
	public List<?> selectAllReview(ReviewVO vo) throws Exception {
		return movDAO.selectAllReview(vo);
	}
	@Override
	public List<?> selectAllSeriesReview(ReviewVO vo) throws Exception {
		return movDAO.selectAllSeriesReview(vo);
	}
	@Override
	public List<?> selectAllActorReview(ReviewVO vo) throws Exception {
		return movDAO.selectAllActorReview(vo);
	}
	// 즐겨찾기 리스트
	@Override
	public List<?> checkLike(LikeVO vo) throws Exception {
		return movDAO.checkLike(vo);
	}
	@Override
	public List<?> selectAllLike(LikeVO vo) throws Exception {
		return movDAO.selectAllLike(vo);
	}
	// 영화 댓글 삭제
	@Override
	public void deleteMovieComment(ReviewVO vo) throws Exception {
		movDAO.deleteMovieComment(vo);
	}
	// 시리즈 댓글 삭제
	@Override
	public void deleteSeriesComment(ReviewVO vo) throws Exception {
		movDAO.deleteSeriesComment(vo);
	}
	// 응원 글 샂게
	@Override
	public void deleteActorComment(ReviewVO vo) throws Exception {
		movDAO.deleteActorComment(vo);
	}
	// 프로필 경로 조회
	@Override
	public MemberVO profileRoot(MemberVO vo) throws Exception {
		return movDAO.profileRoot(vo);
	}
	// 프로필 경로 업데이트
	@Override
	public int updateProfile(MemberVO vo) throws Exception {
		return movDAO.updateProfile(vo);
	}
	// 전체 영화 조회
	@Override
	public List<?> selectAllMovie(MovieVO vo) throws Exception {
		return movDAO.selectAllMovie(vo);
	}
	// 장르 리스트 조회
	@Override
	public List<?> getGenre(GenreVO vo) throws Exception {
		return movDAO.getGenre(vo);
	}
	// 장르 별 검색
	@Override
	public List<?> searchByGenre(MovieVO vo) throws Exception {
		return movDAO.searchByGenre(vo);
	}
	// 시리즈 리스트 조회
	@Override
	public List<?> seriesList(CollectionVO vo) throws Exception {
		return movDAO.seriesList(vo);
	}
}
