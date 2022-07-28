// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
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

//variaveis
   double speed = 1.0;
   Timer timer = new Timer();

//controle led
   ledControl lc = new ledControl(8);

//garraMovel
   Victor m_roller = new Victor(5);
   Victor m_arm1 = new Victor(4);



//sensores digitais
   Encoder encoder = new Encoder(2, 1,false, EncodingType.k1X);
   DigitalInput input = new DigitalInput(3);
   DigitalInput input1 = new DigitalInput(4);


//joystick
   Joystick joy1 = new Joystick(0);


/*
 * classe para formatar uma double em duas casas decimais.
 * (string)
 * @return valor da double formatado em string
 */

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
    timer.start();
    startTime = timer.get();
    System.out.println("start:" + startTime);
  }

  @Override
  public void autonomousPeriodic() {
    double currentTime = timer.get();

    if (currentTime <3) {
      System.out.println(currentTime);
      System.out.println("menos de 3s");
      m_drive.arcadeDrive(0.7,0.0);
    }
    else if(currentTime<7){
      System.out.println(currentTime);
      System.out.println("menos de 7s");
      m_drive.arcadeDrive(0.0,0.6);
    }else if(currentTime<9){
      m_drive.arcadeDrive(0.0,0.0);
    }else if(currentTime<13){
      m_drive.arcadeDrive(0.0,-0.6);
    }else{
      m_drive.arcadeDrive(0.0,0.0);
    }

  }

  @Override
  public void teleopInit() {
    
  }

  @Override

//modificador   
  public void teleopPeriodic() {
    SmartDashboard.putString("Velocidade", formatForDashboard(-joy1.getY()));
    SmartDashboard.putNumber("Modificador", speed);
    
    if(joy1.getRawButton(1)){
      speed = .6;
      SmartDashboard.putNumber("Modificador", speed);
    }else if(joy1.getRawButton(2)){
      speed = .8;
      SmartDashboard.putNumber("Modificador", speed);
    }else if(joy1.getRawButton(2)){
      speed = .8;
      SmartDashboard.putNumber("Modificador", speed);
    }else if(joy1.getRawButton(3)){
      speed = 1.0;
      SmartDashboard.putNumber("Modificador", speed);
    }
    
    m_drive.arcadeDrive(-joy1.getY()*speed, joy1.getRawAxis(4)*0.75);

//esteira
    if(joy1.getRawButton(5)){
      m_roller.set(0.7);
      SmartDashboard.putString("esteira", "pra fora ");
    }else if(joy1.getRawButton(6)){//rb
      m_roller.set(-0.7);

      SmartDashboard.putString("esteira", "pra dentro");
    }
    else{
      m_roller.set(0.0);
    }


    
//subir/descer
    if(joy1.getRawButton(7) && input.get() && input1.get()){
      m_arm1.set(0.7);
      SmartDashboard.putString("garra estado", "subindo");
    }else if(joy1.getRawButton(8) && input.get() && input1.get()){//
      SmartDashboard.putString("garra estado", "descendo");
      m_arm1.set(-0.7);
    }


// sinal sensor fim de curso 
    if(!input.get() || !input1.get()){
      m_arm1.set(0.0);
    }





    SmartDashboard.putNumber("Encoder", encoder.get());
    SmartDashboard.putBoolean("top EOC ", input.get());
    SmartDashboard.putBoolean("bottom EOC", input1.get());
  
  
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
