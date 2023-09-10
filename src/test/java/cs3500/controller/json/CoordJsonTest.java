package cs3500.controller.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.model.Coord;
import org.junit.jupiter.api.Test;


/**
 * Tests for CoordJson
 */
class CoordJsonTest {

  CoordJson coordJson1 = new CoordJson(1, 2);
  CoordJson coordJson2 = new CoordJson(3, 4);

  /**
   * Tests for toCoord
   */
  @Test
  void toCoord() {
    assertEquals(new Coord(1, 2), coordJson1.toCoord());
    assertEquals(new Coord(3, 4), coordJson2.toCoord());
  }

  /**
   * Tests for x
   */
  @Test
  void xtest() {
    assertEquals(1, coordJson1.x());
    assertEquals(3, coordJson2.x());
  }

  /**
   * Tests for y
   */
  @Test
  void ytest() {
    assertEquals(2, coordJson1.y());
    assertEquals(4, coordJson2.y());
  }
}