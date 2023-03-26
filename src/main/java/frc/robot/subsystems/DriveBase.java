package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants.CanIDs;

public class DriveBase extends SubsystemBase {
    private final CANSparkMax left0 = new CANSparkMax(CanIDs.LEFT_DRIVE_0, MotorType.kBrushless);
    private final CANSparkMax right0 = new CANSparkMax(CanIDs.RIGHT_DRIVE_0, MotorType.kBrushless);
    private final CANSparkMax left1 = new CANSparkMax(CanIDs.LEFT_DRIVE_1, MotorType.kBrushless);
    private final CANSparkMax right1 = new CANSparkMax(CanIDs.RIGHT_DRIVE_1, MotorType.kBrushless);
    
    private final RelativeEncoder left0Encoder = left0.getEncoder();
    private final RelativeEncoder right0Encoder = right0.getEncoder();
 
    public DriveBase() {
        left0.restoreFactoryDefaults();
        left1.restoreFactoryDefaults();
        right0.restoreFactoryDefaults();
        right1.restoreFactoryDefaults();

        right0.setInverted(true);

        zero();

        left1.follow(left0);
        right1.follow(right0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("DriveEncoderMean", getEncoderMean());
        SmartDashboard.putNumber("DriveAbsoluteEncoderMean", getEncoderMean(false));
    }

    /**
     * Control the x and y components of the drivetrain's motor speed, allowing for fine control.
     * <p> 
     * * Used only for joystick drive.
     * @param x X value. Usually supplied by joystick x axis.
     * @param y Y value. Usually supplied by joystick y axis.
     * @param throttle Throttle multiplier. Usually supplied by the joystick throttle.
     */
    public void setSpeed(double x, double y, double throttle) {
        left0.set((-x + y) * throttle);
        right0.set((-x - y) * throttle);
    }

    /**
     * Set the drivetrain's motor output to the given speed. Does not allow for fine control or varied speeds.
     * <p>
     * * Used for autonomous drive.
     * @param speed Speed value.
     * @param throttle Throttle multiplier.
     */
    public void setSpeed(double speed, double throttle) {
        left0.set(speed * throttle);
        right0.set(speed * throttle);
    }

    public void rotate(double speed, double throttle) {
        left0.set(speed * throttle);
        right0.set(-speed * throttle);
    }

    /**
     * Returns the mean value of the drivetrain encoders.
     * @return mean value. 
     */
    public double getEncoderMean() {
        return Math.abs(((left0Encoder.getPosition() + right0Encoder.getPosition()) / 2));
    }

    /**
     * Alternative method to return the mean value of the drivetrain encoders.
     * @param absolute {boolean} Whether to use absolute values or to favour the higher of the two encoder values.
     * @return mean value. 
     */
    public double getEncoderMean(boolean absolute) {
        if(absolute) {
            return Math.abs(((Math.abs(left0Encoder.getPosition()) + Math.abs(right0Encoder.getPosition())) / 2));
        } else {
            return Math.max(left0Encoder.getPosition(), right0Encoder.getPosition());
        }
    }

    /**
     * Zeros the left and right drive-lead encoders.
     */
    public void zero() {
        left0Encoder.setPosition(0);
        right0Encoder.setPosition(0);
        System.out.println("### Zeroed drivetrain lead encoders.");

    }
}
