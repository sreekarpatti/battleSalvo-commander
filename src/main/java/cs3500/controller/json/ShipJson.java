package cs3500.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a ship as a JSON object.
 *
 * @param coord     the coordinate of the ship
 * @param length    the length of the ship
 * @param direction the direction of the ship
 */
public record ShipJson(
    @JsonProperty("coord") CoordJson coord,
    @JsonProperty("length") int length,
    @JsonProperty("direction") String direction) {
}
