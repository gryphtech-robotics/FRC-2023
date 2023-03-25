package frc.robot;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import frc.robot.Constants.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * Handles command button-assignment.
 */
public class RobotContainer {
    private Joystick DriveController = new Joystick(USB.DRIVER);
    private CommandXboxController CopilotController = new CommandXboxController(USB.COPILOT);

    private final DriveBase driveBase = new DriveBase();
    private final Arm arm = new Arm();
    private final Clamp clamp = new Clamp();

    private final SendableChooser<String> m_chooser = new SendableChooser<>();

    public RobotContainer() {
        driveBase.setDefaultCommand(new DriveWithJoystick(driveBase, () -> DriveController.getRawAxis(Controls.JOYSTICK_X_AXIS), () -> DriveController.getRawAxis(Controls.JOYSTICK_Y_AXIS), () -> (1 + (-DriveController.getRawAxis(Controls.JOYSTICK_THROTTLE))) / 2));
        configureBindings();

        m_chooser.setDefaultOption("NO AUTO", "Nothing");
        m_chooser.addOption("SCORE", "Score");
        m_chooser.addOption("TAXI", "Taxi");
        SmartDashboard.putData(m_chooser);
    }

    /**
     * Configures driver and copilot bindings. 
     * ! Does not configure the driveBase defaults. That's done in robot container to prioritize it.
     */
    private void configureBindings() {
        CopilotController.leftBumper().whileTrue(new ClampMovement(clamp, 1));
        CopilotController.rightBumper().whileTrue(new ClampMovement(clamp, -1));
        
        CopilotController.leftTrigger().whileTrue(new InstantCommand(() -> clamp.setPos(PID.POS_C_OPEN), clamp));
        CopilotController.rightTrigger().whileTrue(new InstantCommand(() -> clamp.setPos(clamp.getRawPos()), clamp));

        CopilotController.y().whileTrue(new ManualArmRotation(arm, 1));
        CopilotController.a().whileTrue(new ManualArmRotation(arm, -1));

        CopilotController.b().whileTrue(new InstantCommand(() -> arm.setPos(arm.getRawPos()), arm));
        CopilotController.povUp().whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_TOP), arm));
        CopilotController.povDown().whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_BOTTOM), arm));
        CopilotController.povRight().whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_L2), arm));

        // CopilotController.start().whileTrue(new InstantCommand(() -> arm.setExtensionPos(encoderPositions.ARM_MIDDLE_TARGET)));
        // CopilotController.back().whileTrue(new InstantCommand(() -> arm.setExtensionPos(0)));

       // CopilotController.start().whileTrue(new ManualArmExtension(arm, 1));
       // CopilotController.back().whileTrue(new ManualArmExtension(arm, -1));
    }

    public Command getAutonomousCommand() {
        if(m_chooser.getSelected() == "Taxi") {
            return new DriveForTime(driveBase, -0.25, 60);
        } else if(m_chooser.getSelected() == "Score") {
            return new SequentialCommandGroup(
                new InstantCommand(() -> arm.setPos(PID.POS_L2), arm),
                new InstantCommand(() -> clamp.setPos(PID.POS_C_OPEN), clamp)//,
                //new InstantCommand(() -> clamp.setSpeed(0.0), clamp)//,
               // new InstantCommand(() -> arm.setPos(PID.POS_TOP), arm),
               // new DriveForTime(driveBase, -0.25, 6)
            );
        } else {
            return null;
        }
    }
} 