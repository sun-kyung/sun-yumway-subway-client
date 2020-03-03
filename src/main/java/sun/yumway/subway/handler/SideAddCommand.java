package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sun.yumway.subway.dao.SideDao;
import sun.yumway.subway.domain.Side;
import sun.yumway.subway.util.Prompt;

public class SideAddCommand implements Command {

  Prompt prompt;
  SideDao sideDao;

  public SideAddCommand(SideDao sideDao, Prompt prompt) {
    this.prompt = prompt;
    this.sideDao = sideDao;
  }

  @Override
  public void execute() {
    Side side = new Side();
    side.setNo(prompt.inputInt("번호? "));
    side.setCookie(prompt.inputString("쿠키? "));
    side.setBeverage(prompt.inputString("음료? "));
    side.setOthers(prompt.inputString("그 외? "));
    try {
      sideDao.insert(side);
      System.out.println("저장하였습니다");
    } catch (Exception e) {
      System.out.println("통신 오류 발생");
    }
  }
}
