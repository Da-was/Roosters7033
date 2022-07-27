// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.motorcontrol.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  /**
   * 
   * Mapa de portas:
   * 
   * motores [0,1,2,3,4,5] pwm
   * encoder [1,2] Digital
   * fim de curso [3,4] Digital
   */
  private double startTime;

//chassi
   Victor victor1 = new Victor(1);
   Talon talon1 = new Talon(0);
   Victor victor2 = new Victor(2);
   Talon talon2 = new Talon(3);
  

   MotorControllerGroup m_left = new MotorControllerGroup(victor1, talon1);
   MotorControllerGroup m_right = new MotorControllerGroup(victor2, talon2);

   DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right); 
   double speed = 1.0;


//garraMovel
   Victor m_arm = new Victor(4);
   Victor m_arm1 = new Victor(5);




   Encoder encoder = new Encoder(2, 1,false, EncodingType.k1X);
   DigitalInput input = new DigitalInput(3);
   DigitalInput input1 = new DigitalInput(4);
   Joystick joy1 = new Joystick(0);

   public static String formatForDashboard(double speed1) {
    return String.format("%.2f", speed1);
}

   



  @Override
  public void robotInit(){
    
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
   startTime = Timer.getFPGATimestamp();
  }

  @Override
  public void autonomousPeriodic() {
    double time = Timer.getFPGATimestamp();

    if (time - startTime <3) {
      victor1.set(0.6);
      talon1.set(0.6);
      victor2.set(-0.6);
      talon2.set(-0.6);
    }
    
    else{
      victor1.set(0);
      talon1.set(0);
      victor2.set(0);
      talon2.set(0);
    }

  }

  @Override
  public void teleopInit() {
    
  }

  @Override
  public void teleopPeriodic() {
    SmartDashboard.putString("Velocidade", formatForDashboard(-joy1.getY()));
    SmartDashboard.putNumber("Modificador", speed);
    
    if(joy1.getRawButton(1)){
      speed = .6;

      SmartDashboard.putNumber("Modificador", speed);
    }else if(joy1.getRawButton(2)){
      speed = .8;


      SmartDashboard.putNumber("Modificador", speed);
    }else if(joy1.getRawButton(3)){
      speed = 1.0;
      SmartDashboard.putNumber("Modificador", speed);
    }
    
    m_drive.arcadeDrive(-joy1.getY()*speed, joy1.getRawAxis(4)*0.75);// TODO: arrumar o problema com o eixo Z;

//esteira
    if(joy1.getRawButton(5)){
      m_arm.set(0.7);
      SmartDashboard.putString("esteira", "pra fora ");
    }else if(joy1.getRawButton(6)){//rb
      SmartDashboard.putString("esteira", "pra dentro");
      m_arm.set(-0.7);
    }
     else if(!joy1.getRawButton(5) || !joy1.getRawButton(6))
     {
         m_arm.set(0.0);
     }
    
//subir/descer
    if(joy1.getRawButton(7)){
      m_arm1.set(0.7);
      SmartDashboard.putString("garra estado", "subindo");
    }else if(joy1.getRawButton(8)){//rb
      SmartDashboard.putString("garra estado", "descendo");
      m_arm1.set(-0.7);
    }

    else if(!joy1.getRawButton(7) || !joy1.getRawButton(8));
    {
      m_arm1.set(0.0);
    }

    
    
    m_arm.set(-0.0);
     if(input.get()){
      System.out.println("FOI");
    }else{
      System.out.println("NAO");   
      
    } 


    
    SmartDashboard.putNumber("Encoder", encoder.get());
    SmartDashboard.putBoolean("Fim de curso em cima ", input.get());
    SmartDashboard.putBoolean("Fim de curso em baixo", input1.get());
  
  
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
