package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sun.yumway.subway.util.Prompt;

public class SideDeleteCommand implements Command {

  Prompt prompt;
  ObjectOutputStream out;
  ObjectInputStream in;

  public SideDeleteCommand(ObjectOutputStream out, ObjectInputStream in, Prompt prompt) {
    this.prompt = prompt;
    this.out = out;
    this.in = in;
  }


  @Override
  public void execute() {
    int no = prompt.inputInt("번호? ");

    try {
      out.writeUTF("/side/delete");
      out.writeInt(no);
      out.flush();

      String response = in.readUTF();
      if (response.equals("FAIL")) {
        System.out.println(in.readUTF());
        return;
      }
      System.out.println("사이드를 삭제했습니다");
    } catch (Exception e) {
      System.out.println("명령 실행 중 오류 발생");
    }
  }
}