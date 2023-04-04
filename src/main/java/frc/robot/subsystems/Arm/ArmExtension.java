package frc.robot.subsystems.Arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmExtension extends SubsystemBase {
    public static TalonFX talon = new TalonFX(Constants.CanIDs.ARM_EXTENDER);

    //sets the arm extension motor to a given POS
    public static void setPos(double targetPos) {
        talon.set(ControlMode.Position, targetPos);
        SmartDashboard.putNumber("targetPOs", targetPos);

    }


    //zeroes the arm extension encoder
    public static void zero(){
        talon.setSelectedSensorPosition(0);
        System.out.println("Arm Extension Zeroed");
    }
      
}
