package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase {
    private final CANSparkMax arm0 = new CANSparkMax(61, MotorType.kBrushless);
    private final CANSparkMax arm1 = new CANSparkMax(31, MotorType.kBrushless);
    private final RelativeEncoder encoder = arm0.getEncoder();

    private double relativeMotorPos = 0;

    public Arm() {
        arm0.restoreFactoryDefaults();
        arm1.restoreFactoryDefaults();

        arm1.follow(arm0);

        encoder.setPositionConversionFactor(360/42);
        encoder.setPosition(0);
    }

    /**
     * Sets the arm motor speed.
     * @param speed Decimal percentage to set as the speed.
     */
    public void setSpeed(double speed) {
        arm0.set(speed);
    }

    /**
     * Gets the raw encoder position of the arm. 
     * Please note this is incremental, so it is not reflective of the actual motor position.
     * @return value
     */
    public double getRawPos() {
        return encoder.getPosition();
    }

    /**
     * Get the relative encoder position of the arm.
     * @return value
     */
    public double getPos() {
        return relativeMotorPos;
    }

    /**
     * Tracks the motor encoder position in a relative manner.
     * @param direction 1 is going up, -1 is going down.
     * @param curPos "Current" encoder position.
     * @param newPos New motor position.
     * @return
     */
    public void setPos(int direction, double curPos, double newPos) {
        if(direction == -1)
            relativeMotorPos = curPos - newPos;
        else if(direction == 1)
            relativeMotorPos = curPos + newPos;
    }
}
