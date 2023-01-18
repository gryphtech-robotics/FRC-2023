
/**
 * Copyright 2020 Gryphtech Robotics
 * # axelsNOTgreat
 */

 package frc.robot.Systems;

 //Driver control
 import edu.wpi.first.wpilibj.Joystick;
 import com.revrobotics.CANSparkMax;
 import com.revrobotics.CANSparkMaxLowLevel.MotorType;
 
 public class Drive {
 
   // Initialize Joysticks
   public static Joystick driverController;
 
   public static boolean go = false;
 
   public static CANSparkMax lDrive1;
   public static CANSparkMax rDrive1;
   public static CANSparkMax lDrive0;
   public static CANSparkMax rDrive0;
 
   public static double x;
   public static double y;
 
   public static double speed;
 
   public static float minMin = 0.05f;
   public static float Kp = -0.01f;
   
   public static void init(Joystick boystick) {
     driverController = boystick;
     
     lDrive0 = new CANSparkMax(24, MotorType.kBrushless);
     rDrive0 = new CANSparkMax(22, MotorType.kBrushless);
     lDrive1 = new CANSparkMax(23, MotorType.kBrushless);
     rDrive1 = new CANSparkMax(21, MotorType.kBrushless);
 
     // for new robot 1 ld0, 4 rd0, 2 ld1,3rd1
     lDrive0.restoreFactoryDefaults();
     rDrive0.restoreFactoryDefaults();
     lDrive1.restoreFactoryDefaults();
     rDrive1.restoreFactoryDefaults();
 
     lDrive1.follow(lDrive0);
     rDrive1.follow(rDrive0);
   }
 
   public static void periodic (){
     speed = (1+(-driverController.getRawAxis(3)))/2;
         
     x = driverController.getRawAxis(0);
     y = driverController.getRawAxis(1);
 
     rDrive0.set((x+y) * speed);
     lDrive0.set((x-y) * speed);
     
   }
 

 }