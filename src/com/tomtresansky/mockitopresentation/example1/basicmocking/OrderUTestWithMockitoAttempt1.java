package com.tomtresansky.mockitopresentation.example1.basicmocking;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Test;

public class OrderUTestWithMockitoAttempt1 {
  @Test
  public void testOrderFills() {
    System.out.println("Setting up a mock warehouse using Mockito...");
    final Warehouse mockWarehouse = mock(Warehouse.class);

    System.out.println("Creating an order...");
    final Order pantsOrder = new Order("Blue Pants", 3);

    System.out.println("Attempting to fill order from warehouse...");
    pantsOrder.fill(mockWarehouse);

    System.out.println("Testing order is filled...");
    Assert.assertTrue("Order should be filled!", pantsOrder.isFilled());
  }
}
