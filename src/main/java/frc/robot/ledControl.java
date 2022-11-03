package frc.robot;


import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;

/**
 * Essa classe faz o controle dos leds kkkkkkkkkk
 */
public class ledControl implements Runnable {
    private int Red, Green,Blue;
    private DigitalOutput redDI,greenDI, BlueDI;
    private double delay = 5.0;
    private Timer timer = new Timer();
    private int settedPin;
    private String[] cores = {"red","blue","green"};
    private boolean blink = true;
    

    public ledControl(int Red, int Green, int Blue){
        this.Red = Red;
        this.Green = Green;
        this.Blue = Blue;

        this.redDI   = new DigitalOutput(this.Red);
        this.greenDI = new DigitalOutput(this.Green);
        this.BlueDI  = new DigitalOutput(this.Blue);
   }

   public void setDelay(float delay) {
       this.delay = delay;
   }
  
   public int[] getPins(){
       int[] arrayPinos = {this.Red,this.Green,this.Blue};
       return arrayPinos;
   }

   public void startLed(){
    this.timer.start();
    System.out.println("running");
    this.settedPin = 0;

   }

   public void run(){
    if(this.timer.get() >= this.delay){
        System.out.println(this.cores[this.settedPin]);
        if(this.BlueDI.get() == false){
            this.BlueDI.set(true);
        }else{
            this.BlueDI.set(false);
        }
        System.out.println(this.BlueDI.get());
        this.blink = !this.blink;
        this.settedPin++;
        if(this.settedPin >2){
            this.settedPin = 0;
        }
        timer.reset();
    }
   
    
   }
}
