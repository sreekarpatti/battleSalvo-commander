package cs3500.controller.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for JoinJson
 */
class JoinJsonTest {

  JoinJson joinJson1 = new JoinJson("name1", "SINGLE");
  JoinJson joinJson2 = new JoinJson("name2", "MULTI");

  /**
   * Tests for name
   */
  @Test
  void name() {
    assertEquals("name1", joinJson1.name());
    assertEquals("name2", joinJson2.name());
  }

  /**
   * Tests for gt
   */
  @Test
  void gt() {
    assertEquals("SINGLE", joinJson1.gt());
    assertEquals("MULTI", joinJson2.gt());
  }
}