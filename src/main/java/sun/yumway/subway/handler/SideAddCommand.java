package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sun.yumway.subway.domain.Side;
import sun.yumway.subway.util.Prompt;

public class SideAddCommand implements Command {

  Prompt prompt;
  ObjectOutputStream out;
  ObjectInputStream in;

  public SideAddCommand(ObjectOutputStream out, ObjectInputStream in, Prompt prompt) {
    this.prompt = prompt;
    this.out = out;
    this.in = in;
  }

  @Override
  public void execute() {
    Side side = new Side();
    side.setNo(prompt.inputInt("번호? "));
    side.setCookie(prompt.inputString("쿠키? "));
    side.setBeverage(prompt.inputString("음료? "));
    side.setOthers(prompt.inputString("그 외? "));
    try {
      out.writeUTF("/side/add");
      out.writeObject(side);
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
