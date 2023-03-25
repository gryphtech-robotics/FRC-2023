package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;

public class DriveWithJoystick extends CommandBase {
    private final DriveBase driveBase;
    private final DoubleSupplier throttle, x, y;
    
    public DriveWithJoystick(DriveBase driveBase, DoubleSupplier x, DoubleSupplier y, DoubleSupplier throttle) {
        this.driveBase = driveBase;
        this.throttle = throttle;
        this.x = x;
        this.y = y;

        addRequirements(driveBase);
    }

    @Override
    public void execute() {
        driveBase.setSpeed(this.x.getAsDouble(), this.y.getAsDouble(), this.throttle.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        driveBase.setSpeed(0, 0);
    }
}
