package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Arm.ArmExtension;



public class ExtendArm extends CommandBase {
    private final ArmExtension armExtension;
    private double targetPos;
    private final int direction;

    public ExtendArm(ArmExtension armExtension, double targetPos, int direction) {
        this.armExtension = armExtension;
        this.targetPos = targetPos;
        this.direction = direction;

        ArmExtension.zero();

        addRequirements(armExtension);
    }


    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if(direction == 1){
            targetPos ++;
            armExtension.setPos(targetPos);
    
        } else if(direction == -1)
            targetPos--;
            armExtension.setPos(targetPos);

       
            
    }
    

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }
    
}
