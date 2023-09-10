package cs3500.model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a ship placer in the game of BattleSalvo.
 */
public class ShipPlacer {
  private static final int OCCUPIED = 1; // the cell is occupied
  private final Random random; // the random number generator

  /**
   * Constructs a ShipPlacer object.
   *
   * @param random the random number generator
   */
  public ShipPlacer(Random random) {
    this.random = random;
  }

  /**
   * Places the ships on the board.
   *
   * @param width     the width of the board
   * @param height    the height of the board
   * @param shipType  the type of ship
   * @param currBoard the current board
   * @return the ship
   */
  public Ship placeShips(int width, int height, ShipType shipType, int[][] currBoard) {
    boolean placed = false;
    Ship currShip = new Ship(shipType, new ArrayList<Coord>());
    while (!placed) {
      int x = random.nextInt(width);
      int y = random.nextInt(height);
      int direction = random.nextInt(2); // 0 for horizontal, 1 for vertical
      if (canPlaceShip(currBoard, x, y, direction, shipType.getSize())) {
        currShip.setCoords(placeShip(currBoard, x, y, direction, shipType.getSize()));
        placed = true;
      }
    }
    return currShip;
  }

  /**
   * Checks if the ship can be placed on the board.
   *
   * @param currBoard the current board
   * @param x         the x coordinate
   * @param y         the y coordinate
   * @param direction the direction
   * @param size      the size of the ship
   * @return true if the ship can be placed, false otherwise
   */
  private boolean canPlaceShip(int[][] currBoard, int x, int y, int direction, int size) {
    int width = currBoard[0].length;
    int height = currBoard.length;

    if (direction == 0) { // horizontal
      if (x + size > width) {
        return false;
      }

      for (int i = x; i < x + size; i++) {
        if (currBoard[y][i] == OCCUPIED) {
          return false;
        }
      }
    } else { // vertical
      if (y + size > height) {
        return false;
      }

      for (int i = y; i < y + size; i++) {
        if (currBoard[i][x] == OCCUPIED) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Places the ship on the board.
   *
   * @param currBoard the current board
   * @param x         the x coordinate
   * @param y         the y coordinate
   * @param direction the direction
   * @param size      the size of the ship
   * @return the coordinates of the ship
   */
  private ArrayList<Coord> placeShip(int[][] currBoard, int x, int y, int direction,
                                     int size) {
    ArrayList<Coord> coords = new ArrayList<>();
    if (direction == 0) { // horizontal
      for (int i = x; i < x + size; i++) {
        currBoard[y][i] = OCCUPIED;
        coords.add(new Coord(i, y));
      }
    } else { // vertical
      for (int i = y; i < y + size; i++) {
        currBoard[i][x] = OCCUPIED;
        coords.add(new Coord(x, i));
      }
    }
    return coords;
  }
}

