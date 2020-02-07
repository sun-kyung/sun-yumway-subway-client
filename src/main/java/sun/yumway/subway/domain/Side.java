package sun.yumway.subway.domain;

import java.io.Serializable;

public class Side implements Serializable {

  private static final long serialVersionUID = 20200131L;
  private int no;
  private String cookie;
  private String beverage;
  private String others;

  public static Side valueOf(String csv) {
    String[] data = csv.split(",");

    Side side = new Side();
    side.setNo(Integer.parseInt(data[0]));
    side.setCookie(data[1]);
    side.setBeverage(data[2]);
    side.setOthers(data[3]);
    return side;
  }

  public String toCsvString() {
    return String.format("%d,%s,%s,%s", this.getNo(), this.getCookie(), this.getBeverage(),
        this.getOthers());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((beverage == null) ? 0 : beverage.hashCode());
    result = prime * result + ((cookie == null) ? 0 : cookie.hashCode());
    result = prime * result + no;
    result = prime * result + ((others == null) ? 0 : others.hashCode());
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
    Side other = (Side) obj;
    if (beverage == null) {
      if (other.beverage != null)
        return false;
    } else if (!beverage.equals(other.beverage))
      return false;
    if (cookie == null) {
      if (other.cookie != null)
        return false;
    } else if (!cookie.equals(other.cookie))
      return false;
    if (no != other.no)
      return false;
    if (others == null) {
      if (other.others != null)
        return false;
    } else if (!others.equals(other.others))
      return false;
    return true;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getCookie() {
    return cookie;
  }

  public void setCookie(String cookie) {
    this.cookie = cookie;
  }

  public String getBeverage() {
    return beverage;
  }

  public void setBeverage(String beverage) {
    this.beverage = beverage;
  }

  public String getOthers() {
    return others;
  }

  public void setOthers(String others) {
    this.others = others;
  }

}
