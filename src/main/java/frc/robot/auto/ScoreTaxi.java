package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.commands.DriveForPeriod;

/**
 * This autonomous routine is used to score a game piece on a lower or middle platform, before reversing about 12 feet.
 */
public class ScoreTaxi extends SequentialCommandGroup {
    /**
     * ScoreTaxi with default score height.
     * @param arm       {@link ArmRotation} subsystem
     * @param clamp     {@link Clamp} subsystem
     * @param driveBase {@link DriveBase} subsystem
     * @param period    encoder period to drive for 
     */
    public ScoreTaxi(ArmRotation armRotation, Clamp clamp, DriveBase driveBase, double period) {
        addCommands(
            new Score(armRotation, clamp),
            new DriveForPeriod(driveBase, -0.20, period)
        );
    }

    /**
     * ScoreTaxi with default score height.
     * @param arm       {@link ArmRotation} subsystem
     * @param clamp     {@link Clamp} subsystem
     * @param driveBase {@link DriveBase} subsystem
     * @param period encder period to drive for
     * @param rotationTarget the rotation PID target (for scoring in places other than L2)
     */
    public ScoreTaxi(ArmRotation armRotation, Clamp clamp, DriveBase driveBase, double period, double rotationTarget) {
        addCommands(
            new Score(armRotation, clamp, rotationTarget),
            new DriveForPeriod(driveBase, -0.20, period)
        );
    }
}
