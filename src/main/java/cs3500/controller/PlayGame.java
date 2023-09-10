package cs3500.controller;


import cs3500.model.ComputerPlayer;
import cs3500.model.Coord;
import cs3500.model.GameBoard;
import cs3500.model.GameResult;
import cs3500.model.Ship;
import cs3500.model.ShipType;
import cs3500.model.UserPlayer;
import cs3500.view.GameDisplay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Represents the controller for the game of BattleSalvo.
 */
public class PlayGame implements Controller {
  private UserPlayer player1;
  private ComputerPlayer player2;
  private final GameDisplay view;
  private GameBoard board;
  private final Random random;
  private final Scanner sc;

  /**
   * Constructs a PlayGame object.
   *
   * @param rd   the readable
   * @param ap   the appendable
   * @param seed the seed
   */
  public PlayGame(Readable rd, Appendable ap, int seed) {
    this.random = new Random(seed);
    this.player1 = null;
    this.player2 = null;
    this.view = new GameDisplay(ap);
    this.sc = new Scanner(rd);
  }

  /**
   * Constructs a PlayGame object.
   *
   * @param rd the readable
   * @param ap the appendable
   */
  public PlayGame(Readable rd, Appendable ap) {
    this.random = new Random();
    this.player1 = null;
    this.player2 = null;
    this.view = new GameDisplay(ap);
    this.sc = new Scanner(rd);
  }

  /**
   * Runs the game.
   */
  public void run() {
    view.displayGameStart();
    initialize();

  }

  /**
   * Initializes the game.
   */
  private void initialize() {
    view.getBoardSize();
    int boardSizeX;
    int boardSizeY;

    try {
      boardSizeX = Integer.parseInt(sc.next());
      boardSizeY = Integer.parseInt(sc.next());
    } catch (NumberFormatException e) {
      view.invalidBoardSize();
      initialize();
      return;
    }

    if (boardSizeX < 6 || boardSizeX > 15 || boardSizeY < 6 || boardSizeY > 15) {
      view.invalidBoardSize();
      initialize();
    } else {
      HashMap<ShipType, Integer> specs = getShipSpecifications(boardSizeX, boardSizeY);
      setupGame(boardSizeX, boardSizeY, specs);
      playGame(boardSizeX, boardSizeY);
    }
  }


  /**
   * Gets the ship specifications.
   *
   * @param boardSizeX the board size x
   * @param boardSizeY the board size y
   * @return the ship specifications
   */
  private HashMap<ShipType, Integer> getShipSpecifications(int boardSizeX, int boardSizeY) {
    HashMap<ShipType, Integer> specs = new HashMap<>();
    specs = (HashMap<ShipType, Integer>) getShips(boardSizeX, boardSizeY, specs);
    return specs;
  }

  /**
   * Sets up the game.
   *
   * @param boardSizeX the board size x
   * @param boardSizeY the board size y
   * @param specs      the ship specifications
   */
  private void setupGame(int boardSizeX, int boardSizeY, HashMap<ShipType, Integer> specs) {
    this.board = new GameBoard(random, boardSizeX, boardSizeY);
    player1 = new UserPlayer(board, random);
    player2 = new ComputerPlayer(board, random);

    ArrayList<Ship> list1 = (ArrayList<Ship>) player1.setup(boardSizeY, boardSizeX, specs);
    ArrayList<Ship> list2 = (ArrayList<Ship>) player2.setup(boardSizeY, boardSizeX, specs);
    board.setUserShipList(list1);
    board.setOpponentShipList(list2);
    board.genBoards();
  }

  /**
   * Game loop for the game.
   *
   * @param boardSizeX the board size x
   * @param boardSizeY the board size y
   */
  private void playGame(int boardSizeX, int boardSizeY) {
    ArrayList<Coord> hitsOnComputer = new ArrayList<>();
    ArrayList<Coord> hitsOnUser = new ArrayList<>();
    ArrayList<Coord> missesOnComputer = new ArrayList<>();
    ArrayList<Coord> missesOnUser = new ArrayList<>();

    String[][] userBoard = this.board.getBoard();
    String[][] compBoard = this.board.getOpponentBoard();

    while (gameOver()) {
      // Update boards
      board.updateBoard(userBoard, hitsOnUser, missesOnUser);
      board.updateBoard(compBoard, hitsOnComputer, missesOnComputer);

      view.makeBoard(player2, compBoard);
      view.makeBoard(player1, userBoard);

      // Get shots
      getShots(board.getActiveUserShips(), boardSizeX, boardSizeY);
      board.generateShots();

      // Get hits
      hitsOnComputer = (ArrayList<Coord>) player2.reportDamage(player1.takeShots());
      hitsOnUser = (ArrayList<Coord>) player1.reportDamage(player2.takeShots());

      // Get misses
      missesOnComputer = (ArrayList<Coord>) board.reportMisses(hitsOnComputer, player1.takeShots());
      missesOnUser = (ArrayList<Coord>) board.reportMisses(hitsOnUser, player2.takeShots());
    }
  }

  /**
   * checks if the game is over.
   *
   * @return true if the game is not over, false otherwise
   */
  private boolean gameOver() {
    if (board.getActiveUserShips() == 0 && board.getActiveCompShips() == 0) {
      view.displayGameResult(GameResult.TIE);
      return false;
    } else if (board.getActiveUserShips() == 0) {
      view.displayGameResult(GameResult.LOSE);
      return false;
    } else if (board.getActiveCompShips() == 0) {
      view.displayGameResult(GameResult.WIN);
      return false;
    } else {
      return true;
    }
  }

  /**
   * Gets the shots from the user.
   *
   * @param activeShips the number of active ships for the user
   * @param width       the width of the board
   * @param height      the height of the board
   */
  private void getShots(int activeShips, int width, int height) {
    view.askForShot(activeShips);
    ArrayList<Coord> shots = new ArrayList<Coord>();
    int i = 0;
    while (i < activeShips) {
      int x;
      int y;
      try {
        x = Integer.parseInt(sc.next());
        y = Integer.parseInt(sc.next());
      } catch (NumberFormatException e) {
        view.invalidShot();
        i = 0; // Reset i to restart the loop
        getShots(activeShips, width, height);
        return;
      }
      Coord c = new Coord(x, y);
      if (c.getX() < 0 || c.getX() >= width || c.getY() < 0 || c.getY() >= height) {
        shots.clear();
        view.invalidShot();
        view.askForShot(activeShips);
        i = 0; // Reset i to restart the loop
      } else {
        shots.add(c);
        i++;
      }
    }
    board.setUserCurrVolley(shots);
  }

  /**
   * Gets the fleet from the user.
   *
   * @param boardSizeX the x size of the board
   * @param boardSizeY the y size of the board
   * @param specs      the specifications of the ships
   * @return the map of ships
   */
  private Map<ShipType, Integer> getShips(int boardSizeX, int boardSizeY,
                                          Map<ShipType, Integer> specs) {
    view.getFleetSize(Math.min(boardSizeX, boardSizeY));
    int carriers;
    int battleship;
    int destroyer;
    int submarine;

    try {
      carriers = Integer.parseInt(sc.next());
      battleship = Integer.parseInt(sc.next());
      destroyer = Integer.parseInt(sc.next());
      submarine = Integer.parseInt(sc.next());
    } catch (NumberFormatException e) {
      view.invalidFleetSize();
      return getShips(boardSizeX, boardSizeY, specs);
    }

    if ((carriers + battleship + destroyer + submarine > Math.min(boardSizeX, boardSizeY)
        || carriers <= 0
        || battleship <= 0
        || destroyer <= 0
        || submarine <= 0)) {
      view.invalidFleetSize();
      return getShips(boardSizeX, boardSizeY, specs);
    } else {
      specs.put(ShipType.CARRIER, carriers);
      specs.put(ShipType.BATTLESHIP, battleship);
      specs.put(ShipType.DESTROYER, destroyer);
      specs.put(ShipType.SUBMARINE, submarine);
      return specs;
    }
  }
}
