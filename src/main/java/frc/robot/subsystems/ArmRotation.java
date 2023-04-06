package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Handles arm rotation.
 */
public class ArmRotation extends SubsystemBase {
    private final CANSparkMax arm0 = new CANSparkMax(Constants.CanIDs.ARM_0, MotorType.kBrushless);
    private final CANSparkMax arm1 = new CANSparkMax(Constants.CanIDs.ARM_1, MotorType.kBrushless);

    private final RelativeEncoder encoder = arm0.getEncoder();
    private final SparkMaxPIDController pidController = arm0.getPIDController();

    private double cachedRefPos = 0.0;

    public ArmRotation() {
        arm0.restoreFactoryDefaults();
        arm1.restoreFactoryDefaults();

        arm1.follow(arm0);
        
        pidController.setFeedbackDevice(encoder);
        pidController.setP(Constants.PID.ARM_P);

        encoder.setPositionConversionFactor(Constants.Math.ARM_ENCODER_CONVERSION_FACTOR);

        zero();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("debug/RotationEncoderPosition", getRawPos());
        SmartDashboard.putNumber("debug/RotationEncoderTarget", cachedRefPos);       
        SmartDashboard.putNumber("debug/Arm0RotationCurrent", arm0.getOutputCurrent());
        SmartDashboard.putNumber("debug/Arm1RotationCurrent", arm0.getOutputCurrent());
        SmartDashboard.putNumber("debug/Arm0RotationTemperature", arm0.getMotorTemperature());
        SmartDashboard.putNumber("debug/Arm1RotationTemperature", arm0.getMotorTemperature());
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
     * <p>
     * * Please note this is incremental, so it is not reflective of the actual motor position.
     * @return value
     */
    public double getRawPos() {
        return encoder.getPosition();
    }

    /**
     * Set the arm's reference position for PID control. 
     * @param position position in motor units.
     */
    public void setPos(double target) {
        cachedRefPos = target;
        pidController.setReference(target, ControlType.kPosition);
    }

    /**
     * Zeroes the arm rotation encoders and {@link #cachedRefPos}.
     */
    public void zero() {
        cachedRefPos = 0.0;
        encoder.setPosition(0.0);

        System.out.println("### Zeroed arm rotation encoders and reset cachedRefPos.");
    }
}
