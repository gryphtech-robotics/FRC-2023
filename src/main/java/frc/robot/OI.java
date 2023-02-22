package frc.robot;
import edu.wpi.first.wpilibj.Joystick;

import frc.robot.Systems.Drive;



public class OI {

    public static Joystick driverController;
    
    public static void init() {
        driverController = new Joystick(0);
    }

    public static void periodic(){
        
        if(driverController.getRawButton(5)){
            Drive.gyro();
        }else{
            Drive.periodic(driverController.getRawAxis(0), driverController.getRawAxis(1), (1+(-driverController.getRawAxis(3)))/2);
        }



        
    }
}
