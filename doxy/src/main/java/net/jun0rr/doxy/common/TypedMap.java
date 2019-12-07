/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.doxy.common;

import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.Set;
import net.jun0rr.doxy.cfg.Host;


/**
 *
 * @author Juno
 */
public class TypedMap implements Map<String,String> {
  
  private final Map<String,String> map;
  
  public TypedMap(Map<String,String> map) {
    this.map = Objects.requireNonNull(map, "Bad null Map");
  }
  
  public TypedMap() {
    this(new HashMap<>());
  }
  
  public TypedMap​(int initialCapacity) {
    this(new HashMap<>(initialCapacity));
  }
  
  public TypedMap​(int initialCapacity, float loadFactor) {
    this(new HashMap<>(initialCapacity, loadFactor));
  }
  
  @Override
  public int size() {
    return map.size();
  }
  
  @Override
  public boolean isEmpty() {
    return map.isEmpty();
  }
  
  @Override
  public boolean containsKey(Object key) {
    return map.containsKey(key);
  }
  
  @Override
  public boolean containsValue(Object value) {
    return map.containsValue(value);
  }
  
  @Override
  public String get(Object key) {
    return map.get(key);
  }
  
  public OptionalInt getAsInt(String key) {
    try {
      return OptionalInt.of(Integer.parseInt(this.get(key)));
    } catch(Exception e) {
      return OptionalInt.empty();
    }
  }
  
  public OptionalDouble getAsDouble(String key) {
    try {
      return OptionalDouble.of(Double.parseDouble(this.get(key)));
    } catch(Exception e) {
      return OptionalDouble.empty();
    }
  }
  
  public OptionalLong getAsLong(String key) {
    try {
      return OptionalLong.of(Long.parseLong(this.get(key)));
    } catch(Exception e) {
      return OptionalLong.empty();
    }
  }
  
  public Optional<Host> getAsHost(String key) {
    String val = get(key);
    if(val == null || !val.contains(":")) {
      return Optional.empty();
    }
    return Optional.of(Host.of(val));
  }
  
  public boolean getAsBoolean(String key) {
    String val = get(key);
    if(val == null || val.isBlank()) return false;
    return Boolean.parseBoolean(val);
  }
  
  public Optional<LocalDate> getAsLocalDate(String key, String pattern) {
    String val = get(key);
    if(val == null || val.isBlank()) return Optional.empty();
    return Optional.of(LocalDate.parse(val, DateTimeFormatter.ofPattern(pattern)));
  }
  
  public Optional<LocalDate> getAsLocalDate(String key) {
    String val = get(key);
    try {
      return Optional.of(LocalDate.parse(val, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    } catch(Exception e) {
      return Optional.empty();
    }
  }
  
  public Optional<LocalDateTime> getAsLocalDateTime(String key, String pattern) {
    String val = get(key);
    if(val == null || val.isBlank()) return Optional.empty();
    return Optional.of(LocalDateTime.parse(val, DateTimeFormatter.ofPattern(pattern)));
  }
  
  public Optional<LocalDateTime> getAsLocalDateTime(String key) {
    String val = get(key);
    try {
      return Optional.of(LocalDateTime.parse(val, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    } catch(Exception e) {
      return Optional.empty();
    }
  }
  
  public Optional<ZonedDateTime> getAsZonedDateTime(String key, String pattern) {
    String val = get(key);
    if(val == null || val.isBlank()) return Optional.empty();
    return Optional.of(ZonedDateTime.parse(val, DateTimeFormatter.ofPattern(pattern)));
  }
  
  public Optional<ZonedDateTime> getAsZonedDateTime(String key, ZoneId zid) {
    return getAsLocalDateTime(key).map(d->d.atZone(zid));
  }
  
  public Optional<ZonedDateTime> getAsZonedDateTime(String key) {
    return getAsZonedDateTime(key, ZoneId.systemDefault());
  }
  
  public Optional<Instant> getAsInstant(String key, ZoneId zid) {
    return getAsZonedDateTime(key, zid).map(ZonedDateTime::toInstant);
  }
  
  public Optional<Instant> getAsInstant(String key) {
    return getAsZonedDateTime(key).map(ZonedDateTime::toInstant);
  }
  
  public Optional<InetAddress> getAsInetAddress(String key) {
    try {
      return Optional.of(InetAddress.getByName(get(key)));
    } catch(Exception e) {
      return Optional.empty();
    }
  }
  
  public Optional<Class> getAsClass(String key) {
    try {
      return Optional.of(Class.forName(get(key)));
    } catch(Exception e) {
      return Optional.empty();
    }
  }
  
  public Optional<Path> getAsPath(String key) {
    String val = get(key);
    if(val == null || val.isBlank()) return Optional.empty();
    return Optional.of(Paths.get(val));
  }
  
  public Optional<ByteBuffer> getAsBase64Encoded(String key) {
    String val = get(key);
    if(val == null || val.isBlank()) return Optional.empty();
    return Optional.of(ByteBuffer.wrap(Base64.getDecoder().decode(val)));
  }
  
  @Override
  public String put(String key, String value) {
    return map.put(key, value);
  }
  
  @Override
  public String remove(Object key) {
    return map.remove(key);
  }
  
  @Override
  public void putAll(Map<? extends String,? extends String> m) {
    map.putAll(m);
  }
  
  @Override
  public void clear() {
    map.clear();
  }
  
  @Override
  public Set<String> keySet() {
    return map.keySet();
  }
  
  @Override
  public Collection<String> values() {
    return map.values();
  }
  
  @Override
  public Set<Entry<String,String>> entrySet() {
    return map.entrySet();
  }
  
}
