package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ArmExtension;

public class ExtendArm extends CommandBase {
    private final ArmExtension armExtension;
    private final DoubleSupplier targetPos;

    public ExtendArm(ArmExtension armExtension, DoubleSupplier targetPos) {
        this.armExtension = armExtension;
        this.targetPos = targetPos;

        addRequirements(armExtension);
    }

    @Override
    public void execute() {
        armExtension.setPos(targetPos.getAsDouble()); 
    }
    
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        armExtension.setSpeed(0.0);
    } 
}
