package frc.robot.Systems;

import javax.swing.SortingFocusTraversalPolicy;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {

    public static CANSparkMax arm1;
    public static CANSparkMax arm2;

    public static RelativeEncoder encoder;

    public static void init(){
        arm1 = new CANSparkMax(61, MotorType.kBrushless);
        arm2 = new CANSparkMax(31, MotorType.kBrushless);

        arm1.restoreFactoryDefaults();
        arm2.restoreFactoryDefaults();

        arm2.follow(arm1);

        encoder = arm1.getEncoder();

        encoder.setPositionConversionFactor(360/42);
        encoder.setPosition(0);

    }



    public static void up(){
        arm1.set(0.2);


    }

    public static void down(){



        arm1.set(-0.2);
    }

    public static void stop(){
        arm1.set(0);
    }

    public static void toBottom(){
        while ( encoder.getPosition() > -110) {
            arm1.set(0.1);
        }
    }
    
    public static double periodic(){
        System.out.println(encoder.getPosition());
        return encoder.getPosition();

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
