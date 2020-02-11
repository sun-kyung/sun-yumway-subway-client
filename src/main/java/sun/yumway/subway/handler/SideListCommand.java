package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import sun.yumway.subway.domain.Side;

public class SideListCommand implements Command {

  ObjectOutputStream out;
  ObjectInputStream in;

  public SideListCommand(ObjectOutputStream out, ObjectInputStream in) {
    this.out = out;
    this.in = in;
  }


  @SuppressWarnings("unchecked")
  @Override
  public void execute() {
    try {
      out.writeUTF("/side/list");
      out.flush();

      String response = in.readUTF();
      if (response.equals("FAIL")) {
        System.out.println(in.readUTF());
        return;
      }
      List<Side> sides = (List<Side>) in.readObject();
      for (Side s : sides) {
        System.out.printf("%d, %s, %s, %s\n", s.getNo(), s.getCookie(), s.getBeverage(),
            s.getOthers());
      }
    } catch (Exception e) {
      System.out.println("통신 오류 발생");
      e.printStackTrace();
    }
  }
}
