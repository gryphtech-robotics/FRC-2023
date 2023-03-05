package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import frc.robot.Constants.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.util.HandleSignal;

/**
 * Handles command button-assignment.
 */
public class RobotContainer {
    private Joystick DriveController = new Joystick(USB.DRIVER);
    private XboxController CopilotController = new XboxController(USB.COPILOT);

    private final DriveBase driveBase = new DriveBase();
    private final Arm arm = new Arm();
    private final Clamp clamp = new Clamp();

    public RobotContainer() {
        driveBase.setDefaultCommand(new DriveWithJoystick(driveBase, () -> (1 + (-DriveController.getRawAxis(Controls.JOYSTICK_X_AXIS))) / 2, () -> DriveController.getRawAxis(Controls.JOYSTICK_Y_AXIS), () -> DriveController.getRawAxis(Controls.JOYSTICK_THROTTLE)));
        //clamp.setDefaultCommand(new ClampMovement(clamp, () -> HandleSignal.ClampTrigger(CopilotController.leftTrigger(null).getAsBoolean(), CopilotController.rightTrigger(null).getAsBoolean())));
        //arm.setDefaultCommand(new ArmRotation(arm, () -> HandleSignal.ArmButton(DriveController.getRawButton(1), DriveController.getRawButton(4))));
    
        JoystickButton ClampOpen = new JoystickButton(CopilotController, Controls.CLAMP_OPEN);
        JoystickButton ClampClose = new JoystickButton(CopilotController, Controls.CLAMP_CLOSE);

        ClampOpen.onTrue(new InstantCommand(() -> {
            new ClampMovement(clamp, () -> 200);
        }));
        ClampClose.onTrue(new InstantCommand(() -> {
            new ClampMovement(clamp, () -> 400);
        }));

        JoystickButton ArmUp = new JoystickButton(CopilotController, Controls.ARM_UP);
        JoystickButton ArmDown = new JoystickButton(CopilotController, Controls.ARM_DOWN);

        ArmUp.onTrue(new InstantCommand(() -> {
            new ManualArmRotation(arm, () -> 200);
        }));
        ArmDown.onTrue(new InstantCommand(() -> {
            new ManualArmRotation(arm, () -> 400);
        }));

    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
            new InstantCommand(() -> new DriveForTime(driveBase, 0.4, 4)),
            new InstantCommand(() -> arm.setPos(30))
        );
    }
} 