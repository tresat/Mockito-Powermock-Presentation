package com.tomtresansky.mockitopresentation.example10.mocknastyclass;

import org.junit.Test;

/*
 * Here is an example more akin to real-world or especially legacy code.
 * 
 * We want to test the doSomething() method of the SystemUnderTest class,
 * without causing any output written to the Console (this output represents
 * where external resources would be referenced, which is what we DON'T want to
 * happen in our unit test!
 * 
 * This demonstrates the default behaviour without using PowerMock.
 */
public final class DemonstrateDefaultBehaviour {
  @Test
  public void testDoSomething() {
    // Create the instance of our system to test
    final SystemUnderTest systemUnderTest = new SystemUnderTest();

    // Create a collaborator object needed as an argument
    final Collaborator3 collaborator3 = new Collaborator3();

    // And call the method on it - at this point there will 
    // be LOTS of console output, signifying unwanted access to external resources
    System.out.println(
        "Resulting value: " + systemUnderTest.doSomething(collaborator3));
  }
}
