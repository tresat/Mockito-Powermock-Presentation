package com.tomtresansky.mockitopresentation.example1.basicmocking;

class Order {
  private final String id;
  private final int quantity;

  private boolean isFilled = false;

  public boolean isFilled() {
    return isFilled;
  }

  public Order(final String ID, final int quantity) {
    this.id = ID;
    this.quantity = quantity;
  }

  public boolean fill(final Warehouse warehouse) {
    isFilled = warehouse.process(id, quantity);
    return isFilled;
  }
}