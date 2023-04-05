package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;

/**
 * Handles arm extension.
 */
public class ArmExtension extends SubsystemBase {
    private final TalonFX extension = new TalonFX(CanIDs.ARM_EXTENDER);
    private double cachedRefPos = 0.0;

    public ArmExtension() {
        extension.configFactoryDefault();
        extension.setNeutralMode(NeutralMode.Coast);
        extension.config_kP(0, PID.ARM_EXT_P);
        extension.config_kI(0, 0.0);
        extension.config_kD(0, 0.0);
        extension.config_kF(0, 0.0);

        zero();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("debug/ExtensionEncoderPosition", getPos());
        SmartDashboard.putNumber("debug/ExtensionEncoderTarget", cachedRefPos);
        SmartDashboard.putNumber("debug/ExtensionCurrent", extension.getSupplyCurrent());
        SmartDashboard.putNumber("debug/ExtensionTemperature", extension.getTemperature());
    }

    /**
     * Sets the extension motor speed.
     * @param speed Decimal percentage to set as the speed.
     */
    public void setSpeed(double speed) {
        extension.set(ControlMode.Velocity, speed);
    }

    /**
     * Set the arm extension position.
     * @param target The targeted position in motor units
     */
    public void setPos(double target) {
        cachedRefPos = target;
        extension.set(ControlMode.Position, target);
    }

    /**
     * Gets the extension encoder's raw position.
     * @return value
     */
    public double getPos() {
        return extension.getSelectedSensorPosition();
    }

    /**
     * Zeroes the arm extension encoder and {@link #cachedRefPos}.
     */
    public void zero() {
        cachedRefPos = 0.0;
        extension.setSelectedSensorPosition(0);
        System.out.println("### Zeroed arm extension encoder and reset cachedRefPos.");
    }
}
