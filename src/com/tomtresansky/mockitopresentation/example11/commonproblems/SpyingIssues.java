package com.tomtresansky.mockitopresentation.example11.commonproblems;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SpyingIssues {
  @Test
  public void testSpyStubbing() {
    // Spy on an ArrayList
    final List<String> spyList = spy(new ArrayList<String>());

    // Because mockito uses method calls as interactions to specify stubbing, 
    // if we try to mock a get call on a SPY
    // We're actually calling the method...
    when(spyList.get(0)).thenReturn("monkey");

    // There might be problems...depending on the internals of the class 
    // we're mocking
  }

  @Test
  public void testSpyStubbingCorrected() {
    // Spy on an ArrayList
    final List<String> spyList = spy(new ArrayList<String>());

    // More convoluted syntax can accomplish our goals, see javadoc for 
    // doReturn method...
    doReturn("monkey").when(spyList).get(0);

    // Proof:
    System.out.println("spyList[0] = " + spyList.get(0));

    // And yet...we've now made our spy inconsistent
    System.out.println("spyList.size() = " + spyList.size());

    // The moral of the story: spying on a real object (partial mocking) 
    // should be used sparingly
  }
}
