package com.tomtresansky.mockitopresentation.example07.partialmocks;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Spies {
  @Test
  public void testSpying() {
    /*
     * Here we set up a partial mock using the spy() method.
     * 
     * All method calls DEFAULT to calling the ACTUAL methods of the instance.
     */
    final List<String> spyList = spy(new ArrayList<String>()); // Note: we provide an INSTANCE here, NOT a class token

    // But we can still override methods with stubs...
    when(spyList.contains("Mercury")).thenReturn(true);
    when(spyList.size()).thenReturn(8);

    // Actual method calls work as expected
    System.out.println("Before adding Venus, Contains Venus?: " + spyList.contains("Venus"));
    spyList.add("Venus");
    System.out.println("After adding Venus, Contains Venus?: " + spyList.contains("Venus"));

    // Can add and iterate normally
    spyList.add("Earth");
    spyList.add("Mars");

    System.out.println("\nPlanets in the list:");
    for (final String planet : spyList) {
      System.out.println(planet);
    }

    // And yet, the PARTIALLY MOCKED LIST contains 8 items, and contains Mercury, which we never added
    // It won't show up in the iteration, because we didn't stub those 
    System.out.println("\nSize: " + spyList.size());
    System.out.println("Contains Mercury?: " + spyList.contains("Mercury"));
  }

  /*
   * A sample interface with which to test partial mocks.
   */
  interface Book {
    String getChapter1();
    String getChapter2();
    String getChapter3();
    String getChapter4();
  }

  /*
   * A sample implementation for testing partial mocks.
   */
  class Dracula implements Book {
    @Override
    public String getChapter1() {
      return "It was a dark and stormy night...";
    }

    @Override
    public String getChapter2() {
      return "In walked Van Helsing...";
    }

    @Override
    public String getChapter3() {
      return "Thus began the final showdown...";
    }

    @Override
    public String getChapter4() {
      return "And they all lived happily ever after.";
    }
  }

  @Test
  public void testRealPartialMocks() {
    /*
     * Here we set up a parial mock using the the call read method syntax.
     * 
     * All method calls DEFAULT to using stubs (or returning default stubbing
     * values if unstubbed).
     */
    final Book mockBook = mock(Dracula.class); // NOTE: we must mock the IMPLEMENTATION here to call real methods

    // Stub chapters 1 and 2
    when(mockBook.getChapter1()).thenReturn("Stubbed chapter 1...");
    when(mockBook.getChapter2()).thenReturn("Stubbed chapter 2...");
    // But chapter 3
    when(mockBook.getChapter3()).thenCallRealMethod();
    // And don't touch chapter 4

    // Test
    System.out.println("Chapter 1: " + mockBook.getChapter1());
    System.out.println("Chapter 2: " + mockBook.getChapter2());
    System.out.println("Chapter 3: " + mockBook.getChapter3());
    System.out.println("Chapter 4: " + mockBook.getChapter4()); // returns null, unstubbed methods return stub DEFAULT values
  }
}
