package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.controller.json.CoordJson;
import cs3500.model.Coord;
import cs3500.model.Ship;
import cs3500.model.ShipType;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {

  Ship s1;
  Ship s2;
  Ship s3;
  Ship s4;

  Ship s5;

  @BeforeEach
  void setUp() {
    ArrayList<Coord> coords1 = new ArrayList<Coord>();
    coords1.add(new Coord(1, 1));
    coords1.add(new Coord(1, 2));
    coords1.add(new Coord(1, 3));
    coords1.add(new Coord(1, 4));
    coords1.add(new Coord(1, 5));
    coords1.add(new Coord(1, 6));
    ArrayList<Coord> coords2 = new ArrayList<Coord>();
    coords2.add(new Coord(2, 1));
    coords2.add(new Coord(2, 2));
    coords2.add(new Coord(2, 3));
    coords2.add(new Coord(2, 4));
    coords2.add(new Coord(2, 5));
    ArrayList<Coord> coords3 = new ArrayList<Coord>();
    coords3.add(new Coord(3, 1));
    coords3.add(new Coord(3, 2));
    coords3.add(new Coord(3, 3));
    ArrayList<Coord> coords4 = new ArrayList<Coord>();
    coords4.add(new Coord(4, 1));
    coords4.add(new Coord(4, 2));
    coords4.add(new Coord(4, 3));
    coords4.add(new Coord(4, 4));
    ArrayList<Coord> coords5 = new ArrayList<Coord>();
    coords5.add(new Coord(0, 1));
    coords5.add(new Coord(1, 1));
    coords5.add(new Coord(2, 1));
    coords5.add(new Coord(3, 1));
    coords5.add(new Coord(4, 1));

    s1 = new Ship(ShipType.CARRIER, coords1);
    s2 = new Ship(ShipType.BATTLESHIP, coords2);
    s3 = new Ship(ShipType.SUBMARINE, coords3);
    s4 = new Ship(ShipType.DESTROYER, coords4);
    s5 = new Ship(ShipType.BATTLESHIP, coords5);
  }

  @Test
  void getName() {
    assertEquals("CARRIER", s1.getName().name());
    assertEquals("BATTLESHIP", s2.getName().name());
    assertEquals("SUBMARINE", s3.getName().name());
    assertEquals("DESTROYER", s4.getName().name());
  }

  @Test
  void getCoords() {
    assertEquals(6, s1.getCoords().size());
    assertEquals(5, s2.getCoords().size());
    assertEquals(3, s3.getCoords().size());
    assertEquals(4, s4.getCoords().size());
  }

  @Test
  void setCoords() {
    ArrayList<Coord> coords1 = new ArrayList<Coord>();
    coords1.add(new Coord(2, 1));
    coords1.add(new Coord(2, 2));
    coords1.add(new Coord(2, 3));
    coords1.add(new Coord(2, 4));
    coords1.add(new Coord(2, 5));
    coords1.add(new Coord(2, 6));
    s1.setCoords(coords1);
    assertEquals(6, s1.getCoords().size());
  }

  @Test
  void getSymbol() {
    assertEquals(" C ", s1.getSymbol());
    assertEquals(" B ", s2.getSymbol());
    assertEquals(" S ", s3.getSymbol());
    assertEquals(" D ", s4.getSymbol());
  }

  @Test
  void hit() {
    ArrayList<Coord> coords1 = new ArrayList<Coord>();
    coords1.add(new Coord(2, 1));
    coords1.add(new Coord(2, 2));
    coords1.add(new Coord(2, 3));
    coords1.add(new Coord(2, 4));
    coords1.add(new Coord(2, 5));
    coords1.add(new Coord(2, 6));
    s1.setCoords(coords1);
    s1.hit(new Coord(2, 1));
    s1.hit(new Coord(2, 2));
    s1.hit(new Coord(2, 3));
    s1.hit(new Coord(2, 4));
    s1.hit(new Coord(2, 5));
    s1.hit(new Coord(2, 6));
    assertTrue(s1.isSunk());
  }

  @Test
  void isSunk() {
    assertFalse(s1.isSunk());
    assertFalse(s2.isSunk());
    assertFalse(s3.isSunk());
    assertFalse(s4.isSunk());
  }

  @Test
  void equals() {
    assertFalse(s1.equals(s2));
    ArrayList<Coord> coords = new ArrayList<Coord>();
    coords.add(new Coord(3, 1));
    coords.add(new Coord(3, 2));
    coords.add(new Coord(3, 3));
    Ship s5 = new Ship(ShipType.SUBMARINE, coords);
    assertTrue(s3.equals(s5));
  }

  @Test
  void testDirection() {
    assertEquals("VERTICAL", s1.getDirection());
    assertEquals("VERTICAL", s2.getDirection());
    assertEquals("VERTICAL", s3.getDirection());
    assertEquals("VERTICAL", s4.getDirection());
    assertEquals("HORIZONTAL", s5.getDirection());
  }

  @Test
  void start() {
    assertEquals(new CoordJson(1, 1), s1.getStart());
    assertEquals(new CoordJson(2, 1), s2.getStart());
    assertEquals(new CoordJson(3, 1), s3.getStart());
    assertEquals(new CoordJson(4, 1), s4.getStart());
    assertEquals(new CoordJson(0, 1), s5.getStart());
  }

}