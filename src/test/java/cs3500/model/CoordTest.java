package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.controller.json.CoordJson;
import cs3500.model.Coord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordTest {

  Coord c1;
  Coord c2;

  @BeforeEach
  void setUp() {
    c1 = new Coord(1, 2);
    c2 = new Coord(1, 2);
  }

  @Test
  void getX() {
    assertEquals(1, c1.getX());
    assertEquals(1, c2.getX());
  }

  @Test
  void getY() {
    assertEquals(2, c1.getY());
    assertEquals(2, c2.getY());
  }

  @Test
  void testEquals() {
    assertTrue(c1.equals(c2));
    assertTrue(c2.equals(c1));
  }

  @Test
  void toJson() {
    assertEquals(new CoordJson(1, 2), c1.toJson());
    assertEquals(new CoordJson(1, 2), c2.toJson());
  }
}