package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.model.Coord;
import cs3500.model.GameBoard;
import cs3500.model.Player;
import cs3500.model.Ship;
import cs3500.model.ShipType;
import cs3500.model.UserPlayer;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractPlayerTest {

  GameBoard gb;
  Random rand;
  Player p1;
  Map<ShipType, Integer> specs;
  Ship carrier;
  Ship battleship;
  Ship destroyer;
  Ship submarine;
  ArrayList<Ship> ships;

  @BeforeEach
  void setUp() {
    rand = new Random(4);
    gb = new GameBoard(rand, 6, 7);
    p1 = new UserPlayer(gb, rand);
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
  void setup() {
    ArrayList<Ship> ships2 = (ArrayList<Ship>) p1.setup(6, 7, specs);
    assertEquals(ships, ships2);
  }

  @Test
  void reportDamage() {
    p1.setup(6, 7, specs);
    ArrayList<Coord> coords = new ArrayList<Coord>();
    coords.add(new Coord(4, 0));
    coords.add(new Coord(4, 1));
    coords.add(new Coord(4, 2));
    coords.add(new Coord(0, 5));
    ArrayList<Coord> coords2 = new ArrayList<Coord>();
    coords2.add(new Coord(4, 0));
    coords2.add(new Coord(4, 1));
    coords2.add(new Coord(4, 2));
    assertEquals(coords2, p1.reportDamage(coords));
  }
}