package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sun.yumway.subway.dao.OrderDao;
import sun.yumway.subway.domain.Order;
import sun.yumway.subway.util.Prompt;

public class OrderAddCommand implements Command {

  OrderDao orderDao;
  Prompt prompt;

  public OrderAddCommand(OrderDao orderDao, Prompt prompt) {
    this.prompt = prompt;
    this.orderDao = orderDao;
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
      orderDao.insert(order);
      System.out.println("저장하였습니다");
    } catch (Exception e) {
      System.out.println("통신 오류 발생");
    }
  }
}
