package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;

/**
 * Essa classe faz o controle dos leds kkkkkkkkkk
 */
public class ledControl implements Runnable {
    private int Red, Green,Blue;
    private DigitalOutput redDI,greenDI, BlueDI;
    private float delay = 2.0f;
    private Timer timer = new Timer();
    private int settedPin;

   public void setDelay(float delay) {
       this.delay = delay;
   }
   public int[] getPins(){
       int[] arrayPinos = {this.Red,this.Green,this.Blue};
       return arrayPinos;
   }

   public ledControl(int Red, int Green, int Blue){
        this.Red = Red;
        this.Green = Green;
        this.Blue = Blue;

        this.redDI   = new DigitalOutput(this.Red);
        this.greenDI = new DigitalOutput(this.Blue);
        this.BlueDI  = new DigitalOutput(this.Green);
   }

   public void run(){
    int pin =  this.settedPin;
    if(pin == 0 || pin == this.Blue){
        pin = this.Red;
    }
    timer.start();
    if(timer.get() == delay){

         if(this.Red == pin){
            this.redDI.set(true);
            this.greenDI.set(false);
            this.BlueDI.set(false);
         }else if(this.Green == pin){
            this.redDI.set(false);
            this.greenDI.set(true);
            this.BlueDI.set(false);
         }else if(this.Blue == pin){
            this.redDI.set(false);
            this.greenDI.set(false);
            this.BlueDI.set(true);
         }
        
    }
    this.settedPin++;

    timer.reset();
   }
}
