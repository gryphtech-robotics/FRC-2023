package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Arm;

public class ManualArmRotation extends CommandBase {
    private final Arm arm;
    private final DoubleSupplier commander;

    public ManualArmRotation(Arm arm, DoubleSupplier commander) {
        this.arm = arm;
        this.commander = commander;
    }

    /**
     * A 200 signal will cause the arm to rotate upwards.
     * A 400 signal will cause the arm to rotate downwards.
     */
    @Override
    public void execute() {
        if(commander.getAsDouble() == 200)
            arm.setSpeed(0.2);
        else if(commander.getAsDouble() == 400)
            arm.setSpeed(-0.2);
        else 
            arm.setSpeed(0.0);
    }

    private void executeWithLimits() {
        if (commander.getAsDouble() == 200)
            if (arm.getPos() <= -10) {
                arm.setSpeed(0.2);
                handlePosition(1);
            } else
                arm.setSpeed(0.0);
        else if (commander.getAsDouble() == 400)
            if (arm.getPos() >= 110) {
                arm.setSpeed(-0.2);
                handlePosition(-1);
            } else
                arm.setSpeed(0.0);
        else
            arm.setSpeed(0.0);
    }

    /**
     * Handles the relative position tracking on this side.
     * @param pos Pass through negative or positive one signalling direction. 
     */
    private void handlePosition(int pos) {
        arm.setEncoderPosValue(pos, arm.getPos(), arm.getRawPos());
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
