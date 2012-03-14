package com.tomtresansky.mockitopresentation.example09.mockfinals;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/*
 * Here is a final class with a method we want to stub.
 */
final class Calculator {
  public double computePi() {
    // Simulate a lengthly compuation
    System.out.println("Computing value...");
    try {
      Thread.sleep(15000);
    } catch (final InterruptedException e) {
      // won't be interrupted
    }

    return 3; // close enough, give up
  }
}

/*
 * Notice the annotations added here:
 * 
 * @RunWith tells JUnit to use the PowerMock runner, which uses the PowerMock
 * classloader, enabling bytecode modification.
 * 
 * @PrepareForTest allows mocking a final class, without it, test would fail
 * with exception: you can't mock finals.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Calculator.class)
public class MockFinalClassesWithPowerMock {
  @Test
  public void testMockFinalClassesWithPowerMock() {
    /*
     * All the extra code was in the annotations, now we can mock and stub as
     * normal.
     */
    final Calculator mockCalculator = mock(Calculator.class);
    when(mockCalculator.computePi()).thenReturn(Math.PI);

    // And we get a quick and accurate stubbed calculation from our mock...
    System.out.println("Pi: " + mockCalculator.computePi());
  }
}