package frc.robot.Systems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {

    public static CANSparkMax arm1;
    public static CANSparkMax arm2;

    public static void init(){
        arm1 = new CANSparkMax(61, MotorType.kBrushless);
        arm2 = new CANSparkMax(31, MotorType.kBrushless);

        arm1.restoreFactoryDefaults();
        arm2.restoreFactoryDefaults();

        arm2.follow(arm1);

    }



    public static void periodic(double speed, int setting){
        arm1.set(speed);

        SmartDashboard.putNumber("speed", speed);
    
    }

    public static void up(){
        System.out.println("bal");
        arm1.set(1);
    }

    public static void down(){
        arm1.set(-0.1);
    }

    public static void stop(){
        arm1.set(0);
    }

//     /**
//      * This function will take motor units (units of change) from the the Talon encoders and return human readable degrees. This is relative to our robot.
//      * @param motorUnit Units of change returned by talon encoder
//      * @return degrees
//      */
//     private static double degrees(double motorUnit) {
//         return Math.abs(motorUnit * (360/26326.0));
//     }
//     /**
//      * This function will take degrees from the the Talon encoders and return degrees of change (robot units). This is relative to our robot.
//      * @param degrees Degrees to convert to talon units of change
//      * @return motor units of change
//      */
//     private static double motorUnits(double degrees) {
//         return -(degrees * (26326/360));
//     }
 }
