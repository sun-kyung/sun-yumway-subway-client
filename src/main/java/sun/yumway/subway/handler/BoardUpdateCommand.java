package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sun.yumway.subway.dao.BoardDao;
import sun.yumway.subway.domain.Board;
import sun.yumway.subway.util.Prompt;

public class BoardUpdateCommand implements Command {

  Prompt prompt;
  BoardDao boardDao;

  public BoardUpdateCommand(BoardDao boardDao, Prompt prompt) {
    this.prompt = prompt;
    this.boardDao = boardDao;
  }

  @Override
  public void execute() {
    try {
      int no = prompt.inputInt("번호? ");

      Board oldBoard = null;
      try {
        oldBoard = boardDao.findByNo(no);
      } catch (Exception e){
        System.out.println("해당 번호의 게시글이 없습니다");
        return;
      }
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
      boardDao.update(newBoard);

      System.out.println("게시글을 변경했습니다");
    } catch (Exception e) {
      System.out.println("명령 실행 중 오류 발생");
    }
  }
}


