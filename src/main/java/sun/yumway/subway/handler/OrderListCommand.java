package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import sun.yumway.subway.dao.OrderDao;
import sun.yumway.subway.domain.Order;

public class OrderListCommand implements Command {

  OrderDao orderDao;

  public OrderListCommand(OrderDao orderDao) {
    this.orderDao = orderDao;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void execute() {
    try {
      List<Order> orders = orderDao.findAll();
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
