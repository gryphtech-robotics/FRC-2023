package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ManualArmRotation extends CommandBase {
    private final Arm arm;
    private final double direction;

    public ManualArmRotation(Arm arm, double direction) {
        this.arm = arm;
        this.direction = direction;

        addRequirements(arm);
    }

    /**
     * A direction value of 1 will cause the arm to rotate upwards.
     * A direction value of -1  signal will cause the arm to rotate downwards.
     */
    @Override
    public void execute() {
        if(direction == 1)
            arm.setSpeed(0.25);
        else if(direction == -1)
            arm.setSpeed(-0.25);
        else 
            arm.setSpeed(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        arm.setSpeed(0.0);
    }
}
