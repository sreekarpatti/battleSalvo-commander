package cs3500.controller.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for VolleyJson
 */
class VolleyJsonTest {

  ArrayList<CoordJson> coords = new ArrayList<>();
  VolleyJson volleyJson1;

  /**
   * Sets up the tests
   */
  @BeforeEach
  void setUp() {
    coords.add(new CoordJson(1, 1));
    coords.add(new CoordJson(2, 2));
    coords.add(new CoordJson(3, 3));
    volleyJson1 = new VolleyJson(coords);
  }

  /**
   * Tests for volley
   */
  @Test
  void volley() {
    assertEquals(coords, volleyJson1.volley());
  }
}