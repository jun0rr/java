/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jpower.utils;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author f6036477
 */
public class MainFileUtils {

  private static File f;

  public static void main(String[] args)
  {
    System.out.println( "FileUtils - juno.rr@gmail.com" );
    System.out.println( "Use: " );
    System.out.println( "  utils.jar <option> <filepath>" );
    System.out.println(  );
    System.out.println( "  Options: " );
    System.out.println( "   -e : Encode File;" );
    System.out.println( "   -d : Decode File;" );
    System.out.println( "   -s <Size>: Split File by Size (KB);" );
    System.out.println( "   -p <Parts>: Split File in Parts;" );
    System.out.println( "   -u : Unsplit File." );
    System.out.println(  );

    if(args.length < 2) {
      args = new String[3];
      readOptions(args);
    }

    String option = args[0];
    if(option.equals("-s") || option.equals("-p"))
      f = new File(args[2]);
    else
      f = new File(args[1]);

    if(!f.exists()) {
      System.out.println( "[ERRO]: Arquivo n�o existe!" );
      return;
    }

    if(option.equals("-e"))
      encode();
    else if(option.equals("-d"))
      decode();
    else if(option.equals("-s"))
      split(Long.parseLong(args[1]) * 1024);
    else if(option.equals("-p"))
      split(Integer.parseInt(args[1]));
    else if(option.equals("-u"))
      unsplit();
    else
      System.out.println( "[ERRO]: Uso Incorreto!" );
  }

  public static void encode()
  {
    boolean success;
    FileEncoder encoder = new FileEncoder(f);
    success = encoder.encode();
    if(success)
      System.out.println( "[INFO]: Arquivo codificado com Sucesso!" );
    else
      System.out.println( "[ERRO]: Erro durante a codifica��o. Falhou!" );
  }

  public static void decode()
  {
    boolean success;
    FileEncoder encoder = new FileEncoder(f);
    success = encoder.decode();
    if(success)
      System.out.println( "[INFO]: Arquivo decodificado com Sucesso!" );
    else
      System.out.println( "[ERRO]: Erro durante a decodifica��o. Falhou!" );
  }

  public static void split(int parts)
  {
    if(parts <= 0)
      System.out.println( "[ERRO]: N�mero de partes inv�lido para divis�o: " + parts + "!" );

    boolean success;
    FileSplitter splitter = new FileSplitter(f);
    success = splitter.split(parts);
    if(success)
      System.out.println( "[INFO]: Arquivo dividido com Sucesso!" );
    else
      System.out.println( "[ERRO]: Erro durante a divis�o do arquivo. Falhou!" );
  }

  public static void split(long size)
  {
    if(size <= 0)
      System.out.println( "[ERRO]: Tamanho do arquivo inv�lido para divis�o: " + size + "!" );

    boolean success;
    FileSplitter splitter = new FileSplitter(f);
    success = splitter.split(size);
    if(success)
      System.out.println( "[INFO]: Arquivo dividido com Sucesso!" );
    else
      System.out.println( "[ERRO]: Erro durante a divis�o do arquivo. Falhou!" );
  }

  public static void unsplit()
  {
    boolean success;
    FileSplitter splitter = new FileSplitter(f);
    success = splitter.unsplit();
    if(success)
      System.out.println( "[INFO]: Arquivo unificado com Sucesso!" );
    else
      System.out.println( "[ERRO]: Erro durante a unifica��o do arquivo. Falhou!" );
  }

  public static void readOptions(String[] args)
  {
    Scanner scan = new Scanner(System.in);
    System.out.print( "Informe a op��o desejada\n[REQUEST]: " );
    args[0] = scan.next();
    int fileIndex = 1;
    if(args[0].equals("-s")) {
      System.out.print( "Informe o tamanho m�ximo para divis�o (KB)\n[REQUEST]: " );
      args[1] = scan.next();
      fileIndex = 2;
    } else if(args[0].equals("-p")) {
      System.out.print( "Informe o n�mero de partes para divis�o\n[REQUEST]: " );
      args[1] = scan.next();
      fileIndex = 2;
    }
    System.out.print( "Informe o caminho seguido pelo nome do Arquivo\n[REQUEST]: " );
    args[fileIndex] = scan.next();
    System.out.println(  );
  }

}
