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

import sun.yumway.subway.dao.proxy.BoardDaoProxy;
import sun.yumway.subway.dao.proxy.OrderDaoProxy;
import sun.yumway.subway.dao.proxy.SideDaoProxy;
import sun.yumway.subway.handler.BoardAddCommand;
import sun.yumway.subway.handler.BoardDeleteCommand;
import sun.yumway.subway.handler.BoardDetailCommand;
import sun.yumway.subway.handler.BoardListCommand;
import sun.yumway.subway.handler.BoardUpdateCommand;
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

    BoardDaoProxy boardDao = new BoardDaoProxy(in, out);
    OrderDaoProxy orderDao = new OrderDaoProxy(in, out);
    SideDaoProxy sideDao = new SideDaoProxy(in, out);

    HashMap<String, Command> commandMap = new HashMap<>();
    commandMap.put("/board/list", new BoardListCommand(boardDao));
    commandMap.put("/board/add", new BoardAddCommand(boardDao, prompt));
    commandMap.put("/board/detail", new BoardDetailCommand(boardDao, prompt));
    commandMap.put("/board/update", new BoardUpdateCommand(boardDao, prompt));
    commandMap.put("/board/delete", new BoardDeleteCommand(boardDao, prompt));
    commandMap.put("/order/list", new OrderListCommand(orderDao));
    commandMap.put("/order/add", new OrderAddCommand(orderDao, prompt));
    commandMap.put("/order/detail", new OrderDetailCommand(orderDao, prompt));
    commandMap.put("/order/update", new OrderUpdateCommand(orderDao, prompt));
    commandMap.put("/order/delete", new OrderDeleteCommand(orderDao, prompt));
    commandMap.put("/side/list", new SideListCommand(sideDao));
    commandMap.put("/side/add", new SideAddCommand(sideDao, prompt));
    commandMap.put("/side/detail", new SideDetailCommand(sideDao, prompt));
    commandMap.put("/side/update", new SideUpdateCommand(sideDao, prompt));
    commandMap.put("/side/delete", new SideDeleteCommand(sideDao, prompt));

    try {
      while (true) {
        String command;
        command = prompt.inputString("\n명령> ");
        if (command.length() == 0)
          continue;
        if (command.equals("quit") || command.equals("/server/stop")) {
          out.writeUTF(command);
          out.flush();
          System.out.println("서버: " + in.readUTF());
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


