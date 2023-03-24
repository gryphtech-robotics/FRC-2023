package frc.robot.commands;

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
     * A 1 signal will cause the arm to rotate upwards.
     * A -1 signal will cause the arm to rotate downwards.
     */
    @Override
    public void execute() {
        if(opCode == 1)
            arm.setSpeed(0.25);
        else if(opCode == -1)
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
