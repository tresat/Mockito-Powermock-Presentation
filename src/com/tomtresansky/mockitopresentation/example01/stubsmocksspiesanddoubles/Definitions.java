package com.tomtresansky.mockitopresentation.example01.stubsmocksspiesanddoubles;

import java.util.Date;
import java.util.Random;

/*
 * Thermometer interface measures temperature.
 */
interface Thermometer {
  double measureTemperature(Person p);
}

/*
 * Person class has a single field: it's temperature, and a constant: normal
 * human body temperature.
 */
class Person {
  protected static final double NORMAL_TEMP = 98.6;

  protected double temp;

  public Person(final double temp) {
    this.temp = temp;
  }
}

/*
 * The Doctor can use a thermometer to check patient for a fever.
 */
class Doctor {
  public boolean checkForFever(final Person p, final Thermometer t) {
    return t.measureTemperature(p) > Person.NORMAL_TEMP;
  }
}

/*
 * "Actual" thermometer uses complex routines to determine temperature.
 */
class ActualThermometer implements Thermometer {
  @Override
  public double measureTemperature(final Person p) {
    System.out.println("Calibrating...");
    //...

    System.out.println("Converting from metric...");
    //...

    System.out.println("Re-Calibrating...");
    //...

    /*
     * And as with any ACTUAL implementation, there's always the danger of
     * something going wrong...
     */
    if (new Random(new Date().getTime()).nextInt(3) == 0) {
      throw new RuntimeException("Insufficient mercury!");
    }

    System.out.println("Measuring...");
    //...

    return p.temp;
  }
}
