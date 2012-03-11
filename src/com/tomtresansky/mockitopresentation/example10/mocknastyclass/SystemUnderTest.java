package com.tomtresansky.mockitopresentation.example10.mocknastyclass;

/*
 * Here is the class we want to test. It has many properties which many it
 * difficult to test: is final, uses initializers and static initializers,
 * sub-class constructor accesses externals, no way to injected dependencies,
 * static references, etc.
 */
final class SystemUnderTest extends BaseSystemUnderTest {
  private static Collaborator1 staticCollaborator = new Collaborator1();

  static {
    System.out.println(
        "SystemUnderTest:static initializer accessing extenal resources!");
  }

  private final Collaborator1 memberCollaborator = new Collaborator1();

  {
    System.out.println(
        "SystemUnderTest:instance initializer() accessing extenal resources!");
  }

  public SystemUnderTest() {
    System.out.println(
        "SystemUnderTest:SystemUnderTest() accessing extenal resources!");
  }

  public int doSomething(final Collaborator3 collaborator3) {
    return 1
        + collaborator3.getValueFinal()
        + staticCollaborator.getValue()
        + memberCollaborator.getValue()
        + Collaborator2.getValueStatic();
  }
}

/*
 * Base class's constructor accesses external resources.
 */
class BaseSystemUnderTest {
  public BaseSystemUnderTest() {
    System.out.println(
        "BaseSystemUnderTest:BaseSystemUnderTest() accessing extenal resources!");
  }
}

/*
 * This collaborator's constructor accesses external resources.
 */
class Collaborator1 {
  public Collaborator1() {
    System.out.println(
        "Collaborator1:Collaborator1() accessing extenal resources!");
  }

  public int getValue() {
    System.out.println(
        "Collaborator1:getValue() accessing extenal resources!");
    return 1;
  }
}

/*
 * This collaborator represents a static utility class.
 */
class Collaborator2 {
  public static int getValueStatic() {
    System.out.println(
        "Collaborator2:getValueStatic() accessing extenal resources!");
    return 1;
  }
}

/*
 * This is a final collaborator with a final method.
 */
final class Collaborator3 {
  public final int getValueFinal() {
    System.out.println(
        "Collaborator3:getValueFinal() accessing extenal resources!");
    return 1;
  }
}