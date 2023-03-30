package frc.robot;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;


import frc.robot.Constants.*;
import frc.robot.auto.Score;
import frc.robot.auto.ScoreTaxi;
import frc.robot.auto.ScoreTwo;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * Handles command button assignment and scheduling.
 */
public class RobotContainer {
    private CommandJoystick DriveController = new CommandJoystick(USB.DRIVER);
    private CommandXboxController CopilotController = new CommandXboxController(USB.COPILOT);

    private final DriveBase driveBase = new DriveBase();
    private final Arm arm = new Arm();
    private final Clamp clamp = new Clamp();

    private final SendableChooser<String> m_chooser = new SendableChooser<>();

    public RobotContainer() {
        driveBase.setDefaultCommand(new DriveWithJoystick(driveBase, () -> DriveController.getX(), () -> DriveController.getY(), () -> (1 + (-DriveController.getThrottle())) / 2));
        configureBindings();

        m_chooser.setDefaultOption("NO AUTO", "Nothing");
        m_chooser.addOption("SCORE", "Score");
        m_chooser.addOption("SCORE TWO", "ScoreTwo");
        m_chooser.addOption("SCORE & TAXI", "ScoreTaxi");
        m_chooser.addOption("TAXI", "Taxi");
        SmartDashboard.putData(m_chooser);

        CameraServer.startAutomaticCapture();

    }

    /**
     * Configures driver and copilot command bindings. 
     * <p>
     * * This does not configure the driveBase defaults. That's done in {@link #RobotContainer()} ensure prioritization.
     */
    private void configureBindings() {
        CopilotController.leftBumper().whileTrue(new ClampMovement(clamp, 1));
        CopilotController.rightBumper().whileTrue(new ClampMovement(clamp, -1));


        //copiolet controlls on drive joystick
        DriveController.button(5).whileTrue(new ClampMovement(clamp, 1));
        DriveController.button(6).whileTrue(new ClampMovement(clamp, -1));
        
        CopilotController.leftTrigger().whileTrue(new InstantCommand(() -> clamp.setPos(PID.POS_C_OPEN), clamp));
        CopilotController.rightTrigger().whileTrue(new InstantCommand(() -> clamp.setPos(clamp.getRawPos()), clamp));

        CopilotController.y().whileTrue(new ManualArmRotation(arm, 1));
        CopilotController.a().whileTrue(new ManualArmRotation(arm, -1));

        //copiolet controlls on drive joystick
        DriveController.button(3).whileTrue(new ManualArmRotation(arm, 1));
        DriveController.button(4).whileTrue(new ManualArmRotation(arm, -1));

        CopilotController.b().whileTrue(new InstantCommand(() -> arm.setPos(arm.getRawPos()), arm));
        CopilotController.povUp().whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_TOP), arm));
        CopilotController.povDown().whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_BOTTOM), arm));
        CopilotController.povRight().whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_L2), arm));


        //copiolet controlls on drive joystick
        DriveController.button(8).whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_TOP), arm));
        DriveController.button(2).whileTrue(new InstantCommand(() -> arm.setPos(arm.getRawPos()), arm));
        DriveController.button(12).whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_BOTTOM), arm));
        DriveController.button(10).whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_L2), arm));

        CopilotController.start().whileTrue(new Rotate(driveBase, 0.1));
        // CopilotController.start().whileTrue(new ManualArmExtension(arm, 1));
        // CopilotController.back().whileTrue(new ManualArmExtension(arm, -1));
    }

    /**
     * Returns a command routine to be run during autonomous mode.
     * Routines are defined in {@link frc.robot.auto}.
     */
    public Command getAutonomousCommand() {
        switch(m_chooser.getSelected()) {
            case "Score":
                return new Score(arm, clamp);
            case "ScoreTwo":
                return new ScoreTwo(arm, clamp, driveBase, 30);
            case "ScoreTaxi":
                return new ScoreTaxi(arm, clamp, driveBase, 70);
            case "Taxi":
                return new DriveForPeriod(driveBase, -0.25, 70);
            default:
                return null;
        }
    }

    /**
     * Returns a command sequence to be run during test mode.
     */
    public Command getTestCommand() {
        return new Rotate(driveBase, 0.2, 32.5);
    }

    /**
     * Zero the selected encoders and reference values.
     * @param driveBase Whether to zero the initialized {@link DriveBase} encoders.
     * @param arm Whether to zero the initialized {@link Arm} encoders.
     * @param clamp Whether to zero the initialized {@link Clamp} encoder.
     */
    public void zeroEncoders(boolean driveBase, boolean arm, boolean clamp) {
        if(driveBase)
            this.driveBase.zero();
        
        if(arm)
            this.arm.zero();

        if(clamp)
            this.clamp.zero();
    }
} 