// package frc.robot.Tests;

// //import suff
// import edu.wpi.first.wpilibj.Joystick;
// import com.revrobotics.CANSparkMax;
// import com.revrobotics.CANSparkMaxLowLevel.MotorType;

// public class Drive {
//     //declare variables
//     public static Joystick driverController;

//     public static CANSparkMax rdrive1;
//     public static CANSparkMax rdrive2;
//     public static CANSparkMax ldrive1;
//     public static CANSparkMax ldrive2;

//     public static double x;
//     public static double y;

    

//     public static void init(){
//         driverController = new Joystick(0);

//         rdrive1 = new CANSparkMax(20, MotorType.kBrushless);
//         rdrive2 = new CANSparkMax(21, MotorType.kBrushless);
//         ldrive1 = new CANSparkMax(22, MotorType.kBrushless);
//         ldrive2 = new CANSparkMax(23, MotorType.kBrushless);

//         ldrive1.follow(ldrive2);
//         rdrive1.follow(rdrive2);

//         rdrive1.restoreFactoryDefaults();
//         rdrive2.restoreFactoryDefaults();
//         ldrive1.restoreFactoryDefaults();
//         ldrive2.restoreFactoryDefaults();
//     }

//     public static void periodic(){
//        y = driverController.getRawAxis(1);
//        x = driverController.getRawAxis(0);

//         rdrive1.set(x+y);
//         ldrive1.set(x-y);



//     }
     
// }
