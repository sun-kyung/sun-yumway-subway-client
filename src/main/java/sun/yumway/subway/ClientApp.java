package sun.yumway.subway;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import sun.yumway.subway.handler.Command;
import sun.yumway.subway.util.Prompt;

public class ClientApp {
  Scanner keyboard = new Scanner(System.in);
  Prompt prompt = new Prompt(keyboard);

  public void service() {
    Deque<String> commandStack = new ArrayDeque<>();
    Queue<String> commandQueue = new LinkedList<>();
    HashMap<String, Command> commandMap = new HashMap<>();
    String command;

    while(true) {
      command = prompt.inputString("\n명령> ");
      if (command.length() == 0)
        continue;
      if(command.equals("quit")){
        System.out.println("안녕!");
        break;
      } else if (command.equals("history")) {
        printCommandHistory(commandStack.iterator());
        continue;
      } else if (command.equals("history2")) {
        printCommandHistory(commandQueue.iterator());
        continue;
      }
      commandStack.push(command);
      commandQueue.offer(command);
      Command commandHandler = commandMap.get(command);

      if (commandHandler != null) {
        try {
          commandHandler.execute();
        } catch (Exception e) {
          System.out.printf("명령어 실행 중 오류 발생: %s\n", e.getMessage());
        }
      } else if (!command.equalsIgnoreCase("quit")) {
        System.out.println("실행할 수 없는 명령입니다");
      }

    }
  }
  private void printCommandHistory(Iterator<String> iterator) {
    int count = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      count++;
      if ((count % 5) == 0) {
        System.out.println(":");
        String str = keyboard.nextLine();
        if (str.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }

  public static void main(String[] args) throws Exception {
    System.out.println("클라이언트 맛있는 서브웨이 시스템입니다");
    
    ClientApp app = new ClientApp();
    app.service();

    /*
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
    */
  }
}


