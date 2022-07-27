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
   * fim de curso [0] Digital
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
   Timer timer = new Timer();

   ledControl lc = new ledControl(8);

//garraMovel
   Victor m_arm = new Victor(4);
   Victor m_arm1 = new Victor(5);

   MotorControllerGroup arm_group = new MotorControllerGroup(m_arm, m_arm1);


   Encoder encoder = new Encoder(2, 1,false, EncodingType.k1X);
   DigitalInput input = new DigitalInput(0);
   Joystick joy1 = new Joystick(0);

   public static String formatForDashboard(double speed1) {
    return String.format("%.2f", speed1);
}

//private final Relay m_relay = new Relay(0);
   

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
  public void teleopPeriodic() {
    SmartDashboard.putString("Velocidade", formatForDashboard(-joy1.getY()));
    SmartDashboard.putNumber("Modificador", speed);
    
    if(joy1.getRawButton(1)){
      speed = .6;
      //m_relay.set(Relay.Value.kOn);
      //m_relay.set(Relay.Value.kForward);
      SmartDashboard.putNumber("Modificador", speed);
    }else if(joy1.getRawButton(2)){
      speed = .8;
      //m_relay.set(Relay.Value.kOff);
      SmartDashboard.putNumber("Modificador", speed);
    }else if(joy1.getRawButton(3)){
      speed = 1.0;
      SmartDashboard.putNumber("Modificador", speed);
    }
    
    m_drive.arcadeDrive(-joy1.getY()*speed, joy1.getRawAxis(4)*0.75);// TODO: arrumar o problema com o eixo Z;


    if(joy1.getRawButton(5)){
      arm_group.set(0.7);
      SmartDashboard.putString("garra estado", "subindo");
    }else if(joy1.getRawButton(6)){//rb
      SmartDashboard.putString("garra estado", "descendo");
      arm_group.set(-0.7);
    }
    
    arm_group.set(-0.0);
    /* if(input.get()){
      System.out.println("FOI");
    }else{
      System.out.println("NAO");   
      
    } */


    
    SmartDashboard.putNumber("Encoder", encoder.get());
    SmartDashboard.putBoolean("Fim de curso", input.get());
  
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
