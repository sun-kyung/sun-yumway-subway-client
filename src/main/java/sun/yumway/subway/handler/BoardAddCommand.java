package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import sun.yumway.subway.domain.Board;
import sun.yumway.subway.util.Prompt;

public class BoardAddCommand implements Command {
  ObjectOutputStream out;
  ObjectInputStream in;
  Prompt prompt;

  public BoardAddCommand(ObjectOutputStream out, ObjectInputStream in, Prompt prompt) {
    this.prompt = prompt;
    this.out = out;
    this.in = in;
  }

  @Override
  public void execute() {
    Board board = new Board();
    board.setNo(prompt.inputInt("번호?"));
    board.setTitle(prompt.inputString("제목?"));
    board.setContents(prompt.inputString("내용?"));
    board.setToday(new Date(System.currentTimeMillis()));
    board.setViewCount(0);

    try {
      out.writeUTF("/board/add");
      out.writeObject(board);
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


