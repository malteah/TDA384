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
      //TODO: lägg in koordinater för alla sensorer, (tror inte de här är rätt) 
      Boolean t_at_s1 = (s.getXpos() ==5 && s.getYpos() == 9);
      Boolean t_at_s2 = (s.getXpos() ==13 && s.getYpos() == 9);
      Boolean t_at_s3 = (s.getXpos() ==19 && s.getYpos() == 8);
      Boolean t_at_s4 = (s.getXpos() ==13 && s.getYpos() == 7);
      
      //TODO: sätt in nåt som funkar här (spår 4,5,7,8)
      Boolean t_at_8 = true;
      Boolean t_at_7 = true;
      Boolean t_at_5 = true;
      Boolean t_at_4 = true;
      

      //TODO: sätt in nåt som funkar här
      Boolean movingUp = true; //(sensor 1 && 2 spår 1) ELLER (sensor 1 && 2 spår 2)
      Boolean movingDown = false; //motsvarande fast spår 7,8

      //logik (förhoppningsvis)
      if(t_at_s1 && movingUp) {
        track1.acquire(); // vi är på spår 1
        tsi.setSpeed(tId, 0); // vänta tills vi får tillgång till spår 3
        track3.acquire(); // vi är på spår 3
        tsi.setSpeed(tId, tspeed); // kör
        track1.release(); // vi är inte längre på spår 1
        if(t_at_5){
          
        }
        


      }


    }
    
    
  }
  catch(Exception e){
    System.out.println(e);
  }
  }
}

      