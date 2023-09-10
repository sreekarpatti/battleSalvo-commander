package cs3500.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Maintains the state of the game board.
 */
public class GameBoard {
  private final int width; // number of columns
  private final int height; // number of rows
  private String[][] board; // the board
  private String[][] opponentBoard; // the opponent's board
  private ArrayList<Ship> userShipList; // the user's ships
  private ArrayList<Ship> opponentShipList; // the opponent's ships
  private final ArrayList<Coord> compAllShots = new ArrayList<>();
  // all shots taken by the computer
  private ArrayList<Coord> userCurrVolley = new ArrayList<>(); // the user's current volley
  private ArrayList<Coord> compCurrVolley = new ArrayList<>(); // the computer's current volley
  private final Random random; // the random number generator

  /**
   * Constructs a GameBoard object.
   *
   * @param random the random number generator
   * @param width  the width of the board
   * @param height the height of the board
   */
  public GameBoard(Random random, int width, int height) {
    this.random = random;
    this.width = width;
    this.height = height;
  }

  /**
   * Generates the user's and the opponent's boards.
   */
  public void genBoards() {
    this.board = generateBoard();
    this.opponentBoard = generateOppBoard();
  }

  /**
   * generates the opponent's board.
   *
   * @return the opponent's board
   */
  private String[][] generateOppBoard() {
    String[][] board = new String[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        board[y][x] = " - ";
      }
    }
    return board;
  }

  /**
   * Generates the user's board.
   *
   * @return the user's board
   */
  private String[][] generateBoard() {
    String[][] board = new String[height][width];
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        board[y][x] = " - ";
      }
    }
    for (Ship ship : userShipList) {
      for (Coord c : ship.getCoords()) {
        board[c.getY()][c.getX()] = ship.getSymbol();
      }
    }
    return board;
  }

  /**
   * Updates the given board with the given hits and misses.
   *
   * @param userBoard the board to be updated
   * @param hits      the hits to be added to the board
   * @param misses    the misses to be added to the board
   */
  public void updateBoard(String[][] userBoard,
                          ArrayList<Coord> hits,
                          ArrayList<Coord> misses) {
    for (Coord c : misses) {
      userBoard[c.getY()][c.getX()] = " M ";
    }
    for (Coord c : hits) {
      userBoard[c.getY()][c.getX()] = " H ";
    }
  }

  /**
   * Generates the computer's shots.
   * Mutates the compAllShots and compCurrVolley fields.
   */
  public void generateShots() {
    ArrayList<Coord> shots = new ArrayList<>();
    int maxShots = (height * width) - compAllShots.size();
    for (int i = 0; i < Math.min(getActiveCompShips(), maxShots); i++) {
      boolean flag = true;
      while (flag) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        Coord c = new Coord(x, y);
        if (!compAllShots.contains(c)) {
          compAllShots.add(c);
          shots.add(c);
          flag = false;
        }
      }
      compCurrVolley = shots;
    }
  }

  /**
   * gets the misses from the given list of hits and coordinates.
   *
   * @param hits   the hits
   * @param coords all shots
   * @return the misses
   */
  public List<Coord> reportMisses(ArrayList<Coord> hits, List<Coord> coords) {
    ArrayList<Coord> misses = new ArrayList<>();
    for (Coord c : coords) {
      if (!hits.contains(c)) {
        misses.add(c);
      }
    }
    return misses;
  }

  /**
   * Gets the number of active ships in the given list of ships.
   *
   * @param shipList the list of ships
   * @return the number of active ships
   */
  private int getActiveShips(ArrayList<Ship> shipList) {
    int count = 0;
    for (Ship s : shipList) {
      if (!s.isSunk()) {
        count++;
      }
    }
    return count;
  }

  /**
   * Gets the number of active ships for the user.
   *
   * @return the number of active ships
   */
  public int getActiveUserShips() {
    return getActiveShips(userShipList);
  }

  /**
   * Gets the number of active ships for the computer.
   *
   * @return the number of active ships
   */
  public int getActiveCompShips() {
    return getActiveShips(opponentShipList);
  }

  /**
   * Sets the user's ships.
   *
   * @param userShipList the user's ships
   */
  public void setUserShipList(ArrayList<Ship> userShipList) {
    this.userShipList = userShipList;
  }

  /**
   * Sets the opponent's ships.
   *
   * @param opponentShipList the opponent's ships
   */
  public void setOpponentShipList(ArrayList<Ship> opponentShipList) {
    this.opponentShipList = opponentShipList;
  }

  /**
   * Sets the user's current volley.
   *
   * @param shots the user's current volley
   */
  public void setUserCurrVolley(ArrayList<Coord> shots) {
    this.userCurrVolley = shots;
  }

  /**
   * Gets the user's current volley.
   *
   * @return the user's current volley
   */
  public List<Coord> getUserCurrVolley() {
    return this.userCurrVolley;
  }

  /**
   * Gets the computer's current volley.
   *
   * @return the computer's current volley
   */
  public List<Coord> getCompCurrVolley() {
    return this.compCurrVolley;
  }

  /**
   * Gets the board.
   *
   * @return the board
   */
  public String[][] getBoard() {
    return this.board;
  }

  /**
   * Gets the opponent's board.
   *
   * @return the opponent's board
   */
  public String[][] getOpponentBoard() {
    return this.opponentBoard;
  }
}
