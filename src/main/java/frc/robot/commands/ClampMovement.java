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
     * A 1 code signals the clamp to open, as long as the limit switch is not being pressed.
     * A -1 code signals the clamp to close.
     */
    @Override
    public void execute() {
        /**
         * ? This is insanely stupid and weirdly bugged.
         * 
         * ? Something with the logic causes it to go about 25% of the speed that you actually supply.
         * ? Without this logic (the clamp.getLimit() check), the clamp will go **much** faster, so the values need to be tweaked.
         * ? For this competition it's better to just leave it as it, because to be honest I have no clue why an if statement causes this behavior.
         */
        if(opCode == 1) {
            if(!clamp.getLimit())
                clamp.setSpeed(-1);
            else 
                clamp.setSpeed(0.0);
                clamp.setPos(Constants.PID.POS_C_OPEN);
        } else if(opCode == -1)
            clamp.setSpeed(0.25);
        else
            clamp.setSpeed(0.0);
    }

    @Override
    public boolean isFinished() {
        if(opCode == 1)
            return clamp.getLimit();
        else
            return false;
    }

    @Override
    public void end(boolean interrupted) {
        clamp.setSpeed(0.0);
    }
}
