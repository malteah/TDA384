import TSim.*;
import java.util.concurrent.Semaphore;

public class Lab1{
  
  public Lab1(int speed1, int speed2) {
    try {
      Train Train1 = new Train();
      Train1.tId = 1;
      Train1.tspeed = speed1;

      Train Train2 = new Train();
      Train2.tId = 2;
      Train2.tspeed = speed2;

      Thread t1 = new Thread(Train1);
      Thread t2 = new Thread(Train2);

      t1.start();
      t2.start();
      
    }
    catch (Exception e) {
      System.out.println("hello");
      //e.printStackTrace();    // or only e.getMessage() for the error
      //System.exit(1);
    }
  }
}

class Train implements Runnable{
  int tId;
  int tspeed;

  Semaphore track1 = new Semaphore(1);
  Semaphore track2 = new Semaphore(1);
  Semaphore track3 = new Semaphore(1);
  Semaphore track4 = new Semaphore(1);
  Semaphore track5 = new Semaphore(1);
  Semaphore track6 = new Semaphore(1);
  Semaphore track7 = new Semaphore(1);
  Semaphore track8 = new Semaphore(1);

  public void run()
  { 
    try
    {
    TSimInterface tsi = TSimInterface.getInstance();
    tsi.setSpeed(tId, tspeed);

    while(true){
      SensorEvent s = tsi.getSensor(tId);
      //TODO: ändra koordinater (förmodligen)
      Boolean t_at_s1 = (s.getXpos() ==5 && s.getYpos() == 9);
      Boolean t_at_s2 = (s.getXpos() ==13 && s.getYpos() == 9);
      Boolean t_at_s3 = (s.getXpos() ==19 && s.getYpos() == 8);
      Boolean t_at_s4 = (s.getXpos() ==13 && s.getYpos() == 7);
      Boolean movingForward = (tspeed > 0);
      Boolean movingBackward = (tspeed < 0);

      if(t_at_s1 && movingForward) {
        track1.acquire();
        track3.acquire(); //wait

      }


    }
    
    
  }
  catch(Exception e){
    System.out.println(e);
  }
  }
}

      