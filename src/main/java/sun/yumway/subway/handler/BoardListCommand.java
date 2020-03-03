package sun.yumway.subway.handler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import sun.yumway.subway.dao.BoardDao;
import sun.yumway.subway.domain.Board;

public class BoardListCommand implements Command {
  BoardDao boardDao;

  public BoardListCommand(BoardDao boardDao) {
    this.boardDao = boardDao;
  }


  @SuppressWarnings("unchecked")
  @Override
  public void execute() {
    try {
      List<Board> boards = boardDao.findAll();
      for (Board b : boards) {
        System.out.printf("%d, %s, %s, %s, %s\n", b.getNo(), b.getTitle(), b.getContents(),
            b.getToday(), b.getViewCount());
      }
    } catch (Exception e) {
      System.out.println("통신 오류 발생");
      e.printStackTrace();
    }
  }
}


