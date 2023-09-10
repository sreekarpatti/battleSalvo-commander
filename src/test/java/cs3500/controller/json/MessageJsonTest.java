package cs3500.controller.json;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

/**
 * Tests for MessageJson
 */
public class MessageJsonTest {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Tests for MessageJson constructor
   */
  @Test
  public void testMessageJsonConstructor() {
    String methodName = "testMethod";
    JsonNode arguments = objectMapper.createObjectNode().put("param1", "value1");

    MessageJson messageJson = new MessageJson(methodName, arguments);

    assertEquals(methodName, messageJson.methodName());
    assertEquals(arguments, messageJson.arguments());
  }

  /**
   * Tests for MessageJson json property names
   */
  @Test
  public void testMessageJsonJsonProperty() {
    String methodName = "testMethod";
    JsonNode arguments = objectMapper.createObjectNode().put("param1", "value1");

    MessageJson messageJson = new MessageJson(methodName, arguments);

    assertEquals(methodName, messageJson.methodName());
    assertEquals(arguments, messageJson.arguments());

    // Verify the JSON property names
    assertEquals(methodName, messageJson.methodName());
    assertEquals(arguments, messageJson.arguments());
  }
}
