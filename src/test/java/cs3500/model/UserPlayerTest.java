package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.model.Coord;
import cs3500.model.GameBoard;
import cs3500.model.Player;
import cs3500.model.UserPlayer;
import java.util.ArrayList;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserPlayerTest {
  Player user;
  Random rand;
  GameBoard board;

  @BeforeEach
  void setUp() {
    rand = new Random(4);
    board = new GameBoard(rand, 6, 7);
    user = new UserPlayer(board, rand);
    ArrayList<Coord> shots = new ArrayList<Coord>();
    shots.add(new Coord(1, 1));
    shots.add(new Coord(2, 2));
    board.setUserCurrVolley(shots);
  }

  @Test
  void name() {
    assertEquals("User", user.name());
  }

  @Test
  void takeShots() {
    assertEquals(board.getUserCurrVolley(), user.takeShots());
  }

  @Test
  void successfulHits() {
  }

  @Test
  void endGame() {
  }
}