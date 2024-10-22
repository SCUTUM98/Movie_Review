package movreview.service;

import java.util.List;

public interface MovieService {
	
	//영화 검색
	List<?> searchMovie(MovieVO vo) throws Exception;
	//영화 등록
	String insertMovie(MovieVO vo) throws Exception;
	//시리즈 등록
	String insertCollection(CollectionVO vo) throws Exception;
	//연기자 등록
	String insertActor(ActorVO vo) throws Exception;
	//영화 상세 페이지
	MovieVO selectMovie(MovieVO vo) throws Exception;
}
