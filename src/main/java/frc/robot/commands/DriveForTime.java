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

    private int counter = 0;
    private int target = 0;

    /**
     * Please note that speed must be a fraction, because throttle is assumed to be a value of 1.
     */
    public DriveForTime(DriveBase driveBase, double speed, double seconds) {
        this.driveBase = driveBase;
        this.speed = speed;

        // Convert time in seconds to robot cycles (50 cycles/s)
        target = (int) (seconds * 50);

        addRequirements(driveBase);
    }

    @Override
    public void execute() {
        if (counter < target)
            counter++;

        driveBase.setSpeed(speed, 1);
    }

    @Override
    public boolean isFinished() {
        return counter >= target;
    }

    @Override
    public void end(boolean interrupted) {
        driveBase.setSpeed(0, 0, 0);
    }

}