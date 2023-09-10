package cs3500.model;

import cs3500.controller.json.CoordJson;
import java.util.ArrayList;

/**
 * Represents a ship in the game of BattleSalvo.
 */
public class Ship {
  private ShipType name; // the name of the ship
  private ArrayList<Coord> coords; //  the coordinates of the ship
  private String symbol; // the symbol of the ship
  private ArrayList<Coord> hits; // the coordinates of the hits on the ship
  private boolean sunk; // whether the ship is sunk


  /**
   * Constructs a Ship object.
   *
   * @param shipType       the type of ship
   * @param currShipCoords the coordinates of the ship
   */
  public Ship(ShipType shipType, ArrayList<Coord> currShipCoords) {
    this.name = shipType;
    this.coords = currShipCoords;
    this.symbol = shipType.getSymbol();
    this.hits = new ArrayList<>();
    this.sunk = false;
  }

  /**
   * Returns the ShipType of the ship.
   *
   * @return the name of the ship
   */
  public ShipType getName() {
    return name;
  }

  /**
   * Returns the coordinates of the ship.
   *
   * @return the coordinates of the ship
   */
  public ArrayList<Coord> getCoords() {
    return coords;
  }

  /**
   * Sets the coordinates of the ship.
   *
   * @param coords the coordinates of the ship
   */
  public void setCoords(ArrayList<Coord> coords) {
    this.coords = coords;
  }

  /**
   * Returns the symbol of the ship.
   *
   * @return the symbol of the ship
   */
  public String getSymbol() {
    return symbol;
  }

  /**
   * Returns whether the ship is sunk.
   *
   * @return whether the ship is sunk
   */
  public boolean isSunk() {
    return sunk;
  }

  /**
   * Adds a hit to the ship.
   *
   * @param c the coordinate of the hit
   */
  public void hit(Coord c) {
    hits.add(c);
    if (hits.size() == coords.size()) {
      sunk = true;
    }
  }

  /**
   * Checks if 2 ships are equal.
   *
   * @param o the object to be compared
   * @return whether the 2 ships are equal
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Ship) {
      Ship s = (Ship) o;
      return this.name.equals(s.name) && this.coords.equals(s.coords)
          && this.symbol.equals(s.symbol) && this.hits.equals(s.hits)
          && this.sunk == s.sunk;
    } else {
      return false;
    }
  }

  /**
   * Gets the direction of the ship.
   *
   * @return the direction of the ship
   */
  public String getDirection() {
    if (this.coords.get(0).getY() == this.coords.get(1).getY()) {
      return "HORIZONTAL";
    } else {
      return "VERTICAL";
    }
  }

  /**
   * Gets the start coordinate of the ship.
   *
   * @return the start coordinate of the ship
   */
  public CoordJson getStart() {
    Coord c = this.coords.get(0);
    return new CoordJson(c.getX(), c.getY());
  }
}
