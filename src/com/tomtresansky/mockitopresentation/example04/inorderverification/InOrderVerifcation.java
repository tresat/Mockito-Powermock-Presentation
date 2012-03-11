package com.tomtresansky.mockitopresentation.example04.inorderverification;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.InOrder;

public class InOrderVerifcation {
  /*
   * The return of our warehouse type.
   */
  interface Warehouse {
    int stock(String item);
    int ship(String item) throws OutOfStockException;
    int numberInStock(String item);
    void clear();
  }

  @Test
  public void testInOrderVerification() {
    final Warehouse mockWarehouse = mock(Warehouse.class);

    // Add some sneakers
    mockWarehouse.stock("sneakers");

    // And ship them
    mockWarehouse.ship("sneakers");

    // We want to verify we've shipped only AFTER we've stocked
    final InOrder warehouseOrdering = inOrder(mockWarehouse);
    warehouseOrdering.verify(mockWarehouse).stock("sneakers");
    warehouseOrdering.verify(mockWarehouse).ship("sneakers");

    // What if we shipped boots PRIOR to stocking?
    mockWarehouse.ship("boots");
    mockWarehouse.stock("loafers"); // extraneous
    mockWarehouse.stock("boots");

    // FAILS - we SHOULD HAVE stocked first
    //warehouseOrdering.verify(mockWarehouse).stock("boots");
    //warehouseOrdering.verify(mockWarehouse).ship("boots"); 
    // NOTE: it failed on previous line - only verifies order as needed

    // Also note: stocking loafers doesn't matter - only verifies 
    // order of methods checked on InOrder object
    // Could still check a method was called regardless of order
    verify(mockWarehouse).stock("loafers");
  }

  @Test
  public void testInOrderAcrossMultipleMocks() {
    final Warehouse mockWarehouse1 = mock(Warehouse.class);
    final Warehouse mockWarehouse2 = mock(Warehouse.class);

    // Shipping bananas from first warehouse to second
    mockWarehouse1.stock("bananas");
    mockWarehouse1.ship("bananas");
    mockWarehouse2.stock("bananas");

    // Success
    final InOrder warehouseOrdering = inOrder(mockWarehouse1, mockWarehouse2);
    warehouseOrdering.verify(mockWarehouse1).ship("bananas");
    warehouseOrdering.verify(mockWarehouse2).stock("bananas");

    // But what if warehouse 2 stocks an item before it is shipped
    // from warehouse 1?
    mockWarehouse1.stock("apples");
    mockWarehouse2.stock("apples");
    mockWarehouse1.ship("apples");

    // FAILS: we stocked in 2 before 1 shipped!
    //warehouseOrdering.verify(mockWarehouse1).ship("apples");
    //warehouseOrdering.verify(mockWarehouse2).stock("apples");
  }

  @SuppressWarnings("serial")
  class OutOfStockException extends RuntimeException {
    // left blank
  }
}
