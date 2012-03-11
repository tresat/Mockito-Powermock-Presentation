package com.tomtresansky.mockitopresentation.example08.mockinjection;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/*
 * A sample interface to mock.
 */
interface Engine {
  void start();
  void stop();
  boolean isRunning();
  int getRPMs();
}

/*
 * Here is an example test class for the engine interface.
 */
public final class MockAnnotationExample {
  /*
   * The @Mock annotation means that JUnit will inject a mock Engine here.
   */
  @Mock
  private Engine mockStartedEngine;

  @Before
  public void setup() {
    /*
     * Either need this call in a @Before method, or need to annotate class
     * 
     * @RunWith(MockitoJUnitRunner.class).
     */
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testGetRPMs() {
    // We never initialized mockStartedEngine = mock(Engine.class); 
    // but it IS setup for us.
    when(mockStartedEngine.isRunning()).thenReturn(true);
    when(mockStartedEngine.getRPMs()).thenReturn(2000);

    assertTrue("Engine should be running!",
        mockStartedEngine.isRunning());
    assertTrue("A running engine should be revolving!",
        mockStartedEngine.getRPMs() > 0);
  }
}
