package com.jpower.utils;


import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * Thread exclusiva para impress�o
 * de mensagens na sa�da padr�o
 * do sistema.
 */
public class PrinterThread {

  private ConcurrentLinkedQueue printQueue;
  private Thread t;
  private boolean run;


  /**
   * Construtor padr�o.
   */
  public PrinterThread() {
    run = true;
    printQueue = new ConcurrentLinkedQueue();

    t = new Thread(new Runnable() {
        public void run() {
          try {
            while (isRunning()) {
              printAndRemoveFromQueue();
              waitPrint();
            }//while

          } catch (InterruptedException e) {
            System.err.println("Error running PrinterThread");
            e.printStackTrace();
          }//try-catch
        }//run
    });//new Runnable
  }//method


  private void waitPrint() throws InterruptedException {
    if(run) {
      synchronized (printQueue) {
        printQueue.wait();
      }//synchronized
    }//if
  }//method


  private void printAndRemoveFromQueue() {
    while (!printQueue.isEmpty())
      System.out.println(printQueue.poll());
  }//method


  /**
   * Verifica se a thread est�
   * ativa.
   */
  private boolean isRunning() {
    return run || !printQueue.isEmpty();
  }//method


  /**
   * Inicia a thread de impress�o.
   */
  public void start() {
    run = true;
    t.start();
  }//method()


  /**
   * P�ra a thread de impress�o
   */
  public void stop() {
    run = false;
    notifyPrintQueue();
  }//method()


  /**
   * Seta uma mensagem a ser impressa
   * pela thread na sa�da pad�o do sistema.
   */
  public void print(String s) {
    if(run) {
      printQueue.add(s);
      notifyPrintQueue();
    } else {
      throw new IllegalStateException("PrinterThread stoped.");
    }//if-else
  }//method


  private void notifyPrintQueue() {
    synchronized (printQueue) {
      printQueue.notifyAll();
    }//sync
  }//method


  public static void main(String[] args) {
    PrinterThread p = new PrinterThread();
    p.start();

    for(int i = 0; i < 10; i++) {
      p.print("PrinterTest " + String.valueOf(i));
    }//for

    p.stop();
  }//method()


}//class

