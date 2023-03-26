package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;

/**
 * Allows for the robot for drive for a set time period.
 * Based heavily off of Team 4152's DriveForTime command.
 * Only used in Autonomous.
 */
public class DriveForPeriod extends CommandBase {

    private final DriveBase driveBase;
    private final double speed;

    private double elapsed = 0;
    private double target = 0;

    /**
     * Please note that speed must be a fraction, because throttle is assumed to be a value of 1.
     * Period is a value of encoder units.
     */
    public DriveForPeriod(DriveBase driveBase, double speed, double period) {
        this.driveBase = driveBase;
        this.speed = speed;
        this.target = period;

        addRequirements(driveBase);
    }

    @Override
    public void initialize() {
        elapsed = driveBase.getEncoderMean(); 
    }

    @Override
    public void execute() {
        // System.out.println(elapsed);
        driveBase.setSpeed(speed, 1);
        elapsed = driveBase.getEncoderMean();
    }

    @Override
    public boolean isFinished() {
        return elapsed >= target;
    }

    @Override
    public void end(boolean interrupted) {
        driveBase.setSpeed(0, 0);
    }

}