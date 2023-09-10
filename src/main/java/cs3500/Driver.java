package cs3500;

import cs3500.controller.PlayGame;
import cs3500.controller.ProxyController;
import cs3500.model.AiPlayer;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * The driver.
 */
public class Driver {
  /**
   * This method connects to the server at the given host and port, builds a proxy referee
   * to handle communication with the server, and sets up a client player.
   *
   * @param host the server host
   * @param port the server port
   * @throws IOException if there is a communication issue with the server
   */
  private static void runClient(String host, int port) {
    Socket server = null;
    try {
      server = new Socket(host, port);
    } catch (IOException e) {
      System.out.println("Unable to connect to server");
    }
    ProxyController proxyController = null;
    try {
      proxyController = new ProxyController(server, new AiPlayer());
      proxyController.run();
    } catch (IOException e) {
      System.out.println("Unable to connect to server");
    }
  }

  /**
   * Runs the program depending on if you want to play against a server or the computer.
   *
   * @param args The expected parameters are the server's host and port
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      PlayGame play = new PlayGame(new InputStreamReader(System.in), System.out);
      play.run();
    } else if (args.length == 2) {
      String host = args[0];
      int port = Integer.parseInt(args[1]);
      Driver.runClient(host, port);
    } else {
      throw new IllegalArgumentException("Invalid number of arguments");
    }
  }
}
