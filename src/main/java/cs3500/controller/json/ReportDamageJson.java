package cs3500.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Represents a volley of shots as a JSON object.
 *
 * @param volley the volley of shots
 */
public record ReportDamageJson(
    @JsonProperty("coordinates") List<CoordJson> volley) {
}
