package cs3500.controller.json;

// relevant imports

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.model.Coord;

/**
 * Represents a single coordinate on a BattleSalvo board as a JSON object.
 *
 * @param x the x coordinate
 * @param y the y coordinate
 */

public record CoordJson(
    @JsonProperty("x") int x,
    @JsonProperty("y") int y) {

  /**
   * Converts a CoordJson object to a Coord object.
   *
   * @return a Coord object
   */
  public Coord toCoord() {
    return new Coord(this.x(), this.y());
  }

}
