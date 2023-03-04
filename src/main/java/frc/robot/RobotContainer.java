package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import frc.robot.util.HandleSignal;

/**
 * Handles command button-assignment.
 */
public class RobotContainer {
    private Joystick DriveController = new Joystick(0);
    private XboxController CopilotController = new XboxController(1);

    private final DriveBase driveBase = new DriveBase();
    private final Arm arm = new Arm();
    private final Clamp clamp = new Clamp();

    public RobotContainer() {
        driveBase.setDefaultCommand(new DriveWithJoystick(driveBase, () -> (1 + (-DriveController.getRawAxis(3))) / 2, () -> DriveController.getRawAxis(0), () -> DriveController.getRawAxis(1)));
        clamp.setDefaultCommand(new ClampMovement(clamp, () -> HandleSignal.ClampTrigger(CopilotController.leftTrigger(null).getAsBoolean(), CopilotController.rightTrigger(null).getAsBoolean())));
        arm.setDefaultCommand(new ArmRotation(arm, () -> HandleSignal.ArmButton(DriveController.getRawButton(1), DriveController.getRawButton(4))));
    }

    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(
            new InstantCommand(() -> new DriveForTime(driveBase, 0.4, 4))
        );
        
        /* 
                new InstantCommand(() -> shooter.openBallGate()),
                new ParallelCommandGroup(
                        new TurnTurretForTime(turret, 0.22, 4),
                        new DriveForTime(driveBase, 0.6, 4)),
                new AlignTurret(turret, limelight, true),
                new ShooterRev(shooter, limelight),
                new TimedIntake(intake, 1.0, 0.22, TimedIntake.IntakeMode.INTERNAL),
                new ShooterRev(shooter, limelight),
                new TimedIntake(intake, 1.0, 0.15, TimedIntake.IntakeMode.INTERNAL),
                new ShooterRev(shooter, limelight),
                new TimedIntake(intake, 1.0, 0.15, TimedIntake.IntakeMode.INTERNAL),
                new InstantCommand(() -> intake.setRaised(false)));*/
    }
} 