package com.hubspot.jinjava.lib.filter;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.hubspot.jinjava.Jinjava;

public class SymmetricDifferenceFilterTest {

  Jinjava jinjava;

  @Before
  public void setup() {
    jinjava = new Jinjava();
    jinjava.getGlobalContext().registerClasses(EscapeJsFilter.class);
  }

  @Test
  public void itComputesSetDifferences() {
    assertThat(jinjava.render("{{ [1, 2, 3, 3, 4]|symmetric_difference([1, 2, 5, 6]) }}", new HashMap<>())).isEqualTo("[3, 4, 5, 6]");
    assertThat(jinjava.render("{{ ['do', 'ray']|symmetric_difference(['ray', 'me']) }}", new HashMap<>())).isEqualTo("[do, me]");
  }

  @Test
  public void itReturnsEmptyOnNullParameters() {
    assertThat(jinjava.render("{{ [1, 2, 3]|symmetric_difference(null) }}", new HashMap<>())).isEqualTo("[1, 2, 3]");
  }

}
