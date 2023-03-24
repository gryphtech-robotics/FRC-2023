package frc.robot;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj.Joystick;
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

    public RobotContainer() {
        driveBase.setDefaultCommand(new DriveWithJoystick(driveBase, () -> DriveController.getRawAxis(Controls.JOYSTICK_X_AXIS), () -> DriveController.getRawAxis(Controls.JOYSTICK_Y_AXIS), () -> (1 + (-DriveController.getRawAxis(Controls.JOYSTICK_THROTTLE))) / 2));

        CopilotController.leftBumper().whileTrue(new ClampMovement(clamp, 200));
        CopilotController.rightBumper().whileTrue(new ClampMovement(clamp, 400));
        
        CopilotController.rightTrigger().whileTrue(new InstantCommand(() -> clamp.setPos(clamp.getRawPos()), clamp));
        CopilotController.leftTrigger().whileTrue(new InstantCommand(() -> clamp.setPos(PID.POS_C_OPEN), clamp)); 

        CopilotController.y().whileTrue(new ManualArmRotation(arm, 200));
        CopilotController.a().whileTrue(new ManualArmRotation(arm, 400));

        CopilotController.b().whileTrue(new InstantCommand(() -> arm.setPos(arm.getRawPos()), arm));
        CopilotController.povUp().whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_TOP), arm));
        CopilotController.povDown().whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_BOTTOM), arm));
        CopilotController.povRight().whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_L2), arm));

        // CopilotController.start().whileTrue(new InstantCommand(() -> arm.setExtensionPos(encoderPositions.ARM_MIDDLE_TARGET)));
        // CopilotController.back().whileTrue(new InstantCommand(() -> arm.setExtensionPos(0)));

        CopilotController.start().whileTrue(new ManualArmExtension(arm, 200));
        CopilotController.back().whileTrue(new ManualArmExtension(arm, 400));
    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
            new DriveForTime(driveBase, 0.25, 6),
            new InstantCommand(() -> arm.setPos(PID.POS_L2))//,
            //new InstantCommand(() -> clamp.setPos(PID.POS_C_OPEN))
        );
    }
} 