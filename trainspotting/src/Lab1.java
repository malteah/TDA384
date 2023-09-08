import TSim.*;
import java.util.concurrent.Semaphore;



public class Lab1{
  //public Semaphore sem = new Semaphore(1);

  public static String test()
  {
    return "test";
  }
  
  public Lab1(int speed1, int speed2) {
    Semaphore sem3_11_r = new Semaphore(1);
    Semaphore sem3_11_l = new Semaphore(1);
    Semaphore sem15_9_r = new Semaphore(1);
    Semaphore sem15_9_l = new Semaphore(1);
    Semaphore sem4_9_r = new Semaphore(1);
    Semaphore sem4_9_l = new Semaphore(1);
    Semaphore sem17_7_r = new Semaphore(1);
    Semaphore sem17_7_l = new Semaphore(1);

    try {
      Train Train1 = new Train();
      Train1.tId = 1;
      Train1.tspeed = speed1;
      Train1.sem3_11_r = sem3_11_r;
      Train1.sem3_11_l = sem3_11_l;
      Train1.sem15_9_r = sem15_9_r;
      Train1.sem15_9_l = sem15_9_l;
      Train1.sem4_9_r = sem4_9_r;
      Train1.sem4_9_l = sem4_9_l;
      Train1.sem17_7_r = sem17_7_r;
      Train1.sem17_7_l = sem17_7_l;



      Train Train2 = new Train();
      Train2.tId = 2;
      Train2.tspeed = speed2;
      Train2.sem3_11_r = sem3_11_r;
      Train2.sem3_11_l = sem3_11_l;
      Train2.sem15_9_r = sem15_9_r;
      Train2.sem15_9_l = sem15_9_l;
      Train2.sem4_9_r = sem4_9_r;
      Train2.sem4_9_l = sem4_9_l;
      Train2.sem17_7_r = sem17_7_r;
      Train2.sem17_7_l = sem17_7_l;
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
  Semaphore sem3_11_r;
  Semaphore sem3_11_l;
  Semaphore sem15_9_r;
  Semaphore sem15_9_l;
  Semaphore sem17_7_r;
  Semaphore sem17_7_l;
  Semaphore sem4_9_r;
  Semaphore sem4_9_l;
  //Semaphore sem3_11 = new Semaphore(1);
  
  public void toggle_switch(int sen1_x, int sen1_y, int sen2_x, int sen2_y, int swt_x, int swt_y, int dir, Semaphore sem, SensorEvent s)
  {
    try {
          TSimInterface tsi = TSimInterface.getInstance();
          
          if (s.getXpos() == sen1_x && s.getYpos() == sen1_y && s.getTrainId() == tId);
          {
            try 
            {
              sem.acquire();
              tsi.setSwitch(swt_x, swt_y, dir);
              
              s = tsi.getSensor(tId);
              if (s.getXpos() == sen2_x && s.getYpos() ==sen2_y && s.getTrainId() == tId)
                {
                  sem.release();
                }
            } 
            catch (Exception e) 
            {
              tsi.setSpeed(tId, 0);
            }

            finally
            {
              //sem.release();
            }

          }
        }
          
          catch (Exception e) 
          {
          // TODO: handle exception
          }
  }

  public void toggle_split_switch(int sen1_x, int sen1_y, int sen2_x, int sen2_y, int swt_x, int swt_y, int dir, Semaphore sem, SensorEvent s)
  {
    try {
          TSimInterface tsi = TSimInterface.getInstance();
          
          if (s.getXpos() == sen1_x && s.getYpos() == sen1_y && s.getTrainId() == tId);
          {
            try 
            {
              sem.acquire();
              tsi.setSwitch(swt_x, swt_y, dir);
              s = tsi.getSensor(tId);
              if (s.getXpos() == sen2_x && s.getYpos() ==sen2_y && s.getTrainId() == tId)
                {
                  sem.release();
                }
            } 
            catch (Exception e) 
            {
              tsi.setSpeed(tId, 0);
            }

            finally
            {
              sem.release();
            }

          }
        }
          
          catch (Exception e) 
          {
          // TODO: handle exception
          }
  }

  
  public void run()
  { 
    try
    {
    TSimInterface tsi = TSimInterface.getInstance();
    tsi.setSpeed(tId, tspeed);

    while(true){
      SensorEvent s = tsi.getSensor(tId); 
      // Switch (17,7)
      toggle_switch(14, 3, 19, 8 ,17 ,7 , 0, sem17_7_r,s);
      toggle_switch(14, 5, 19, 8 ,17 ,7 , 1, sem17_7_l,s);
      toggle_switch(14, 7, 18, 7 ,17 ,7 , 0, sem17_7_r,s);
      

      // Switch (15,9)
      toggle_switch(13, 9, 19, 8 ,15 , 9, 0, sem15_9_r, s);
      
      // Switch(4,9)
      
      toggle_switch(5, 10, 2, 9, 4, 9, 0, sem4_9_r, s);
      toggle_switch(7, 9, 2, 9 ,4 , 9, 1, sem4_9_l, s);

      // Switch (3,11)
      toggle_switch(2, 9, 2, 9 ,3 ,11 , 0, sem3_11_l,s);
      toggle_switch(5, 13, 2, 9 ,3 , 11, 1, sem3_11_l, s);
      


      
      
      
      
      
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

      