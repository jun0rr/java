package com.jpower.utils;


import java.io.File;

/**
 * Classe que indica o sistema operacional
 * na qual a JVM est� rodando, pode ser acessado
 * pela vari�vel est�tica OS.CURRENT
 * Os sistemas operacionais s�o representados
 * pelo enum interno OsType.
 * @author Juno Roesler
 */
public class OS {

  /**
   * Cont�m os valores poss�veis
   * para os sistemas operacionais:
   * OsType.WINDOWS, OsType.LINUX
   */
  public static enum OsType { WINDOWS, LINUX }

  /**
   * Sistema operacional atual,
   * no qual a JVM est� rodando.
   * Um dos tipos poss�veis para OsType.
   */
  public static final OsType CURRENT = 
      (File.separator.equals("/") ?
        OsType.LINUX : OsType.WINDOWS);

  /**
   * Construtor ilegal. OS n�o deve ser instanciado.
   * @throws IllegalAccessException
   */
  private OS() throws IllegalAccessException {
    throw new IllegalAccessException("OS n�o pode ser instanciado!");
  }

  /**
   * Retorna true se o sistema operacional
   * for Linux.
   * @return boolean
   */
  public static boolean isLinux()
  {
    return (OS.CURRENT == OS.OsType.LINUX);
  }

  /**
   * Retorna true se o sistema operacional
   * for Windows.
   * @return boolean
   */
  public static boolean isWindows()
  {
    return (OS.CURRENT == OS.OsType.WINDOWS);
  }

}
