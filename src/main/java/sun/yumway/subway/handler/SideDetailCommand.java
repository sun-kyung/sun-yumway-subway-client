package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sun.yumway.subway.domain.Side;
import sun.yumway.subway.util.Prompt;

public class SideDetailCommand implements Command {

  Prompt prompt;
  ObjectOutputStream out;
  ObjectInputStream in;

  public SideDetailCommand(ObjectOutputStream out, ObjectInputStream in, Prompt prompt) {
    this.prompt = prompt;
    this.out = out;
    this.in = in;
  }


  @Override
  public void execute() {
    try {
      int no = prompt.inputInt("번호? ");

      out.writeUTF("/side/detail");
      out.writeInt(no);
      out.flush();

      String response = in.readUTF();

      if (response.equals("FAIL")) {
        System.out.println(in.readUTF());
        return;
      }
      Side side = (Side) in.readObject();
      System.out.printf("쿠키: %s\n", side.getCookie());
      System.out.printf("음료: %s\n", side.getBeverage());
      System.out.printf("그 외: %s\n", side.getOthers());
    } catch (Exception e) {
      System.out.println("명령 실행 중 오류 발생");
    }
  }
}
