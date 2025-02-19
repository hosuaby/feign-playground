package io.hosuaby.feign.playground.domain;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Ice cream flavors.
 *
 * @author Alexei KLENIN
 */
public enum Flavor {
  STRAWBERRY,
  CHOCOLATE,
  BANANA,
  PISTACHIO,
  MELON,
  VANILLA;

  public static final String FLAVORS_JSON =
      Stream.of(Flavor.values())
          .map(flavor -> "\"" + flavor + "\"")
          .collect(Collectors.joining(", ", "[ ", " ]"));
}
