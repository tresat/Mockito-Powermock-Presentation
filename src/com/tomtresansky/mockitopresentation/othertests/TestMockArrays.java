package com.tomtresansky.mockitopresentation.othertests;

import static org.mockito.Mockito.*;

import org.junit.Test;

public class TestMockArrays {
  interface Animal {
    String getName();
  }

  @Test
  public void testMockArray() {
    final Animal[] mockArray = mock(Animal[].class);

    when(mockArray[0].getName()).thenReturn("cat");
    when(mockArray[1].getName()).thenReturn("dog");
    when(mockArray[2].getName()).thenReturn("fish");

    print1st3(mockArray);
  }

  public static void print1st3(final Animal[] animals) {
    System.out.println(animals[0].getName() + " " + animals[1].getName() + " " + animals[2].getName());
  }
}
