package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm;

public class ManualArmExtension extends CommandBase{
    private final Arm arm;
    private final double direction;

    public ManualArmExtension(Arm arm, double direction){
        this.arm = arm;
        this.direction = direction;

        addRequirements(arm);
    }
    
    /**
     * A direction value of 1 will cause the arm to rotate upwards.
     * A direction value of -1 will cause the arm to rotate downwards.
     */
    // @Override
    // public void execute() {
    //     if(direction == 1)
    //         arm.setExtensionSpeed(0.3);
    //     else if(direction == -1)
    //         arm.setExtensionSpeed(-0.3);
    //     else 
    //         arm.setExtensionSpeed(0.0);
    // }

    @Override
    public boolean isFinished() {
        return false;
    }

    // @Override
    // public void end(boolean interrupted) {
    //     arm.setExtensionSpeed(0.0);
    // }
}
