package com.tomtresansky.mockitopresentation.example11.commonproblems;

import static org.mockito.Mockito.*;

import org.junit.Test;

/*
 * Test what happens when we mock an array.
 * 
 * Remember all arrays are implicitly final.
 */
public class TestMockArrays {
  /*
   * Class to test.
   */
  interface Animal {
    String getName();
  }

  @Test
  public void testMockArray() {
    final Animal[] mockArray = mock(Animal[].class);
    
    when(mockArray[0].getName()).thenReturn("cat");
    when(mockArray[1].getName()).thenReturn("dog");
    when(mockArray[2].getName()).thenReturn("fish");

    /*
     * The call to mock(ArrayType[].class) produces a very helpful stack trace:
     * 
    org.mockito.exceptions.base.MockitoException: 
      Cannot mock/spy class [Lcom.tomtresansky.mockitopresentation.othertests.TestMockArrays$Animal;
      Mockito cannot mock/spy following:
        - final classes
        - anonymous classes
        - primitive types
        at com.tomtresansky.mockitopresentation.othertests.TestMockArrays.testMockArray(TestMockArrays.java:14)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:57)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:601)
        at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
        at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
        at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
        at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
        at org.junit.runners.BlockJUnit4ClassRunner.runNotIgnored(BlockJUnit4ClassRunner.java:79)
        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:71)
        at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:49)
        at org.junit.runners.ParentRunner$3.run(ParentRunner.java:193)
        at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:52)
        at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:191)
        at org.junit.runners.ParentRunner.access$000(ParentRunner.java:42)
        at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:184)
        at org.junit.runners.ParentRunner.run(ParentRunner.java:236)
        at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:49)
        at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
        at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:467)
        at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:683)
        at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:390)
        at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:197)
    */
  }
}
