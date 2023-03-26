package frc.robot.util;

public class Util {
    public static double applyDeadband(double value, double deadband) {
        if (Math.abs(value) < deadband)
            return 0.0;
        else
            return value;
    }

    public static boolean deadCheck(double input, double desired, double band) {
        if(input - band == desired || input + band == desired) {
            return true;
        } else {
            return false;
        }
    }
}
