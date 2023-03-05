package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.CanIDs;

public class DriveBase extends SubsystemBase {
    private final CANSparkMax left0 = new CANSparkMax(CanIDs.LEFT_DRIVE_0, MotorType.kBrushless);
    private final CANSparkMax right0 = new CANSparkMax(CanIDs.RIGHT_DRIVE_0, MotorType.kBrushless);
    private final CANSparkMax left1 = new CANSparkMax(CanIDs.LEFT_DRIVE_1, MotorType.kBrushless);
    private final CANSparkMax right1 = new CANSparkMax(CanIDs.RIGHT_DRIVE_1, MotorType.kBrushless);

    //private final DifferentialDrive drive = new DifferentialDrive(left0, right0);

    public DriveBase() {
        left0.restoreFactoryDefaults();
        left1.restoreFactoryDefaults();
        right0.restoreFactoryDefaults();
        right1.restoreFactoryDefaults();

        right0.setInverted(true);

        left1.follow(left0);
        right1.follow(right0);
    }

    /**
     * Set the drivetrain's motor speed, allowing for fine control. 
     * @param x X value. Usually supplied by joystick x axis.
     * @param y Y value. Usually supplied by joystick y axis.
     * @param throttle Throttle multiplier. Usually supplied by the joystick throttle.
     */
    public void setSpeed(double x, double y, double throttle) {
        left0.set((x + y) * throttle);
        right0.set((x - y) * throttle);
    }

    /**
     * Set the drivetrain's motor output to the given speed.
     * This is a variation of setSpeed used in autonomous.
     * @param speed Speed value.
     * @param throttle Throttle multiplier.
     */
    public void setSpeed(double speed, double throttle) {
        left0.set(speed * throttle);
        right0.set(speed * throttle);
    }
    /**
     * Another way to do this, using the built in WPILib solution.
     * @param throttle throttle value
     * @param rotation z-axis rotation value
     * @param squareInputs boolean square inputs, defaults to false
     */
    public void setSpeed(double throttle, double rotation, boolean squareInputs) {
        rotation *= -1;

        //drive.arcadeDrive(throttle, rotation, squareInputs);
    }
}
