package com.tomtresansky.mockitopresentation.example10.mocknastyclass;

import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/*
 * Here is an example more akin to real-world or especially legacy code.
 * 
 * We want to test the doSomething() method of the SystemUnderTest class,
 * without causing any output written to the Console (this output represents
 * where external resources would be referenced, which is what we DON'T want to
 * happen in our unit test!
 * 
 * Also: if any of the methods we call aren't stubbed properly, they will return
 * the default value of 0. We want the result of doSomething to be 5, to
 * demonstrate all have been stubbed properly.
 */
@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("mockitopresentation.mocknastyclass.SystemUnderTest")
@PrepareForTest({ Collaborator1.class, Collaborator2.class,
    Collaborator3.class, BaseSystemUnderTest.class, SystemUnderTest.class })
public final class DemonstrateTestingWithPowerMock {
  private static SystemUnderTest getNewInstanceToTest() {
    // Mock the static method call on collaborator 2
    PowerMockito.mockStatic(Collaborator2.class);
    when(Collaborator2.getValueStatic()).thenReturn(1);

    // Suppress base class constructor
    PowerMockito.suppress(PowerMockito.constructor(BaseSystemUnderTest.class));

    // Suppress System Under Test constructor
    PowerMockito.suppress(PowerMockito.constructor(SystemUnderTest.class));

    /*
     * The use of the @SuppressStaticInitializationFor annotation prevents ALL
     * static initialization for SystemUnderTest from running.
     * 
     * Unfortunately, this will mean a NullPoinerException when
     * staticCollaborator is accessed, since its default value is set during
     * static initialization. So we need to set it here to a mock.
     * 
     * The Whitebox utilities allow us to do this.
     */
    Whitebox.setInternalStateFromContext(SystemUnderTest.class, MyContext.class);

    // We can now create the instance of our system to test
    final SystemUnderTest systemUnderTest = new SystemUnderTest();

    // Since initialization suppressed, need to manually set value 
    // of memberCollaborator field (to a mock)
    final Collaborator1 mockCollaborator1 = mock(Collaborator1.class);
    when(mockCollaborator1.getValue()).thenReturn(1);
    Whitebox.setInternalState(systemUnderTest, "memberCollaborator",
        mockCollaborator1);

    return systemUnderTest;
  }

  @Test
  public void testSystemUnderTestDoSomething() {
    // Get our instance to test
    final SystemUnderTest systemUnderTest = getNewInstanceToTest();

    // Mock an instance of collaborator3 and stub its final method to 
    // provide as an argument
    final Collaborator3 mockCollaborator3 = PowerMockito.mock(Collaborator3.class);
    when(mockCollaborator3.getValueFinal()).thenReturn(1);

    // And call the method on it with no console output
    System.out.println(
        "Resulting value: " + systemUnderTest.doSomething(mockCollaborator3));
  }
}

/*
 * Need to setup a class with identically named static fields (which are mocked)
 * to get values of those mocked static fields copied to our System Under Test
 * using the Whitebox utils.
 * 
 * Stub the static field in a static initializer.
 */
class MyContext {
  private static Collaborator1 staticCollaborator;
  static {
    staticCollaborator = mock(Collaborator1.class);
    when(staticCollaborator.getValue()).thenReturn(1);
  }
};