package frc.robot.Systems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class Clamp {

    public static TalonFX talon;

    public static void init(){

        //talon = new TalonFX(33);

    }

    public static void out(){
        //talon.set(ControlMode.PercentOutput, 0.1);
    }

    public static void in(){
        //talon.set(ControlMode.PercentOutput, -0.1);
    }

    public static void stop(){
       // talon.set(ControlMode.PercentOutput, 0.0);
    }
    
}
