package com.tomtresansky.mockitopresentation.example11.commonproblems;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class MixedArgumentMatchers {
  /*
   * Some things to test:
   * 
   * Dogs have a name and a breed, and are considered equal if they have the
   * same name.
   * 
   * Breeder interface takes 2 dogs and a requested number of puppies to
   * produce.
   */
  class Dog {
    String name;
    String breed;

    Dog() {
    }

    Dog(final String n, final String b) {
      name = n;
      breed = b;
    };

    @Override
    public boolean equals(final Object obj) {
      return obj instanceof Dog && name.equals(((Dog) obj).name);
    }
  }

  interface Breeder {
    List<Dog> breed(Dog d1, Dog d2, int n);
  }

  @Test
  public void testMixedArgumentMatchers() {
    // Make a mock breeder and some dogs to test it with
    final Breeder mockBreeder = mock(Breeder.class);

    final Dog fido = new Dog("Fido", "Labrador");
    final Dog fluffy = new Dog("Fluffy", "Chiguagua");
    final Dog rex = new Dog("Rex", "Golden Retreiver");
    final Dog spot1 = new Dog("Spot", "Great Dane");
    final Dog spot2 = new Dog("Spot", "Doberman");

    final List<Dog> puppies = Arrays.asList(new Dog(), new Dog(), new Dog());

    // Set up a mock using actual values for all arguments - works fine
    when(mockBreeder.breed(fido, rex, 3)).thenReturn(puppies);
    Assert.assertEquals(puppies, mockBreeder.breed(fido, rex, 3));

    // Set up a mock using argument matchers: ANY DOG NAMED SPOT + ANY 
    // OTHER DOG and ANY NUMBER OF PUPPIES - works fine
    when(mockBreeder.breed(
        eq(new Dog("Spot", null)), any(Dog.class), anyInt())).thenReturn(puppies);
    Assert.assertEquals(puppies, mockBreeder.breed(spot1, fluffy, 2));
    Assert.assertEquals(puppies, mockBreeder.breed(spot2, fluffy, 7));

    // Now set up a mock using a mix of values and argument matchers: ANY 
    // DOG NAMED SPOT + ANY OTHER DOG and 2 PUPPIES
    when(mockBreeder.breed(
        eq(new Dog("Spot", null)), any(Dog.class), 2)).thenReturn(puppies); // <----- FAILS with InvalidUseOfMatchersException

    /*
     * You can't combine eq() and any(), which are Argument Matchers, with
     * actual values like 2. Easy to forget, fortunately Mockito provides a
     * FANTASTIC exception message.
     * 
     * org.mockito.exceptions.misusing.InvalidUseOfMatchersException:
     * 
     * Invalid use of argument matchers! 3 matchers expected, 2 recorded.
     * 
     * This exception may occur if matchers are combined with raw values:
     * incorrect: someMethod(anyObject(), "raw String");
     * 
     * When using matchers, all arguments have to be provided by matchers. For
     * example: correct: someMethod(anyObject(), eq("String by matcher"));
     * 
     * This applies to calls to verify() as well.
     */
  }
}
