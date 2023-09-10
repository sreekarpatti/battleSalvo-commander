package cs3500.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import cs3500.controller.json.CoordJson;
import cs3500.controller.json.EndGameJson;
import cs3500.controller.json.FleetJson;
import cs3500.controller.json.JoinJson;
import cs3500.controller.json.JsonUtils;
import cs3500.controller.json.MessageJson;
import cs3500.controller.json.ReportDamageJson;
import cs3500.controller.json.SetupJson;
import cs3500.controller.json.ShipJson;
import cs3500.controller.json.SuccessfulHitsJson;
import cs3500.controller.json.VolleyJson;
import cs3500.model.Coord;
import cs3500.model.GameResult;
import cs3500.model.Player;
import cs3500.model.Ship;
import cs3500.model.ShipType;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents a proxy controller that handles the communication between the server and the player.
 */
public class ProxyController implements Controller {
  private final Socket server;
  private final InputStream in;
  private final PrintStream out;
  private final Player computerPlayer;
  private final ObjectMapper mapper = new ObjectMapper();

  /**
   * Construct an instance of a ProxyPlayer.
   *
   * @param server the socket connection to the server
   * @param player the instance of the player
   * @throws IOException if the input or output streams cannot be created
   */
  public ProxyController(Socket server, Player player) throws IOException {
    this.server = server;
    this.in = server.getInputStream();
    this.out = new PrintStream(server.getOutputStream());
    this.computerPlayer = player;
  }

  /**
   * Listens for messages from the server as JSON in the format of a MessageJSON. When a complete
   * message is sent by the server, the message is parsed and then delegated to the corresponding
   * helper method for each message. This method stops when the connection to the server is closed
   * or an IOException is thrown from parsing malformed JSON.
   */
  @Override
  public void run() {
    try {
      JsonParser parser = this.mapper.getFactory().createParser(this.in);

      while (!this.server.isClosed()) {
        MessageJson message = parser.readValueAs(MessageJson.class);
        delegateMessage(message);
      }
    } catch (IOException e) {
      // Disconnected from server or parsing exception
      e.printStackTrace();
    }
  }

  /**
   * Handles the join message from the server. This method parses the arguments of the message and
   * then delegates to the computer player to join the game.
   *
   * @param message the message from the server
   */
  private void delegateMessage(MessageJson message) {
    String name = message.methodName();
    JsonNode arguments = message.arguments();

    if ("join".equals(name)) {
      handleJoin(arguments);
    } else if ("setup".equals(name)) {
      handleSetup(arguments);
    } else if ("take-shots".equals(name)) {
      handleTakeShots(arguments);
    } else if ("report-damage".equals(name)) {
      handleReportDamage(arguments);
    } else if ("successful-hits".equals(name)) {
      handleSuccessfulHits(arguments);
    } else if ("end-game".equals(name)) {
      handleEndGame(arguments);
    } else {
      throw new IllegalStateException("Invalid message name");
    }
  }

  /**
   * Handles the end game message from the server. This method parses the arguments of the message
   *
   * @param arguments the arguments of the message
   */
  private void handleEndGame(JsonNode arguments) {
    EndGameJson endGameArgs = this.mapper.convertValue(arguments, EndGameJson.class);
    GameResult result = endGameArgs.result();
    String reason = endGameArgs.reason();
    computerPlayer.endGame(result, reason);
    MessageJson mjResponse = new MessageJson("end-game", mapper.createObjectNode());
    JsonNode jsonResponse = JsonUtils.serializeRecord(mjResponse);
    this.out.println(jsonResponse);
    try {
      this.server.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Handles the successful hits message from the server. This method parses the arguments of the
   * message and then delegates to the computer player to update its model.
   *
   * @param arguments the arguments of the message
   */
  private void handleSuccessfulHits(JsonNode arguments) {
    SuccessfulHitsJson successfulHitsArgs =
        this.mapper.convertValue(arguments, SuccessfulHitsJson.class);
    List<CoordJson> volley = successfulHitsArgs.volley();

    List<Coord> volleyCoords = new ArrayList<Coord>(); // convert volley to coords
    for (CoordJson coordJ : volley) {
      volleyCoords.add(coordJ.toCoord());
    }

    computerPlayer.successfulHits(volleyCoords);

    MessageJson mjResponse = new MessageJson("successful-hits", mapper.createObjectNode());
    JsonNode jsonResponse = JsonUtils.serializeRecord(mjResponse);
    this.out.println(jsonResponse);

  }

  /**
   * Handles the report damage message from the server. This method parses the arguments of the
   * message and then delegates to the computer player to update its model.
   *
   * @param arguments the arguments of the message
   */
  private void handleReportDamage(JsonNode arguments) {
    ReportDamageJson reportDamArgs = this.mapper.convertValue(arguments, ReportDamageJson.class);
    List<CoordJson> volley = reportDamArgs.volley(); // get volley from json

    List<Coord> volleyCoords = new ArrayList<Coord>(); // convert volley to coords
    for (CoordJson coordJ : volley) {
      volleyCoords.add(coordJ.toCoord());
    }

    List<Coord> damage = computerPlayer.reportDamage(volleyCoords); // get damage from model

    List<CoordJson> listOfJson = new ArrayList<CoordJson>(); // convert damage to json
    for (Coord coord : damage) {
      listOfJson.add(coord.toJson());
    }

    VolleyJson response = new VolleyJson(listOfJson);
    JsonNode jsonNode = mapper.valueToTree(response);
    MessageJson mjResponse = new MessageJson("report-damage", jsonNode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(mjResponse);
    this.out.println(jsonResponse);
  }

  /**
   * Handles the take shots message from the server. This method parses the arguments of the
   * message and then delegates to the computer player to take shots.
   *
   * @param arguments the arguments of the message
   */
  private void handleTakeShots(JsonNode arguments) {
    List<Coord> shots = computerPlayer.takeShots();

    List<CoordJson> listOfJson = new ArrayList<CoordJson>();
    for (Coord coord : shots) {
      listOfJson.add(new CoordJson(coord.getX(), coord.getY()));
    }

    VolleyJson response = new VolleyJson(listOfJson);
    JsonNode jsonNode = mapper.valueToTree(response);
    MessageJson mjResponse = new MessageJson("take-shots", jsonNode);

    JsonNode jsonResponse = JsonUtils.serializeRecord(mjResponse);
    this.out.println(jsonResponse);
  }

  /**
   * Handles the setup message from the server. This method parses the arguments of the message and
   * then delegates to the computer player to setup its fleet.
   *
   * @param arguments the arguments of the message
   */
  private void handleSetup(JsonNode arguments) {
    SetupJson setupArgs = this.mapper.convertValue(arguments, SetupJson.class);
    int width = setupArgs.width();
    int height = setupArgs.height();
    HashMap<ShipType, Integer> specs = (HashMap<ShipType, Integer>) setupArgs.fleetSpec();

    List<Ship> listOfShips = computerPlayer.setup(height, width, specs);
    List<ShipJson> listOfJson = convertShipsToJson(listOfShips);

    FleetJson response = new FleetJson(listOfJson);
    JsonNode jsonNode = mapper.valueToTree(response);
    MessageJson mjResponse = new MessageJson("setup", jsonNode);
    JsonNode jsonResponse = JsonUtils.serializeRecord(mjResponse);
    this.out.println(jsonResponse);
  }

  /**
   * Converts a list of ships to a list of shipJsons
   *
   * @param listOfShips the list of ships to convert
   * @return the list of shipJsons
   */
  private List<ShipJson> convertShipsToJson(List<Ship> listOfShips) {
    ArrayList<ShipJson> shipJson = new ArrayList<ShipJson>();
    for (Ship ship : listOfShips) {
      ShipJson s = new ShipJson(ship.getStart(), ship.getName().getSize(), ship.getDirection());
      shipJson.add(s);
    }
    return shipJson;
  }

  /**
   * Handles the join message from the server. This method parses the arguments of the message and
   * then delegates to the computer player to join the game.
   *
   * @param arguments the arguments of the message
   */
  private void handleJoin(JsonNode arguments) {
    String name = computerPlayer.name();
    JoinJson joinResponse = new JoinJson(name, "SINGLE");
    JsonNode jsonNode = mapper.valueToTree(joinResponse);
    MessageJson mjResponse = new MessageJson("join", jsonNode);

    JsonNode jsonResponse = JsonUtils.serializeRecord(mjResponse);
    this.out.println(jsonResponse);
  }
}
