package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import cs3500.model.ComputerPlayer;
import cs3500.model.Coord;
import cs3500.model.GameBoard;
import cs3500.model.Player;
import cs3500.model.Ship;
import cs3500.model.ShipType;
import cs3500.model.UserPlayer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameBoardTest {

  GameBoard gb1;
  Random rand;
  Player player1;
  Player player2;
  String[][] board1;
  ArrayList<Ship> list1;
  ArrayList<Ship> list2;

  @BeforeEach
  void setUp() {
    rand = new Random(4);
    gb1 = new GameBoard(rand, 6, 6);
    player1 = new UserPlayer(gb1, rand);
    player2 = new ComputerPlayer(gb1, rand);
    HashMap<ShipType, Integer> specs = new HashMap<>();
    specs.put(ShipType.CARRIER, 1);
    specs.put(ShipType.BATTLESHIP, 1);
    specs.put(ShipType.SUBMARINE, 1);
    specs.put(ShipType.DESTROYER, 1);

    list1 = (ArrayList<Ship>) player1.setup(6, 6, specs);
    list2 = (ArrayList<Ship>) player2.setup(6, 6, specs);
    gb1.setUserShipList(list1);
    gb1.setOpponentShipList(list2);
    board1 = new String[6][6];
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 6; j++) {
        board1[i][j] = " - ";
      }
    }
  }

  @Test
  void genBoards() {
    gb1.genBoards();
    assertNotNull(gb1.getBoard());
    assertNotNull(gb1.getOpponentBoard());
  }

  @Test
  void updateBoard() {
    ArrayList<Coord> hits = new ArrayList<>();
    ArrayList<Coord> misses = new ArrayList<>();
    hits.add(new Coord(0, 0));
    hits.add(new Coord(0, 1));
    misses.add(new Coord(0, 2));
    misses.add(new Coord(0, 3));
    gb1.updateBoard(board1, hits, misses);
    assertEquals(" H ", board1[0][0]);
    assertEquals(" H ", board1[1][0]);
    assertEquals(" M ", board1[2][0]);
    assertEquals(" M ", board1[3][0]);
  }

  @Test
  void generateShots() {
    gb1.generateShots();
    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 2));
    shots.add(new Coord(2, 5));
    shots.add(new Coord(2, 4));
    shots.add(new Coord(5, 0));
    assertEquals(shots, gb1.getCompCurrVolley());
  }

  @Test
  void reportMisses() {
    ArrayList<Coord> hits = new ArrayList<>();
    hits.add(new Coord(0, 0));
    hits.add(new Coord(0, 1));
    ArrayList<Coord> allcoords = new ArrayList<>();
    allcoords.add(new Coord(0, 0));
    allcoords.add(new Coord(0, 1));
    allcoords.add(new Coord(0, 2));
    allcoords.add(new Coord(0, 3));
    ArrayList<Coord> miss = new ArrayList<>();
    miss.add(new Coord(0, 2));
    miss.add(new Coord(0, 3));
    assertEquals(miss, gb1.reportMisses(hits, allcoords));
  }

  @Test
  void getActiveUserShips() {
    assertEquals(list1.size(), gb1.getActiveUserShips());
  }

  @Test
  void getActiveCompShips() {
    assertEquals(list2.size(), gb1.getActiveCompShips());
  }

  @Test
  void setUserShipList() {

  }

  @Test
  void setOpponentShipList() {
  }

  @Test
  void setUserCurrVolley() {

  }

  @Test
  void getUserCurrVolley() {
    ArrayList<Coord> shots = new ArrayList<>();
    shots.add(new Coord(0, 2));
    shots.add(new Coord(2, 5));
    shots.add(new Coord(2, 4));
    shots.add(new Coord(5, 0));
    gb1.setUserCurrVolley(shots);
    assertEquals(shots, gb1.getUserCurrVolley());
  }

  @Test
  void getCompCurrVolley() {
    gb1.generateShots();
    assertEquals(4, gb1.getCompCurrVolley().size());
  }

  @Test
  void getBoard() {
    gb1.genBoards();
    assertNotNull(gb1.getBoard());
  }

  @Test
  void getOpponentBoard() {
    gb1.genBoards();
    assertEquals(Arrays.deepToString(board1), Arrays.deepToString(gb1.getOpponentBoard()));
  }
}