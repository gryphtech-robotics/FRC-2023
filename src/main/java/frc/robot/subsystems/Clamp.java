package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.*;
import frc.robot.util.Util;

public class Clamp extends SubsystemBase {
    private final TalonFX talon = new TalonFX(CanIDs.CLAMP_TALON);
    private final DigitalInput input = new DigitalInput(EtcIDs.CLAMP_LIMIT);

    private double cachedRefPos = 0.0;

    public Clamp() {
        talon.configFactoryDefault();
        talon.setNeutralMode(NeutralMode.Coast);
        talon.config_kP(0, PID.CLAMP_P);
        talon.config_kI(0, 0.0);
        talon.config_kD(0, 0.0);
        talon.config_kF(0, 0.0);
        
        zero();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("debug/ClampEncoderPosition", getRawPos());
        SmartDashboard.putNumber("debug/ClampEncoderTarget", cachedRefPos);
        SmartDashboard.putBoolean("debug/LimitEngaged", getLimit());
    }

    /**
     * Sets the clamp motor speed.
     * @param speed Decimal percentage to set as the speed.
     */
    public void setSpeed(double speed) {
        talon.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Set the clamp's reference position for PID control.
     * @param target position in motor units.
     */
    public void setPos(double target) {
         this.cachedRefPos = target;
         talon.set(ControlMode.Position, Util.applyDeadband(target, 25));
    }

    /**
     * Gets the raw encoder position of the clamp.
     * @return position in motor units;
     */
    public double getRawPos(){
        return talon.getSelectedSensorPosition();
    }

    /**
     * Get the status of the limit switch.
     * @return Boolean state of the switch. Pressed is true.
     */
    public boolean getLimit() {
        return input.get();
    }

    /**
     * Zero the clamp's encoder and {@link #cachedRefPos}.
     */
    public void zero() {
        cachedRefPos = 0.0;
        talon.setSelectedSensorPosition(0.0);
        System.out.println("### Zeroed clamp encoder and reset cachedRefPos.");
    }
}
