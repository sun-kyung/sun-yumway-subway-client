package sun.yumway.subway.dao;

import java.util.List;
import sun.yumway.subway.domain.Side;

public interface SideDao {

  public int insert(Side side) throws Exception;

  public List<Side> findAll() throws Exception;

  public Side findByNo(int no) throws Exception;

  public int update(Side side) throws Exception;

  public int delete(int no) throws Exception;

}
