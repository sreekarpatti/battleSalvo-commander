package cs3500.model;

import cs3500.controller.json.CoordJson;

/**
 * Represents a single coordinate on a BattleSalvo board.
 */
public class Coord {
  private int xcoord; // x coordinate
  private int ycoord; // y coordinate

  /**
   * Constructor for Coord
   *
   * @param x the x coordinate
   * @param y the y coordinate
   */
  public Coord(int x, int y) {
    this.xcoord = x;
    this.ycoord = y;
  }

  /**
   * Returns the x coordinate
   *
   * @return x
   */
  public int getX() {
    return this.xcoord;
  }

  /**
   * Returns the y coordinate
   *
   * @return y
   */
  public int getY() {
    return this.ycoord;
  }

  /**
   * Overrides the equals method to compare two Coord objects
   *
   * @param o the object to compare
   * @return true if the objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Coord) {
      Coord c = (Coord) o;
      return this.xcoord == c.xcoord && this.ycoord == c.ycoord;
    } else {
      return false;
    }
  }

  /**
   * Converts a Coord object to a CoordJson object.
   *
   * @return a CoordJson object
   */
  public CoordJson toJson() {
    return new CoordJson(this.xcoord, this.ycoord);
  }
}
