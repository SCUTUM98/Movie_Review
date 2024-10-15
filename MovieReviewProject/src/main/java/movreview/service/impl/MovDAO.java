package movreview.service.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;
import movreview.service.MovieVO;
import movreview.service.ActorVO;
import movreview.service.CollectionVO;
import movreview.service.LoginVO;

@Repository("movDAO")
public class MovDAO extends EgovAbstractDAO {
	public List<?> searchMovie(MovieVO vo) throws Exception{
		return list("movDAO.searchMovie", vo);
	}
}
