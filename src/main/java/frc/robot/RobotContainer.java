package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.*;
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
        driveBase.setDefaultCommand(new DriveWithJoystick(driveBase, () -> (1 + (-DriveController.getRawAxis(Controls.JOYSTICK_X_AXIS))) / 2, () -> DriveController.getRawAxis(Controls.JOYSTICK_Y_AXIS), () -> DriveController.getRawAxis(Controls.JOYSTICK_THROTTLE)));

        CopilotController.leftBumper().onTrue(new InstantCommand(() -> new ClampMovement(clamp, () -> 200)));
        CopilotController.rightBumper().onTrue(new InstantCommand(() -> new ClampMovement(clamp, () -> 400)));

        CopilotController.y().onTrue(new InstantCommand(() -> new ManualArmRotation(arm, () -> 200)));
        CopilotController.a().onTrue(new InstantCommand(() -> new ManualArmRotation(arm, () -> 200)));
    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
            new InstantCommand(() -> new DriveForTime(driveBase, 0.4, 4))
            //new InstantCommand(() -> arm.setPos(30))
        );
    }
} 