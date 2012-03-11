package com.tomtresansky.mockitopresentation.example09.mockfinals;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/*
 * A non-final class with a final method, for testing.
 */
class Calculator2 {
  public final double computeE() {
    // Simulate a lengthly compuation
    System.out.println("Computing value...");
    try {
      Thread.sleep(15000);
    } catch (final InterruptedException e) {
      // won't be interrupted
    }
    return 2; // close enough
  }
}

/*
 * Notice the annotations added here:
 * 
 * @RunWith tells JUnit to use the PowerMock runner, which uses the PowerMock
 * classloader, enabling bytecode modification.
 * 
 * @PrepareForTest allows mocking final methods on a class, without it, test
 * would fail with exception: MissingMethodInvocationException, because of the
 * attempted call to a final method during stubbing. (After a 15 second delay,
 * in which it runs the actual, unchanged implementation final method).
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(Calculator2.class)
public class MockFinalMethodsWithPowerMock {
  @Test
  public void testMockAFinalMethod() {
    /*
     * In addition to the annotations, we create the mock using the
     * PowerMockito.mock static method instead of the Mockito.mock method.
     */
    final Calculator2 mockCalculator = PowerMockito.mock(Calculator2.class);

    // After creating the mock, its business as usual.
    when(mockCalculator.computeE()).thenReturn(Math.E);

    // And we get a quick and accurate stubbed calculation from our mock...
    System.out.println("Pi: " + mockCalculator.computeE());
  }
}
