package com.tomtresansky.mockitopresentation.example11.commonproblems;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ImproperStubbing {
  @Test
  public void demoImproperStubbing() {
    // Create a list and a mock list
    final List<String> list = new ArrayList<String>();
    final List<String> mockList = mock(List.class);

    // ...do other stuff

    // Attempt to stub a method on the mock list, but WHOOPS!
    // pass it the real list
    when(list.add("Bob")).thenReturn(true);

    /*
     * Exception as follows:
     * 
     * org.mockito.exceptions.misusing.MissingMethodInvocationException: 
when() requires an argument which has to be 'a method call on a mock'.
For example:
    when(mock.getArticles()).thenReturn(articles);

Also, this error might show up because:
1. you stub either of: final/private/equals()/hashCode() methods.
   Those methods *cannot* be stubbed/verified.
2. inside when() you don't call method on mock but on some other object.

  at com.tomtresansky.mockitopresentation.example11.commonproblems.ImproperStubbing.demoImproperStubbing(ImproperStubbing.java:21)
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
