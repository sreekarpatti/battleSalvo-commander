package cs3500.controller.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

/**
 * Tests for JsonUtils
 */
class JsonUtilsTest {
  JoinJson joinJson = new JoinJson("player1", "SINGLE");

  /**
   * Tests for deserializeJoin
   */
  @Test
  void serializeRecord() {
    JsonNode jn = JsonUtils.serializeRecord(joinJson);
    assertEquals(jn.get("name").asText(), "player1");
    assertEquals(jn.get("game-type").asText(), "SINGLE");
  }
}