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

import java.lang.Math;

import edu.wpi.first.wpilibj.I2C;

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

	public static double mass;

	public static I2C arduino;
		
	
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

		//sets the PID var to a instance of the PID class
		pid = new PIDController(kP, kI, kD);

		//sets the encoder vars to read the values from the encoders on the respective motors
		rDrive0Endocer = rDrive0.getEncoder();
		lDrive0Encoder = lDrive0.getEncoder();

		arduino = new I2C(I2C.Port.kOnboard, 8); //Sets up the Arduino over I2C on port 8


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

		byte[] sendData = "Gimme my data".getBytes(); //Data sent to the Arduino
		byte[] receiveData = new byte[12]; // Bytes requested from Arduino
		arduino.transaction(sendData, sendData.length, receiveData, receiveData.length); // Sends and Receives the Data to and from the Arduino
		System.out.println("Received: " + new String(receiveData, 0, receiveData.length)); //Prints the data received from the Arduino on the console
		System.out.println("b");

	}

	public static void gyro(){
		 setpoint = math(Gy);
		//sets the motors to the correct speed based on out angle using PID control
		rDrive0.set(pid.calculate(rDrive0Endocer.getVelocity(), setpoint));
		lDrive0.set(pid.calculate(lDrive0Encoder.getVelocity(), setpoint));
	}


	public static double math(double Gy){
		return 9.81 * mass * Math.sin(Gy);
	}
 
}