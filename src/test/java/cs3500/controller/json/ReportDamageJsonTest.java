package cs3500.controller.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for ReportDamageJson
 */
class ReportDamageJsonTest {

  CoordJson coordJson = new CoordJson(1, 2);
  CoordJson coordJson2 = new CoordJson(3, 4);
  CoordJson coordJson3 = new CoordJson(5, 6);

  ArrayList<CoordJson> coords = new ArrayList<>();

  /**
   * Sets up the tests
   */
  @BeforeEach
  void setUp() {
    coords.add(coordJson);
    coords.add(coordJson2);
    coords.add(coordJson3);
  }

  /**
   * Tests for volley
   */
  @Test
  void volley() {
    ReportDamageJson reportDamageJson = new ReportDamageJson(coords);
    assertEquals(coords, reportDamageJson.volley());
  }
}