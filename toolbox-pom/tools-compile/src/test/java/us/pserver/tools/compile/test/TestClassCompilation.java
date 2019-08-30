/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import java.time.LocalDate;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.tinylog.Logger;
import us.pserver.tools.compile.ClassCompilation;
import us.pserver.tools.Reflect;


/**
 *
 * @author juno
 */
public class TestClassCompilation {
  
  @Test
  public void test_ClassCompilation() throws Throwable {
    try {
      ClassCompilation cdef = new ClassCompilation("us.pserver.tools.compile.test.Person");
      cdef.appendln("package us.pserver.tools.compile.test;")
          .appendln("import java.time.LocalDate;")
          .appendln("import java.time.Period;")
          .appendln("import java.util.Objects;")
          .appendln("public class Person implements IPerson {")
          .appendln("  private final String name;")
          .appendln("  private final String lastname;")
          .appendln("  private final LocalDate birth;")
          .appendln("  public Person(String name, String lastname, LocalDate birth) {")
          .appendln("    this.name = Objects.requireNonNull(name);")
          .appendln("    this.lastname = Objects.requireNonNull(lastname);")
          .appendln("    this.birth = Objects.requireNonNull(birth);")
          .appendln("  }")
          .appendln("  public String getName() {")
          .appendln("    return name;")
          .appendln("  }")
          .appendln("  public String getLastname() {")
          .appendln("    return lastname;")
          .appendln("  }")
          .appendln("  public LocalDate getBirth() {")
          .appendln("    return birth;")
          .appendln("  }")
          .appendln("  public String getFullName() {")
          .appendln("    return String.join(\" \", name, lastname);")
          .appendln("  }")
          .appendln("  public int getAge() {")
          .appendln("    return Period.between(birth, LocalDate.now()).getYears();")
          .appendln("  }")
          .appendln("  @Override")
          .appendln("  public int hashCode() {")
          .appendln("    int hash = 7;")
          .appendln("    hash = 29 * hash + Objects.hashCode(this.name);")
          .appendln("    hash = 29 * hash + Objects.hashCode(this.lastname);")
          .appendln("    hash = 29 * hash + Objects.hashCode(this.birth);")
          .appendln("    return hash;")
          .appendln("  }")
          .appendln("  @Override")
          .appendln("  public boolean equals(Object obj) {")
          .appendln("    if (this == obj) {")
          .appendln("      return true;")
          .appendln("    }")
          .appendln("    if (obj == null) {")
          .appendln("      return false;")
          .appendln("    }")
          .appendln("    if (getClass() != obj.getClass()) {")
          .appendln("      return false;")
          .appendln("    }")
          .appendln("    final Person other = (Person) obj;")
          .appendln("    if (!Objects.equals(this.name, other.name)) {")
          .appendln("      return false;")
          .appendln("    }")
          .appendln("    if (!Objects.equals(this.lastname, other.lastname)) {")
          .appendln("      return false;")
          .appendln("    }")
          .appendln("    return Objects.equals(this.birth, other.birth);")
          .appendln("  }")
          .appendln("  @Override")
          .appendln("  public String toString() {")
          .appendln("    return \"Person{\" + \"fullname=\" + getFullName() + \", birth=\" + birth + \", age=\" + getAge() + '}';")
          .appendln("  }")
          .appendln("}");
      Reflect<IPerson> ref = cdef.reflectCompiled();
      TriFunction<String,String,LocalDate,IPerson> cct = ref
          .selectConstructor(String.class, String.class, LocalDate.class)
          .constructorAsLambda(TriFunction.class);
      IPerson juno = cct.apply("Juno", "Roesler", LocalDate.of(1980, 7, 7));
      Logger.debug(juno);
      //ref = ref.of(juno, ref.lookup());
      Function<IPerson,String> fullname = ref.selectMethod("getFullName").dynamicMethodAsFunction();
      ToIntFunction<IPerson> age = ref.selectMethod("getAge").dynamicLambdaMethod(ToIntFunction.class);
      Assertions.assertEquals("Juno Roesler", fullname.apply(juno));
      Assertions.assertEquals("Juno Roesler", juno.getFullName());
      Assertions.assertEquals(39, age.applyAsInt(juno));
      Assertions.assertEquals(39, juno.getAge());
    }
    catch(Throwable e) {
      e.printStackTrace();
      throw e;
    }
  }
  
}
