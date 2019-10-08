/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.tools.compile.test;

import org.junit.jupiter.api.Test;
import us.pserver.tools.Reflect;
import us.pserver.tools.compile.CompilationUnit;
import us.pserver.tools.compile.Compilation;


/**
 *
 * @author juno
 */
public class TestCompiler {
  
  @Test
  public void test_multi_class_compile() {
    CompilationUnit cpoint = new CompilationUnit("us.pserver.tools.compile.test.CPoint");
    cpoint.appendln("package us.pserver.tools.compile.test;")
        .appendln("import static us.pserver.tools.compile.test.ITranslatable.translateInt;")
        .appendln("public class CPoint implements IPoint2D {")
        .appendln("  private final int x, y;")
        .appendln("  public CPoint(int x, int y) {")
        .appendln("    this.x = x;")
        .appendln("    this.y = y;")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public int x() {")
        .appendln("    return x;")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public int y() {")
        .appendln("    return y;")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public IPoint2D translate(double t) {")
        .appendln("    return new CPoint(translateInt(x, t), translateInt(y, t));")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public IPoint2D translate(double x, double y) {")
        .appendln("    return new CPoint(translateInt(this.x, x), translateInt(this.y, y));")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public int compareTo(IPoint2D o) {")
        .appendln("    return Integer.compare(this.x + this.y, o.x() + o.y());")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public int hashCode() {")
        .appendln("    int hash = 5;")
        .appendln("    hash = 29 * hash + this.x;")
        .appendln("    hash = 29 * hash + this.y;")
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
        .appendln("    final IPoint2D other = (IPoint2D) obj;")
        .appendln("    if (this.x != other.x()) {")
        .appendln("      return false;")
        .appendln("    }")
        .appendln("    return this.y == other.y();")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public String toString() {")
        .appendln("    return \"IPoint2D{\" + \"x=\" + x + \", y=\" + y + '}';")
        .appendln("  }")
        .appendln("}");
    
    CompilationUnit cdim = new CompilationUnit("us.pserver.tools.compile.test.CDimension");
    cdim.appendln("package us.pserver.tools.compile.test;")
        .appendln("import static us.pserver.tools.compile.test.ITranslatable.translateInt;")
        .appendln("public class CDimension implements IDimension2D {")
        .appendln("  private final int w, h;")
        .appendln("  public CDimension(int width, int height) {")
        .appendln("    this.w = width;")
        .appendln("    this.h = height;")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public int width() {")
        .appendln("    return w;")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public int height() {")
        .appendln("    return h;")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public IDimension2D scale(double x) {")
        .appendln("    return new CDimension(translateInt(w,x), translateInt(h,x));")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public IDimension2D scale(double w, double h) {")
        .appendln("    return new CDimension(translateInt(this.w,w), translateInt(this.h,h));")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public int compareTo(IDimension2D o) {")
        .appendln("    return Integer.compare(this.w + this.h, o.width() + o.height());")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public int hashCode() {")
        .appendln("    int hash = 7;")
        .appendln("    hash = 53 * hash + this.w;")
        .appendln("    hash = 53 * hash + this.h;")
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
        .appendln("    final IDimension2D other = (IDimension2D) obj;")
        .appendln("    if (this.w != other.width()) {")
        .appendln("      return false;")
        .appendln("    }")
        .appendln("    return this.h == other.height();")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public String toString() {")
        .appendln("    return \"IDimension2D{\" + \"width=\" + w + \", height=\" + h + '}';")
        .appendln("  }")
        .appendln("}");
    
    CompilationUnit crect = new CompilationUnit("us.pserver.tools.compile.test.CRectangle");
    crect.appendln("package us.pserver.tools.compile.test;")
        .appendln("import java.util.Objects;")
        .appendln("public class CRectangle implements IRectangle2D {")
        .appendln("  private final IPoint2D point;")
        .appendln("  private final IDimension2D dim;")
        .appendln("  public CRectangle(IPoint2D p, IDimension2D d) {")
        .appendln("    this.point = Objects.requireNonNull(p);")
        .appendln("    this.dim = Objects.requireNonNull(d);")
        .appendln("  }")
        .appendln("  public CRectangle(int x, int y, int width, int height) {")
        .appendln("    this(new CPoint(x, y), new CDimension(width, height));")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public IPoint2D point() {")
        .appendln("    return point;")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public IDimension2D dimension() {")
        .appendln("    return dim;")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public IRectangle2D scale(double d) {")
        .appendln("    return new CRectangle(point, dim.scale(d));")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public IRectangle2D scale(double w, double h) {")
        .appendln("    return new CRectangle(point, dim.scale(w, h));")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public IRectangle2D translate(double x) {")
        .appendln("    return new CRectangle(point.translate(x), dim);")
        .appendln("  }")
        .appendln("  ")
        .appendln("  @Override")
        .appendln("  public IRectangle2D translate(double x, double y) {")
        .appendln("    return new CRectangle(point.translate(x, y), dim);")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public int compareTo(IRectangle2D o) {")
        .appendln("    return Integer.compare(")
        .appendln("        point.x() + point.y() + dim.width() + dim.height(), ")
        .appendln("        o.point().x() + o.point().y() + o.dimension().width() + o.dimension().height()")
        .appendln("    );")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public int hashCode() {")
        .appendln("    int hash = 7;")
        .appendln("    hash = 89 * hash + Objects.hashCode(this.point);")
        .appendln("    hash = 89 * hash + Objects.hashCode(this.dim);")
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
        .appendln("    final IRectangle2D other = (IRectangle2D) obj;")
        .appendln("    if (!Objects.equals(this.point, other.point())) {")
        .appendln("      return false;")
        .appendln("    }")
        .appendln("    return Objects.equals(this.dim, other.dimension());")
        .appendln("  }")
        .appendln("  @Override")
        .appendln("  public String toString() {")
        .appendln("    return \"IRectangle2D{\" + \"point=\" + point + \", dim=\" + dim + '}';")
        .appendln("  }")
        .appendln("}");
    
    Compilation compiler = new Compilation();
    compiler.addAll(cpoint, cdim, crect).compile();

    Reflect<IPoint2D> r = compiler.reflectCompiled("CPoint");
    IntBiFunction<IPoint2D> cct = r.selectConstructor(int.class, int.class).constructorAsLambda(IntBiFunction.class);
    IPoint2D p = cct.apply(10, 20);
    System.out.println(p);
    
    
    Reflect<IDimension2D> r2 = compiler.reflectInstanceOf(IDimension2D.class);
    IntBiFunction<IDimension2D> cct2 = r2.selectConstructor(int.class, int.class).constructorAsLambda(IntBiFunction.class);
    IDimension2D d = cct2.apply(10, 20);
    System.out.println(d);
    System.out.println(d.scale(2.0, 0.5));
    
    
    Reflect<IRectangle2D> r3 = compiler.reflectCompiled("CRectangle");
    Int4Function<IRectangle2D> cct3 = r3.selectConstructor(int.class, int.class, int.class, int.class)
        .constructorAsLambda(Int4Function.class);
    IRectangle2D rect = cct3.apply(10, 20, 30, 40);
    System.out.println(rect);
    System.out.println(rect.translate(0.7).scale(1.3));
  }
  
}
