package movreview.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


import egovframework.example.sample.service.impl.EgovSampleServiceImpl;
import movreview.service.ActorSnsVO;
import movreview.service.ActorVO;
import movreview.service.CollectionVO;
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
	// 관람평 작성
	@Override
	public ReviewVO insertReview(ReviewVO vo) throws Exception {
		return movDAO.insertReview(vo);
	}
	// 회원 가입
	@Override
	public MemberVO registerMember(MemberVO vo) throws Exception {
		return movDAO.registerMember(vo);
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

}
