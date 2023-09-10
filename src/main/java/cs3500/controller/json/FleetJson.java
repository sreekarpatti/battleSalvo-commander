package cs3500.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a fleet of ships as a JSON object.
 *
 * @param fleet the fleet of ships
 */
public record FleetJson(
    @JsonProperty("fleet") List<ShipJson> fleet) {
}
