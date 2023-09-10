package cs3500.pa04.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.model.ShipType;
import org.junit.jupiter.api.Test;

class ShipTypeTest {

  ShipType carrier = ShipType.CARRIER;
  ShipType battleship = ShipType.BATTLESHIP;
  ShipType submarine = ShipType.SUBMARINE;
  ShipType destroyer = ShipType.DESTROYER;

  @Test
  void getSize() {
    assertEquals(6, carrier.getSize());
    assertEquals(5, battleship.getSize());
    assertEquals(3, submarine.getSize());
    assertEquals(4, destroyer.getSize());
  }

  @Test
  void getSymbol() {
    assertEquals(" C ", carrier.getSymbol());
    assertEquals(" B ", battleship.getSymbol());
    assertEquals(" S ", submarine.getSymbol());
    assertEquals(" D ", destroyer.getSymbol());
  }
}