package sun.yumway.subway.dao;

import java.util.List;
import sun.yumway.subway.domain.Order;

public interface OrderDao {

  public int insert(Order order) throws Exception;

  public List<Order> findAll() throws Exception;

  public Order findByNo(int no) throws Exception;

  public int update(Order order) throws Exception;

  public int delete(int no) throws Exception;

}
