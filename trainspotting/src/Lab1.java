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
    System.out.println(e.getMessage());
    }
  }
}

class Train implements Runnable{
  int tId;
  int tspeed;
  static Semaphore track1 = new Semaphore(1);
  static Semaphore track2 = new Semaphore(1);
  static Semaphore track3 = new Semaphore(1);
  static Semaphore track4 = new Semaphore(1);
  static Semaphore track5 = new Semaphore(1);
  static Semaphore track6 = new Semaphore(1);
  static Semaphore track7 = new Semaphore(1);
  static Semaphore track8 = new Semaphore(1);
  static Semaphore intersection = new Semaphore(1);
  public void run()
  { 
    try{
      TSimInterface tsi = TSimInterface.getInstance();
      boolean movingDown = false;
      boolean movingUp = false;
      if( tId == 1){ 
        movingDown = true;
        movingUp = false;
        track8.acquire();
      }

      if(tId == 2){ 
        movingDown = false;
        movingUp = true;
        track2.acquire();
        }
      
      while(true)
      {
        tsi.setSpeed(tId, tspeed);
        SensorEvent s = tsi.getSensor(tId);
        
        
        try
        {
          
        Boolean t_at_s1_r = (s.getXpos() == 14 && s.getYpos() == 13 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s1_l = (s.getXpos() ==  6 && s.getYpos() == 13 && s.getTrainId() == tId && s.getStatus() == 1); // Sensor close to junction at track 1
        Boolean t_at_s2_r = (s.getXpos() == 14 && s.getYpos() == 11 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s2_l = (s.getXpos() ==  6 && s.getYpos() == 11 && s.getTrainId() == tId && s.getStatus() == 1);//  Sensor close to junction at track 2
        Boolean t_at_s3   = (s.getXpos() ==  1 && s.getYpos() == 10 && s.getTrainId() == tId && s.getStatus() == 1);// Sensor at track 3
        Boolean t_at_s4_r = (s.getXpos() == 12 && s.getYpos() == 10 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s4_l = (s.getXpos() ==  7 && s.getYpos() == 10 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s5_r = (s.getXpos() == 12 && s.getYpos() == 9 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s5_l = (s.getXpos() ==  7 && s.getYpos() == 9 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s6   = (s.getXpos() == 19 && s.getYpos() == 8 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s7_r = (s.getXpos() == 15 && s.getYpos() == 8 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s7_l = (s.getXpos() == 10 && s.getYpos() == 8 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s7_t = (s.getXpos() == 14 && s.getYpos() == 5 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s7_tl = (s.getXpos() == 8 && s.getYpos() == 5 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s8_r = (s.getXpos() == 15 && s.getYpos() == 7 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s8_l = (s.getXpos() == 10 && s.getYpos() == 7 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s8_t = (s.getXpos() == 14 && s.getYpos() == 3 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s8_tl = (s.getXpos() == 6 && s.getYpos() == 6 && s.getTrainId() == tId && s.getStatus() == 1);


            
            // //TODO: sätt in nåt som funkar här (spår 4,5,7,8)
            // Boolean t_at_8 = ((s.getYpos() == 3 || s.getYpos() == 7) && s.getTrainId() == tId);
            // Boolean t_at_7 = ((s.getYpos() == 5 || s.getYpos() == 8) && s.getTrainId() == tId);
            // //Boolean t_at_5 = (s.getYpos() ==  9 && s.getTrainId() == tId);
            // //Boolean t_at_4 = (s.getYpos() == 10 && s.getTrainId() == tId);
            // Boolean t_at_2 = (s.getYpos() == 11 && s.getTrainId() == tId);
            // Boolean t_at_1 = (s.getYpos() == 13 && s.getTrainId() == tId);
        
            //logik (förhoppningsvis)
            if(t_at_s1_l && movingUp) {   // Spår 1
              track1.acquire(); // vi är på spår 1
              tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 3
              track3.acquire(); // vi ska till spår 3
              tsi.setSwitch(3,11, 0); // sätt switch till högerläge
              tsi.setSpeed(tId, tspeed); // kör
              s = tsi.getSensor(tId);
              track1.release(); // vi är inte längre på spår 1  
            }

            if(t_at_s2_l && movingUp) {   // Spår 2
              tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 3
              track3.acquire(); // vi ska till spår 3
              tsi.setSwitch(3,11,1); // sätt switch till vänster
              tsi.setSpeed(tId, tspeed); // kör
              s = tsi.getSensor(tId);
              track2.release(); // vi är inte längre på spår 2  
            }

            if(t_at_s3 && movingUp) {
              tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 5/4
              if(track5.tryAcquire(1)){
                tsi.setSwitch(4,9,1); // kör till vänster
                tsi.setSpeed(tId, tspeed); // kör
                s = tsi.getSensor(tId);
              } else if (track4.tryAcquire(1)){
                tsi.setSwitch(4,9,0); // kör till höger
                tsi.setSpeed(tId, tspeed); // kör
                s = tsi.getSensor(tId);
              }
              }

            if(t_at_s4_l && movingUp) {
              track3.release();
              s = tsi.getSensor(tId);
              tsi.setSpeed(tId, 0);
              track6.acquire();
              tsi.setSwitch(15,9,1); // sätt till vänster
              tsi.setSpeed(tId, tspeed); // kör
              s = tsi.getSensor(tId);
              track4.release();
            }
            
            if(t_at_s5_l && movingUp) {
              track3.release();
              s = tsi.getSensor(tId);
              tsi.setSpeed(tId, 0);
              track6.acquire();
              tsi.setSwitch(15,9,0); // sätt till höger
              tsi.setSpeed(tId, tspeed); // kör
              s = tsi.getSensor(tId);
              track5.release();
            }

            if(t_at_s6 && movingUp) {
              tsi.setSpeed(tId, 0);
              if(track7.tryAcquire(1)){
                tsi.setSwitch(17,7,1); // kör till vänster
                tsi.setSpeed(tId, tspeed); // kör
                s = tsi.getSensor(tId);
                track6.release();
                s = tsi.getSensor(tId); //vänta vid sensor innan korsningen
                tsi.setSpeed(tId, 0);
                intersection.acquire();
                tsi.setSpeed(tId, tspeed); // kör
                s = tsi.getSensor(tId); //släpp vid sensor efter korsningen
                intersection.release();
                }
              } 
              else if (track8.tryAcquire(1)){
                tsi.setSwitch(17,7,0); // kör till höger
                tsi.setSpeed(tId, tspeed);
                s = tsi.getSensor(tId); // kör
                track6.release();
                s=tsi.getSensor(tId); //vänta vid sensor innan korsningen
                tsi.setSpeed(tId, 0);
                intersection.acquire();
                tsi.setSpeed(tId, tspeed); // kör
                s = tsi.getSensor(tId); //släpp vid sensor efter korsningen
                intersection.release(); 
              }

            if(t_at_s8_t && movingUp){ // Stanna på spår 8 och vänder
              
              tsi.setSpeed(tId, 0);
              Thread.sleep(1500); // sleep
              tspeed = -tspeed;
              tsi.setSpeed(tId , (tspeed));
              movingDown = true;
              movingUp = false;
            }

            if(t_at_s7_t && movingUp){ // Stanna på spår 7 och   
              tsi.setSpeed(tId, 0);
              Thread.sleep(1500); // sleep
              tspeed = -tspeed;
              tsi.setSpeed(tId, (tspeed));
              movingDown = true;
              movingUp = false;

            }

            if(t_at_s1_r && movingDown){ // Stanna på spår 1 och vänder
              tsi.setSpeed(tId, 0);
              Thread.sleep(1500); // sleep
              tspeed = -tspeed;
              tsi.setSpeed(tId , tspeed);
              movingUp = true;
              movingDown = false;
              
            }

            if(t_at_s2_r && movingDown){ // Stanna på spår 2 och vänder
              tsi.setSpeed(tId, 0);
              Thread.sleep(1500); // sleep
              tspeed = -tspeed;
              tsi.setSpeed(tId , tspeed);
              movingUp = true;
              movingDown = false;
            }

            if(t_at_s8_tl && movingDown) {   // Spår 8
              tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till intersection
              intersection.acquire(tId);
              tsi.setSpeed(tId, tspeed); // kör
              s = tsi.getSensor(tId);
              intersection.release();
              s=tsi.getSensor(tId);
              tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 6
              track6.acquire(); // vi ska till spår 6
              tsi.setSwitch(17,7,0); // sätt switch till högerläge
              tsi.setSpeed(tId, tspeed); // kör
              s = tsi.getSensor(tId);
              track8.release(); // vi är inte längre på spår 8  
            }

            if(t_at_s7_tl && movingDown) {   // Spår 2
              tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till intersection
              intersection.acquire(tId);
              tsi.setSpeed(tId, tspeed); // kör
              s = tsi.getSensor(tId);
              intersection.release();
              s=tsi.getSensor(tId);
              tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 6
              track6.acquire(); // vi ska till spår 6
              tsi.setSwitch(17,7,1); // sätt switch till vänster
              tsi.setSpeed(tId, tspeed); // kör
              s = tsi.getSensor(tId);
              track7.release(); // vi är inte längre på spår 8 
            }
          


          if(t_at_s3 && movingDown) {
              tsi.setSpeed(tId, 0);
              if(track2.tryAcquire(1)){
                tsi.setSwitch(3,11,1); // kör till 2
                tsi.setSpeed(tId, tspeed); // kör
                s = tsi.getSensor(tId);
                track3.release();
              } else if (track1.tryAcquire(1)){
                tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 4
                tsi.setSwitch(3,11,0); // kör till 1
                tsi.setSpeed(tId, tspeed); // kör
                s = tsi.getSensor(tId);
                track3.release();
                track1.release();
              }
              }

          if(t_at_s4_r && movingDown) {
              track6.release();
              s = tsi.getSensor(tId);
              tsi.setSpeed(tId, 0);
              track3.acquire();
              tsi.setSwitch(4,9,0); // sätt till höger
              tsi.setSpeed(tId, tspeed); // kör
              s = tsi.getSensor(tId);
              track4.release();
              
            }
            
          if(t_at_s5_r && movingDown) {
            track6.release();
            
            tsi.setSpeed(tId, 0);
            track3.acquire();
            ;
            tsi.setSwitch(4,9,1); // sätt till vänster
            tsi.setSpeed(tId, tspeed); // kör
            s = tsi.getSensor(tId);
            track5.release();
            }
          
          if(t_at_s6 && movingDown) {
            tsi.setSpeed(tId, 0);
              if(track5.tryAcquire(1)){
                 // vänta tills vi får tillgång till spår 5
                tsi.setSwitch(15,9,0); // kör till höger
                tsi.setSpeed(tId, tspeed); // kör
                s = tsi.getSensor(tId);
              } else if (track4.tryAcquire(1)){
                 // vänta tills vi får tillgång till spår 4
                tsi.setSwitch(15,9,1); // kör till höger
                tsi.setSpeed(tId, tspeed); // kör
                s = tsi.getSensor(tId);
              }
              }

          

          
        }
          catch(Exception e){
          System.out.println(e.getMessage());
          }
      } 
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
}
}
