package com.tomtresansky.mockitopresentation.example01.stubsmocksspiesanddoubles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.tomtresansky.mockitopresentation.example01.stubsmocksspiesanddoubles.DoctorUnitTestsWithMocks.Megameter;
import com.tomtresansky.mockitopresentation.example01.stubsmocksspiesanddoubles.DoctorUnitTestsWithSpies.SpyMegameter.Call;

public class DoctorUnitTestsWithSpies {
  /*
   * Kind of annoying to have to set up expectations ahead of time.
   * 
   * Also can lead to brittle tests---need to know what to enable ahead of time,
   * what if you only want to ensure a particular method WASN'T CALLED?
   * 
   * Enter SPIES - verify interactions AFTER use.
   */

  /*
   * Simple spy implementation records calls and inputs.
   */
  class SpyMegameter implements Megameter {
    /*
     * A call is a method and an arg, we'll store them as they are made.
     */
    class Call {
      String method;
      Person arg;

      public Call(final String method, final Person arg) {
        this.method = method;
        this.arg = arg;
      }
    }

    private final List<Call> calls = new ArrayList<Call>();

    /*
     * Retrieve a copy of the calls.
     */
    public List<Call> getCalls() {
      return Collections.unmodifiableList(calls);
    }

    @Override
    public double measureTemperature(final Person p) {
      calls.add(new Call("measureTemperature", p));

      return Person.NORMAL_TEMP + 7; // always a fever
    }

    @Override
    public boolean isInfected(final Person p) {
      calls.add(new Call("isInfected", p));

      return false; // never infected
    }

    @Override
    public boolean isBreathing(final Person p) {
      calls.add(new Call("isBreathing", p));

      return true; // always breathing
    }
  }

  /*
   * Bad Doctor RETURNS!
   */
  class BadDoctor extends Doctor {
    public boolean checkForFever(final Person p, final Megameter m) {
      return m.isBreathing(p);
    }
  }

  @Test
  public void testBadDoctorCanDiagnoseFever__AttemptUsingSpyMegameter() {
    // Create a bad doctor to test
    final BadDoctor badDoctor = new BadDoctor();

    // Create a person with a fever to diagnose
    final Person feverishPerson = new Person(Person.NORMAL_TEMP + 10);

    // We'll test with a SPY megameter
    final SpyMegameter spyMegameter = new SpyMegameter();

    // And repeat our test again
    Assert.assertTrue(
        "Doctor should detect a fever!",
        badDoctor.checkForFever(feverishPerson, spyMegameter));

    // ASSERTION PASSES!

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
     * But since we have a spy we can check the record of calls made...
     */
    for (final Call call : spyMegameter.getCalls()) {
      System.out.println("Called: " + call.method + " with param: " + call.arg);
    }

    /*
     * And even ensure that ONLY necessary methods were called.
     */
    for (final Call call : spyMegameter.getCalls()) {
      Assert.assertTrue(
          "Should only be measuring temperature with megameter!",
          "measureTemperature".equals(call.method));
    }
  }
}
