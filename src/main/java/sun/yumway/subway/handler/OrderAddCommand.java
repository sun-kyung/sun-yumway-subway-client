package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sun.yumway.subway.domain.Order;
import sun.yumway.subway.util.Prompt;

public class OrderAddCommand implements Command {

  ObjectOutputStream out;
  ObjectInputStream in;
  Prompt prompt;

  public OrderAddCommand(ObjectOutputStream out, ObjectInputStream in, Prompt prompt) {
    this.prompt = prompt;
    this.out = out;
    this.in = in;
  }

  @Override
  public void execute() {
    Order order = new Order();
    order.setNo(prompt.inputInt("번호? "));
    order.setBread(prompt.inputString("빵? "));
    order.setMain(prompt.inputString("메인? "));
    order.setCheese(prompt.inputString("치즈? "));
    order.setVegetable(prompt.inputString("채소? "));
    order.setSauce(prompt.inputString("소스? "));
    try {
      out.writeUTF("/order/add");
      out.writeObject(order);
      out.flush();

      String response = in.readUTF();
      if (response.equals("FAIL")) {
        System.out.println(in.readUTF());
        return;
      }
      System.out.println("저장하였습니다");
    } catch (Exception e) {
      System.out.println("통신 오류 발생");
    }
  }
}
