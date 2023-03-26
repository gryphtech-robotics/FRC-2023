package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;

/**
 * Allows for the robot for drive for a set time period.
 * Based heavily off of Team 4152's DriveForTime command.
 * Only used in Autonomous.
 */
public class Rotate extends CommandBase {

    private final DriveBase driveBase;
    private final double speed;
    private final double rotation;

    private double encoderMean;

    /**
     * Please note that speed must be a fraction, because throttle is assumed to be a value of 1.
     * Period is a value of encoder units.
     */
    public Rotate(DriveBase driveBase, double speed, double rotation) {
        this.driveBase = driveBase;
        this.speed = speed;
        this.rotation = rotation;

        addRequirements(driveBase);
    }

    @Override
    public void initialize() {
        encoderMean = driveBase.getEncoderMean(true); 
    }

    @Override
    public void execute() {
        System.out.println(encoderMean);
        driveBase.rotate(speed, 1);
        encoderMean = driveBase.getEncoderMean(true);
    }

    @Override
    public boolean isFinished() {
        return false;
        // return encoderMean - rotation <= 7;
    }

    @Override
    public void end(boolean interrupted) {
        driveBase.setSpeed(0, 0);
    }

}