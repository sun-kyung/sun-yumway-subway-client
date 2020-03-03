package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import sun.yumway.subway.dao.SideDao;
import sun.yumway.subway.domain.Side;

public class SideListCommand implements Command {

  SideDao sideDao;

  public SideListCommand(SideDao sideDao) {
    this.sideDao = sideDao;
  }


  @SuppressWarnings("unchecked")
  @Override
  public void execute() {
    try {
      List<Side> sides = sideDao.findAll();
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
