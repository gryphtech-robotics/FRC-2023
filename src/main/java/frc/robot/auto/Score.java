package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Clamp;
import frc.robot.subsystems.ArmRotation;
import frc.robot.Constants.PID;

/**
 * This autonomous routine is used to score a game piece on a lower or middle platform.
 */
public class Score extends SequentialCommandGroup {
    /**
     * @param arm {@link ArmRotation} subsystem
     * @param clamp {@link Clamp} subsystem
     */
    public Score (ArmRotation armRotation, Clamp clamp) {
        addCommands(
            new InstantCommand(() -> armRotation.setPos(PID.POS_L2), armRotation),
            new WaitUntilCommand(() -> armRotation.getRawPos() - PID.POS_L2 <= 7),
            new InstantCommand(() -> clamp.setSpeed(-0.33), clamp),
            new WaitUntilCommand(clamp::getLimit),
            new InstantCommand(() -> clamp.setSpeed(0.0), clamp),
            new InstantCommand(() -> armRotation.setPos(PID.POS_TOP), armRotation)
        );
    }

    /**
     * @param arm {@link ArmRotation} subsystem
     * @param clamp {@link Clamp} subsystem
     * @param rotationTarget custom rotation PID target (for scoring at heights other than L2)
     */
    public Score (ArmRotation armRotation, Clamp clamp, double rotationTarget) {
        addCommands(
            new InstantCommand(() -> armRotation.setPos(rotationTarget), armRotation),
            new WaitUntilCommand(() -> armRotation.getRawPos() - rotationTarget <= 7),
            new InstantCommand(() -> clamp.setSpeed(-0.33), clamp),
            new WaitUntilCommand(clamp::getLimit),
            new InstantCommand(() -> clamp.setSpeed(0.0), clamp),
            new InstantCommand(() -> armRotation.setPos(PID.POS_TOP), armRotation)
        );
    }
}
