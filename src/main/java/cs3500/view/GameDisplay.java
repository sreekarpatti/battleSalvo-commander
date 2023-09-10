package cs3500.view;

import cs3500.model.GameResult;
import cs3500.model.Player;
import java.io.IOException;

/**
 * Represents the view for a game of BattleSalvo.
 */
public class GameDisplay {
  private final Appendable appendable; // the output

  /**
   * Constructs a GameDisplay object.
   *
   * @param appendable the output
   */
  public GameDisplay(Appendable appendable) {
    this.appendable = appendable;
  }

  /**
   * Displays the start of the game.
   */
  public void displayGameStart() {
    try {
      appendable.append("Hello! Welcome to the OOD BattleSalvo Game!\n");
    } catch (IOException e) {
      System.out.println("Append failed");
    }
  }

  /**
   * displays message to get board size
   */
  public void getBoardSize() {

    try {
      appendable.append("Please enter a height and width between 6 and 15 below:\n"
          + "____________________________________________________________\n");
    } catch (IOException e) {
      System.out.println("Append failed");
    }
  }

  /**
   * displays message to get fleet size
   *
   * @param smallerDimension the smaller dimension of the board
   *                         (height or width)
   */
  public void getFleetSize(int smallerDimension) {
    try {
      appendable.append("Please enter your fleet in the order "
              + "[Carrier, Battleship, Destroyer, Submarine]. \n"
              + "Remember, your fleet may not exceed size ")
          .append(String.valueOf(smallerDimension)).append(".\n"
              + "____________________________________________________________\n");
    } catch (IOException e) {
      System.out.println("Append failed");
    }

  }

  /**
   * displays message if fleet size is invalid
   */
  public void invalidFleetSize() {
    try {
      appendable.append("Uh Oh! You've entered invalid fleet sizes.\n");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * creates the board for the player
   *
   * @param player the player
   * @param board  the board
   */
  public void makeBoard(Player player, String[][] board) {
    try {
      appendable.append(player.name()).append("'s board:\n");
      for (String[] strings : board) {
        for (String string : strings) {
          appendable.append(string);
        }
        appendable.append("\n");
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * displays message to get shot
   *
   * @param result the result of the shot
   */
  public void displayGameResult(GameResult result) {

    try {
      switch (result) {
        case WIN -> appendable.append("You win!");
        case LOSE -> appendable.append("You lose!");
        case TIE -> appendable.append("You tied!");
        default -> appendable.append("Something went wrong!");
      }
    } catch (IOException e) {
      System.out.println("Append failed");
    }
  }

  /**
   * displays message if board size is invalid
   */
  public void invalidBoardSize() {
    try {
      appendable.append("Uh Oh! You've entered invalid board size.\n");
    } catch (IOException e) {
      System.out.println("Append failed");
    }
  }

  /**
   * displays message to ask for shots
   *
   * @param activeShips the number of active ships
   *                    the player has left
   */
  public void askForShot(int activeShips) {
    try {
      appendable.append("Please enter a shot in the form \"X Y\". You have ")
          .append(String.valueOf(activeShips)).append(" shots.\n");
    } catch (IOException e) {
      System.out.println("Append failed");
    }
  }

  /**
   * displays message if shot is invalid
   */
  public void invalidShot() {
    try {
      appendable.append("Uh Oh! You've entered an invalid shot.\n");
    } catch (IOException e) {
      System.out.println("Append failed");
    }
  }

  /**
   * Returns the appendable
   *
   * @return appendable
   */
  public Appendable getAppendable() {
    return appendable;
  }
}
