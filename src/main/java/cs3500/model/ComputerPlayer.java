package cs3500.model;

import java.util.List;
import java.util.Random;

/**
 * Represents a computer player in the game of BattleSalvo.
 */
public class ComputerPlayer extends AbstractPlayer {
  /**
   * Constructs a ComputerPlayer object.
   *
   * @param board  the game board
   * @param random the random number generator
   */
  public ComputerPlayer(GameBoard board, Random random) {
    super(board, random);
  }

  /**
   * Get the player's name.
   *
   * @return the player's name
   */
  @Override
  public String name() {
    return "computer";
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  @Override
  public List<Coord> takeShots() {
    return this.board.getCompCurrVolley();
  }

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  @Override
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {

  }

  /**
   * Notifies the player that the game is over.
   * Win, lose, and draw should all be supported
   *
   * @param result if the player has won, lost, or forced a draw
   * @param reason the reason for the game ending
   */
  @Override
  public void endGame(GameResult result, String reason) {

  }
}