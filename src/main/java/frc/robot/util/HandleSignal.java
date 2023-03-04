package frc.robot.util;

public class HandleSignal {

    /**
     * Handles trigger logic for clamp-related systems.
     * @param left Left-trigger boolean state.
     * @param right Right trigger boolean state.
     * @return A 200, 400, or 600 code, signalling the closing, opening, or ignoring of the clamp.
     */
    public static double ClampTrigger(boolean left, boolean right) {
        switch((left && right) ? 0 : 1) {
            case 0:
                return 600;
            case 1:
                if(left && !right)
                    return 200;
                else if (!left && right)
                    return 400;
                else 
                    return 600;
            default: 
                return 600;
        }   
    }

    /**
     * Handles button logic for arm-related systems.
     * @param one Button one boolean state.
     * @param four Button four boolean state.
     * @return A 200, 400, or 600 code, signalling the upwards, downwards, or stationary movement of the arm.
     */
    public static double ArmButton(boolean one, boolean four) {
        switch((one && four) ? 0 : 1) {
            case 0:
                return 600;
            case 1:
                if(one && !four) 
                    return 200;
                else if (!one && four)
                    return 400;
                else 
                    return 600;
            default:
                return 600;
        }
    }
}