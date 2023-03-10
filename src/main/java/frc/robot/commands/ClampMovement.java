package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Clamp;

public class ClampMovement extends CommandBase {
    private final Clamp clamp;
    private final double opCode;

    public ClampMovement(Clamp clamp, double opCode) {
        this.clamp = clamp;
        this.opCode = opCode;

        addRequirements(clamp);
    }

    /**
     * A 200 code signals the clamp to open, as long as the limit switch is not being pressed.
     * A 400 code signals the clamp to close.
     */
    @Override
    public void execute() {
        if(opCode == 200) {
            if(!clamp.getLimit())
                clamp.setSpeed(-0.15);
            else
                clamp.setSpeed(0.0);
                clamp.setPos(Constants.PID.POS_C_OPEN);
        } else if(opCode == 400)
            clamp.setSpeed(0.15);
        else
            clamp.setSpeed(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        clamp.setSpeed(0.0);
    }
}
