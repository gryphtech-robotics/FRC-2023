package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.subsystems.ArmRotation;
import frc.robot.commands.DriveForPeriod;

/**
 * This autonomous routine is used to score a game piece on a lower or middle platform, before reversing about 12 feet.
 */
public class ScoreTaxi extends SequentialCommandGroup {
    /**
     * @param arm       {@link ArmRotation} subsystem
     * @param clamp     {@link Clamp} subsystem
     * @param driveBase {@link DriveBase} subsystem
     */
    public ScoreTaxi(ArmRotation armRotation, Clamp clamp, DriveBase driveBase, double period) {
        addCommands(
            new Score(armRotation, clamp),
            new DriveForPeriod(driveBase, -0.20, period)
        );
  }
}
