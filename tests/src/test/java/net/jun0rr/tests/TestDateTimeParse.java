/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jun0rr.tests;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;


/**
 *
 * @author Juno
 */
public class TestDateTimeParse {
  
  private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  
  @Test
  public void method() {
    LocalDateTime dt = LocalDateTime.of(2019, 12, 6, 21, 02, 45);
    System.out.println(fmt.format(dt));
    dt = LocalDateTime.parse(fmt.format(dt), fmt);
    System.out.println(dt.getClass().getSimpleName() + " :: " + dt);
  }
  
  @Test
  public void method2() {
    LocalDateTime dt = LocalDateTime.of(2019, 12, 6, 21, 02, 45);
    ZoneId id = ZoneId.of("America/Sao_Paulo");
    System.out.println(dt.atZone(id));
    id = ZoneId.of("-02:00");
    System.out.println(dt.atZone(id));
  }
  
}
