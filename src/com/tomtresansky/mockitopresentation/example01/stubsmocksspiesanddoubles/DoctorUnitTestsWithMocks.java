package com.tomtresansky.mockitopresentation.example01.stubsmocksspiesanddoubles;

import junit.framework.Assert;

import org.junit.Test;

public class DoctorUnitTestsWithMocks {
  /*
   * Megameter extends thermometer, adding additional capabilities.
   */
  interface Megameter extends Thermometer {
    boolean isInfected(Person p);

    boolean isBreathing(Person p);
  }

  /*
   * Lets set up a stub for testing
   */
  class StubMegameter implements Megameter {
    @Override
    public double measureTemperature(final Person p) {
      return Person.NORMAL_TEMP + 7; // always a fever
    }

    @Override
    public boolean isInfected(final Person p) {
      return false; // stub implementation always returns false
    }

    @Override
    public boolean isBreathing(final Person p) {
      return true; // stub implementation always returns true
    }
  };

  @Test
  public void testCanDiagnoseFever__AttemptUsingStubMegameter() {
    // Create a doctor to test
    final Doctor doctor = new Doctor();

    // Create a person with a fever to diagnose
    final Person feverishPerson = new Person(Person.NORMAL_TEMP + 10);

    // And repeat our original test
    Assert.assertTrue("Doctor should detect a fever!", doctor.checkForFever(feverishPerson, new StubMegameter()));

    /*
     * Problems?
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * How do we really know the doctor is functioning correctly?
     */
  }

  /*
   * Bad Doctor isn't measuring fever, it's checking if the patient is breathing
   * and leaving it at that!
   */
  class BadDoctor extends Doctor {
    public boolean checkForFever(final Person p, final Megameter m) {
      return m.isBreathing(p);
    }
  }

  @Test
  public void testBadDoctorCanDiagnoseFever__AttemptUsingStubMegameter() {
    // Create a BAD doctor to test
    final BadDoctor doctor = new BadDoctor();

    // Create a person with a fever to diagnose
    final Person feverishPerson = new Person(Person.NORMAL_TEMP + 10);

    // And repeat our test again re-using the Stub Megameter
    Assert.assertTrue("Doctor should detect a fever!", doctor.checkForFever(feverishPerson, new StubMegameter()));

    /*
     * IT PASSES!
     * 
     * But...should it? We stubbed correctly! The previous test proves that!
     * 
     * How can we ensure the doctor uses the megameter correctly?
     * 
     * Mocks!
     */
  }

  /*
   * A mock megameter only allows expected use.
   */
  final class MockMegameter implements Megameter {
    /*
     * We use flags to mark when a function is allowed to fire.
     */
    private boolean canMeasureTemperature = false;
    private boolean canCheckInfection = false;
    private boolean canCheckBreathing = false;

    /*
     * Have to EXPLICITLY allow methods to be called by setting the flags.
     */
    public void setCanMeasureTemparature(final boolean value) {
      canMeasureTemperature = value;
    }

    public void setCanCheckInfection(final boolean value) {
      canCheckInfection = value;
    }

    public void setCanCheckBreathing(final boolean value) {
      canCheckBreathing = value;
    }

    /*
     * And our stub methods will first check if they are expected before firing.
     */
    @Override
    public double measureTemperature(final Person p) {
      if (!canMeasureTemperature) {
        throw new RuntimeException("Shouldn't be measuring!");
      } else {
        return Person.NORMAL_TEMP + 7; // always a fever
      }
    }

    @Override
    public boolean isInfected(final Person p) {
      if (!canCheckInfection) {
        throw new RuntimeException("Shouldn't be checking infection!");
      } else {
        return false; // never infected
      }
    }

    @Override
    public boolean isBreathing(final Person p) {
      if (!canCheckBreathing) {
        throw new RuntimeException("Shouldn't be checking breathing!");
      } else {
        return false; // never breathing
      }
    }
  }

  @Test
  public void testDoctorCanDiagnoseFever__AttemptUsingMockMegameter() {
    // Create a GOOD doctor to test
    final Doctor goodDoctor = new Doctor();

    // Create a person with a fever to diagnose
    final Person feverishPerson = new Person(Person.NORMAL_TEMP + 10);

    // Set up a Mock Megameter
    final MockMegameter mockMegameter = new MockMegameter();
    mockMegameter.setCanMeasureTemparature(true);

    // Test our good doctor using the mock megameter
    Assert.assertTrue("Doctor should detect a fever!", goodDoctor.checkForFever(feverishPerson, mockMegameter));

    // IT PASSES: we only called appropriate method on the megameter

    // Now try testing a BAD doctor
    final BadDoctor badDoctor = new BadDoctor();

    // Run the same test with our BAD doctor
    //Assert.assertTrue("Doctor should detect a fever!", badDoctor.checkForFever(feverishPerson, mockMegameter));

    /*
     * 
     * 
     * 
     * 
     * 
     * 
     * 
     * The MOCK ensures that only expected functionality of dependent objects is
     * used!
     * 
     * This adds another level of confidence to our tests.
     */
  }
}
