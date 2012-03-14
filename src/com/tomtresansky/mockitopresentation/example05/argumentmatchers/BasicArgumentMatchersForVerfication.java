package com.tomtresansky.mockitopresentation.example05.argumentmatchers;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Random;
import java.util.Set;

import org.junit.Test;

public class BasicArgumentMatchersForVerfication {
  /*
   * A sample interface to test: given a person and their skill level, assign
   * them to a team.
   */
  interface RosterManager {
    String assignToTeam(String name, int skill);
  }

  @Test
  public void testCallVerificationForArgumentValues() {
    final RosterManager mockRosterManager = mock(RosterManager.class);

    // ... would normally stub here

    // Make some calls to the roster manager
    mockRosterManager.assignToTeam("Bob", 2);
    mockRosterManager.assignToTeam("Robert", new Random().nextInt(4));
    // We don't what skill Robert has been assigned using

    // Verifying Bob was assigned is easy
    verify(mockRosterManager).assignToTeam("Bob", 2);

    /*
     * But how do we verify if Bob was added if we don't know what skill he was
     * called with?
     * 
     * ...
     * 
     * With argument matchers!
     */
    verify(mockRosterManager).assignToTeam("Robert", anyInt()); // attempt 1: FAILS

    // Have to use argument matchers for ALL arguments if used for ANY argumnet
    //verify(mockRosterManager).assignToTeam(eq("Robert"), anyInt()); 
    // attempt 2: SUCCESS
  }

  @Test
  public void testCallVerificationForArgumentTypes() {
    // Here is a set which could contain items of any type
    @SuppressWarnings("unchecked")
    final Set<String> mockSet = mock(Set.class);

    // ... would normally stub here

    // Make a call to the mock set using some unknown value
    mockSet.contains(new Random().nextInt());

    /*
     * Problem: we want to verify we checked for an int, not a String.
     * 
     * In other words ensure we DIDN'T do this: mockSet.contains("Some String");
     * 
     * No problem, we can use argument matchers as above, right?
     */
    verify(mockSet).contains(anyInt()); // SUCCESS??? It seems to work

    // But what about below:
    verify(mockSet).contains(anyString()); // SUCCESS!?!?! It shouldn't succeed?

    /*
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * From the anyX() family of methods javadoc:
     * 
     * This method doesn't do any type checks, it is only there to avoid casting
     * in your code. This might however change (type checks could be added) in a
     * future major release.
     * 
     * Have to use isA to do type checking! anyX() methods are all equivalent to
     * any() and to anyObject(), just to avoid compile time type errors.
     */
    verify(mockSet).contains(isA(Integer.class));
    // to correctly ensure the method was called, AND WAS CALLED WITH AN INT

    // Conversely, if we checked if it was called with a String, 
    // this FAILS (which is what we want to happen!)
    // verify(mockSet).contains(isA(String.class));
  }
}
