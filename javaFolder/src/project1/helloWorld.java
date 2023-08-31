package javaFolder.src.com.project1;

class Printer implements Runnable {

  public void run () {
    System.out.println("Hello world");
  }

  public static void main (String[] args) {
    try {
      Printer c = new Printer ();

      // Create a thread that run our run () method.
      Thread t1 = new Thread (c, "thread1");
      //Thread t2 = new Thread (c, "thread2");


      t1.start();
      //t2.start();
      

      // Wait for the thread to finish.
      t1.join();
      //t2.join();

    } catch (InterruptedException e) {
      System.out.println ("Interrupted!");
    }
  }
}