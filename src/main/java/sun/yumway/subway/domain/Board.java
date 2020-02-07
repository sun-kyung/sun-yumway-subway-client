package sun.yumway.subway.domain;

import java.io.Serializable;
import java.sql.Date;

public class Board implements Serializable {

  private static final long serialVersionUID = 20200131L;
  private int no;
  private String title;
  private String contents;
  private Date today;
  private int viewCount;

  public static Board valueOf(String csv) {
    String[] data = csv.split(",");

    Board board = new Board();
    board.setNo(Integer.parseInt(data[0]));
    board.setTitle(data[1]);
    board.setContents(data[2]);
    board.setToday(Date.valueOf(data[3]));
    board.setViewCount(Integer.parseInt(data[4]));

    return board;
  }

  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%d", this.getNo(), this.getTitle(), this.getContents(),
        this.getToday(), this.getViewCount());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((contents == null) ? 0 : contents.hashCode());
    result = prime * result + no;
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    result = prime * result + ((today == null) ? 0 : today.hashCode());
    result = prime * result + viewCount;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Board other = (Board) obj;
    if (contents == null) {
      if (other.contents != null)
        return false;
    } else if (!contents.equals(other.contents))
      return false;
    if (no != other.no)
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    if (today == null) {
      if (other.today != null)
        return false;
    } else if (!today.equals(other.today))
      return false;
    if (viewCount != other.viewCount)
      return false;
    return true;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContents() {
    return contents;
  }

  public void setContents(String contents) {
    this.contents = contents;
  }

  public Date getToday() {
    return today;
  }

  public void setToday(Date today) {
    this.today = today;
  }

  public int getViewCount() {
    return viewCount;
  }

  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

}
