package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sun.yumway.subway.dao.OrderDao;
import sun.yumway.subway.domain.Order;
import sun.yumway.subway.util.Prompt;

public class OrderUpdateCommand implements Command {

  OrderDao orderDao;
  Prompt prompt;

  public OrderUpdateCommand(OrderDao orderDao, Prompt prompt) {
    this.prompt = prompt;
    this.orderDao = orderDao;
  }

  @Override
  public void execute() {
    try {
      int no = prompt.inputInt("번호? ");

      Order oldOrder = null;
      try {
        oldOrder = orderDao.findByNo(no);
      } catch (Exception e) {
        System.out.println("해당 번호의 샌드위치가 없습니다");
        return;
      }
      Order newOrder = new Order();
      newOrder.setBread(
          prompt.inputString(String.format("빵(%s)? ", oldOrder.getBread()), oldOrder.getBread()));
      newOrder.setMain(
          prompt.inputString(String.format("메인(%s)? ", oldOrder.getMain()), oldOrder.getMain()));
      newOrder.setCheese(prompt.inputString(String.format("치즈(%s)? ", oldOrder.getCheese()),
          oldOrder.getCheese()));
      newOrder.setVegetable(prompt.inputString(String.format("채소(%s)? ", oldOrder.getVegetable()),
          oldOrder.getVegetable()));
      newOrder.setSauce(
          prompt.inputString(String.format("소스(%s)? ", oldOrder.getSauce()), oldOrder.getSauce()));

      newOrder.setNo(oldOrder.getNo());

      if (oldOrder.equals(newOrder)) {
        System.out.println("게시물 변경을 취소했습니다");
        return;
      }
      orderDao.update(newOrder);
      System.out.println("게시글을 변경했습니다");
    } catch (Exception e) {
      System.out.println("명령 실행 중 오류 발생");
    }
  }
}
