package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;

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
        return encoderMean - rotation <= 7;
    }

    @Override
    public void end(boolean interrupted) {
        driveBase.setSpeed(0, 0);
    }

}