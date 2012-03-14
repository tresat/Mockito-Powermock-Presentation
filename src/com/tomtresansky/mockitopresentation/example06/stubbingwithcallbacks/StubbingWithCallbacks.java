package com.tomtresansky.mockitopresentation.example06.stubbingwithcallbacks;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class StubbingWithCallbacks {
  /*
   * An interface to with a method to mock, any int provided to its triplePlus
   * is tripled and has a value added to it.
   * 
   * Pretend like we have to go out to a database to lookup the value...
   */
  interface Tripler {
    int triplePlus(int x);
    List<Integer> calculateAllKnownPrimes(); // DON'T want to have to implement this!
  }

  @Test
  public void testStubbingWithCallback() {
    final Tripler mockTripler = mock(Tripler.class);

    /*
     * Create new callback as anonymous implementation of Answer class - the
     * triplePlus() method returns an int, so generisized to Integer
     */
    final Answer<Integer> triplerAnswer = new Answer<Integer>() {
      // Override the answer callback - will be invokes each time 
      // method is called on mock to calculate result
      @Override
      public Integer answer(final InvocationOnMock invocation) throws Throwable {
        // Need to extract the int triplePlus() was 
        // provided as argument (assuming non-nulls)
        final Object[] args = invocation.getArguments();
        final Integer arg = (Integer) args[0];

        final int extra = 7; // in an actual implementation, getting this might be a db lookup

        return (arg * 3) + extra;
      }
    };

    // Instead of supplying an int result as argument to 
    // then() call, we provide our Custom Answer implementation
    when(mockTripler.triplePlus(anyInt())).thenAnswer(triplerAnswer);

    System.out.println("Try it:");
    System.out.println("triplePlus(2): " + mockTripler.triplePlus(2));
    System.out.println("triplePlus(5): " + mockTripler.triplePlus(5));

    // Proviso: remember, use Answers SPARINGLY, might be 
    // a code smell that you're trying to test too much at once
  }

  // The bark method should cause output to be printed
  interface Dog {
    void bark(); // a void method for which we want to stub side-effects
  }

  @Test
  public void testStubbingSideEffectsOfVoidMethod() {
    final Dog mockDog = mock(Dog.class);

    // Create the answer sub-class
    final Answer<Void> barkAnswer = new Answer<Void>() {
      @Override
      public Void answer(final InvocationOnMock invocation) throws Throwable {
        // Produce desired side-effect of printing
        System.out.println("Woof woof!");

        // We must return something compatible with "Void" 
        // to make the compiler happy, result won't affect anything
        return null;
      }
    };

    /*
     * Syntax for stubbing void methods is a little different, since doing as
     * per normal:
     * 
     * when(mock.voidMethod()).thenAnswer(answer);
     * 
     * would cause the compiler to complain that we're passing the result of a
     * voidMethod() as an argument to another method.
     */
    // when(mockDog.bark()).thenAnswer(barkAnswer); // COMPILER ERROR

    /*
     * Instead, have to use an alternate backwards syntax:
     * doAnswer(answer).when(mock).voidMethod();
     */
    doAnswer(barkAnswer).when(mockDog).bark();

    // Test that it works: should see side-effects printed to Console
    mockDog.bark();
  }

  @Test
  public void exampleOfAnswerAbuse() {
    /*
     * Here we're mocking a set which should only "contain" words that are
     * synonyms for "big"
     */
    @SuppressWarnings("unchecked")
    final Set<String> mockBigWordsSet = mock(Set.class);

    // Create our custom callback
    final Answer<Boolean> meansBigAnswer = new Answer<Boolean>() {
      @Override
      public Boolean answer(final InvocationOnMock invocation) throws Throwable {
        // Need to extract the String contains() was provided as argument
        final Object[] args = invocation.getArguments();
        final String argument = (String) args[0];

        // And if that argument is contained in the list of synonyms, 
        // contains() returns true
        return Arrays.asList("big", "huge", "massive").contains(argument);
      }
    };

    // Instead of supplying a boolean true or false as argument 
    // to then() call, we provide our Custom Answer implementation
    when(mockBigWordsSet.contains(anyString())).thenAnswer(meansBigAnswer);

    System.out.println("Try some words:");
    System.out.println("big: " + mockBigWordsSet.contains("big"));
    System.out.println("huge: " + mockBigWordsSet.contains("huge"));
    System.out.println("tiny: " + mockBigWordsSet.contains("tiny"));

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
     * But wait: the result does NOT need to *ACT* on the argument and we
     * weren't interested in side effects!
     * 
     * So we don't really NEED an Answer callback...
     * 
     * Same result with ArgumentMatcher:
     */
    //    @SuppressWarnings("unchecked")
    //    final Set<String> mockBigWordsSet2 = mock(Set.class);
    //
    //    final ArgumentMatcher<String> meansBig = new ArgumentMatcher<String>() {
    //      @Override
    //      public boolean matches(final Object argument) {
    //        // Convert the argument to a String
    //        final String arg = (String) argument;
    //
    //        // And if that argument is contained in the list of synonyms, 
    //        // contains() returns true
    //        return Arrays.asList("big", "huge", "massive").contains(arg);
    //      }
    //    };
    //
    //    // Stub using matcher, always return true on a match
    //    when(mockBigWordsSet2.contains(argThat(meansBig))).thenReturn(true);
    //
    //    System.out.println("\nSame results with Matcher:");
    //    System.out.println("big: " + mockBigWordsSet2.contains("big"));
    //    System.out.println("huge: " + mockBigWordsSet2.contains("huge"));
    //    System.out.println("tiny: " + mockBigWordsSet2.contains("tiny"));

    /*
     * This is the *PREFERED* method. Closer to *SPIRIT* of stubbing: instead of
     * running logic to calculate a result, we specify the desired result ahead
     * of time for certain arguments, and use the logic to determine if we have
     * a matching argument.
     */
  }
}
