package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
<<<<<<< Updated upstream
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Systems.Drive;
import frc.robot.Systems.Arm;
import frc.robot.Systems.Clamp;
=======
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
>>>>>>> Stashed changes

public class Robot extends TimedRobot {
    private Command autonomousCommand;

    private RobotContainer robotContainer;

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();

        System.out.println("Robot initializing");
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = robotContainer.getAutonomousCommand();

<<<<<<< Updated upstream
  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = "peaky balls";//m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
          Arm.toBottom();
          Clamp.allOut();
          int i = 0;

          Drive.drive(0.3);

          Timer.delay(3);
          Drive.stop();

        break;
=======
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }

        System.out.println("Entering autonomous period.");
>>>>>>> Stashed changes
    }

<<<<<<< Updated upstream
  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    Drive.init();
    OI.init();
    Arm.init();
    Clamp.init();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    OI.periodic();
    //Arm.periodic();
  }
=======
    @Override
    public void teleopInit() {
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }

        System.out.println("Entering teleop period.");
    }
>>>>>>> Stashed changes

    @Override
    public void testInit() {
        CommandScheduler.getInstance().cancelAll();

        System.out.println("Entering testing mode.");
    }
}