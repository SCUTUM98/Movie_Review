package movreview.service;

import java.util.List;

public interface MovieService {
	
	//영화 검색
	List<?> searchMovie(MovieVO vo) throws Exception;
	// 연기자 검색
	List<?> searchActor(ActorVO vo) throws Exception;
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
}
