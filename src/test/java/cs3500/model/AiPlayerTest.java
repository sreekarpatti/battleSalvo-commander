package cs3500.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AiPlayerTest {
  AiPlayer player1;
  Map<ShipType, Integer> specs;
  Ship carrier;
  Ship battleship;
  Ship destroyer;
  Ship submarine;
  ArrayList<Ship> ships;

  @BeforeEach
  void setUp() {
    player1 = new AiPlayer(4);
    specs = Map.of(ShipType.CARRIER, 1, ShipType.BATTLESHIP, 1, ShipType.DESTROYER, 1,
        ShipType.SUBMARINE, 1);

    ArrayList<Coord> coordsCarrier = new ArrayList<Coord>();
    coordsCarrier.add(new Coord(4, 0));
    coordsCarrier.add(new Coord(4, 1));
    coordsCarrier.add(new Coord(4, 2));
    coordsCarrier.add(new Coord(4, 3));
    coordsCarrier.add(new Coord(4, 4));
    coordsCarrier.add(new Coord(4, 5));
    ArrayList<Coord> coordsBattleship = new ArrayList<Coord>();
    coordsBattleship.add(new Coord(6, 0));
    coordsBattleship.add(new Coord(6, 1));
    coordsBattleship.add(new Coord(6, 2));
    coordsBattleship.add(new Coord(6, 3));
    coordsBattleship.add(new Coord(6, 4));
    ArrayList<Coord> coordsDestroyer = new ArrayList<Coord>();
    coordsDestroyer.add(new Coord(2, 0));
    coordsDestroyer.add(new Coord(2, 1));
    coordsDestroyer.add(new Coord(2, 2));
    coordsDestroyer.add(new Coord(2, 3));
    ArrayList<Coord> coordsSubmarine = new ArrayList<Coord>();
    coordsSubmarine.add(new Coord(0, 2));
    coordsSubmarine.add(new Coord(0, 3));
    coordsSubmarine.add(new Coord(0, 4));

    carrier = new Ship(ShipType.CARRIER, coordsCarrier);
    battleship = new Ship(ShipType.BATTLESHIP, coordsBattleship);
    destroyer = new Ship(ShipType.DESTROYER, coordsDestroyer);
    submarine = new Ship(ShipType.SUBMARINE, coordsSubmarine);
    ships = new ArrayList<Ship>();
    ships.add(carrier);
    ships.add(battleship);
    ships.add(destroyer);
    ships.add(submarine);
  }

  @Test
  void name() {
    assertEquals("Krish-002", player1.name());
  }

  @Test
  void setup() {
    ArrayList<Ship> ships2 = (ArrayList<Ship>) player1.setup(6, 7, specs);
    assertEquals(ships, ships2);
  }

  @Test
  void takeShots() {
    player1.setup(6, 7, specs);
    ArrayList<Coord> shots = new ArrayList<Coord>();
    shots.add(new Coord(1, 4));
    shots.add(new Coord(2, 4));
    shots.add(new Coord(0, 0));
    shots.add(new Coord(3, 1));
    assertEquals(shots, player1.takeShots());
  }

  @Test
  void reportDamage() {
    player1.setup(6, 7, specs);
    ArrayList<Coord> coords = new ArrayList<Coord>();
    coords.add(new Coord(4, 0));
    coords.add(new Coord(4, 1));
    coords.add(new Coord(4, 2));
    coords.add(new Coord(0, 5));
    ArrayList<Coord> coords2 = new ArrayList<Coord>();
    coords2.add(new Coord(4, 0));
    coords2.add(new Coord(4, 1));
    coords2.add(new Coord(4, 2));
    assertEquals(coords2, player1.reportDamage(coords));
  }

  @Test
  void successfulHits() {
  }

  @Test
  void endGame() {
  }
}