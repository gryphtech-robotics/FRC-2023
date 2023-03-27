package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import frc.robot.subsystems.*;
import frc.robot.Constants.PID;
import frc.robot.commands.DriveForPeriod;
import frc.robot.commands.Rotate;

/**
 * This autonomous routine is used to score a game piece on a lower or middle platform, before reversing about 12 feet, completing a 180, and picking up another piece.
 */
public class ScoreTwo extends SequentialCommandGroup {
    /**
     * @param arm {@link Arm} subsystem
     * @param clamp {@link Clamp} subsystem
     * @param driveBase {@link DriveBase} subsystem
     */
    public ScoreTwo(Arm arm, Clamp clamp, DriveBase driveBase) {
        addCommands(
            new ScoreTaxi(arm, clamp, driveBase),
            new Rotate(driveBase, 0.3, 10),
            new DriveForPeriod(driveBase, -0.20, 70),
            new ParallelCommandGroup(
                new InstantCommand(() -> arm.setPos(PID.POS_BOTTOM), arm),
                new InstantCommand(() -> clamp.setSpeed(0.33), clamp)
                    .until(clamp::getLimit)
                    .andThen(() -> clamp.setSpeed(0.0), clamp)
            ),
            new InstantCommand(() -> arm.setPos(PID.POS_TOP), arm)
        );
    }
}
