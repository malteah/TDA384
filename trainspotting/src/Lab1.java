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
  Semaphore sem3_11 = new Semaphore(1);
  public void run()
  { 
    try
    {
    TSimInterface tsi = TSimInterface.getInstance();
    tsi.setSpeed(tId, tspeed);

    while(true){
      SensorEvent s = tsi.getSensor(tId); 

      // if(s.getYpos() == 7) {
      //    try{
      //     tsi.setSwitch(17, 7, 0);
      //    } catch (Exception e) {
      //     tsi.setSpeed(tId, 0);
      // }
      
      // if(s.getYpos() == 9) {
         
      //   tsi.setSwitch(15, 9, 0);

      // }

      
      if(s.getYpos() == 8 && s.getXpos() ==19) {
        try {
          sem3_11.acquire();
          

            
          
          
          
        } catch (Exception e) {
          // TODO: handle exception
        }
         
        tsi.setSwitch(15, 9, 0);

      }

      
      
    }
    
    
  }
  catch(Exception e){
    System.out.println(e);
  }
  }
}



// tsi.setSwitch(15, 9, 0); byter switch vid (15,9) till höger
 
      //tsi.setSpeed(1,speed1);
      //tsi.setSpeed(2,speed2);
      //tsi.setSwitch(17,7, 0);
      
      //SensorEvent sensor11 = new SensorEvent(1, 17, 7, 1);
      
     // System.out.println(sensor11);

      