package frc.robot.auto;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Clamp;
import frc.robot.subsystems.Arm.ArmRotate;
import frc.robot.Constants.PID;

/**
 * This autonomous routine is used to score a game piece on a lower or middle platform.
 */
public class Score extends SequentialCommandGroup {
    /**
     * @param arm {@link ArmRotate} subsystem
     * @param clamp {@link Clamp} subsystem
     */
    public Score (ArmRotate arm, Clamp clamp) {
        addCommands(
            new InstantCommand(() -> arm.setPos(PID.POS_L2), arm),
            new WaitUntilCommand(() -> arm.getRawPos() - PID.POS_L2 <= 7),
            new InstantCommand(() -> clamp.setSpeed(-0.33), clamp),
            new WaitUntilCommand(clamp::getLimit),
            new InstantCommand(() -> clamp.setSpeed(0.0), clamp),
            new InstantCommand(() -> arm.setPos(PID.POS_TOP), arm)
        );
    }
}
