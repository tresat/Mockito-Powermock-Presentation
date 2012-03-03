package com.tomtresansky.mockitopresentation.othertests;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Test;

public class TestStubsWithNull {
  @Test
  public void testStubWithNull() {
    final List<String> mockList = mock(List.class);

    when(mockList.contains(null)).thenReturn(true);

    System.out.println("null: " + mockList.contains(null));
    System.out.println("turtle: " + mockList.contains("turtle"));
  }
}
