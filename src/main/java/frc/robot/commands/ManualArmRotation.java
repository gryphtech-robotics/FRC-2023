package frc.robot.commands;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Arm;

public class ManualArmRotation extends CommandBase {
    private final Arm arm;
    private final double opCode;

    public ManualArmRotation(Arm arm, double opCode) {
        this.arm = arm;
        this.opCode = opCode;

        addRequirements(arm);
    }

    /**
     * A 200 signal will cause the arm to rotate upwards.
     * A 400 signal will cause the arm to rotate downwards.
     */
    @Override
    public void execute() {
        if(opCode == 200)
            arm.setSpeed(0.2);
        else if(opCode == 400)
            arm.setSpeed(-0.2);
        else 
            arm.setSpeed(0.0);
    }

    @Override
    public void end(boolean isInterupted) {
        arm.setSpeed(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
