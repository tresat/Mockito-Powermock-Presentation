package com.tomtresansky.mockitopresentation.example01.stubsmocksspiesanddoubles;

import junit.framework.Assert;

import org.junit.Test;

public class DoctorUnitTestsWithStubs {
  @Test
  public void testCanDiagnoseFever__AttemptUsingActualClass() {
    // Create a doctor to test
    final Doctor doctor = new Doctor();

    // Create a person with a fever to diagnose
    final Person feverishPerson = new Person(Person.NORMAL_TEMP + 10);

    /*
     * To test that the call to Person#hasFever behaves correctly, we need to
     * pass it a Thermometer...
     * 
     * But the actual thermometer might break!
     */
    Assert.assertTrue("Doctor should detect a fever!", doctor.checkForFever(feverishPerson, new ActualThermometer()));

    // NOTHING is worse than a test which passes MOST of the time!
  }

  @Test
  public void testCanDiagnoseFever__AttemptUsingStubs() {
    // Create a doctor to test
    final Doctor doctor = new Doctor();

    // Create a person with a fever to diagnose
    final Person feverishPerson = new Person(Person.NORMAL_TEMP + 10);

    /*
     * Create a STUB implementation of a thermometer using an anonymous class.
     * 
     * The stub will ALWAYS return 100 for the temperature. This is okay, we're
     * not testing our Thermometer implementation here, we're testing the
     * ability of the Doctor class to read the thermometer and diagnose the
     * patient properly. So it's fine to use a "rigged" thermometer.
     */
    final Thermometer stubThermometer = new Thermometer() {
      @Override
      public double measureTemperature(final Person p) {
        return Person.NORMAL_TEMP + 7;
      }
    };

    Assert.assertTrue("Doctor should detect a fever!", doctor.checkForFever(feverishPerson, stubThermometer));

    // But now we want to test that doctor doesn't mis-diagnose a healthy patient as well
    final Person healthyPerson = new Person(Person.NORMAL_TEMP);

    // WON'T WORK, STUB IS INAPPROPRIATE
    //Assert.assertFalse("Patient should be fine!", doctor.checkForFever(healthyPerson, stubThermometer));

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
     * 
     * 
     * 
     * 
     * 
     * 
     * Need to make a 2nd stub...
     */

    // THIS STUB WILL ALWAYS REPORT THE NORMAL TEMP, AND SHOULD ALLOW US TO RUN A NEGATIVE TEST
    //    final Thermometer stubThermometer2 = new Thermometer() {
    //      @Override
    //      public double measureTemperature(final Person p) {
    //        return Person.NORMAL_TEMP;
    //      }
    //    };
    //
    //    Assert.assertFalse("Patient should be fine!", doctor.checkForFever(healthyPerson, stubThermometer2));
  }
}
