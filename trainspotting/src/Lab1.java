import TSim.*;
import java.util.concurrent.Semaphore;



public class Lab1{
  //public Semaphore sem = new Semaphore(1);

  public static String test()
  {
    return "test";
  }
  
  public Lab1(int speed1, int speed2) {
    Semaphore track1 = new Semaphore(1);
    Semaphore track2 = new Semaphore(0);
    Semaphore track3 = new Semaphore(1);
    Semaphore track4 = new Semaphore(1);
    Semaphore track5 = new Semaphore(1);
    Semaphore track6 = new Semaphore(1);
    Semaphore track7 = new Semaphore(1);
    Semaphore track8 = new Semaphore(1);
    try{
      Train Train1 = new Train();
      Train1.tId = 1;
      Train1.tspeed = speed1;

      Train1.track1 = track1;
      Train1.track2 = track2;
      Train1.track3 = track3;
      Train1.track4 = track4;
      Train1.track5 = track5;
      Train1.track6 = track6;
      Train1.track7 = track7;
      Train1.track8 = track8;

      Train Train2 = new Train();
      Train2.tId = 2;
      Train2.tspeed = speed2;

      Train2.track1 = track1;
      Train2.track2 = track2;
      Train2.track3 = track3;
      Train2.track4 = track4;
      Train2.track5 = track5;
      Train2.track6 = track6;
      Train2.track7 = track7;
      Train2.track8 = track8;

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
  Semaphore track1;
  Semaphore track2; 
  Semaphore track3;
  Semaphore track4;
  Semaphore track5;
  Semaphore track6;
  Semaphore track7;
  Semaphore track8;
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
    SensorEvent s = tsi.getSensor(tId);
    while(true){
      s = tsi.getSensor(tId); 
      Boolean t_at_s1_r = (s.getXpos() == 14 && s.getYpos() == 13 && s.getTrainId() == tId && s.getStatus() == 1);
      Boolean t_at_s1_l = (s.getXpos() ==  6 && s.getYpos() == 13 && s.getTrainId() == tId && s.getStatus() == 1); // Senscor close to juntion at track 1
      Boolean t_at_s2_r = (s.getXpos() == 14 && s.getYpos() == 11 && s.getTrainId() == tId && s.getStatus() == 1);
      Boolean t_at_s2_l = (s.getXpos() ==  6 && s.getYpos() == 11 && s.getTrainId() == tId && s.getStatus() == 1);//  Senscor close to juntion at track 2
      Boolean t_at_s3   = (s.getXpos() ==  1 && s.getYpos() == 10 && s.getTrainId() == tId);// Senscor at track 3
      Boolean t_at_s4_r = (s.getXpos() == 12 && s.getYpos() == 10 && s.getTrainId() == tId);// Spår
      Boolean t_at_s4_l = (s.getXpos() ==  7 && s.getYpos() == 10 && s.getTrainId() == tId);// Spår
      Boolean t_at_s5_r = (s.getXpos() == 12 && s.getYpos() == 9 && s.getTrainId() == tId);// Spår
      Boolean t_at_s5_l = (s.getXpos() ==  7 && s.getYpos() == 9 && s.getTrainId() == tId);// Spår
      Boolean t_at_s6   = (s.getXpos() == 19 && s.getYpos() == 8 && s.getTrainId() == tId);// Spår
      Boolean t_at_s7_r = (s.getXpos() == 14 && s.getYpos() == 8 && s.getTrainId() == tId);
      Boolean t_at_s7_l = (s.getXpos() == 11 && s.getYpos() == 8 && s.getTrainId() == tId);
      Boolean t_at_s7_t = (s.getXpos() == 14 && s.getYpos() == 5 && s.getTrainId() == tId);
      Boolean t_at_s8_r = (s.getXpos() == 14 && s.getYpos() == 7 && s.getTrainId() == tId);
      Boolean t_at_s8_l = (s.getXpos() == 11 && s.getYpos() == 7 && s.getTrainId() == tId);
      Boolean t_at_s8_t = (s.getXpos() == 14 && s.getYpos() == 4 && s.getTrainId() == tId);

      // if(t_at_s2_r)
      // {tsi.setSpeed(tId, 0);
      // Thread.sleep(1000);
      // tsi.setSpeed(tId, tspeed);}

      // if(t_at_s2_l)
      // {tsi.setSpeed(tId, 0);
      // Thread.sleep(1000);
      // tsi.setSpeed(tId, tspeed);}


      // //TODO: sätt in nåt som funkar här (spår 4,5,7,8)
      Boolean t_at_8 = ((s.getYpos() == 3 || s.getYpos() == 7) && s.getTrainId() == tId);
      Boolean t_at_7 = ((s.getYpos() == 5 || s.getYpos() == 8) && s.getTrainId() == tId);
      //Boolean t_at_5 = (s.getYpos() ==  9 && s.getTrainId() == tId);
      //Boolean t_at_4 = (s.getYpos() == 10 && s.getTrainId() == tId);
      Boolean t_at_2 = (s.getYpos() == 11 && s.getTrainId() == tId);
      Boolean t_at_1 = (s.getYpos() == 13 && s.getTrainId() == tId);

      if(t_at_8)
      {tsi.setSpeed(tId, 2);}
      else{tsi.setSpeed(tId, 10);}
      


      


      
      
      
      
      
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

      