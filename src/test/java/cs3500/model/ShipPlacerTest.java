package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.model.Coord;
import cs3500.model.Ship;
import cs3500.model.ShipPlacer;
import cs3500.model.ShipType;
import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipPlacerTest {

  ShipPlacer sp1;
  Random rand;
  int[][] currBoard;
  int boardWidth;
  int boardHeight;

  @BeforeEach
  void setUp() {
    rand = new Random(4);
    sp1 = new ShipPlacer(rand);
    boardWidth = 6;
    boardHeight = 7;
    currBoard = new int[boardHeight][boardWidth];

  }

  @Test
  void placeShips() {
    ArrayList<Coord> carrierCoordss = new ArrayList<>();
    carrierCoordss.add(new Coord(5, 0));
    carrierCoordss.add(new Coord(5, 1));
    carrierCoordss.add(new Coord(5, 2));
    carrierCoordss.add(new Coord(5, 3));
    carrierCoordss.add(new Coord(5, 4));
    carrierCoordss.add(new Coord(5, 5));
    Ship carrier = sp1.placeShips(boardWidth, boardHeight, ShipType.CARRIER, currBoard);
    assertEquals(6, carrier.getCoords().size());
    assertEquals(carrierCoordss, carrier.getCoords());

    ArrayList<Coord> battleshipCoords = new ArrayList<>();
    battleshipCoords.add(new Coord(1, 2));
    battleshipCoords.add(new Coord(1, 3));
    battleshipCoords.add(new Coord(1, 4));
    battleshipCoords.add(new Coord(1, 5));
    battleshipCoords.add(new Coord(1, 6));
    Ship battleship = sp1.placeShips(boardWidth, boardHeight, ShipType.BATTLESHIP, currBoard);
    assertEquals(5, battleship.getCoords().size());
    assertEquals(battleshipCoords, battleship.getCoords());

    ArrayList<Coord> submarineCoords = new ArrayList<>();
    submarineCoords.add(new Coord(3, 0));
    submarineCoords.add(new Coord(3, 1));
    submarineCoords.add(new Coord(3, 2));
    Ship submarine = sp1.placeShips(boardWidth, boardHeight, ShipType.SUBMARINE, currBoard);
    assertEquals(3, submarine.getCoords().size());
    assertEquals(submarineCoords, submarine.getCoords());

    ArrayList<Coord> destroyerCoords = new ArrayList<>();
    destroyerCoords.add(new Coord(4, 0));
    destroyerCoords.add(new Coord(4, 1));
    destroyerCoords.add(new Coord(4, 2));
    destroyerCoords.add(new Coord(4, 3));
    Ship destroyer = sp1.placeShips(boardWidth, boardHeight, ShipType.DESTROYER, currBoard);
    assertEquals(4, destroyer.getCoords().size());
    assertEquals(destroyerCoords, destroyer.getCoords());
  }
}