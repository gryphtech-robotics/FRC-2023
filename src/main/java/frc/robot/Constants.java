package frc.robot;


public class Constants {
    public static class USB {
        public static final int DRIVER = 0;
        public static final int COPILOT = 1;
    }

    public static class CanIDs {
        public static final int LEFT_DRIVE_0 = 14;
        public static final int RIGHT_DRIVE_0 = 11;
        public static final int LEFT_DRIVE_1 = 13;
        public static final int RIGHT_DRIVE_1 = 12;

        public static final int CLAMP_TALON = 5;
        public static final int ARM_EXTENDER = 6;

        public static final int ARM_0 = 15;
        public static final int ARM_1 = 16;
    }

    public static class EtcIDs {
        public static final int CLAMP_LIMIT = 0;
    }    

    public static class Math {
        public static final double ARM_ENCODER_CONVERSION_FACTOR = 360/42;
    }

    public static class PID {
        public static final double OLD_ARM_P = 0.007;
        public static final double ARM_P = 0.0095;
        public static final double ARM_EXT_P = 0.07;
        public static final double CLAMP_P = 0.0109;
    
        public static final double POS_TOP = -5;
        public static final double POS_BOTTOM = -95; // -85;
        public static final double POS_L2 = -55; //-48;
        public static final double POS_C_OPEN = -20000;
        
        public static final double POS_ARM_OPTIMAL = -35;
        public static final double POS_EXT_OPTIMAL = -8100;

        public static final double ARM_EXT_LIMIT = -18000;
    }

    public static class AUTO {
        public static final double OPTIMAL_DRIVE_PERIOD = 70;

        public static final double ARM_EXT_TOP = -19575;
        public static final double ARM_ROT_TOP = -37;

        public static final double CLAMP_CONE = 2050;
        public static final double ARM_EXT_CONE = -10100;
        public static final double ARM_ROT_CONE = -40;
    }
}
