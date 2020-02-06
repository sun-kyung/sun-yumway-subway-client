package sun.yumway.subway;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientApp {
  public static void main(String[] args) throws Exception {
    System.out.println("클라이언트 맛있는 서브웨이 시스템입니다");

    Scanner keyScan = new Scanner(System.in);
    String serverAddr = null;
    int port = 0;
    try {
      System.out.print("서버? ");
      serverAddr = keyScan.nextLine();
      System.out.print("포트? ");
      port = Integer.parseInt(keyScan.nextLine());

    } catch (Exception e) {
      System.out.println("서버 주소 또는 포트번호가 유효하지 않습니다");
      return;
    }

    try (Socket socket = new Socket("localhost", 9999);
        PrintStream out = new PrintStream(socket.getOutputStream());
        Scanner in = new Scanner(socket.getInputStream())) {

      System.out.println("서버와 연결되었음");
      System.out.println("서버에 보낼 메시지");
      String sendMsg = keyScan.nextLine();
      out.println(sendMsg);
      System.out.println("서버에 메시지를 전송하였음");
      String message = in.nextLine();
      System.out.println("서버로부터 메시지를 수신하였음");
      System.out.println("서버: " + message);
      System.out.println("서버와 연결을 끊었음");
    } catch (Exception e) {
      System.out.println("예외 발생: ");
      e.printStackTrace();
    }
    keyScan.close();
  }
}


