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
    final List<String> spyList = spy(new ArrayList<String>());
    // Note: we provide an INSTANCE here, NOT a class token

    // But we can still override methods with stubs...
    when(spyList.contains("Mercury")).thenReturn(true);
    when(spyList.size()).thenReturn(8);

    // Actual method calls work as expected
    System.out.println(
        "Before adding Venus, Contains Venus?: " + spyList.contains("Venus"));

    spyList.add("Venus");

    System.out.println(
        "After adding Venus, Contains Venus?: " + spyList.contains("Venus"));

    // Can add and iterate normally
    spyList.add("Earth");
    spyList.add("Mars");

    System.out.println("\nPlanets in the list:");
    for (final String planet : spyList) {
      System.out.println(planet);
    }

    // And yet, the PARTIALLY MOCKED LIST contains 8 items, 
    // and contains Mercury, which we never added
    // It won't show up in the iteration, because we didn't stub those 
    System.out.println("\nSize: " + spyList.size());
    System.out.println("Contains Mercury?: " + spyList.contains("Mercury"));
  }
}
