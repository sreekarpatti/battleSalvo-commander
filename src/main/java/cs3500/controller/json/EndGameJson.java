package cs3500.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import cs3500.model.GameResult;

/**
 * Represents the end of a game as a JSON object.
 *
 * @param result the result of the game
 * @param reason the reason the game ended
 */
public record EndGameJson(
    @JsonProperty("result") GameResult result,
    @JsonProperty("reason") String reason) {
}
