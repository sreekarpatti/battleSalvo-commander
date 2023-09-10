package cs3500.model;

/**
 * Represents a single ship in a game of BattleSalvo.
 */
public enum ShipType {
  /**
   * The carrier ship.
   */
  CARRIER(6),
  /**
   * The battleship.
   */
  BATTLESHIP(5),
  /**
   * The submarine.
   */
  SUBMARINE(3),
  /**
   * The destroyer.
   */
  DESTROYER(4);

  private final int size;

  /**
   * Constructor for ShipType
   *
   * @param size the size of the ship
   */
  ShipType(int size) {
    this.size = size;
  }

  /**
   * Returns the size of the ship
   *
   * @return size
   */
  public int getSize() {
    return size;
  }

  /**
   * Returns the symbol of the ship
   *
   * @return symbol
   */
  public String getSymbol() {
    return switch (this) {
      case CARRIER -> " C ";
      case BATTLESHIP -> " B ";
      case SUBMARINE -> " S ";
      case DESTROYER -> " D ";
      default -> null;
    };
  }
}
