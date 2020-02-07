package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import sun.yumway.subway.domain.Order;

public class OrderListCommand implements Command {

  ObjectOutputStream out;
  ObjectInputStream in;

  public OrderListCommand(ObjectOutputStream out, ObjectInputStream in) {
    this.out = out;
    this.in = in;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void execute() {
    try {
      out.writeUTF("/board/list");
      out.flush();

      String response = in.readUTF();
      if (response.equals("FAIL")) {
        System.out.println(in.readUTF());
        return;
      }
      List<Order> orders = (List<Order>) in.readObject();
      for (Order o : orders) {
        System.out.printf("\n%d, %s, %s, %s, %s, %s\n", o.getNo(), o.getBread(), o.getMain(),
            o.getCheese(), o.getVegetable(), o.getSauce());
      }
    } catch (Exception e) {
      System.out.println("통신 오류 발생");
      e.printStackTrace();
    }
  }
}
