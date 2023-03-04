package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveBase extends SubsystemBase {
    private final CANSparkMax left0 = new CANSparkMax(40, MotorType.kBrushless);
    private final CANSparkMax right0 = new CANSparkMax(23, MotorType.kBrushless);
    private final CANSparkMax left1 = new CANSparkMax(20, MotorType.kBrushless);
    private final CANSparkMax right1 = new CANSparkMax(21, MotorType.kBrushless);

    public DriveBase() {
        left0.restoreFactoryDefaults();
        left1.restoreFactoryDefaults();
        right0.restoreFactoryDefaults();
        right1.restoreFactoryDefaults();

        left1.follow(left0);
        right1.follow(left0);
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
}
