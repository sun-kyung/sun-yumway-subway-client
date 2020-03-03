package sun.yumway.subway.dao.proxy;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import sun.yumway.subway.dao.OrderDao;
import sun.yumway.subway.domain.Order;

public class OrderDaoProxy implements OrderDao {

  ObjectInputStream in;
  ObjectOutputStream out;

  public OrderDaoProxy(ObjectInputStream in, ObjectOutputStream out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public int insert(Order order) throws Exception {
    out.writeUTF("/order/add");
    out.writeObject(order);
    out.flush();

    String response = in.readUTF();
    if (response.equals("FAIL")) {
      throw new Exception(in.readUTF());
    }
    return 1;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Order> findAll() throws Exception {
    out.writeUTF("/order/list");
    out.flush();
    String response = in.readUTF();
    if (response.equals("FAIL")) {
      throw new Exception(in.readUTF());
    }
    return (List<Order>) in.readObject();
  }

  @Override
  public Order findByNo(int no) throws Exception {
    out.writeUTF("/order/detail");
    out.writeInt(no);
    out.flush();

    String response = in.readUTF();

    if (response.equals("FAIL")) {
      throw new Exception(in.readUTF());
    }
    return (Order) in.readObject();
  }

  @Override
  public int update(Order order) throws Exception {
    out.writeUTF("/order/update");
    out.writeObject(order);
    out.flush();
    String response = in.readUTF();
    if (response.equals("FAIL")) {
      throw new Exception(in.readUTF());
    }
    return 1;
  }

  @Override
  public int delete(int no) throws Exception {
    out.writeUTF("/order/delete");
    out.writeInt(no);
    out.flush();

    String response = in.readUTF();
    if (response.equals("FAIL")) {
      throw new Exception(in.readUTF());
    }
    return 1;
  }

}
