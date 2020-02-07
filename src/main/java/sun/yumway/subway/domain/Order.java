package sun.yumway.subway.domain;

import java.io.Serializable;

public class Order implements Serializable {

  private static final long serialVersionUID = 20200131L;
  private int no;
  private String bread;
  private String main;
  private String cheese;
  private String vegetable;
  private String sauce;

  public static Order valueOf(String csv) {
    String[] data = csv.split(",");

    Order order = new Order();
    order.setNo(Integer.parseInt(data[0]));
    order.setBread(data[1]);
    order.setMain(data[2]);
    order.setCheese(data[3]);
    order.setVegetable(data[4]);
    order.setSauce(data[5]);

    return order;
  }

  public String toCsvString() {
    return String.format("%d,%s,%s,%s,%s,%s", this.getNo(), this.getBread(), this.getMain(),
        this.getCheese(), this.getVegetable(), this.getSauce());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bread == null) ? 0 : bread.hashCode());
    result = prime * result + ((cheese == null) ? 0 : cheese.hashCode());
    result = prime * result + ((main == null) ? 0 : main.hashCode());
    result = prime * result + no;
    result = prime * result + ((sauce == null) ? 0 : sauce.hashCode());
    result = prime * result + ((vegetable == null) ? 0 : vegetable.hashCode());
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
    Order other = (Order) obj;
    if (bread == null) {
      if (other.bread != null)
        return false;
    } else if (!bread.equals(other.bread))
      return false;
    if (cheese == null) {
      if (other.cheese != null)
        return false;
    } else if (!cheese.equals(other.cheese))
      return false;
    if (main == null) {
      if (other.main != null)
        return false;
    } else if (!main.equals(other.main))
      return false;
    if (no != other.no)
      return false;
    if (sauce == null) {
      if (other.sauce != null)
        return false;
    } else if (!sauce.equals(other.sauce))
      return false;
    if (vegetable == null) {
      if (other.vegetable != null)
        return false;
    } else if (!vegetable.equals(other.vegetable))
      return false;
    return true;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getBread() {
    return bread;
  }

  public void setBread(String bread) {
    this.bread = bread;
  }

  public String getMain() {
    return main;
  }

  public void setMain(String main) {
    this.main = main;
  }

  public String getCheese() {
    return cheese;
  }

  public void setCheese(String cheese) {
    this.cheese = cheese;
  }

  public String getVegetable() {
    return vegetable;
  }

  public void setVegetable(String vegetable) {
    this.vegetable = vegetable;
  }

  public String getSauce() {
    return sauce;
  }

  public void setSauce(String sauce) {
    this.sauce = sauce;
  }

}
