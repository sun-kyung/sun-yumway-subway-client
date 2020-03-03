package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sun.yumway.subway.dao.SideDao;
import sun.yumway.subway.util.Prompt;

public class SideDeleteCommand implements Command {

  Prompt prompt;
  SideDao sideDao;

  public SideDeleteCommand(SideDao sideDao, Prompt prompt) {
    this.prompt = prompt;
    this.sideDao = sideDao;
  }


  @Override
  public void execute() {

    try {
    int no = prompt.inputInt("번호? ");
    sideDao.delete(no);
      System.out.println("사이드를 삭제했습니다");
    } catch (Exception e) {
      System.out.println("명령 실행 중 오류 발생");
    }
  }
}
