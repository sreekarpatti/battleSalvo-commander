package cs3500.pa04.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.model.GameBoard;
import cs3500.model.GameResult;
import cs3500.model.Player;
import cs3500.model.UserPlayer;
import cs3500.view.GameDisplay;
import java.io.IOException;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GameDisplayTest {
  GameDisplay gd1;
  Random rand;
  Player p1;
  String[][] boardTest;

  @BeforeEach
  void setUp() {
    gd1 = new GameDisplay(new StringBuilder());
    rand = new Random(4);
    p1 = new UserPlayer(new GameBoard(rand, 10, 10), rand);
    boardTest = new String[10][10];
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 10; j++) {
        boardTest[i][j] = " - ";
      }
    }
  }


  @Test
  void displayGameStart() throws IOException {
    gd1.displayGameStart();
    assertEquals("Hello! Welcome to the OOD BattleSalvo Game!\n", gd1.getAppendable().toString());
  }

  @Test
  void getBoardSize() throws IOException {
    gd1.getBoardSize();
    assertEquals("Please enter a height and width between 6 and 15 below:\n"
            + "____________________________________________________________\n",
        gd1.getAppendable().toString());
  }

  @Test
  void getFleetSize() throws IOException {
    gd1.getFleetSize(6);
    assertEquals("Please enter your fleet in the order "
            + "[Carrier, Battleship, Destroyer, Submarine]. \n"
            + "Remember, your fleet may not exceed size "
            + 6 + ".\n"
            + "____________________________________________________________\n",
        gd1.getAppendable().toString());
  }

  @Test
  void invalidFleetSize() throws IOException {
    gd1.invalidFleetSize();
    assertEquals("Uh Oh! You've entered invalid fleet sizes.\n", gd1.getAppendable().toString());
  }

  @Test
  void makeBoard() {
    gd1.makeBoard(p1, boardTest);
    assertEquals("User's board:\n"
        + " -  -  -  -  -  -  -  -  -  - \n"
        + " -  -  -  -  -  -  -  -  -  - \n"
        + " -  -  -  -  -  -  -  -  -  - \n"
        + " -  -  -  -  -  -  -  -  -  - \n"
        + " -  -  -  -  -  -  -  -  -  - \n"
        + " -  -  -  -  -  -  -  -  -  - \n"
        + " -  -  -  -  -  -  -  -  -  - \n"
        + " -  -  -  -  -  -  -  -  -  - \n"
        + " -  -  -  -  -  -  -  -  -  - \n"
        + " -  -  -  -  -  -  -  -  -  - \n", gd1.getAppendable().toString());
  }

  @Test
  void displayGameResultWin() throws IOException {
    gd1.displayGameResult(GameResult.WIN);
    assertEquals("You win!", gd1.getAppendable().toString());
  }

  @Test
  void displayGameResultLose() throws IOException {
    gd1.displayGameResult(GameResult.LOSE);
    assertEquals("You lose!", gd1.getAppendable().toString());
  }

  @Test
  void displayGameResultTie() throws IOException {
    gd1.displayGameResult(GameResult.TIE);
    assertEquals("You tied!", gd1.getAppendable().toString());
  }

  @Test
  void invalidBoardSize() throws IOException {
    gd1.invalidBoardSize();
    assertEquals("Uh Oh! You've entered invalid board size.\n", gd1.getAppendable().toString());
  }

  @Test
  void askForShot() throws IOException {
    gd1.askForShot(6);
    assertEquals("Please enter a shot in the form \"X Y\". You have 6 shots.\n",
        gd1.getAppendable().toString());
  }

  @Test
  void invalidShot() throws IOException {
    gd1.invalidShot();
    assertEquals("Uh Oh! You've entered an invalid shot.\n", gd1.getAppendable().toString());
  }
}