package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
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
    public ScoreTwo(Arm arm, Clamp clamp, DriveBase driveBase, double period) {
        addCommands(
            new ScoreTaxi(arm, clamp, driveBase, period),
            new InstantCommand(() -> driveBase.zero(), driveBase),
            new Rotate(driveBase, 0.3, 16.25),
            new InstantCommand(() -> arm.setPos(PID.POS_BOTTOM), arm),
            new InstantCommand(() -> clamp.setSpeed(-0.19), clamp),
            new WaitUntilCommand(() -> clamp.getLimit() && arm.getRawPos() - PID.POS_L2 <= 7),
            new InstantCommand(() -> clamp.setSpeed(0.0), clamp),
            new WaitCommand(0.45),
            new InstantCommand(() -> arm.setPos(PID.POS_TOP), arm),
            new InstantCommand(() -> driveBase.zero(), driveBase),
            new Rotate(driveBase, 0.3, 18),
            new DriveForPeriod(driveBase, 0.20, period),
            new InstantCommand(() -> driveBase.zero(), driveBase),
            new Score(arm, clamp)
        );
    }
}
