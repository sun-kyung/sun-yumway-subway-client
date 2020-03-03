package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sun.yumway.subway.dao.OrderDao;
import sun.yumway.subway.domain.Order;
import sun.yumway.subway.util.Prompt;

public class OrderDetailCommand implements Command {

  OrderDao orderDao;
  Prompt prompt;

  public OrderDetailCommand(OrderDao orderDao, Prompt prompt) {
    this.prompt = prompt;
    this.orderDao = orderDao;
  }

  @Override
  public void execute() {
    try {
      int no = prompt.inputInt("번호? ");

      Order order = orderDao.findByNo(no);

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
