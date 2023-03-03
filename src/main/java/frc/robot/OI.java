package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import java.lang.Math;

import frc.robot.Systems.Drive;
import frc.robot.Systems.Gyro;
import frc.robot.Systems.Arm;
import frc.robot.Systems.Clamp;


public class OI {

    public static Joystick driverController;
    public static Joystick systemController;

    public static boolean axelmode = false;
    
    public static void init() {
        driverController = new Joystick(0);
        systemController = new Joystick(1);
    }

    public static void periodic(){
        
        Drive.periodic(driverController.getRawAxis(0), driverController.getRawAxis(1), (1+(-driverController.getRawAxis(3)))/2);

        if(driverController.getRawButton(6)){
            System.out.println("g");
           Arm.up();

        }else{

           Arm.stop();
        }

        if(driverController.getRawButton(4)){
            System.out.println("h");
            Arm.down();
        }else{
            Arm.stop();
        }

        if (driverController.getRawButton(5)){
            Clamp.in();
        }else if(driverController.getRawButton(3)){
            Clamp.out();
        }else{
            Clamp.stop();
        }


        
    }

}
