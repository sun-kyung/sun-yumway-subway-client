package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import sun.yumway.subway.domain.Board;
import sun.yumway.subway.util.Prompt;

public class BoardUpdateCommand implements Command {
  ObjectOutputStream out;
  ObjectInputStream in;
  Prompt prompt;

  public BoardUpdateCommand(ObjectOutputStream out, ObjectInputStream in, Prompt prompt) {
    this.prompt = prompt;
    this.out = out;
    this.in = in;
  }

  @Override
  public void execute() {
    try {
      int no = prompt.inputInt("번호? ");

      out.writeUTF("/board/detail");
      out.writeInt(no);
      out.flush();

      String response = in.readUTF();
      if (response.equals("FAIL")) {
        System.out.println(in.readUTF());
        return;
      }

      Board oldBoard = (Board) in.readObject();
      Board newBoard = new Board();

      newBoard.setTitle(
          prompt.inputString(String.format("제목(%s)? ", oldBoard.getTitle()), oldBoard.getTitle()));

      newBoard.setContents(prompt.inputString(String.format("내용(%s)? ", oldBoard.getContents()),
          oldBoard.getContents()));

      newBoard.setNo(oldBoard.getNo());
      newBoard.setToday(oldBoard.getToday());
      newBoard.setViewCount(oldBoard.getViewCount());

      if (oldBoard.equals(newBoard)) {
        System.out.println("게시물 변경을 취소했습니다");
        return;
      }

      out.writeUTF("/board/update");
      out.writeObject(newBoard);
      out.flush();

      response = in.readUTF();
      if (response.equals("FAIL")) {
        System.out.println(in.readUTF());
        return;
      }
      System.out.println("게시글을 변경했습니다");
    } catch (Exception e) {
      System.out.println("명령 실행 중 오류 발생");
    }
  }
}


