package com.tomtresansky.mockitopresentation.example05.argumentmatchers;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Map;

import org.junit.Test;

public class BasicArgumentMatchersForStubbing {
  @Test
  public void testSingleArgumentMatcher() {
    // Mock a map, which we'll use to store information about various numbers
    @SuppressWarnings("unchecked")
    final Map<Integer, String> mockNumbers = mock(Map.class);

    // Here we'll use an argument matchers to specify what happens when we request a number
    when(mockNumbers.get(anyInt())).thenReturn("Here is a whole number.");

    System.out.println("With only anyInt() specified:");
    System.out.println("2: " + mockNumbers.get(2)); // Without argument matchers, we would get DEFAULT value of null for all of these
    System.out.println("17: " + mockNumbers.get(17));
    System.out.println("239: " + mockNumbers.get(239));
    System.out.println("0: " + mockNumbers.get(0));

    // Now we'll override some specific values - must do this AFTER specifying anyInt() (more specific matchers go LAST)
    when(mockNumbers.get(eq(0))).thenReturn("This is 0!");
    when(mockNumbers.get(eq(Math.PI))).thenReturn("This is Pi!");

    System.out.println("\nWith specific values 'overriden':");
    System.out.println("1: " + mockNumbers.get(1));
    System.out.println("Pi: " + mockNumbers.get(Math.PI));
    System.out.println("44: " + mockNumbers.get(44));
    System.out.println("0: " + mockNumbers.get(0));
  }

  /*
   * A sample interface to test: given a person and their skill level, assign
   * them to a team.
   */
  interface RosterManager {
    String assignToTeam(String name, int skill);
  }

  @Test
  public void testMultipleArgumentMatchers() {
    final RosterManager mockRosterManager = mock(RosterManager.class);

    // Set up some matchers for our roster manager
    when(mockRosterManager.assignToTeam(endsWith("Jones"), anyInt())).thenReturn("The Jones Team");
    when(mockRosterManager.assignToTeam(matches(".*Sm.*th.*"), anyInt())).thenReturn("The Smiths Team");
    when(mockRosterManager.assignToTeam(eq("Margie Dunn"), anyInt())).thenReturn("The Jones Team");
    // More specific matchers are added last: any player with skill level 3 should match High Skill Team
    when(mockRosterManager.assignToTeam(anyString(), eq(3))).thenReturn("The High-Skill Team");

    System.out.println("Try adding some players:");
    System.out.println("Linda Smith (1): " + mockRosterManager.assignToTeam("Linda Smith", 1));
    System.out.println("Margie Dunn (2): " + mockRosterManager.assignToTeam("Margie Dunn", 2));
    System.out.println("Debbie Smythe (2): " + mockRosterManager.assignToTeam("Debbie Smith", 2));
    System.out.println("Wilma Jones (1): " + mockRosterManager.assignToTeam("Wilma Jones", 1));
    System.out.println("Tina Jones (3): " + mockRosterManager.assignToTeam("Tina Jones", 3));

    /*
     * The real value of matchers versus 'dumb' stubs which ALWAYS return: we
     * know when something didn't match.
     * 
     * Can get us real close to being back to an expectation-based style of
     * mocking...but without some of the negatives.
     */
    System.out.println("\nDoes not match the Matchers:");
    System.out.println("Brenda Evans (2): " + mockRosterManager.assignToTeam("Brenda Evans", 2)); // unmatched value results in null
  }
}
