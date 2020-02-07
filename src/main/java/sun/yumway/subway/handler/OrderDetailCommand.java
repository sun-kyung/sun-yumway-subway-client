package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sun.yumway.subway.domain.Order;
import sun.yumway.subway.util.Prompt;

public class OrderDetailCommand implements Command {

  ObjectOutputStream out;
  ObjectInputStream in;
  Prompt prompt;

  public OrderDetailCommand(ObjectOutputStream out, ObjectInputStream in, Prompt prompt) {
    this.prompt = prompt;
    this.out = out;
    this.in = in;
  }

  @Override
  public void execute() {
    try {
      int no = prompt.inputInt("번호? ");

      out.writeUTF("/order/detail");
      out.writeInt(no);
      out.flush();

      String response = in.readUTF();

      if (response.equals("FAIL")) {
        System.out.println(in.readUTF());
        return;
      }
      Order order = (Order) in.readObject();

      System.out.printf("번호: %d\n", order.getNo());
      System.out.printf("빵: %s\n", order.getBread());
      System.out.printf("메인: %s\n", order.getMain());
      System.out.printf("치즈: %s\n", order.getCheese());
      System.out.printf("채소: %s\n", order.getVegetable());
      System.out.printf("소스: %s\n", order.getSauce());
    } catch (Exception e) {
      System.out.println("명령 실행 중 오류 발생");
    }
  }
}
