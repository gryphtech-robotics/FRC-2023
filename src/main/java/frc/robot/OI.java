package frc.robot;
import edu.wpi.first.wpilibj.Joystick;
import java.lang.Math;

import frc.robot.Systems.Drive;
import frc.robot.Systems.Gyro;
import frc.robot.Systems.Arm;


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

        if(driverController.getRawButton(4)){
            System.out.println("g");
            Arm.periodic(0.2, 0);
        }else{
            Arm.periodic(0, 0);
        }

        if(driverController.getRawButton(6)){
            System.out.println("h");
            Arm.periodic(-0.1, 1);
        }else{
            Arm.periodic(0, 0);
        }
    }

}
