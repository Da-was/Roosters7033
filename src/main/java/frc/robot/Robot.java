// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
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
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

   Victor victor1 = new Victor(1);
   Talon talon1 = new Talon(0);
   Victor victor2 = new Victor(2);
   Talon talon2 = new Talon(3);
  
  
   MotorControllerGroup m_left = new MotorControllerGroup(victor1, talon1);
   MotorControllerGroup m_right = new MotorControllerGroup(victor2, talon2);

   DifferentialDrive m_drive = new DifferentialDrive(m_left, m_right); 
   double speed = 1.0;


   

   Joystick joy1 = new Joystick(0);

public static float clamp(float val, float min, float max) {
  return Math.max(min, Math.min(max, val));
}

   private double startTime;

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
    SmartDashboard.putNumber("Velocidade", -joy1.getY());
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
    //double speed = joy1.getRawAxis(1)*clamp((float)joy1.getRawAxis(3), 0.1f, 1.0f);
    
    m_drive.arcadeDrive(-joy1.getY()*speed, joy1.getZ()*0.60);
    
    

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
