/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.screept.parse;

import java.util.List;
import java.util.stream.Stream;
import us.pserver.screept.Memory;
import us.pserver.screept.Statement;


/**
 *
 * @author juno
 */
public interface ParsingStack {
  
  public ParsingStack put(Statement s);
  
  public Stream<Statement> statements();
  
  public Statement nextMaxPriority();
  
  public List<Statement> getArgs(Statement s);
  
  public void clear();
  
  public Memory memory();
  
}
