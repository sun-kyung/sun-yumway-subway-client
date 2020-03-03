package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sun.yumway.subway.dao.SideDao;
import sun.yumway.subway.domain.Side;
import sun.yumway.subway.util.Prompt;

public class SideDetailCommand implements Command {

  Prompt prompt;
  SideDao sideDao;

  public SideDetailCommand(SideDao sideDao, Prompt prompt) {
    this.prompt = prompt;
    this.sideDao = sideDao;
  }


  @Override
  public void execute() {
    try {
      int no = prompt.inputInt("번호? ");
      Side side = sideDao.findByNo(no);
      System.out.printf("쿠키: %s\n", side.getCookie());
      System.out.printf("음료: %s\n", side.getBeverage());
      System.out.printf("그 외: %s\n", side.getOthers());
    } catch (Exception e) {
      System.out.println("명령 실행 중 오류 발생");
    }
  }
}
