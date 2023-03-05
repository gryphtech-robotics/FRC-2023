package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Arm extends SubsystemBase {
    private final CANSparkMax arm0 = new CANSparkMax(Constants.CanIDs.ARM_0, MotorType.kBrushless);
    private final CANSparkMax arm1 = new CANSparkMax(Constants.CanIDs.ARM_1, MotorType.kBrushless);

    private final RelativeEncoder encoder = arm0.getEncoder();
    private final SparkMaxPIDController pidController = arm0.getPIDController();

    public Arm() {
        arm0.restoreFactoryDefaults();
        arm1.restoreFactoryDefaults();

        arm1.follow(arm0);
        
        pidController.setFeedbackDevice(encoder);
        pidController.setP(Constants.PID.ARM_P);

        encoder.setPositionConversionFactor(Constants.Math.ARM_ENCODER_CONVERSION_FACTOR);
        encoder.setPosition(0);
    }

    @Override
    public void periodic() {
        //System.out.println(getRawPos());
        SmartDashboard.putNumber("rawEncoderPos", this.getRawPos());
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

    public void setPos(double position) {
        pidController.setReference(position, ControlType.kPosition);
    }
}
