package us.pserver.tools.compile.test;

import java.time.LocalDate;

public interface IPerson {
  
  public String getName();
  
  public String getLastname();
  
  public LocalDate getBirth();
  
  public String getFullName();
  
  public int getAge();
  
}
