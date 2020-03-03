package sun.yumway.subway.dao.proxy;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import sun.yumway.subway.dao.SideDao;
import sun.yumway.subway.domain.Side;

public class SideDaoProxy implements SideDao {

  ObjectInputStream in;
  ObjectOutputStream out;

  public SideDaoProxy(ObjectInputStream in, ObjectOutputStream out) {
    this.in = in;
    this.out = out;
  }

  @Override
  public int insert(Side side) throws Exception {
    out.writeUTF("/side/add");
    out.writeObject(side);
    out.flush();

    String response = in.readUTF();
    if (response.equals("FAIL")) {
      throw new Exception(in.readUTF());
    }
    return 1;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Side> findAll() throws Exception {
    out.writeUTF("/side/list");
    out.flush();
    String response = in.readUTF();
    if (response.equals("FAIL")) {
      throw new Exception(in.readUTF());
    }
    return (List<Side>) in.readObject();
  }

  @Override
  public Side findByNo(int no) throws Exception {
    out.writeUTF("/side/detail");
    out.writeInt(no);
    out.flush();

    String response = in.readUTF();

    if (response.equals("FAIL")) {
      throw new Exception(in.readUTF());
    }
    return (Side) in.readObject();
  }

  @Override
  public int update(Side side) throws Exception {
    out.writeUTF("/side/update");
    out.writeObject(side);
    out.flush();
    String response = in.readUTF();
    if (response.equals("FAIL")) {
      throw new Exception(in.readUTF());
    }
    return 1;
  }

  @Override
  public int delete(int no) throws Exception {
    out.writeUTF("/side/delete");
    out.writeInt(no);
    out.flush();

    String response = in.readUTF();
    if (response.equals("FAIL")) {
      throw new Exception(in.readUTF());
    }
    return 1;
  }

}
