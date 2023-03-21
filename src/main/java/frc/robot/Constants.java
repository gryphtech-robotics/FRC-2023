package frc.robot;


public class Constants {
    public static class Controls {
        public static final int JOYSTICK_Y_AXIS = 0;
        public static final int JOYSTICK_X_AXIS = 1;
        public static final int JOYSTICK_THROTTLE = 3;
    }

    public static class USB {
        public static final int DRIVER = 0;
        public static final int COPILOT = 1;
    }

    public static class CanIDs {
        public static final int LEFT_DRIVE_0 = 40;
        public static final int RIGHT_DRIVE_0 = 23;
        public static final int LEFT_DRIVE_1 = 20;
        public static final int RIGHT_DRIVE_1 = 21;

        public static final int CLAMP_TALON = 46;
        public static final int ARM_TALON = 5;

        public static final int ARM_0 = 61;
        public static final int ARM_1 = 31;
    }

    public static class etcIDs {
        public static final int CLAMP_LIMIT = 0;
    }    

    public static class Math {
        public static final double ARM_ENCODER_CONVERSION_FACTOR = 360/42;
    }

    public static class PID {
        public static final double ARM_P = 0.007;
        public static final double CLAMP_P = 0.0109;
    
        public static final double POS_TOP = -5;
        public static final double POS_BOTTOM = -85;
        public static final double POS_L2 = -48;
        public static final double POS_C_OPEN = -20000; 

    }

    /**
     * ! These are not accurate and should not be used yet. Need to be found by analyzing sysid_data JSON files.
     */
    public static class Pathfinding {
        public static final double ksVolts = 0.0;
        public static final double kvVoltSecondsPerMeter = 0.0;
        public static final double kaVoltSecondsSquaredPerMeter = 0.0;
    }

    public static class encoderPositions{
        public static final double ARM_MIDDLE_TARGET = 0.0;
    }
}
