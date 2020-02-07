package sun.yumway.subway;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import sun.yumway.subway.handler.BoardAddCommand;
import sun.yumway.subway.handler.BoardListCommand;
import sun.yumway.subway.handler.Command;
import sun.yumway.subway.handler.OrderAddCommand;
import sun.yumway.subway.handler.OrderDeleteCommand;
import sun.yumway.subway.handler.OrderDetailCommand;
import sun.yumway.subway.handler.OrderListCommand;
import sun.yumway.subway.handler.OrderUpdateCommand;
import sun.yumway.subway.handler.SideAddCommand;
import sun.yumway.subway.handler.SideDeleteCommand;
import sun.yumway.subway.handler.SideDetailCommand;
import sun.yumway.subway.handler.SideListCommand;
import sun.yumway.subway.handler.SideUpdateCommand;
import sun.yumway.subway.util.Prompt;

public class ClientApp {
  Scanner keyboard = new Scanner(System.in);
  Prompt prompt = new Prompt(keyboard);

  public void service() {
    Scanner keyboard = new Scanner(System.in);
    String serverAddr = null;
    int port = 0;
    try {
      serverAddr = prompt.inputString("서버? ");
      port = prompt.inputInt("포트? ");

    } catch (Exception e) {
      System.out.println("서버 주소 또는 포트번호가 유효하지 않습니다");
      keyboard.close();
      return;
    }

    try (Socket socket = new Socket(serverAddr, port);
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

      System.out.println("서버와 연결되었음");

      processCommand(out, in);

      System.out.println("서버와 연결을 끊었음");
    } catch (Exception e) {
      System.out.println("예외 발생: ");
      e.printStackTrace();
    }
    keyboard.close();
  }

  private void processCommand(ObjectOutputStream out, ObjectInputStream in) {
    Deque<String> commandStack = new ArrayDeque<>();
    Queue<String> commandQueue = new LinkedList<>();
    HashMap<String, Command> commandMap = new HashMap<>();
    commandMap.put("/board/list", new BoardListCommand(out, in));
    commandMap.put("/board/add", new BoardAddCommand(out, in, prompt));
    commandMap.put("/board/detail", new BoardAddCommand(out, in, prompt));
    commandMap.put("/board/update", new BoardAddCommand(out, in, prompt));
    commandMap.put("/board/delete", new BoardAddCommand(out, in, prompt));
    commandMap.put("/order/list", new OrderListCommand(out, in));
    commandMap.put("/order/add", new OrderAddCommand(out, in, prompt));
    commandMap.put("/order/detail", new OrderDetailCommand(out, in, prompt));
    commandMap.put("/order/update", new OrderUpdateCommand(out, in, prompt));
    commandMap.put("/order/delete", new OrderDeleteCommand(out, in, prompt));
    commandMap.put("/side/list", new SideListCommand(out, in));
    commandMap.put("/side/add", new SideAddCommand(out, in, prompt));
    commandMap.put("/side/detail", new SideDetailCommand(out, in, prompt));
    commandMap.put("/side/update", new SideUpdateCommand(out, in, prompt));
    commandMap.put("/side/delete", new SideDeleteCommand(out, in, prompt));

    try {
      while (true) {
        String command;
        command = prompt.inputString("\n명령> ");
        if (command.length() == 0)
          continue;
        if (command.equals("quit")) {
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
    } catch (Exception e) {
      System.out.println("프로그램 실행 중 오류 발생");
    }
    keyboard.close();
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
  }
}


