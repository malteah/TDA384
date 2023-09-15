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
  Semaphore curretSemaphore;
  Semaphore pastSemaphore;

  //static Semaphore track1 = new Semaphore(1); // Creating one semaphore for each critical section
  static Semaphore track2 = new Semaphore(1);
  static Semaphore track3 = new Semaphore(1);
  //static Semaphore track4 = new Semaphore(1);
  static Semaphore track5 = new Semaphore(1);
  static Semaphore track6 = new Semaphore(1);
  static Semaphore track7 = new Semaphore(1);
  //static Semaphore track8 = new Semaphore(1);
  static Semaphore intersection = new Semaphore(1);

  public void run()
  { 
    try{
      TSimInterface tsi = TSimInterface.getInstance();
      boolean movingDown = false; // Direction variables  
      boolean movingUp = false;
      // initial setup
      if( tId == 1){  
        movingDown = true;
        movingUp = false;
        //track8.acquire();
      }

      if(tId == 2){ 
        movingDown = false;
        movingUp = true;
        track2.acquire();
        curretSemaphore = track2;
        }

      //Main logick loop
      while(true)
      {
        tsi.setSpeed(tId, tspeed);
        SensorEvent s = tsi.getSensor(tId);

        
        

          
        // One variable for every Sensor, True when it is the last passed sensor
        Boolean t_at_s1_r = (s.getXpos() == 14 && s.getYpos() == 13 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s1_l = (s.getXpos() ==  6 && s.getYpos() == 13 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s2_r = (s.getXpos() == 14 && s.getYpos() == 11 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s2_l = (s.getXpos() ==  6 && s.getYpos() == 11 && s.getTrainId() == tId && s.getStatus() == 1);
        Boolean t_at_s3   = (s.getXpos() ==  1 && s.getYpos() == 9 && s.getTrainId() == tId && s.getStatus() == 1);
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
        
        try
        {
          try { // Handeling relising semaphors
            if  (s.getStatus() == 1) // && !t_at_s3 && !t_at_s6
            {
              curretSemaphore.release();
              curretSemaphore = null;
            } 
          } catch (Exception e) {
                // TODO: handle exception
              }

        if(t_at_s1_l && movingUp) {    // Train at track1 and moving upwards
          tsi.setSpeed(tId, 0); 
          track3.acquire();   
          curretSemaphore = track3;         
          tsi.setSwitch(3,11, 0);  
          tsi.setSpeed(tId, tspeed);  
          //s = tsi.getSensor(tId);     
          //track1.release();          
        }

        if(t_at_s2_l && movingUp) {   // Train at track2 and moving upwards
          tsi.setSpeed(tId, 0); 
          track3.acquire(); 
          curretSemaphore = track3;
          tsi.setSwitch(3,11,1); 
          tsi.setSpeed(tId, tspeed); 
         // s = tsi.getSensor(tId);
          //track2.release(); 
        }

        if(t_at_s3 && movingUp) { // Train at track3 moving upwards
          tsi.setSpeed(tId, 0); 
          if(track5.tryAcquire(1)){ // Train preoratize track5 and try going there first
            curretSemaphore = track5;
            tsi.setSwitch(4,9,1); 
            tsi.setSpeed(tId, tspeed);
            //s = tsi.getSensor(tId);
          } else { // If track5 s ocupide then the train runns on track 4
            tsi.setSwitch(4,9,0); 
            tsi.setSpeed(tId, tspeed); 
            //s = tsi.getSensor(tId);
          }
        }

        // if(t_at_s4_l && movingUp) { // Once train is on track 4 relise the track3 semaphore
        //   track3.release();
        // }

        if(t_at_s4_r && movingUp){ // Train approaching end of track 4
          tsi.setSpeed(tId, 0);
          track6.acquire();
          curretSemaphore = track6;
          tsi.setSwitch(15,9,1);
          tsi.setSpeed(tId, tspeed); 
        }
        
        // if(t_at_s5_l && movingUp) { // Train at track5
        //   track3.release();
        // }

        if(t_at_s5_r && movingUp){ // Train approaching end of track 5
          tsi.setSpeed(tId, 0);
          track6.acquire();
          curretSemaphore = track6;
          tsi.setSwitch(15,9,0); 
          tsi.setSpeed(tId, tspeed); 
        }

        

        if(t_at_s6 && movingUp) { // Train at track6
          tsi.setSpeed(tId, 0);
          //TODO: kan inte släppa båda semaphores
          if(track7.tryAcquire(1)){ // Trying track 7 
            curretSemaphore = track7;
            tsi.setSwitch(17,7,1); 
            tsi.setSpeed(tId, tspeed); 
            // s = tsi.getSensor(tId);
            // track6.release();
            // s = tsi.getSensor(tId);
            // tsi.setSpeed(tId, 0);
            // intersection.acquire();
            // tsi.setSpeed(tId, tspeed); 
            // s = tsi.getSensor(tId); 
            // intersection.release();
          }
        
          else{ // If 7 is busy then try track 8
            tsi.setSwitch(17,7,0); 
            tsi.setSpeed(tId, tspeed);
            // s = tsi.getSensor(tId); 
            // track6.release();
            // s=tsi.getSensor(tId); 
            // tsi.setSpeed(tId, 0);
            // intersection.acquire();
            // tsi.setSpeed(tId, tspeed); 
            // s = tsi.getSensor(tId); 
            // intersection.release(); 
          }

          if(t_at_s7_r && movingUp){

            Thread.sleep(2000);
            track6.release();
          }

          if(t_at_s7_l && movingUp)
          {
            
            tsi.setSpeed(tId, 0);
            intersection.acquire();
            curretSemaphore = intersection;
            tsi.setSpeed(tId, tspeed);
          }

          if(t_at_s8_r && movingUp){
            track6.release();
          }

          if(t_at_s8_l && movingUp)
          {
            
            tsi.setSpeed(tId, 0);
            intersection.acquire();
            curretSemaphore = intersection;
            tsi.setSpeed(tId, tspeed);
          }

        } 
        if(t_at_s7_t && movingUp){ // At track 7, stops and then switch direction at end of track
          tsi.setSpeed(tId, 0);
          Thread.sleep(1500); 
          tspeed = -tspeed;
          tsi.setSpeed(tId, (tspeed));
          movingDown = true;
          movingUp = false;
        }

        if(t_at_s8_t && movingUp){  // At track 8, stops and then switch direction at end of track
          tsi.setSpeed(tId, 0);
          Thread.sleep(1500); 
          tspeed = tspeed * -1;
          tsi.setSpeed(tId , (tspeed));
          movingDown = true;
          movingUp = false;
        }

        if(t_at_s1_r && movingDown){ // At track 1, stops and then switch direction at end of track
          tsi.setSpeed(tId, 0);
          Thread.sleep(1500); 
          tspeed = -tspeed;
          tsi.setSpeed(tId , tspeed);
          movingUp = true;
          movingDown = false;
        }

        if(t_at_s2_r && movingDown){ // At track 2, stops and then switch direction at end of track
          tsi.setSpeed(tId, 0);
          Thread.sleep(1500); 
          tspeed = -tspeed;
          tsi.setSpeed(tId , tspeed);
          movingUp = true;
          movingDown = false;
        }

        if(t_at_s3 && movingDown) { // At Track3 downwards 
            tsi.setSpeed(tId, 0);
            if(track2.tryAcquire(1)){ // Trying track 2
              curretSemaphore = track2;
              tsi.setSwitch(3,11,1); 
              tsi.setSpeed(tId, tspeed); 
              // s = tsi.getSensor(tId);
              // s = tsi.getSensor(tId);
              // track3.release();
            } else { // If not track 1 then try track 1
              tsi.setSpeed(tId, 0); 
              tsi.setSwitch(3,11,0); 
              tsi.setSpeed(tId, tspeed); 
              // s = tsi.getSensor(tId);
              // s = tsi.getSensor(tId);
              // track3.release();
              
            }
        }

        if(t_at_s4_l && movingDown) {// At track 4 moving downwards
            // track6.release();
            // s = tsi.getSensor(tId);
            tsi.setSpeed(tId, 0);
            track3.acquire();
            curretSemaphore = track3;
            tsi.setSwitch(4,9,0); 
            tsi.setSpeed(tId, tspeed); 
            // s = tsi.getSensor(tId);
            // track4.release();
            
        }
          
        if(t_at_s5_l && movingDown) { // At track 5 moving downwards
          //track6.release();
          
          tsi.setSpeed(tId, 0);
          
          
          track3.acquire();
          curretSemaphore = track3;
          
          tsi.setSwitch(4,9,1); 
          tsi.setSpeed(tId, tspeed); 
          // s = tsi.getSensor(tId);
          // track5.release();
        }
        
        if(t_at_s6 && movingDown) { // At track 6 moving downwards
          tsi.setSpeed(tId, 0);
            if(track5.tryAcquire(1)){ // Trying for track 5
              curretSemaphore = track6; 
              tsi.setSwitch(15,9,0); 
              tsi.setSpeed(tId, tspeed); // kör             
            } else { // If not 5 then try 4
                
              tsi.setSwitch(15,9,1); 
              tsi.setSpeed(tId, tspeed); 
              // s = tsi.getSensor(tId);
            }
        }

        if(t_at_s7_tl && movingDown) {  // At track 7 moving downwards, watching out for the intersection
          tsi.setSpeed(tId, 0); 
          intersection.acquire();
          curretSemaphore = intersection;
          tsi.setSpeed(tId, tspeed); 
          // s = tsi.getSensor(tId);
          // intersection.release();
        }

        if(t_at_s7_r && movingDown) {  // At track 4 moving downwards
            tsi.setSpeed(tId, 0); 
            track6.acquire();
            curretSemaphore = track6;
            tsi.setSwitch(17,7,1); 
            // tsi.setSpeed(tId, tspeed);
            // s = tsi.getSensor(tId);
            // track7.release(); 
        }

        if(movingDown){
          tsi.setSpeed(tId, 0);
        }

        if(t_at_s8_tl && movingDown) {    // At track 8 moving downwards, watching out for the intersection
            tsi.setSpeed(tId, 0); 
            
            intersection.acquire();
            curretSemaphore = intersection;
            tsi.setSpeed(tId, tspeed); 
            // s = tsi.getSensor(tId);
            // intersection.release();
        }

        if(t_at_s8_r && movingDown){ // At track 8 moving downwards
            tsi.setSpeed(tId, 0); 
            track6.acquire(); 
            curretSemaphore = track6;
            tsi.setSwitch(17,7,0); 
            tsi.setSpeed(tId, tspeed); 
           
            // track8.release();  
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
