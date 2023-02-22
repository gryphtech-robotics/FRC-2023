package frc.robot.Systems;

import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;

import com.revrobotics.*;


public class TurnTabel {
    public static CANSparkMax turnTabel;

    public static RelativeEncoder encoder;

    public static void init(){
        turnTabel = new CANSparkMax(24, MotorType.kBrushless);
        
         encoder = turnTabel.getEncoder();
    }

    public static void periodic(double speed){

        if(speed > 0 && encoder.getPosition() > 0){
        turnTabel.set(0.3);
        }else if(speed > 0 && encoder.getPosition() < 180){
            turnTabel.set(-0.3);
        }
    }
}
