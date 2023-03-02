package frc.robot.Systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.RelativeEncoder;

public class Arm {

    public static TalonFX talon1;
    public static TalonFX talon2;

    public static void init(){
        talon1 = new TalonFX(0);
        talon2 = new TalonFX(1);
        talon1.setSelectedSensorPosition(0);
       // zero();

    }

    /*public static void zero(){
        if(encoder >= -1000){
            talon1.set(ControlMode.PercentOutput, 0.05);
        }
    }*/

    public static void periodic(double speed, int setting){
        // if((degrees(talon1.getSelectedSensorPosition())) <= 10 && setting == 1){
        //     talon1.set(ControlMode.PercentOutput, 0);
        //     talon2.set(ControlMode.PercentOutput, 0);
        // } else if ((degrees(talon1.getSelectedSensorPosition()) >= 355) && setting == 0) {
        //     talon1.set(ControlMode.PercentOutput, 0);
        //     talon2.set(ControlMode.PercentOutput, 0);
        // } else {
        //     talon1.set(TalonFXControlMode.PercentOutput, speed);
        //     talon2.set(TalonFXControlMode.PercentOutput, speed);
    
        // }

        talon1.set(TalonFXControlMode.PercentOutput, speed);
        talon2.set(TalonFXControlMode.PercentOutput, speed);

        // System.out.println("e -> " + encoder);
        // System.out.println("a -> " + degrees(encoder));
        // System.out.println("t -> " + encoder / 2048 * 360);
    }

    /**
     * This function will take motor units (units of change) from the the Talon encoders and return human readable degrees. This is relative to our robot.
     * @param motorUnit Units of change returned by talon encoder
     * @return degrees
     */
    private static double degrees(double motorUnit) {
        return Math.abs(motorUnit * (360/26326.0));
    }
    /**
     * This function will take degrees from the the Talon encoders and return degrees of change (robot units). This is relative to our robot.
     * @param degrees Degrees to convert to talon units of change
     * @return motor units of change
     */
    private static double motorUnits(double degrees) {
        return -(degrees * (26326/360));
    }
}
