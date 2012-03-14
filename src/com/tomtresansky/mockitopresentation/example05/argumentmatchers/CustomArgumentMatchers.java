package com.tomtresansky.mockitopresentation.example05.argumentmatchers;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Set;

import org.junit.Test;
import org.mockito.ArgumentMatcher;

public class CustomArgumentMatchers {
  @Test
  public void testCustomMatcher() {
    @SuppressWarnings("unchecked")
    final Set<Integer> mockNumsDivisibleBy5 = mock(Set.class);

    /*
     * We want to be able to call .contains() method on this set with ANY
     * number, and have it only return true for those numbers which are evenly
     * divisible by 5.
     * 
     * We'll build a custom matcher.
     */
    final ArgumentMatcher<Integer> divisibleBy5 = new ArgumentMatcher<Integer>() {
      /*
       * Override the matches method
       */
      @Override
      public boolean matches(final Object argument) {
        // Convert the argument to an int (assume this will 
        // only be called with non-null ints, for convenience)
        final int num = (Integer) argument;

        // Perform the divisibility test
        return (num % 5 == 0);
      }
    };

    /*
     * Now stub the contains method on our mock using the custom matcher.
     * 
     * Have to wrap custom matchers in XThat() family of methods in
     * org.mockito.Matchers package:
     * 
     * intThat(matcher), booleanThat(matcher), argThat(matcher) <-- use argThat
     * for objects.
     */
    when(mockNumsDivisibleBy5.contains(intThat(divisibleBy5))).thenReturn(true);

    System.out.println("Mock set contains: ");
    System.out.println("3: " + mockNumsDivisibleBy5.contains(3));
    System.out.println("5: " + mockNumsDivisibleBy5.contains(5));
    System.out.println("12: " + mockNumsDivisibleBy5.contains(12));
    System.out.println("15: " + mockNumsDivisibleBy5.contains(15));
    System.out.println("25: " + mockNumsDivisibleBy5.contains(25));
  }
}
