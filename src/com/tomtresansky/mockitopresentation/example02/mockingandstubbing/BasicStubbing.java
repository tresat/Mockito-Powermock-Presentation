package com.tomtresansky.mockitopresentation.example02.mockingandstubbing;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Stack;

import org.junit.Test;

public class BasicStubbing {
  @Test
  public void testBasicStubbing() {
    // Create the mock
    final List mockList = mock(List.class);

    // Call a method to demonstrate methods are available
    mockList.add("turkey");

    // And yet, results are still null
    System.out.println("Value at position 0 in mocked list: " + mockList.get(0));

    // But if we STUB the method
    when(mockList.get(0)).thenReturn("chicken");

    // Now there is a chicken in the list
    System.out.println("Value after stubbing get(0) call: " + mockList.get(0));

    // Note that none of the semantics of list have been preserved: 
    // for example, we can "put" something in position 4
    when(mockList.get(4)).thenReturn("ostrich");

    // This is an odd list...BECAUSE list semantics are NOT preserved, 
    // so we can have 0th and 4th items without
    // 1st, 2nd and 3rd values, which will still return default value of null
    // Note that list[0] is still chicken
    for (int i = 0; i <= 4; i++) {
      System.out.println("List[" + i + "] = " + mockList.get(i));
    }

    // Once again: you only get results you've specified...the MOCK list 
    // doesn't know anything you haven't told it
    // we haven't stubbed size() calls, so it returns the DEFAULT 
    // numeric value of 0
    System.out.println("List has " + mockList.size() + " items.");
  }

  @Test
  public void testBasicStubbingWithGenerics() {
    // Mocking generic-ized list also possible, with unchecked conversion
    @SuppressWarnings("unchecked")
    final List<Integer> mockList = mock(List.class);

    mockList.add(5); // okay
    //mockList.add("turtle"); // ERRORS - generics are preserved in mock

    when(mockList.get(0)).thenReturn(2); // okay

    // generics say no when stubbing, too, this line will ERROR
    //when(mockList.get(1)).thenReturn("frog"); 
  }

  @Test
  public void testStubbingMultipleInvocations() {
    // Say we want to stub repeated calls to the same method: 
    // each returning different results
    @SuppressWarnings("unchecked")
    final Stack<String> mockStack = mock(Stack.class, "Tom's Mock Stack");
    // 2 argument override to mock() method allows us to name each mock, 
    // name displayed in errors, helpful for debugging tests

    // Naive approach:
    when(mockStack.pop()).thenReturn("head");
    when(mockStack.pop()).thenReturn("neck");
    when(mockStack.pop()).thenReturn("shoulders");
    when(mockStack.pop()).thenReturn("chest");

    // Doesn't seem to work
    System.out.println("Stack 1:");
    for (int i = 0; i < 4; i++) {
      System.out.println(mockStack.pop());
    }

    // Mockito only remembers LAST stubbed result...so how to 
    // mock a Stack keeping to AAA?

    // Let's try again...
    @SuppressWarnings("unchecked")
    final Stack<String> mockStack2 = mock(Stack.class);

    // Var-args call to then return tells mockito to keep track of 
    // various results
    when(mockStack2.pop()).thenReturn("head", "neck", "shoulders", "chest");

    // Proof:
    System.out.println("\nStack 2:");
    for (int i = 0; i < 4; i++) {
      System.out.println(mockStack2.pop());
    }

    // After exhausting the supplied var-args, stuck on last result
    System.out.println("\nOn the 5th plus pop: " +
        mockStack2.pop() + " " + mockStack2.pop() + " " + mockStack2.pop());
  }
}
