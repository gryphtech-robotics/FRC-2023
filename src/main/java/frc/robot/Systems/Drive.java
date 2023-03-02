/**
 * Copyright 2023 Gryphtech Robotics
 * 
 */

package frc.robot.Systems;




//inports for Motor controllers
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class Drive {
	
	public static double Gx, Gy, Gz, Ax, Ay, Az;

	//creates vars for all the motor controllers
	public static CANSparkMax lDrive1;
	public static CANSparkMax rDrive1;
	public static CANSparkMax lDrive0;
	public static CANSparkMax rDrive0;
	


		
	
	public static void init() {
		
		//sets the motor controllers to a instance of the Sparkmax Class
		lDrive0 = new CANSparkMax(29, MotorType.kBrushless);
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


	}
	
	public static void periodic (double x, double y, double speed){
		//sets the motors to the speed given by the logic in OI file
		rDrive0.set((x+y) * speed);
		lDrive0.set((x-y) * speed);

	}


 
}