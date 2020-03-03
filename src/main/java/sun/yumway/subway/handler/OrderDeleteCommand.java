package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sun.yumway.subway.dao.OrderDao;
import sun.yumway.subway.util.Prompt;

public class OrderDeleteCommand implements Command {

  OrderDao orderDao;
  Prompt prompt;

  public OrderDeleteCommand(OrderDao orderDao, Prompt prompt) {
    this.prompt = prompt;
    this.orderDao = orderDao;
  }

  @Override
  public void execute() {

    try {
    int no = prompt.inputInt("번호? ");
    orderDao.delete(no);
      System.out.println("주문을 삭제했습니다");
    } catch (Exception e) {
      System.out.println("명령 실행 중 오류 발생");
    }
  }
}
