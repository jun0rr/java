/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.server.http.util;

import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.jun0rr.doxy.cfg.Host;
import net.jun0rr.doxy.common.Conditional;
import us.pserver.tools.Unchecked;


/**
 *
 * @author Juno
 */
public class StringValue {
  
  public static final Predicate<String> BOOLEAN_PATTERN = Pattern.compile("(true|false)", Pattern.CASE_INSENSITIVE).asMatchPredicate();
  
  public static final Predicate<String> DOUBLE_PATTERN = Pattern.compile("(-|\\+)?[0-9]+\\.[0-9]+").asMatchPredicate();
  
  public static final Predicate<String> INT_PATTERN = Pattern.compile("\\-?[0-9]{1,9}").asMatchPredicate();
  
  public static final Predicate<String> LONG_PATTERN = Pattern.compile("\\-?[0-9]{10,19}").asMatchPredicate();
  
  public static final Predicate<String> LOCAL_DATE_DDMMYYYY_PATTERN = Pattern.compile("[0-9]{2}\\/[0-9]{2}\\/[0-9]{4}").asMatchPredicate();
  
  public static final Predicate<String> LOCAL_DATE_YYYYMMDD_PATTERN = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}").asMatchPredicate();
  
  public static final Predicate<String> LOCAL_DATE_TIME_PATTERN = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]+)?").asMatchPredicate();
  
  public static final Predicate<String> LOCAL_ISO_DATE_TIME_PATTERN = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}(\\.[0-9]+)?").asMatchPredicate();
  
  public static final Predicate<String> LOCAL_TIME_PATTERN = Pattern.compile("[0-9]{2}:[0-9]{2}(:[0-9]{2})?(\\.[0-9]+)?").asMatchPredicate();
  
  public static final Predicate<String> LIST_PATTERN = Pattern.compile("([\\w\\.\\-\\/:\\s]+,)+[\\w\\.\\-\\/:\\s]+").asMatchPredicate();
  
  public static final Predicate<String> IPV4 = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$").asMatchPredicate();
  
  public static final Predicate<String> HOST = Pattern.compile("[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}:[0-9]{1,5}$").asMatchPredicate();
  
  
  private final String value;
  
  public StringValue(String val) {
    this.value = Conditional.<String>of(v->v != null && !v.isBlank())
        .elseThrow(v->new IllegalArgumentException("Bad string value: " + v))
        .apply(val).get();
  }
  
  public static StringValue of(String val) {
    return new StringValue(val);
  }
  
  public double getAsDouble() {
    try {
      return Double.parseDouble(value);
    } catch(Exception e) {
      return -1;
    }
  }
  
  public int getAsInt() {
    try {
      return Integer.parseInt(value);
    } catch(Exception e) {
      return -1;
    }
  }
  
  public long getAsLong() {
    try {
      return Long.parseLong(value);
    } catch(Exception e) {
      return -1;
    }
  }
  
  public Host getAsHost() {
    return Host.of(value);
  }
  
  public InetAddress getAsInetAddress() {
    return Unchecked.call(()->InetAddress.getByName(value));
  }
  
  public LocalDate getAsLocalDate() {
    return Conditional.<String,LocalDate>of(LOCAL_DATE_DDMMYYYY_PATTERN, v->LocalDate.from(DateTimeFormatter.ofPattern("dd/MM/yyyy").parse(v)))
        .elseIf(LOCAL_DATE_YYYYMMDD_PATTERN, v->LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(v)))
        .apply(value)
        .orElse(null);
  }
  
  public LocalDateTime getAsLocalDateTime() {
    return Conditional.<String,LocalDateTime>of(LOCAL_DATE_TIME_PATTERN, v->LocalDateTime.from(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse(v)))
        .elseIf(LOCAL_ISO_DATE_TIME_PATTERN, v->LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(v)))
        .apply(value)
        .orElse(null);
  }
  
  public boolean getAsBoolean() {
    return Boolean.parseBoolean(value);
  }
  
  public List<Object> getAsList() {
    return List.of(value.split(",")).stream()
        .map(StringValue::of)
        .map(StringValue::getAsObject)
        .collect(Collectors.toList());
  }
  
  public Object getAsObject() {
    return Conditional.<String,Object>of(BOOLEAN_PATTERN, v->Boolean.parseBoolean(v))
        .elseIf(INT_PATTERN, v->Integer.parseInt(v))
        .elseIf(LONG_PATTERN, v->Long.parseLong(v))
        .elseIf(DOUBLE_PATTERN, v->Double.parseDouble(v))
        .elseIf(LOCAL_DATE_DDMMYYYY_PATTERN, v->LocalDate.from(DateTimeFormatter.ofPattern("dd/MM/yyyy").parse(v)))
        .elseIf(LOCAL_DATE_YYYYMMDD_PATTERN, v->LocalDate.from(DateTimeFormatter.ISO_LOCAL_DATE.parse(v)))
        .elseIf(LOCAL_DATE_TIME_PATTERN, v->LocalDateTime.from(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").parse(v)))
        .elseIf(LOCAL_ISO_DATE_TIME_PATTERN, v->LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(v)))
        .elseIf(LIST_PATTERN, v->List.of(v.split(",")).stream().map(StringValue::of).map(StringValue::getAsObject).collect(Collectors.toList()))
        .elseIf(IPV4, v->Unchecked.call(()->InetAddress.getByName(v)))
        .elseIf(HOST, Host::of)
        .elseThen(s->s)
        .apply(value).get();
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + Objects.hashCode(this.value);
    return hash;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final StringValue other = (StringValue) obj;
    return Objects.equals(this.value, other.value);
  }
  
  @Override
  public String toString() {
    return value;
  }
  
}
