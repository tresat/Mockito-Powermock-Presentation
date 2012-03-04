package com.tomtresansky.mockitopresentation.example08.mockinjection;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/*
 * Our System Unit Test - a car class.
 */
class Car {
  private Engine engine;
  private Transmission transmission;

  public Engine getEngine() {
    return engine;
  }

  public Transmission getTransmission() {
    return transmission;
  }
}

/*
 * Another sample collaborator used by the S.U.T.; in addition to our already
 * defined Engine interface.
 */
interface Transmission {
  void shiftUp();
  void shiftDown();
  int getCurrentGear();
}

/*
 * Here is an example test class for the car class.
 * 
 * Could have also included a call to MockitoAnnotations.initMocks(this); in a
 * 
 * @Before annotated method.
 */
@RunWith(MockitoJUnitRunner.class)
public final class InjectMocksAnnotationExample {
  @Mock
  private Engine mockStartedEngine;

  @Mock
  private Transmission mockFirstGearTransmission;

  @InjectMocks
  private Car carToTest;

  @Test
  public void testCarHasEngine() {
    /*
     * We NEVER created mocks for the car's engine or transmission, and we NEVER
     * set them on our carToTest instance, yet here they are, available to us
     */
    assertNotNull("Car should have an engine!", carToTest.getEngine());
    assertNotNull("Car should have a transmission!", carToTest.getTransmission());

    // We can now use the mock engine and mock transmission as needed...
  }
}
