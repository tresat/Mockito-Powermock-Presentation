package com.tomtresansky.mockitopresentation.example06.stubbingwithcallbacks;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Set;

import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class StubbingWithCallbacks {
  /*
   * An interface to mock, any int provided to its triplePlus is tripled and has
   * 7 added to it.
   * 
   * Pretend like we have to go out to a database to lookup the 7...
   */
  interface Tripler {
    int triplePlus(int x);
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
        final Integer x = (Integer) args[0];

        return (x * 3) + 7;
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
  interface ElectronicDog {
    void printBark(); // a void method for which we want to stub side-effects
  }

  @Test
  public void testStubbingrSideEffectsOfVoidMethod() {
    final ElectronicDog mockDog = mock(ElectronicDog.class);

    /*
     * Syntax for stubbing void methods is a little different, since doing as
     * per normal:
     * 
     * when(mock.voidMethod()).thenAnswer(answer);
     * 
     * would cause the compiler to complain that we're passing the result of a
     * voidMethod() as an argument to another method.
     * 
     * Instead, have to use an alternate backwards syntax:
     * doAnswer(answer).when(mock).voidMethod();
     */
    doAnswer(new Answer<Void>() {
      @Override
      public Void answer(final InvocationOnMock invocation) throws Throwable {
        // Produce desired side-effect of printing
        System.out.println("Woof!");

        // We must return something compatible with "Void" 
        // to make the compiler happy
        return null;
      }
    }).when(mockDog).printBark();

    // Test that it works: should see side-effects printed to Console
    mockDog.printBark();
  }

  @Test
  public void exampleOfAnswerAbuse() {
    /*
     * Here we're mocking a set which should only "contain" words that are
     * synonyms for "big"
     */
    @SuppressWarnings("unchecked")
    final Set<String> mockSetOfWordsThatMeanBig = mock(Set.class);

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
    when(mockSetOfWordsThatMeanBig.contains(
        anyString())).thenAnswer(meansBigAnswer);

    System.out.println("Try some words:");
    System.out.println("big: " + mockSetOfWordsThatMeanBig.contains("big"));
    System.out.println("huge: " + mockSetOfWordsThatMeanBig.contains("huge"));
    System.out.println("tiny: " + mockSetOfWordsThatMeanBig.contains("tiny"));

    /*
     * But wait: the result does NOT need to *ACT* on the argument.
     * 
     * So we don't really NEED an Answer class...
     * 
     * Same result with ArgumentMatcher:
     */
    @SuppressWarnings("unchecked")
    final Set<String> mockSetOfWordsThatMeanBig2 = mock(Set.class);

    final ArgumentMatcher<String> meansBig = new ArgumentMatcher<String>() {
      @Override
      public boolean matches(final Object argument) {
        // Convert the argument to a String
        final String arg = (String) argument;

        // And if that argument is contained in the list of synonyms, 
        // contains() returns true
        return Arrays.asList("big", "huge", "massive").contains(arg);
      }
    };

    when(mockSetOfWordsThatMeanBig2.contains(
        argThat(meansBig))).thenReturn(true);

    System.out.println("\nSame results with Matcher:");
    System.out.println("big: " + mockSetOfWordsThatMeanBig2.contains("big"));
    System.out.println("huge: " + mockSetOfWordsThatMeanBig2.contains("huge"));
    System.out.println("tiny: " + mockSetOfWordsThatMeanBig2.contains("tiny"));

    /*
     * This is the *PREFERED* method. Closer to *SPIRIT* of stubbing: instead of
     * running logic to calculate a result, we specify the desired result ahead
     * of time for certain arguments, and use the logic to determine if we have
     * a matching argument.
     */
  }
}
