package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sun.yumway.subway.domain.Side;
import sun.yumway.subway.util.Prompt;

public class SideUpdateCommand implements Command {

  Prompt prompt;
  ObjectOutputStream out;
  ObjectInputStream in;

  public SideUpdateCommand(ObjectOutputStream out, ObjectInputStream in, Prompt prompt) {
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
      Side oldSide = (Side) in.readObject();
      Side newSide = new Side();

      newSide.setCookie(
          prompt.inputString(String.format("쿠키(%s)? ", oldSide.getCookie()), oldSide.getCookie()));
      newSide.setBeverage(prompt.inputString(String.format("음료(%s)? ", oldSide.getBeverage()),
          oldSide.getBeverage()));
      newSide.setOthers(
          prompt.inputString(String.format("그 외(%s)? ", oldSide.getOthers()), oldSide.getOthers()));
      newSide.setNo(oldSide.getNo());

      if (oldSide.equals(newSide)) {
        System.out.println("사이드 변경을 취소했습니다");
        return;
      }
      out.writeUTF("/side/update");
      out.writeObject(newSide);
      out.flush();

      response = in.readUTF();
      if (response.equals("FAIL")) {
        System.out.println(in.readUTF());
        return;
      }
      System.out.println("사이드를 변경했습니다");
    } catch (Exception e) {
      System.out.println("명령 실행 중 오류 발생");
    }
  }

}
