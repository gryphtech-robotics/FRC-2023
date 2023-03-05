package frc.robot;


public class Constants {
    public static class Controls {
        public static final int JOYSTICK_X_AXIS = 3;
        public static final int JOYSTICK_Y_AXIS = 0;
        public static final int JOYSTICK_THROTTLE = 1;

        public static final int CLAMP_OPEN = 4;
        public static final int CLAMP_CLOSE = 5; 

        public static final int ARM_UP = 4;
        public static final int ARM_DOWN = 1;
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
        public static final double ARM_P = 0.001;
        //public static final double ARM_FF = 0.2;
    }
}
