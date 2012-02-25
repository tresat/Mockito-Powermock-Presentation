package com.tomtresansky.mockitopresentation.example03.verification;

import static org.mockito.Mockito.*;

import org.junit.Test;

public class BasicVerification {
  /*
   * A simple warehouse to test -- each time stock is called, item count should
   * increase.
   * 
   * If ship is called, count should decrease to 1 if greater than one, else
   * should throw exception.
   * 
   * Stock and ship both return the NEW item count AFTER stocking/shipping.
   * 
   * Clear will empty the entire warehouse.
   */
  interface Warehouse {
    int stock(String item);
    int ship(String item) throws OutOfStockException;
    int numberInStock(String item);
    void clear();
  }

  @Test
  public void testVerification() {
    final Warehouse mockWarehouse = mock(Warehouse.class);

    // Normally we would do any required stubbing here...

    // Normally we would now have the System Under Test actually utilize the mockWarehouse somehow here
    // Let's pretend it added a bicycle to our mock warehouse
    mockWarehouse.stock("bicycle");

    // Note: even WITHOUT any stubbing, Mockito allows for verification of mocks
    verify(mockWarehouse).stock("bicycle");

    // We DIDN'T clear the warehouse, this should fail
    verify(mockWarehouse).clear();

    // Check out the JUnit Failure message!
  }

  @Test
  public void testArgumentVerification() {
    // Setup mock warehouse
    final Warehouse mockWarehouse = mock(Warehouse.class);
    mockWarehouse.stock("playstation");

    verify(mockWarehouse).stock("playstation"); // yep, we stocked a playstation

    verify(mockWarehouse).stock("xbox"); // no xbox was stocked = verification fails
  }

  @Test
  public void testVerifyNumberOfCalls() {
    // Setup mock warehouse - has 3 hats
    final Warehouse mockWarehouse = mock(Warehouse.class);
    mockWarehouse.stock("hat");
    mockWarehouse.stock("hat");
    mockWarehouse.stock("hat");

    // FAILS - there is an implicit assumption that verify means "verify it happened exactly ONCE"
    verify(mockWarehouse).stock("hat");

    // FAILS - Identical to the above (times(1) is the default)
    // verify(mockWarehouse, times(1)).stock("hat");

    // Commenting out the above, we see how to verify number of calls
    verify(mockWarehouse, times(3)).stock("hat");

    // Can also be less specific
    verify(mockWarehouse, atMost(10)).stock("hat");
    verify(mockWarehouse, atLeast(2)).stock("hat");
    verify(mockWarehouse, atLeastOnce()).stock("hat");
  }

  @Test
  public void ensureOnlyExpectedCalls() {
    // Often easier to say what you DON'T want called then to explicitly specify what you DO want called

    // Setup mock warehouse - has pants
    final Warehouse mockClothingWarehouse = mock(Warehouse.class);
    mockClothingWarehouse.stock("pants");

    // This is a clothing warehouse --- ensure no food was added 
    verify(mockClothingWarehouse, never()).stock("orange");

    // Check that ONLY pants were added
    verify(mockClothingWarehouse, only()).stock("pants");

    // Can still act on mock AFTER verification
    mockClothingWarehouse.ship("pants");

    // But now the ONLY verification will FAIL
    // verify(mockClothingWarehouse, only()).stock("pants");
  }

  @Test
  public void ensureNoMoreInteractions() {
    final Warehouse mockBeefWarehouse = mock(Warehouse.class);

    // We EXPECT this stocking
    mockBeefWarehouse.stock("beef");

    // ... do something ...

    // We do NOT EXPECT this one
    mockBeefWarehouse.stock("pork");

    // ... finish doing something ...

    // So we'll verify the interactions we expect
    verify(mockBeefWarehouse).stock("beef");

    // FAILS - catches the UNEXPECTED pork stocking
    verifyNoMoreInteractions(mockBeefWarehouse);
  }

  class OutOfStockException extends RuntimeException {
    // left blank
  }
}
