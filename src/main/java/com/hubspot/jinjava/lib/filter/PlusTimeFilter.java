package com.hubspot.jinjava.lib.filter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import com.hubspot.jinjava.doc.annotations.JinjavaDoc;
import com.hubspot.jinjava.doc.annotations.JinjavaParam;
import com.hubspot.jinjava.doc.annotations.JinjavaSnippet;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.lib.fn.Functions;
import com.hubspot.jinjava.objects.date.PyishDate;

/**
 * {@link ChronoUnit} for valid time units
 */
@JinjavaDoc(
    value = "Adds a specified amount of time to a datetime object",
    params = {
        @JinjavaParam(value = "var", desc = "Datetime object or timestamp"),
        @JinjavaParam(value = "diff", desc = "The amount to add to the datetime"),
        @JinjavaParam(value = "unit", desc = "Which temporal unit to use"),
    },
    snippets = {
        @JinjavaSnippet(code = "{% mydatetime|plus_time(3, 'days') %}"),
    })
public class PlusTimeFilter extends BaseDateFilter {

  @Override
  public Object filter(Object var, JinjavaInterpreter interpreter, String... args) {

    long diff = parseDiffAmount(args);
    ChronoUnit chronoUnit = parseChronoUnit(args);

    if (var instanceof ZonedDateTime) {
      ZonedDateTime dateTime = (ZonedDateTime) var;
      return new PyishDate(dateTime.plus(diff, chronoUnit));
    } else if (var instanceof PyishDate) {
      PyishDate pyishDate = (PyishDate) var;
      return new PyishDate(pyishDate.toDateTime().plus(diff, chronoUnit));
    } else if (var instanceof Number) {
      Number timestamp = (Number) var;
      ZonedDateTime zonedDateTime = Functions.getDateTimeArg(timestamp, ZoneOffset.UTC);
      return new PyishDate(zonedDateTime.plus(diff, chronoUnit));
    }

    return var;
  }

  @Override
  public String getName() {
    return "plus_time";
  }
}
