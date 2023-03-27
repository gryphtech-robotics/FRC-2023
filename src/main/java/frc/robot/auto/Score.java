package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Clamp;
import frc.robot.Constants.PID;

/**
 * This autonomous routine is used to score a game piece on a lower or middle platform.
 */
public class Score extends SequentialCommandGroup {
    /**
     * @param arm {@link Arm} subsystem
     * @param clamp {@link Clamp} subsystem
     */
    public Score (Arm arm, Clamp clamp) {
        addCommands(
            new InstantCommand(() -> arm.setPos(PID.POS_L2), arm)
                .until(() -> arm.getRawPos() - PID.POS_L2 <= 7),
            new InstantCommand(() -> clamp.setSpeed(-0.33), clamp)
                .until(clamp::getLimit)
                .andThen(() -> clamp.setSpeed(0.0), clamp),
            new InstantCommand(() -> arm.setPos(PID.POS_TOP), arm)
        );
    }
}
