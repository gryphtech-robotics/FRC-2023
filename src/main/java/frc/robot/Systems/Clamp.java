package frc.robot.Systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.DigitalInput;

public class Clamp {

    public static TalonFX talon;
    public static DigitalInput limit;

    public static void init(){
        talon = new TalonFX(46);
        limit = new DigitalInput(0);

        talon.setSelectedSensorPosition(0);
    }

    public static void out(){
        talon.set(ControlMode.PercentOutput, 0.1);
    }

    public static void in(){
        talon.set(ControlMode.PercentOutput, -0.1);
    }

    public static void stop(){
       talon.set(ControlMode.PercentOutput, 0.0);
    }

    public static boolean limitSender(){
        return limit.get();
    }

    public static void allOut() {
        while (limit.get() == false) {
            talon.set(ControlMode.PercentOutput, 0.1);
        }
    }
}
