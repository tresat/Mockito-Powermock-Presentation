package com.tomtresansky.mockitopresentation.example1.basicmocking;

import org.junit.Assert;
import org.junit.Test;

public class OrderUTestWithHandWrittenStubs {
  public static class StubWarehouse implements Warehouse {
    @Override
    public boolean process(final String id, final int quantity) {
      System.out.println("STUB WAREHOUSE: processing order...");
      return true; // Stubbed warehouse always processes successfully
    }
  }

  @Test
  public void testOrderFills() {
    System.out.println("Setting up a stub warehouse...");
    final StubWarehouse pantsWarehouse = new StubWarehouse();

    System.out.println("Creating an order...");
    final Order pantsOrder = new Order("Blue Pants", 3);

    System.out.println("Attempting to fill order from warehouse...");
    pantsOrder.fill(pantsWarehouse);

    System.out.println("Testing order is filled...");
    Assert.assertTrue("Order should be filled!", pantsOrder.isFilled());
  }
}