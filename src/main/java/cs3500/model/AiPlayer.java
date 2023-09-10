package cs3500.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an AI player in the game of BattleSalvo.
 */
public class AiPlayer implements Player {
  private Random random;
  private ArrayList<Ship> shipList;
  private ArrayList<Coord> allShots;
  private int height;
  private int width;

  /**
   * Constructs an AIPlayer object.
   */
  public AiPlayer() {
    this.random = new Random();
    this.allShots = new ArrayList<>();
    this.height = 0;
    this.width = 0;
  }

  /**
   * Constructs an AIPlayer object with a seed.
   *
   * @param seed the seed for the random number generator
   */
  public AiPlayer(int seed) {
    this.random = new Random(seed);
    this.allShots = new ArrayList<>();
    this.height = 0;
    this.width = 0;
  }

  /**
   * Returns the name of the AIPlayer.
   *
   * @return the name of the AIPlayer
   */
  @Override
  public String name() {
    return "Krish-002"; // get github username
  }

  /**
   * Sets up the AIPlayer's board.
   *
   * @param height         the height of the board
   * @param width          the width of the board
   * @param specifications the specifications for the ships
   * @return a list of the AIPlayer's ships
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.height = height;
    this.width = width;
    int[][] currBoard = new int[height][width];
    ArrayList<Ship> listOfShips = new ArrayList<>();

    ShipPlacer sp = new ShipPlacer(random);

    int carOccur = specifications.get(ShipType.CARRIER);
    // carrier
    for (int i = 0; i < carOccur; i++) {
      Ship currShip = sp.placeShips(width, height, ShipType.CARRIER, currBoard);
      listOfShips.add(currShip);
    }

    int batOccur = specifications.get(ShipType.BATTLESHIP);
    // battleship
    for (int i = 0; i < batOccur; i++) {
      Ship currShip = sp.placeShips(width, height, ShipType.BATTLESHIP, currBoard);
      listOfShips.add(currShip);
    }

    int desOccur = specifications.get(ShipType.DESTROYER);
    //destroyer
    for (int i = 0; i < desOccur; i++) {
      Ship currShip = sp.placeShips(width, height, ShipType.DESTROYER, currBoard);
      listOfShips.add(currShip);
    }

    int subOccur = specifications.get(ShipType.SUBMARINE);
    //submarine
    for (int i = 0; i < subOccur; i++) {
      Ship currShip = sp.placeShips(width, height, ShipType.SUBMARINE, currBoard);
      listOfShips.add(currShip);
    }
    this.shipList = listOfShips;
    return listOfShips;
  }

  /**
   * Returns the AIPlayer's shots.
   *
   * @return the AIPlayer's shots
   */
  @Override
  public List<Coord> takeShots() {
    return randomShotTaker();
  }

  /**
   * Returns the number of active ships the AIPlayer has.
   *
   * @return the number of active ships the AIPlayer has
   */
  private int getActiveShips() {
    int count = 0;
    for (Ship s : this.shipList) {
      if (!s.isSunk()) {
        count++;
      }
    }
    return count;
  }

  /**
   * Reports the damage done to the AIPlayer's ships.
   *
   * @param opponentShotsOnBoard the shots that hit the AIPlayer's ships
   * @return the coordinates of the shots that hit the AIPlayer's ships
   */
  @Override
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> hits = new ArrayList<>();
    for (Ship ship : shipList) {
      if (!ship.isSunk()) {
        for (Coord c : opponentShotsOnBoard) {
          if (existsIn(c, ship.getCoords())) {
            ship.hit(c);
            hits.add(c);
          }
        }
      }
    }
    return hits;
  }

  /**
   * Returns whether or not the given coordinate exists in the given list of coordinates.
   *
   * @param c    the coordinate to check
   * @param list the list of coordinates to check
   * @return whether or not the given coordinate exists in the given list of coordinates
   */
  private boolean existsIn(Coord c, List<Coord> list) {
    for (Coord coord : list) {
      if (coord.equals(c)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Reports the AIPlayer's successful hits.
   *
   * @param shotsThatHitOpponentShips the AIPlayer's successful hits
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
  }

  /**
   * Reports the AIPlayer's end game result.
   *
   * @param result the AIPlayer's end game result
   * @param reason the reason for the AIPlayer's end game result
   */
  @Override
  public void endGame(GameResult result, String reason) {
  }

  /**
   * Returns a list of random shots.
   *
   * @return a list of random shots
   */
  private List<Coord> randomShotTaker() {
    ArrayList<Coord> shots = new ArrayList<>();
    int maxShots = (height * width) - allShots.size();
    for (int i = 0; i < Math.min(getActiveShips(), maxShots); i++) {
      boolean flag = true;
      while (flag) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        Coord c = new Coord(x, y);
        if (!allShots.contains(c)) {
          allShots.add(c);
          shots.add(c);
          flag = false;
        }
      }
    }
    return shots;
  }
}