package sun.yumway.subway.handler;


import java.sql.Date;
import sun.yumway.subway.dao.BoardDao;
import sun.yumway.subway.domain.Board;
import sun.yumway.subway.util.Prompt;

public class BoardAddCommand implements Command {
  BoardDao boardDao;
  Prompt prompt;

  public BoardAddCommand(BoardDao boardDao, Prompt prompt) {
    this.boardDao = boardDao;
    this.prompt = prompt;
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
      boardDao.insert(board);
      System.out.println("저장하였습니다");
    } catch (Exception e) {
      System.out.println("통신 오류 발생");
    }
  }
}


