package movreview.service;

import java.util.List;

public interface MovieService {
	
	//영화 검색
	List<?> searchMovie(MovieVO vo) throws Exception;
	// 관련 영화 검색
	List<?> searchOverview(MovieVO vo) throws Exception;
	// 연기자 검색
	List<?> searchActor(ActorVO vo) throws Exception;
	// 시리즈 검색
	List<?> searchCollection(CollectionVO vo) throws Exception;
	//영화 등록
	String insertMovie(MovieVO vo) throws Exception;
	//시리즈 등록
	String insertCollection(CollectionVO vo) throws Exception;
	//연기자 등록
	String insertActor(ActorVO vo) throws Exception;
	// 연기자 sns 등록
	String insertSns(ActorSnsVO vo) throws Exception;
	//영화 상세 페이지
	MovieVO selectMovie(MovieVO vo) throws Exception;
	// 시리즈 상세 페이지
	CollectionVO selectCollection(CollectionVO vo) throws Exception;
	// 연기자 상세 페이지
	ActorVO actorDetail(ActorVO vo) throws Exception;
	ActorSnsVO snsDetail(ActorSnsVO vo) throws Exception;
	//시리즈 중복확인
	CollectionVO checkCollection(CollectionVO vo) throws Exception;
	//연기자 중복확인
	ActorVO checkActor(ActorVO vo) throws Exception;
	// 영화 중복확인
	int checkMovie(MovieVO vo) throws Exception;
	// 최근 등록 영화
	List<?> recentlyAdded(MovieVO vo) throws Exception;
	// 최근 등록 시리즈
	List<?> recentlyCollected(CollectionVO vo) throws Exception;
	// 시리즈 소속 영화
	List<?> collectionMovie(MovieVO vo) throws Exception;
	// 리뷰 작성
	ReviewVO insertReview(ReviewVO vo) throws Exception;
	// 리뷰 리스트 불러오기
	List<?> selectReview(ReviewVO vo) throws Exception;
	// 시리즈 댓글 작성
	ReviewVO insertSeriesReview(ReviewVO vo) throws Exception;
	// 시리즈 댓글 불러오기
	List<?> selectSeriesReview(ReviewVO vo) throws Exception;
	// 응원 글 작성
	ReviewVO insertActorReview(ReviewVO vo) throws Exception;
	// 응원 글 불러오기
	List<?> selectActorReview(ReviewVO vo) throws Exception;
	// 즐겨찾기 추가
	LikeVO insertLike(LikeVO vo) throws Exception;
	// 즐겨찾기 제거
	void deleteLike(LikeVO vo) throws Exception;
	// 즐겨찾기 확인
	int selectLike(LikeVO vo) throws Exception;
	// 영화 업데이트
	int movieUpdate(MovieVO vo) throws Exception;
	// 시리즈 업데이트
	int seriesUpdate(CollectionVO vo) throws Exception;
	// 배우 업데이트
	int actorUpdate(ActorVO vo) throws Exception;
	int actorSnsUpdate(ActorSnsVO vo) throws Exception;
	// 시리즈 찾기
	MovieVO findSeriesByName(MovieVO vo) throws Exception;
	// 회원 가입
	MemberVO registerMember(MemberVO vo) throws Exception;
	// 아이디 중복 확인
	int checkId(String id) throws Exception;
	// 이메일 중복 확인
	int checkEmail(String email) throws Exception;
	// 인증 랜덤 번호 저장
	int updateMailKey(MemberVO vo) throws Exception;
	// 메일 인증 업데이트
	int updateMailAuth(MemberVO vo) throws Exception;
	// 메일 인증 확인
	int emailAuthFail(String id) throws Exception;
	// 인증 번호 확인
	int verify(MemberVO vo) throws Exception;
	// 인증 여부 확인
	int verifyCheck(String email) throws Exception;
	// 비밀번호 업데이트
	int updatePassword(MemberVO vo) throws Exception;
	// 이메일 업데이트
	int updateEmail(MemberVO vo) throws Exception;
	// 계정 찾기
	MemberVO searchAcc(MemberVO vo) throws Exception;
	// 아이디 찾기
	String findId(MemberVO vo) throws Exception;
	// 비밀번호 찾기
	int findPass(MemberVO vo) throws Exception;
	// 회원 탈퇴
	int deleteAcc(String id) throws Exception;
	// 최근 리뷰 확인
	List<?> checkReview(ReviewVO vo) throws Exception;
	List<?> selectAllReview(ReviewVO vo) throws Exception;
	List<?> selectAllSeriesReview(ReviewVO vo) throws Exception;
	List<?> selectAllActorReview(ReviewVO vo) throws Exception;
	// 즐겨찾기 리스트
	List<?> checkLike(LikeVO vo) throws Exception;
	List<?> selectAllLike(LikeVO vo) throws Exception;
	// 영화 댓글 삭제
	void deleteMovieComment(ReviewVO vo) throws Exception;
	// 시리즈 댓글 삭제
	void deleteSeriesComment(ReviewVO vo) throws Exception;
	// 응원 글 삭제
	void deleteActorComment(ReviewVO vo) throws Exception;
	// 프로필 경로 조회
	MemberVO profileRoot(MemberVO vo) throws Exception;
	// 프로필 경로 업데이트
	int updateProfile(MemberVO vo) throws Exception;
	// 전체 영화 조회
	List<?> selectAllMovie(MovieVO vo) throws Exception;
	// 장르 리스트 조회
	List<?> getGenre(GenreVO vo) throws Exception;
	// 장르 별 검색
	List<?> searchByGenre(MovieVO vo) throws Exception;
	// 시리즈 리스트 조회
	List<?> seriesList(CollectionVO vo) throws Exception;
	// 회원 목록 조회
	List<?> searchAllUsers(MemberVO vo) throws Exception;
	// 관리자 계정 조회
	List<?> searchAllAdmin(MemberVO vo) throws Exception;
	// 회원 상세 조회
	MemberVO searchUserDetail(MemberVO vo) throws Exception;
	// 관리자 권한 부여
	int upgradeToAdmin(String id) throws Exception;
	// 관리자 권한 회수
	int downToUser(String id) throws Exception;
	// 관리자 확인
	int checkAdmin(String id) throws Exception;
	// 로그 추가
	LogVO insertLog(LogVO vo) throws Exception;
	// 게정 별 로그 조회
	List<?> searchAccLog(String id) throws Exception;
	// 로그 가이드 조회
	List<?> logCategory() throws Exception;
	// 로그 조회
	List<?> selectLog() throws Exception;
	// 로그 세부 내용 조회
	LogVO logDetail(int logId) throws Exception;
	// 로그 가이드 조회 by typeName
	LogVO logByName(String typeName) throws Exception;
	// 작성한 영화 리뷰 찾아오기
	ReviewVO findReviewByTime(ReviewVO vo) throws Exception;
	// 시리즈 리뷰 찾아오기
	ReviewVO seriesReviewByTime(ReviewVO vo) throws Exception;
	// 작성한 배우 응원글 찾아오기
	ReviewVO actorReviewByTime(ReviewVO vo) throws Exception;
	// 로그 종류별 개수 조회
	LogChartVO logCnt() throws Exception;
	// 날짜 별 로그인 횟수 조회
	List<?> loginCnt() throws Exception;
	// 메인화면 로그 테이블
	List<?> mainLogTable() throws Exception;
	// 장르 별 검색 횟수 조회
	LogChartVO genreCnt() throws Exception;
	// 검색 로그 테이블
	List<?> searchLogTable() throws Exception;
	// 삽입 로그 테이블
	List<?> insertLogTable() throws Exception;
}
