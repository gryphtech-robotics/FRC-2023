package frc.robot.util;

public class Util {
    public static double applyDeadband(double value, double deadband) {
        if (Math.abs(value) < deadband)
            return 0.0;
        else
            return value;
    }
}
