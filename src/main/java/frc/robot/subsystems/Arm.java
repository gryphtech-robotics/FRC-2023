package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;



import frc.robot.Constants.*;
import frc.robot.Constants;


public class Arm extends SubsystemBase {
    private final CANSparkMax arm0 = new CANSparkMax(CanIDs.ARM_0, MotorType.kBrushless);
    private final CANSparkMax arm1 = new CANSparkMax(CanIDs.ARM_1, MotorType.kBrushless);

    private final RelativeEncoder encoder = arm0.getEncoder();
    private final SparkMaxPIDController pidController = arm0.getPIDController();

    private double cachedRefPos = 0.0;

    private final TalonFX talon = new TalonFX(CanIDs.ARM_TALON);

    public Arm() {
        arm0.restoreFactoryDefaults();
        arm1.restoreFactoryDefaults();

        arm1.follow(arm0);
        
        pidController.setFeedbackDevice(encoder);
        pidController.setP(PID.ARM_P);

        encoder.setPositionConversionFactor(Constants.Math.ARM_ENCODER_CONVERSION_FACTOR);
        encoder.setPosition(0.0);

        talon.configFactoryDefault();
        talon.setNeutralMode(NeutralMode.Coast);
        talon.config_kP(0, Constants.PID.ARM_EXT_P);
        talon.config_kI(0, 0.0);
        talon.config_kD(0, 0.0);
        talon.config_kF(0, 0.0);
        talon.setSelectedSensorPosition(0.0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("ArmEncoderPosition", getRawPos());
        SmartDashboard.putNumber("ArmEncoderTarget", cachedRefPos);
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
     * Set the arm's reference position for PID control. 
     * @param position position in motor units.
     */
    public void setPos(double position) {
        this.cachedRefPos = position;
        pidController.setReference(position, ControlType.kPosition);
    }

    public void setExtensionSpeed(double speed){
        talon.set(ControlMode.PercentOutput, speed);
    }

    public void setExtensionPos(double pos){
        talon.set(ControlMode.Position, pos);
    }

}
