package cs3500.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a join request as a JSON object.
 *
 * @param name the name of the player
 * @param gt   the game type
 */

public record JoinJson(
    @JsonProperty("name") String name,
    @JsonProperty("game-type") String gt) {

}
