package com.tomtresansky.mockitopresentation.example07.partialmocks;

import static org.mockito.Mockito.*;

import org.junit.Test;

public class RealPartialMocks {

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
     * Here we set up a partial mock using the the call read method syntax.
     * 
     * All method calls DEFAULT to using stubs (or returning default stubbing
     * values if unstubbed).
     */
    final Book mockBook = mock(Dracula.class);
    // NOTE: we must mock the IMPLEMENTATION here to call real methods

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
    System.out.println("Chapter 4: " + mockBook.getChapter4());
    // getChapter4() returns null, unstubbed methods return stub DEFAULT values
  }
}
