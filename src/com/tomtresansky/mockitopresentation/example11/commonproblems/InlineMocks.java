package com.tomtresansky.mockitopresentation.example11.commonproblems;

import static org.mockito.Mockito.*;

import org.junit.Test;

public class InlineMocks {
  /*
   * Some interfaces to mock.
   */
  interface Cow {
    public Milk getMilk();
  }

  interface Milk {
    public void drink();
  }

  @Test
  public void testInlineMockCreation() {
    final Cow mockCow = mock(Cow.class); // Here's a mock cow

    /*
     * Now we'll want to return a mock Milk when milking the Cow.
     * 
     * We'll create the mock milk using mock(Milk.class) inside the call to
     * thenReturn() to save space. Java always evaluates arguments to functions
     * before the functions itself, so this:
     * 
     * when(mockCow.getMilk()).thenReturn(mock(Milk.class));
     * 
     * should be EXACTLY equivalent to doing this:
     * 
     * Milk mockMilk = mock(Milk.class);
     * when(mockCow.getMilk()).thenReturn(mock(Milk.class));
     */
    when(mockCow.getMilk()).thenReturn(mock(Milk.class)); // <----- BUT THIS LINE CAUSES UnfinishedStubbingException!

    /*
     * From the Mockito documentation:
     * 
     * The reason is that the part of the framework's validation logic which
     * detects unfinished stubbing doesn't work with the above construct.
     * 
     * Mockito uses ThreadLocal state in its implementation. Every time you
     * interact with Mockito framework it validates the ThreadLocal state in
     * case you misused the API.
     * 
     * This is usually GREAT - means really helpful error messages.
     * 
     * Unfortunately this means you have to initialize mocks in separate
     * statements, as below:
     */
    final Cow mockCow2 = mock(Cow.class);
    final Milk mockMilk2 = mock(Milk.class);

    when(mockCow2.getMilk()).thenReturn(mockMilk2);
  }
}
