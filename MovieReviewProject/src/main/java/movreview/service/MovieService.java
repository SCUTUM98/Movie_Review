package movreview.service;

import java.util.List;

public interface MovieService {
	/**
	 * 영화 검색
	 * @param vo - 등록할 정보가 담긴 MovieVO
	 * @return 등록 결과
	 * @exception Exception
	 */
	List<?> searchMovie(MovieVO vo) throws Exception;
}
