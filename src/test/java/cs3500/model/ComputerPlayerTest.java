package cs3500.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.model.ComputerPlayer;
import cs3500.model.Coord;
import cs3500.model.GameBoard;
import cs3500.model.Player;
import cs3500.model.Ship;
import cs3500.model.ShipType;
import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ComputerPlayerTest {

  Player computer;
  Random rand;
  GameBoard board;

  @BeforeEach
  void setUp() {
    rand = new Random(4);
    board = new GameBoard(rand, 6, 7);
    ArrayList<Coord> coordsCarrier = new ArrayList<Coord>();
    coordsCarrier.add(new Coord(1, 0));
    coordsCarrier.add(new Coord(1, 1));
    coordsCarrier.add(new Coord(1, 2));
    coordsCarrier.add(new Coord(1, 3));
    coordsCarrier.add(new Coord(1, 4));
    coordsCarrier.add(new Coord(1, 5));
    ArrayList<Coord> coordsBattleship = new ArrayList<Coord>();
    coordsBattleship.add(new Coord(2, 0));
    coordsBattleship.add(new Coord(2, 1));
    coordsBattleship.add(new Coord(2, 2));
    coordsBattleship.add(new Coord(2, 3));
    coordsBattleship.add(new Coord(2, 4));
    ArrayList<Coord> coordsDestroyer = new ArrayList<Coord>();
    coordsDestroyer.add(new Coord(3, 0));
    coordsDestroyer.add(new Coord(3, 1));
    coordsDestroyer.add(new Coord(3, 2));
    coordsDestroyer.add(new Coord(3, 3));
    ArrayList<Coord> coordsSubmarine = new ArrayList<Coord>();
    coordsSubmarine.add(new Coord(4, 0));
    coordsSubmarine.add(new Coord(4, 1));
    coordsSubmarine.add(new Coord(4, 2));

    Ship carrier = new Ship(ShipType.CARRIER, coordsCarrier);
    Ship battleship = new Ship(ShipType.BATTLESHIP, coordsBattleship);
    Ship destroyer = new Ship(ShipType.DESTROYER, coordsDestroyer);
    Ship submarine = new Ship(ShipType.SUBMARINE, coordsSubmarine);
    ArrayList<Ship> ships = new ArrayList<Ship>();
    ships.add(carrier);
    ships.add(battleship);
    ships.add(destroyer);
    ships.add(submarine);
    board.setOpponentShipList(ships);
    board.generateShots();
    computer = new ComputerPlayer(board, rand);
  }

  @Test
  void name() {
    assertEquals("computer", computer.name());
  }

  @Test
  void takeShots() {
    assertEquals(board.getCompCurrVolley(), computer.takeShots());
  }

  @Test
  void successfulHits() {
  }

  @Test
  void endGame() {
  }
}