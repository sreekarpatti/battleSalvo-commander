package cs3500.controller.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.model.ShipType;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for SetupJson
 */
class SetupJsonTest {

  private HashMap<ShipType, Integer> specs = new HashMap<>();
  private SetupJson setupJsonTest1;

  /**
   * Sets up the tests
   */
  @BeforeEach
  void setUp() {
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.DESTROYER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.SUBMARINE, 1);
    setupJsonTest1 = new SetupJson(6, 10, specs);
  }

  /**
   * Tests for width
   */
  @Test
  void width() {
    assertEquals(6, setupJsonTest1.width());
  }

  /**
   * Tests for height
   */
  @Test
  void height() {
    assertEquals(10, setupJsonTest1.height());
  }

  /**
   * Tests for fleetSpec
   */
  @Test
  void fleetSpec() {
    assertEquals(specs, setupJsonTest1.fleetSpec());
  }
}