package com.tomtresansky.mockitopresentation.example07.partialmocks;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class Spies {
  @Test
  public void testSpying() {
    final List<Integer> mockList = spy(new ArrayList<Integer>());

  }
}
