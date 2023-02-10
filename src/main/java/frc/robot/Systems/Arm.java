package frc.robot.Systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Arm {
    public static CANSparkMax arm;

    public static void init(){
        arm = new CANSparkMax(24, MotorType.kBrushless);
    }


    public static void periodic(double direction){
        arm.set(direction);
    }
}
