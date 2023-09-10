package cs3500.controller.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.model.GameResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for EndGameJson
 */
class EndGameJsonTest {

  EndGameJson endGameJson1 = new EndGameJson(GameResult.WIN, "You won!");
  EndGameJson endGameJson2 = new EndGameJson(GameResult.LOSE, "You lost!");
  EndGameJson endGameJson3 = new EndGameJson(GameResult.TIE, "You tied!");

  /**
   * Tests for result
   */
  @Test
  void result() {
    Assertions.assertEquals(GameResult.WIN, endGameJson1.result());
    Assertions.assertEquals(GameResult.LOSE, endGameJson2.result());
    Assertions.assertEquals(GameResult.TIE, endGameJson3.result());
  }

  /**
   * Tests for reason
   */
  @Test
  void reason() {
    assertEquals("You won!", endGameJson1.reason());
    assertEquals("You lost!", endGameJson2.reason());
    assertEquals("You tied!", endGameJson3.reason());
  }
}