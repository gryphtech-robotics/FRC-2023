package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.util.LimelightHelper;

/**
 * For organization purposes, this is arranged with all xxxinit() methods at the top, and their respective xxxPeriodic() methods after.
 */
public class Robot extends TimedRobot {
    private Command autonomousCommand;
    private Command testCommand;
    private RobotContainer robotContainer;
    private Field2d field = new Field2d();;

    @Override
    public void robotInit() {
        robotContainer = new RobotContainer();
        field.setRobotPose(LimelightHelper.getBotPose2d("limelight"));

        System.out.println("### Robot initializing. ###");
    }

    @Override
    public void autonomousInit() {
        autonomousCommand = robotContainer.getAutonomousCommand();

        if (autonomousCommand != null)
            autonomousCommand.schedule();

        System.out.println("### Entering autonomous period. ###");
    }

    @Override
    public void teleopInit() {
        if (autonomousCommand != null)
            autonomousCommand.cancel();
        
        if (testCommand != null)
            testCommand.cancel();

        robotContainer.zeroEncoders(true, false, false);

        System.out.println("### Entering teleoperated period. ###");
    }

    @Override
    public void testInit() {
        DriverStation.silenceJoystickConnectionWarning(true);
        LiveWindow.setEnabled(false);
        CommandScheduler.getInstance().enable();
        
        testCommand = robotContainer.getTestCommand();
       
        if (testCommand != null)
            testCommand.schedule();

        System.out.println("### Entering testing period. ###");
    }

    @Override
    public void robotPeriodic() {
        field.setRobotPose(LimelightHelper.getBotPose2d("limelight"));
        CommandScheduler.getInstance().run();
        SmartDashboard.putData("Field", field);
    }

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopPeriodic() {}

    @Override
    public void testPeriodic() {}
}