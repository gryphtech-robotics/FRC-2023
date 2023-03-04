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



        if(systemController.getRawButton(1)){
            System.out.println("h");

            if (Arm.periodic() <= -110){
                Arm.stop();
            } else {
                Arm.down();
            };

        }else if(systemController.getRawButton(4)){
            if(Arm.periodic() >= -10){
                Arm.stop();
            }else{
                Arm.up();
            };
        }else{Arm.stop();}

        if (systemController.getRawButton(6)){
            Clamp.out();

        }else if(systemController.getRawButton(5)){
            if(Clamp.limitSender() == true){
                Clamp.stop();
                System.out.println("limit");
            } else {
                Clamp.in();
            }
        }else{
            Clamp.stop();
        }


        
    }

}
