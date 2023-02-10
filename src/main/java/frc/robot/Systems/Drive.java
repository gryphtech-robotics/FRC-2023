/**
 * Copyright 2023 Gryphtech Robotics
 * 
 */

package frc.robot.Systems;

//imports for PID
import edu.wpi.first.math.controller.PIDController;

//import shuffleboard
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


//inports for Motor controllers
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.*;

public class Drive {
	
	public static double Gx, Gy, Gz, Ax, Ay, Az;

	//creates vars for all the motor controllers
	public static CANSparkMax lDrive1;
	public static CANSparkMax rDrive1;
	public static CANSparkMax lDrive0;
	public static CANSparkMax rDrive0;
	
	//sets vars for PID
	public static PIDController pid;
	public static double kP,kI,kD, setpoint;

	//sets vars for the encoders
	public static RelativeEncoder rDrive0Endocer;
	public static RelativeEncoder lDrive0Encoder;

	public static double[] g = {Gx, Gy, Gz};
	public static double[] a = {Ax, Ay, Az};
		
	
	public static void init() {
		
		//sets the motor controllers to a instance of the Sparkmax Class
		lDrive0 = new CANSparkMax(22, MotorType.kBrushless);
		rDrive0 = new CANSparkMax(23, MotorType.kBrushless);
		lDrive1 = new CANSparkMax(20, MotorType.kBrushless);
		rDrive1 = new CANSparkMax(21, MotorType.kBrushless);
		
		// for new robot 1 ld0, 4 rd0, 2 ld1,3rd1
		lDrive0.restoreFactoryDefaults();
		rDrive0.restoreFactoryDefaults();
		lDrive1.restoreFactoryDefaults();
		rDrive1.restoreFactoryDefaults();
		
		//tells the rear motors to follow the from motors
		lDrive1.follow(lDrive0);
		rDrive1.follow(rDrive0);

		//sets the PID var to a instance of the PID class
		pid = new PIDController(kP, kI, kD);

		//sets the encoder vars to read the values from the encoders on the respective motors
		rDrive0Endocer = rDrive0.getEncoder();
		lDrive0Encoder = lDrive0.getEncoder();

	}
	
	public static void periodic (double x, double y, double speed){
		//sets the motors to the speed given by the logic in OI file
		rDrive0.set((x+y) * speed);
		lDrive0.set((x-y) * speed);
		
		Gx = SmartDashboard.getNumber("Gx", 0);
		Gy = SmartDashboard.getNumber("Gy", 0);
		Gz = SmartDashboard.getNumber("Gz", 0);
		Ax = SmartDashboard.getNumber("Ax", 0);
		Ay = SmartDashboard.getNumber("Ay", 0);
		Az = SmartDashboard.getNumber("Az", 0);

	}

	// public static void gyro(){
	// 	 setpoint = math(g, a);
	// 	//sets the motors to the correct speed based on out angle using PID control
	// 	rDrive0.set(pid.calculate(rDrive0Endocer.getVelocity(), setpoint));
	// 	lDrive0.set(pid.calculate(lDrive0Encoder.getVelocity(), setpoint));
	// }

	// /***
	//  * 
	//  * @param class1 An Array of doubles, Gx, Gy, Gz,
	//  * @param class2 An Array of doubles, Ax, Ay, Az,
	//  * @return math
	//  */
	// public static double math(double[] g, double[] a){
	// 	System.out.println(a);
	// 	System.out.println(g);
	// 	return 0.2;
	// }
 
}