package com.tomtresansky.mockitopresentation.example02.mockingandstubbing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

public class CornerCases {
  /*
   * An interface to test.
   */
  interface Rocket {
    /*
     * Method might throw an exception, if rocket is unfueled.
     */
    boolean readyToLaunch() throws UnfueledException;
  }

  @Test
  public void testMethodThrowingExceptions() {
    final Rocket mockUnfueledRocket = mock(Rocket.class);

    // instead of thenReturn(value) we use thenThrow(exception)
    when(mockUnfueledRocket.readyToLaunch()).thenThrow(new UnfueledException());

    try {
      // The rocket has no fuel...can't check if ready to launch!    
      mockUnfueledRocket.readyToLaunch();

      // if we made it to here without an exception...is incorrect
      fail();
    } catch (final UnfueledException e) {
      System.out.println(
          "Unfueled rocket threw UnfueledException --- all is well!");
    }
  }

  /*
   * Another type of rocket interface to test.
   */
  interface Rocket2 {
    /*
     * This one has a launch method which might throw an exception.
     */
    void launch() throws ExplodeException;
  }

  @Test
  public void testStubbingVoidMethodWithExceptions() {
    final Rocket2 mockBadRocket = mock(Rocket2.class);

    // How can we test our emergency response to an exploding rocket?  
    // The below won't work on a void method, since there is no
    // result of mockBadRocket.launch() to pass as argument to when()
    // and the compiler doesn't like this = ERROR
    // when(mockBadRocket.launch()).thenThrow(new ExplodeException());

    // Problem is Mocito's when() call... expects a value 
    // (the type of the result), void methods have no result...

    // Syntax to stub this method throwing exceptions is a little stranger
    doThrow(new ExplodeException()).when(mockBadRocket).launch();

    try {
      mockBadRocket.launch();

      // if we made it to here without an exception...is incorrect
      fail();
    } catch (final ExplodeException e) {
      System.out.println(
          "The rocket blew up (as expected) --- call the Fire Dept.!");
    }
  }
}

@SuppressWarnings("serial")
class UnfueledException extends RuntimeException {
  // left blank
};

@SuppressWarnings("serial")
class ExplodeException extends RuntimeException {
  // left blank
};
