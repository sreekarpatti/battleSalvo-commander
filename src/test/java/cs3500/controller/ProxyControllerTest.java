package cs3500.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.Mocket;
import cs3500.controller.json.CoordJson;
import cs3500.controller.json.EndGameJson;
import cs3500.controller.json.MessageJson;
import cs3500.controller.json.ReportDamageJson;
import cs3500.controller.json.SetupJson;
import cs3500.controller.json.SuccessfulHitsJson;
import cs3500.controller.json.VolleyJson;
import cs3500.model.AiPlayer;
import cs3500.model.GameResult;
import cs3500.model.ShipType;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test correct responses for different requests from the socket using a Mock Socket (mocket)
 */
public class ProxyControllerTest {

  private ByteArrayOutputStream testLog;
  private ProxyController controller;
  private final ObjectMapper mapper = new ObjectMapper();


  /**
   * Reset the test log before each test is run.
   */
  @BeforeEach
  public void setup() {
    this.testLog = new ByteArrayOutputStream(2048);
    assertEquals("", logToString());
  }

  @Test
  public void testJoin() {
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode emptyNode = objectMapper.createObjectNode();

    MessageJson jsonServerRequest = new MessageJson("join", emptyNode);

    JsonNode sampleMessage = this.mapper.convertValue(jsonServerRequest, JsonNode.class);
    EndGameJson endGameArgs = new EndGameJson(GameResult.LOSE, "You lose!");
    JsonNode endGameArgsNode = this.mapper.convertValue(endGameArgs, JsonNode.class);

    MessageJson endGameServerRequest = new MessageJson("end-game", endGameArgsNode);

    JsonNode endGameMessage = this.mapper.convertValue(endGameServerRequest, JsonNode.class);

    Mocket socket =
        new Mocket(this.testLog, List.of(sampleMessage.toString(), endGameMessage.toString()));

    try {
      this.controller = new ProxyController(socket, new AiPlayer(1));
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();
    String expected =
        "{\"method-name\":\"join\",\"arguments\":{\"name\":\"Krish-002\","
            + "\"game-type\":\"SINGLE\"}}\n"
            + "{\"method-name\":\"end-game\",\"arguments\":{}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void testSetUp() {
    Map<ShipType, Integer> ships = new HashMap<>();
    ships.put(ShipType.BATTLESHIP, 1);
    ships.put(ShipType.DESTROYER, 1);
    ships.put(ShipType.SUBMARINE, 1);
    ships.put(ShipType.CARRIER, 1);

    SetupJson setupJson = new SetupJson(6, 6, ships);
    JsonNode setupJsonNode = this.mapper.convertValue(setupJson, JsonNode.class);

    MessageJson jsonServerRequest = new MessageJson("setup", setupJsonNode);

    JsonNode sampleMessage = this.mapper.convertValue(jsonServerRequest, JsonNode.class);
    EndGameJson endGameArgs = new EndGameJson(GameResult.LOSE, "You lose!");
    JsonNode endGameArgsNode = this.mapper.convertValue(endGameArgs, JsonNode.class);

    MessageJson endGameServerRequest = new MessageJson("end-game", endGameArgsNode);

    JsonNode endGameMessage = this.mapper.convertValue(endGameServerRequest, JsonNode.class);

    Mocket socket =
        new Mocket(this.testLog, List.of(sampleMessage.toString(), endGameMessage.toString()));

    try {
      this.controller = new ProxyController(socket, new AiPlayer(1));
    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();
    String expected = "{\"method-name\":\"setup\",\"arguments\":{\"fleet\":[{\"coord\":"
        + "{\"x\":0,\"y\":5},\"length\":6,\"direction\":\"HORIZONTAL\"},{\"coord\":"
        + "{\"x\":1,\"y\":3},\"length\":5,\"direction\":\"HORIZONTAL\"},{\"coord\":"
        + "{\"x\":0,\"y\":0},\"length\":4,\"direction\":\"HORIZONTAL\"},{\"coord\":"
        + "{\"x\":3,\"y\":2},\"length\":3,\"direction\":\"HORIZONTAL\"}]}}\n"
        + "{\"method-name\":\"end-game\",\"arguments\":{}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void testTakeShots() {
    List<CoordJson> volley = List.of(new CoordJson(0, 0), new CoordJson(1, 1));
    VolleyJson volleyJson = new VolleyJson(volley);

    JsonNode setupJsonNode = this.mapper.convertValue(volleyJson, JsonNode.class);
    MessageJson jsonServerRequest = new MessageJson("take-shots", setupJsonNode);

    JsonNode sampleMessage = this.mapper.convertValue(jsonServerRequest, JsonNode.class);
    EndGameJson endGameArgs = new EndGameJson(GameResult.LOSE, "You lose!");
    JsonNode endGameArgsNode = this.mapper.convertValue(endGameArgs, JsonNode.class);

    MessageJson endGameServerRequest = new MessageJson("end-game", endGameArgsNode);

    JsonNode endGameMessage = this.mapper.convertValue(endGameServerRequest, JsonNode.class);

    Mocket socket =
        new Mocket(this.testLog, List.of(sampleMessage.toString(), endGameMessage.toString()));

    AiPlayer aiPlayer = new AiPlayer(1);

    try {
      this.controller = new ProxyController(socket, aiPlayer);
      aiPlayer.setup(6, 6,
          Map.of(ShipType.BATTLESHIP, 1, ShipType.DESTROYER, 1, ShipType.SUBMARINE, 1,
              ShipType.CARRIER, 1));

    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();
    String expected = "{\"method-name\":\"take-shots\",\"arguments\":"
        + "{\"coordinates\":[{\"x\":0,\"y\":1},{\"x\":2,\"y\":1},"
        + "{\"x\":2,\"y\":2},{\"x\":4,\"y\":3}]}}\n"
        + "{\"method-name\":\"end-game\",\"arguments\":{}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void testReportDamage() {
    List<CoordJson> volley = List.of(new CoordJson(0, 0), new CoordJson(1, 1));
    ReportDamageJson reportDamageJson = new ReportDamageJson(volley);

    JsonNode reportDamageJsonNode = this.mapper.convertValue(reportDamageJson, JsonNode.class);
    MessageJson jsonServerRequest = new MessageJson("report-damage", reportDamageJsonNode);

    JsonNode sampleMessage = this.mapper.convertValue(jsonServerRequest, JsonNode.class);
    EndGameJson endGameArgs = new EndGameJson(GameResult.LOSE, "You lose!");
    JsonNode endGameArgsNode = this.mapper.convertValue(endGameArgs, JsonNode.class);

    MessageJson endGameServerRequest = new MessageJson("end-game", endGameArgsNode);

    JsonNode endGameMessage = this.mapper.convertValue(endGameServerRequest, JsonNode.class);

    Mocket socket =
        new Mocket(this.testLog, List.of(sampleMessage.toString(), endGameMessage.toString()));

    AiPlayer aiPlayer = new AiPlayer(1);

    try {
      this.controller = new ProxyController(socket, aiPlayer);
      aiPlayer.setup(6, 6,
          Map.of(ShipType.BATTLESHIP, 1, ShipType.DESTROYER, 1, ShipType.SUBMARINE, 1,
              ShipType.CARRIER, 1));

    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();
    String expected = "{\"method-name\":\"report-damage\",\"arguments\":"
        + "{\"coordinates\":[{\"x\":0,\"y\":0}]}}\n"
        + "{\"method-name\":\"end-game\",\"arguments\":{}}\n";
    assertEquals(expected, logToString());

  }

  @Test
  public void testSuccessfulHits() {
    List<CoordJson> volley = List.of(new CoordJson(0, 0), new CoordJson(1, 1));
    SuccessfulHitsJson successfulHitsJson = new SuccessfulHitsJson(volley);

    JsonNode successfulHitsJsonNode = this.mapper.convertValue(successfulHitsJson, JsonNode.class);
    MessageJson jsonServerRequest = new MessageJson("successful-hits", successfulHitsJsonNode);

    JsonNode sampleMessage = this.mapper.convertValue(jsonServerRequest, JsonNode.class);
    EndGameJson endGameArgs = new EndGameJson(GameResult.LOSE, "You lose!");
    JsonNode endGameArgsNode = this.mapper.convertValue(endGameArgs, JsonNode.class);

    MessageJson endGameServerRequest = new MessageJson("end-game", endGameArgsNode);

    JsonNode endGameMessage = this.mapper.convertValue(endGameServerRequest, JsonNode.class);

    Mocket socket =
        new Mocket(this.testLog, List.of(sampleMessage.toString(), endGameMessage.toString()));

    AiPlayer aiPlayer = new AiPlayer(1);

    try {
      this.controller = new ProxyController(socket, aiPlayer);
      aiPlayer.setup(6, 6,
          Map.of(ShipType.BATTLESHIP, 1, ShipType.DESTROYER, 1, ShipType.SUBMARINE, 1,
              ShipType.CARRIER, 1));

    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();
    String expected = "{\"method-name\":\"successful-hits\",\"arguments\":{}}\n"
        + "{\"method-name\":\"end-game\",\"arguments\":{}}\n";
    assertEquals(expected, logToString());

  }

  @Test
  public void testEndGame() {
    EndGameJson endGameArgs = new EndGameJson(GameResult.LOSE, "You lose!");
    JsonNode endGameArgsNode = this.mapper.convertValue(endGameArgs, JsonNode.class);

    MessageJson endGameServerRequest = new MessageJson("end-game", endGameArgsNode);

    JsonNode endGameMessage = this.mapper.convertValue(endGameServerRequest, JsonNode.class);

    Mocket socket = new Mocket(this.testLog, List.of(endGameMessage.toString()));

    AiPlayer aiPlayer = new AiPlayer(1);

    try {
      this.controller = new ProxyController(socket, aiPlayer);

    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    this.controller.run();
    String expected = "{\"method-name\":\"end-game\",\"arguments\":{}}\n";
    assertEquals(expected, logToString());
  }

  @Test
  public void invalidMessage() {
    EndGameJson endGameArgs = new EndGameJson(GameResult.LOSE, "You lose!");
    JsonNode endGameArgsNode = this.mapper.convertValue(endGameArgs, JsonNode.class);

    MessageJson endGameServerRequest = new MessageJson("invalid", endGameArgsNode);

    JsonNode endGameMessage = this.mapper.convertValue(endGameServerRequest, JsonNode.class);

    Mocket socket = new Mocket(this.testLog, List.of(endGameMessage.toString()));

    AiPlayer aiPlayer = new AiPlayer(1);

    try {
      this.controller = new ProxyController(socket, aiPlayer);

    } catch (IOException e) {
      fail(); // fail if the dealer can't be created
    }

    //this.controller.run();
    assertThrows(IllegalStateException.class, () -> this.controller.run());
  }

  /**
   * Converts the ByteArrayOutputStream log to a string in UTF_8 format
   *
   * @return String representing the current log buffer
   */
  private String logToString() {
    return testLog.toString(StandardCharsets.UTF_8);
  }
}