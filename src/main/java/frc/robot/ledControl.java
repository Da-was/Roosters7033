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

   /**
    * Os parametros precisam ser em ordem Vermelho, Verde e Azul.
    * @param Red
    * @param Green
    * @param Blue
    */
   public ledControl(int Red, int Green, int Blue){
        this.Red = Red;
        this.Green = Green;
        this.Blue = Blue;

        this.redDI   = new DigitalOutput(this.Red);
        this.greenDI = new DigitalOutput(this.Green);
        this.BlueDI  = new DigitalOutput(this.Blue);

        this.settedPin = this.Red;
        this.timer.start();
   }

   public void run(){    
    
    if(this.timer.get() >= delay){
        if(this.settedPin < this.Red  || this.settedPin > this.Blue){
            this.settedPin = this.Red;
        }

        int pin =  this.settedPin;
        
         if(pin == this.Red){//7
            this.redDI.set(true);
            System.out.println("vermelho on");
            this.greenDI.set(false);
            System.out.println("verde off");
            this.BlueDI.set(false);
            System.out.println("azul off");
         }else if(pin == this.Green){//8
            this.redDI.set(false);
            System.out.println("vermelho off");
            this.greenDI.set(true);
            System.out.println("verde on");
            this.BlueDI.set(false);
            System.out.println("azul off");
         }else if(pin == this.Blue){//9
            this.redDI.set(false);
            System.out.println("vermelho off");
            this.greenDI.set(false);
            System.out.println("verde off");
            this.BlueDI.set(true);
            System.out.println("azul on");
         }
        
         this.settedPin++;
         this.timer.reset();
    }
   }
}
