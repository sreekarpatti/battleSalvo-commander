package cs3500.controller.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for FleetJson
 */
class FleetJsonTest {

  ArrayList<ShipJson> ships = new ArrayList<>();
  ShipJson ship1 = new ShipJson(new CoordJson(1, 2), 6, "HORIZONTAL");
  ShipJson ship2 = new ShipJson(new CoordJson(3, 4), 4, "VERTICAL");
  ShipJson ship3 = new ShipJson(new CoordJson(5, 6), 3, "HORIZONTAL");

  /**
   * Sets up the tests
   */
  @BeforeEach
  void setUp() {
    ships.add(ship1);
    ships.add(ship2);
    ships.add(ship3);
  }

  /**
   * Tests for fleet
   */
  @Test
  void fleet() {
    FleetJson fleetJson = new FleetJson(ships);
    assertEquals(ships, fleetJson.fleet());
  }
}