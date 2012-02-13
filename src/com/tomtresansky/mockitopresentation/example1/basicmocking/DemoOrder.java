package com.tomtresansky.mockitopresentation.example1.basicmocking;

import java.util.HashMap;
import java.util.Map;

public class DemoOrder {
  public static class SimpleWarehouse implements Warehouse {
    // DB Simulated with a map...
    private final Map<String, Integer> contents = new HashMap<String, Integer>();

    public void store(final String id, final int quantity) {
      contents.put(id, quantity);
    }

    @Override
    public boolean process(final String id, final int quantity) {
      if (inStock(id, quantity)) {
        contents.put(id, contents.get(id) - quantity);
        return true; // we successfully loaded
      } else {
        return false; // couldn't load, not in stock
      }
    }

    private boolean inStock(final String id, final int quantity) {
      System.out.println("WAREHOUSE: Checking db for item...");

      if (contents.containsKey(id) && quantity <= contents.get(id)) {
        System.out.println("WAREHOUSE: Item found!");
        return true;
      } else {
        return false;
      }
    }
  }

  public static void main(final String[] args) {
    System.out.println("Setting up a warehouse...");
    final SimpleWarehouse pantsWarehouse = new SimpleWarehouse();
    pantsWarehouse.store("Blue Pants", 3);

    System.out.println("Creating an order...");
    final Order pantsOrder = new Order("Blue Pants", 3);

    System.out.println("Attempting to fill order from warehouse...");
    final boolean orderResult = pantsOrder.fill(pantsWarehouse);

    if (orderResult) {
      System.out.println("Order successfully filled!");
    }
  }
}
