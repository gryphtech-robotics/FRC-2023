package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;

/**
 * Allows for the robot for drive for a set time period.
 * Based heavily off of Team 4152's DriveForTime command.
 * Only used in Autonomous.
 */
public class DriveForTime extends CommandBase {

    private final DriveBase driveBase;
    private final double speed;

    private double time = 0;
    private double target = 0;

    /**
     * Please note that speed must be a fraction, because throttle is assumed to be a value of 1.
     */
    public DriveForTime(DriveBase driveBase, double speed, double seconds) {
        this.driveBase = driveBase;
        this.speed = speed;
        this.target = seconds;

        addRequirements(driveBase);
    }

    @Override
    public void initialize() {
        this.time = Timer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        driveBase.setSpeed(speed, 1);
    }

    @Override
    public boolean isFinished() {
        return time >= target;
    }

    @Override
    public void end(boolean interrupted) {
        driveBase.setSpeed(0, 0);
    }

}