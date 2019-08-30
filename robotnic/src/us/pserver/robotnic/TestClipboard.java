/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.pserver.robotnic;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Arrays;


/**
 *
 * @author juno
 */
public class TestClipboard {
  
  
  public static void main(String[] args) throws UnsupportedFlavorException, IOException {
    Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
    Arrays.asList(clip.getAvailableDataFlavors()).forEach(System.out::println);
    System.out.println("--------------------------------");
    System.out.println(clip.getData(DataFlavor.stringFlavor));
    System.out.println("--------------------------------");
    Arrays.asList(clip.getContents(null).getTransferDataFlavors()).forEach(System.out::println);
  }
  
}
