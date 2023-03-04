package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Clamp;

public class ClampMovement extends CommandBase {
    private final Clamp clamp;
    private final DoubleSupplier commander;

    public ClampMovement(Clamp clamp, DoubleSupplier commander) {
        this.clamp = clamp;
        this.commander = commander;

        addRequirements(clamp);
    }

    /**
     * A 200 code signals the clamp to open, as long as the limit switch is not being pressed.
     * A 400 code signals the clamp to close.
     */
    @Override
    public void execute() {
        if(commander.getAsDouble() == 200) 
            if(checkValidity())
                clamp.setSpeed(0.1);
            else
                clamp.setSpeed(0.0);
        else if(commander.getAsDouble() == 400)
            clamp.setSpeed(-0.1);
        else
            clamp.setSpeed(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    /**
     * Checks the limit switch to see if it's being pressed, and if the command is to open then it will ignore it.
     * @return boolean
     */
    private boolean checkValidity() {
        if(clamp.getLimit() && commander.getAsDouble() == 400)
            return false;
        else 
            return true;
    }
}
