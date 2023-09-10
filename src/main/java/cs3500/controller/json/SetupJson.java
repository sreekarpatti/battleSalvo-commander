package cs3500.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.model.ShipType;
import java.util.Map;

/**
 * Represents a setup request as a JSON object.
 *
 * @param width     the width of the board
 * @param height    the height of the board
 * @param fleetSpec the fleet specification
 */
public record SetupJson(
    @JsonProperty("width") int width,
    @JsonProperty("height") int height,
    @JsonProperty("fleet-spec") Map<ShipType, Integer> fleetSpec) {
}
