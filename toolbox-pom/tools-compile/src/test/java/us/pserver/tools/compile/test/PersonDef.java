package us.pserver.tools.compile.test;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class PersonDef {
  
  private final String name;
  
  private final String lastname;
  
  private final LocalDate birth;
  
  public PersonDef(String name, String lastname, LocalDate birth) {
    this.name = Objects.requireNonNull(name);
    this.lastname = Objects.requireNonNull(lastname);
    this.birth = Objects.requireNonNull(birth);
  }
  
  public String getName() {
    return name;
  }
  
  public String getLastname() {
    return lastname;
  }
  
  public LocalDate getBirth() {
    return birth;
  }
  
  public String getFullName() {
    return String.join(" ", name, lastname);
  }
  
  public int getAge() {
    return Period.between(birth, LocalDate.now()).getYears();
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + Objects.hashCode(this.name);
    hash = 29 * hash + Objects.hashCode(this.lastname);
    hash = 29 * hash + Objects.hashCode(this.birth);
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
    final PersonDef other = (PersonDef) obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.lastname, other.lastname)) {
      return false;
    }
    return Objects.equals(this.birth, other.birth);
  }
  
  @Override
  public String toString() {
    return "PersonDef{" + "fullname=" + getFullName() + ", birth=" + birth + ", age=" + getAge() + '}';
  }
  
}
