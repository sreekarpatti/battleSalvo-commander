package cs3500.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents an abstract player in the game of BattleSalvo.
 */
public abstract class AbstractPlayer implements Player {
  private Random random;
  private ArrayList<Ship> shipList;
  GameBoard board;

  /**
   * Constructs an abstract player with the given board and random.
   *
   * @param board  the board
   * @param random the random
   */
  public AbstractPlayer(GameBoard board, Random random) {
    this.random = random;
    this.board = board;
    this.shipList = new ArrayList<>();
  }

  /**
   * Gets the ships of this player with their locations.
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the placements of each ship on the board
   */
  @Override
  public List<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
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
   * Reports the damage of the opponent's shots on this player's board.
   *
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return the locations of the hits
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
   * Checks if the given coordinate exists in the given list of coordinates.
   *
   * @param c    the coordinate
   * @param list the list of coordinates
   * @return true if the coordinate exists in the list, false otherwise
   */
  private boolean existsIn(Coord c, List<Coord> list) {
    for (Coord coord : list) {
      if (coord.equals(c)) {
        return true;
      }
    }
    return false;
  }
}
