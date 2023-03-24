package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.Arm;

public class ManualArmExtension extends CommandBase{

    private final Arm arm;
    private final double opCode;

    public ManualArmExtension(Arm arm, double opCode){
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
            arm.setExtensionSpeed(0.3);
        else if(opCode == 400)
            arm.setExtensionSpeed(-0.3);
        else 
            arm.setExtensionSpeed(0.0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        arm.setExtensionSpeed(0.0);
    }
}
