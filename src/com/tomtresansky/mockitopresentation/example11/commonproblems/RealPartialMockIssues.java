package com.tomtresansky.mockitopresentation.example11.commonproblems;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RealPartialMockIssues {
  @Test
  public void testRealPartialMockWithoutImpl() {
    // NOTE: we're mocking the List interface here
    @SuppressWarnings("unchecked")
    final List<String> partialMockList = mock(List.class);

    // Try to use partial mocking to call the real size() implementation
    when(partialMockList.size()).thenCallRealMethod(); // ERROR

    /*
     * Fails, as it should, since there is no implementation of .size() in the
     * INTERFACE to call!
     * 
     * Helpful exception:
     * org.mockito.exceptions.base.MockitoException: Cannot call real method on java interface. 
     * Interface does not have any implementation! 
     * Calling real methods is only possible when mocking concrete classes.
     * //correct example:
     * when(mockOfConcreteClass.doStuff()).thenCallRealMethod();
     */
  }

  @Test
  public void testRealPartialMock() {
    /*
     * Here we set up a parial mock using the the call read method syntax.
     * 
     * All method calls DEFAULT to using stubs (or returning default stubbing
     * values if unstubbed).
     */
    @SuppressWarnings("unchecked")
    final List<String> partialMockList = mock(ArrayList.class); // NOTE: we must mock the IMPLEMENTATION here to call real methods

    /*
     * We'll use the REAL add() and contains() methods, so we should be able to
     * add to the list and check if we've added to it as if it were a plain old
     * ArrayList.
     */
    when(partialMockList.add(anyString())).thenCallRealMethod();
    when(partialMockList.contains(anyString())).thenCallRealMethod();

    // ERROR: This causes a NPE
    partialMockList.add("red");

    /*
     * What's going on?
     * 
     * add() internally calls ensureCapacityInternal(), a PRIVATE method we
     * can't affect, in which the elementData[] array is null.
     * 
     * Moral of the story: real partial mocks are tricky, can behave in
     * unexpected and unpredictable ways, safer to use spy to create partial
     * mock which DEFAULTS to using real methods, then stub out methods you want
     * to behave differently.
     */
  }
}