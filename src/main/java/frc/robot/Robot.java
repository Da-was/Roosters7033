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
   * encoder [1,2, 5,6] Digital
   * fim de curso [3,4] Digital
   */
  //private double startTime;

//chassi
   Victor victor1 = new Victor(1);
   Talon talon1 = new Talon(0);
   Victor victor2 = new Victor(2);
   Talon talon2 = new Talon(3);
  

   MotorControllerGroup m_left = new MotorControllerGroup(victor1, talon1);
   MotorControllerGroup m_right = new MotorControllerGroup(victor2, talon2);
 
   DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right); 

//variaveis

/**
 * Variavel de controle da velocidade
 */
   double modificadorVelocidade = 1.0;
   Timer timer = new Timer();

//controle led
   ledControl lc = new ledControl(7,8,9);

//garraMovel
   Victor m_roller = new Victor(5);
   Victor m_arm1 = new Victor(4);



//sensores digitais
   Encoder encoder = new Encoder(2, 1,false, EncodingType.k1X);
   Encoder encoder2 = new Encoder(6, 5,false, EncodingType.k1X);
   DigitalInput fimDeCursoCima = new DigitalInput(3);


/**
 * joystick map:
 *  buttom 7- velocidade 0.6;
 *  buttom 2- velocidade 0.8;
 *  buttom 3- velocidade 1.0;
 *  buttom 5- velocidade 0.7 (pra fora);
 *  buttom 6- velocidade -0.7 (pra dentro);
 *  buttom 4- velocidade 0.7 (subindo);
 *  buttom 1- velocidade -0.7 (descendo);
 */
   Joystick joy1 = new Joystick(0);


/**
 * função para formatar uma double em duas casas decimais.
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
  }

  @Override
  public void autonomousPeriodic() {
    double currentTime = timer.get();

    if(currentTime < 3){
      m_roller.set(0.7);
    }else if(currentTime <7 ){
      m_roller.stopMotor();
      m_drive.arcadeDrive(-0.5,0.0);
    } else if(currentTime<9){
      m_drive.arcadeDrive(-0.5,0.0);
    }else if(currentTime<13){
      m_drive.arcadeDrive(0.0,-0.6);
    }else {
      m_drive.arcadeDrive(0.0, 0.0);
    }

    System.out.println(currentTime);

  }

  @Override
  public void teleopInit() {
    lc.run();
    
  }

/*
 * modificador de velocidade em 60%, 80% e 100% do motor.
 */
  @Override
  public void teleopPeriodic() {
    SmartDashboard.putString("Velocidade", formatForDashboard(-joy1.getY()));
    SmartDashboard.putNumber("Modificador", modificadorVelocidade);
    
    if(joy1.getRawButton(7)){
      modificadorVelocidade = .6;
      SmartDashboard.putNumber("Modificador", modificadorVelocidade);
    }else if(joy1.getRawButton(2)){
      modificadorVelocidade = .8;
      SmartDashboard.putNumber("Modificador", modificadorVelocidade);
    }else if(joy1.getRawButton(3)){
      modificadorVelocidade = 1.0;
      SmartDashboard.putNumber("Modificador", modificadorVelocidade);
    }
    
    m_drive.arcadeDrive(-joy1.getY()*modificadorVelocidade, joy1.getRawAxis(4)*0.75);

//esteira
    if(joy1.getRawButton(5)){
      m_roller.set(0.7);
      SmartDashboard.putString("esteira", "pra fora ");
    }else if(joy1.getRawButton(6)){//rb
      m_roller.set(-0.7);
      SmartDashboard.putString("esteira", "pra dentro");
    }else{
      m_roller.set(0.0);
    }
    
//subir/descer
    if(joy1.getRawButton(4) && fimDeCursoCima.get()){
      m_arm1.set(0.5);
      SmartDashboard.putString("Estado da garra", "Subindo");
    }
    else
    {
      m_arm1.set(0.0);
      SmartDashboard.putString("Estado da garra", "Idle");
    }



    SmartDashboard.putNumber("Encoder", encoder.get());
    SmartDashboard.putBoolean("Fim de curso superior ", fimDeCursoCima.get());
    
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
