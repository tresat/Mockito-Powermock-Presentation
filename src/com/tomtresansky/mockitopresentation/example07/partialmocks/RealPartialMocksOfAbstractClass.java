package com.tomtresansky.mockitopresentation.example07.partialmocks;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class RealPartialMocksOfAbstractClass {
  /*
   * Here is an abstract class to test. One implemented method, and one abstract
   * one.
   */
  abstract class AbstractStreetFighter {
    public void fireball() {
      System.out.println(
          "Implemented in Abstract class: Throwing a fireball: Had-ou-ken!");
    }

    public abstract void dragonPunch(); // No dragon punch implementation
    public abstract void hurricaneKick(); // No hurricane kick
  }

  @Test
  public void testPartialMockOfAbstractClass() {
    // To spy on abstract class, need to provide concrete 
    // implementation of abstract methods
    // We'll do this via an anonymous sub-class
    final AbstractStreetFighter mockStreetFighter =
        mock(AbstractStreetFighter.class);

    // Use the implemented hadouken method (working with 
    // void methods means we use the backwards do-when syntax)
    doCallRealMethod().when(mockStreetFighter).fireball();

    // Stub the dragonPunch method using an Answer to 
    // supply side-effects of printing to out
    doAnswer(new Answer<Void>() {
      // Answer is generisized using java.lang.Void to 
      //represent no return values
      @Override
      public Void answer(final InvocationOnMock invocation) throws Throwable {
        // Produce desired side-effect of printing
        System.out.println(
                "Stubbed using Answer: Throwing a dragon punch: Sho-ryu-ken!");

        // We must return something compatible with "Void" 
        // to make the compiler happy
        return null;
      }
    }).when(mockStreetFighter).dragonPunch();

    // And demonstrate that we can now call fireball and 
    // dragon punch methods on our mock
    mockStreetFighter.fireball();
    mockStreetFighter.dragonPunch();

    // But hurricane kick still does nothing: no implementation in 
    // abstract class, no stubbing
    System.out.println("\nNow trying a hurricane kick:");
    mockStreetFighter.hurricaneKick();
    System.out.println("Done trying hurricane kick.");

    // And if we wanted, we could now override the concrete implementation 
    // of fireball with a stubbing
    doAnswer(new Answer<Void>() {
      @Override
      public Void answer(final InvocationOnMock invocation) throws Throwable {
        // Produce desired side-effect of printing
        System.out.println(
            "Stubbed using Answer: Throwing a BLUE fireball: had-ou-ken!");

        // We must return something compatible with "Void" 
        // to make the compiler happy
        return null;
      }
    }).when(mockStreetFighter).fireball();

    // And demonstrate that the behaviour has now changed
    System.out.println("\nTrying our newly stubbed fireball:");
    mockStreetFighter.fireball();
  }

  @Test
  public void testAlternatePartialMockOfAbstractClass() {
    /*
     * Alternate method of creation, defaults to calling real methods (when
     * available)
     */
    final AbstractStreetFighter mockStreetFighter2 =
        mock(AbstractStreetFighter.class, CALLS_REAL_METHODS);
    // this is the same as using spy() = defaults to being just a PROXY

    System.out.println(
        "Test method calls on our CALLS_REAL_METHOD created mock:");
    System.out.println("\nHadouken (implemented in abstract class):");
    mockStreetFighter2.fireball();

    // No implementations for this...default is call REAL methods = FAILS
    System.out.println("\nDragon Punch (NOT implemented):");
    mockStreetFighter2.dragonPunch();
  }
}
