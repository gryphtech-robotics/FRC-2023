package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.CanIDs;
import frc.robot.Constants.etcIDs;

public class Clamp extends SubsystemBase {
    private final TalonFX talon = new TalonFX(CanIDs.CLAMP_TALON);
    private final DigitalInput input = new DigitalInput(etcIDs.CLAMP_LIMIT);
    //private final PIDController PID = new PIDController(0.001, 0, 0);

    public Clamp() {
        talon.setSelectedSensorPosition(0);
    }

    /**
     * Sets the clamp motor speed.
     * @param speed Decimal percentage to set as the speed.
     */
    public void setSpeed(double speed) {
        talon.set(ControlMode.PercentOutput, speed);
    }

    /**
     * Get the status of the limit switch.
     * @return Boolean state of the switch. Pressed is true.
     */
    public boolean getLimit() {
        return input.get();
    }

    public void setPos(double pos) {
    }

    public double getRawPos(){
        return 0;
    }
}
