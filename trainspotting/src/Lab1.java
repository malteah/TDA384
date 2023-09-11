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
    catch(Exception e){
    System.out.println(e);
    }
  }
}

class Train implements Runnable{
  int tId;
  int tspeed;
  static Semaphore track1 = new Semaphore(1);
  static Semaphore track2 = new Semaphore(0);
  static Semaphore track3 = new Semaphore(1);
  static Semaphore track4 = new Semaphore(1);
  static Semaphore track5 = new Semaphore(1);
  static Semaphore track6 = new Semaphore(1);
  static Semaphore track7 = new Semaphore(1);
  static Semaphore track8 = new Semaphore(1);
  public void run()
  { 
    try

    {
    TSimInterface tsi = TSimInterface.getInstance();
    SensorEvent s = tsi.getSensor(tId);
    
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

      
      //TODO: sätt in nåt som funkar här (spår 4,5,7,8)
      Boolean t_at_8 = ((s.getYpos() == 3 || s.getYpos() == 7) && s.getTrainId() == tId);
      Boolean t_at_7 = ((s.getYpos() == 5 || s.getYpos() == 8) && s.getTrainId() == tId);
      //Boolean t_at_5 = (s.getYpos() ==  9 && s.getTrainId() == tId);
      //Boolean t_at_4 = (s.getYpos() == 10 && s.getTrainId() == tId);
      Boolean t_at_2 = (s.getYpos() == 11 && s.getTrainId() == tId);
      Boolean t_at_1 = (s.getYpos() == 13 && s.getTrainId() == tId);
      

      //TODO: sätt in nåt som funkar här
      Boolean movingUp = true; //(sensor 1 && 2 spår 1) ELLER (sensor 1 && 2 spår 2)
      Boolean movingDown = false; //motsvarande fast spår 7,8

      
      

    //   if(last_s.getXpos() == 14 && last_s.getYpos() == 3 && s.getXpos() == 15 && s.getYpos() == 7 && s.getTrainId() == tId && last_s.getTrainId()  == tId)
    //   { movingUp = false;
    //   movingDown = true;
    // System.out.println(movingDown);}
      
    //   else if(last_s.getXpos() == 14 && last_s.getYpos() == 6 && s.getXpos() == 15 && s.getYpos() == 8 && s.getTrainId() == tId && last_s.getTrainId()  == tId)
    //   { movingUp = false;
    //   movingDown = true;
    //   System.out.println(movingDown);}
      
    //   else if(last_s.getXpos() == 14 && last_s.getYpos() == 11 && s.getXpos() == 6 && s.getYpos() == 11 && s.getTrainId() == tId && last_s.getTrainId()  == tId)
    //   { movingUp = true;
    //   movingDown = false;
    //   System.out.println(movingUp);}

    //    else if(last_s.getXpos() == 14 && last_s.getYpos() == 13 && s.getXpos() == 6 && s.getYpos() == 13 && s.getTrainId() == tId && last_s.getTrainId()  == tId)
    //   { movingUp = true;
    //   movingDown = false;
    //   System.out.println(movingUp);}

    //   else{
    //     System.out.println("någon sensor är fel inskriven");
    //   }
      
      //logik (förhoppningsvis)
      if(t_at_s1 && movingUp) {   // Spår 1
        track1.acquire(); // vi är på spår 1
        tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 3
        track3.acquire(); // vi ska till spår 3
        tsi.setSwitch(3,11,1); // sätt switch till högerläge
        tsi.setSpeed(tId, tspeed); // kör
        track1.release(); // vi är inte längre på spår 1  
      }

      if(t_at_s2 && movingUp) {   // Spår 2
        track2.acquire(); // vi är på spår 1
        tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 3
        track3.acquire(); // vi ska till spår 3
        tsi.setSwitch(3,11,0); // sätt switch till vänster
        tsi.setSpeed(tId, tspeed); // kör
        track2.release(); // vi är inte längre på spår 1  
      }

      if(t_at_s3 && movingUp) {
        if(track5.tryAcquire()){
          tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 5
          track5.acquire();
          tsi.setSwitch(4,9,0); // kör till vänster
          tsi.setSpeed(tId, tspeed); // kör
          track3.release();
        } else if (track4.tryAcquire()){
          tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 4
          track4.acquire();
          tsi.setSwitch(4,9,1); // kör till höger
          tsi.setSpeed(tId, tspeed); // kör
          track3.release();
        }
        }

      if(t_at_s4_r && movingUp) {
        track6.acquire();
        tsi.setSwitch(15,9,0); // sätt till vänster
        tsi.setSpeed(tId, tspeed); // kör
        track4.release();
      }
      System.out.println(t_at_s5_r);
      
      if(t_at_s5_r && movingUp) {
        tsi.setSpeed(tId, 0);
        track6.acquire();
        System.out.println("spår 6 tagen");
        tsi.setSwitch(15,9,1); // sätt till höger
        tsi.setSpeed(tId, tspeed); // kör
        track5.release();
      }

      if(t_at_s6 && movingUp) {
        if(track7.tryAcquire()){
          tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 5
          track7.acquire();
          tsi.setSwitch(17,7,0); // kör till vänster
          tsi.setSpeed(tId, tspeed); // kör
          track6.release();
        } else if (track8.tryAcquire()){
          tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 4
          track8.acquire();
          tsi.setSwitch(17,7,1); // kör till höger
          tsi.setSpeed(tId, tspeed); // kör
          track6.release();
        }
        }

      else if(t_at_8 && movingUp){ // Stanna på spår 8 och vänder
        tsi.setSpeed(tId, 0);
        Thread.sleep(1500); // sleep
        tsi.setSpeed(tId , -tspeed);
        movingDown = true;
      }

      else if(t_at_7 && movingUp){ // Stanna på spår 7 och   
        tsi.setSpeed(tId, 0);
        Thread.sleep(1500); // sleep
        tsi.setSpeed(tId, -tspeed);
        movingDown = true;
      }

      else if(t_at_1 && movingDown){ // Stanna på spår 1 och vänder
        tsi.setSpeed(tId, 0);
        Thread.sleep(1500); // sleep
        tsi.setSpeed(tId , tspeed);
        movingUp = true;
        
      }

      else if(t_at_2 && movingDown){ // Stanna på spår 1 och vänder
        tsi.setSpeed(tId, 0);
        Thread.sleep(1500); // sleep
        tsi.setSpeed(tId , tspeed);
        movingUp = true;
      }

    }
    
    
  }
  catch(Exception e){
    System.out.println(e);
    System.out.println("FFFs");
    
  }
  }
}



      