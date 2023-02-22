package frc.robot;
import edu.wpi.first.wpilibj.Joystick;

import frc.robot.Systems.Drive;
import frc.robot.Systems.TurnTabel;



public class OI {

    public static Joystick driverController;
    public static Joystick systemController;
    
    public static void init() {
        driverController = new Joystick(0);
        systemController = new Joystick(1);
    }

    public static void periodic(){
        
        if(driverController.getRawButton(5)){
            Drive.gyro();
        }else{
            Drive.periodic(driverController.getRawAxis(0), driverController.getRawAxis(1), (1+(-driverController.getRawAxis(3)))/2);
        }

        TurnTabel.periodic(systemController.getRawAxis(0));

        
    }
}
