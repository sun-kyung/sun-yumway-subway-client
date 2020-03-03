package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import sun.yumway.subway.dao.BoardDao;
import sun.yumway.subway.domain.Board;
import sun.yumway.subway.util.Prompt;

public class BoardDetailCommand implements Command {
  BoardDao boardDao;
  Prompt prompt;

  public BoardDetailCommand(BoardDao boardDao, Prompt prompt) {
    this.boardDao = boardDao;
    this.prompt = prompt;
  }

  @Override
  public void execute() {
    try {
      int no = prompt.inputInt("번호? ");

      Board board = boardDao.findByNo(no);

      System.out.printf("번호: %s\n", board.getNo());
      System.out.printf("제목: %s\n", board.getTitle());
      System.out.printf("내용: %s\n", board.getContents());
      System.out.printf("작성일: %s\n", board.getToday());
      System.out.printf("조회수: %s\n", board.getViewCount());
    } catch (Exception e) {
      System.out.println("명령 실행 중 오류 발생");
    }
  }

}


