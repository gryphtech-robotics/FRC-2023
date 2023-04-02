package frc.robot;

import edu.wpi.first.wpilibj2.command.*;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;
import frc.robot.Constants.*;
import frc.robot.auto.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * Handles command button assignment and scheduling.
 */
public class RobotContainer {
    private CommandJoystick DriveController = new CommandJoystick(USB.DRIVER);
    private CommandXboxController CopilotController = new CommandXboxController(USB.COPILOT);

    private final DriveBase driveBase = new DriveBase();
    private final Arm arm = new Arm();
    private final Clamp clamp = new Clamp();

    private final SendableChooser<String> auto_chooser = new SendableChooser<>();

    /**
     * Configures the robot container without test bindings.
     * <p>
     * * Call with enableTestBindings = true to enable Trent's drive test bindings.
     * @param enableTestBindings Boolean dictating which binding set to enable.
     */
    public RobotContainer() {
        configure(false);
    }

    /**
     * Configures the robot container with test bindings.
     * <p>
     * * Call with enableTestBindings = true to enable Trent's drive test bindings.
     * @param enableTestBindings Boolean dictating which binding set to enable.
     */
    public RobotContainer(boolean enableTestBindings) {
        configure(enableTestBindings);
    }

    private void configure(boolean enableTestBindings) {
        driveBase.setDefaultCommand(new DriveWithJoystick(driveBase, () -> DriveController.getY(), () -> DriveController.getX(), () -> (1 + (-DriveController.getThrottle())) / 2));
        
        if(enableTestBindings) 
            configureTestBindings();
        else 
            configureBindings();

        auto_chooser.setDefaultOption(  "NO AUTO", "Nothing");
        auto_chooser.addOption("SCORE", "Score");
        auto_chooser.addOption("SCORE & TAXI", "ScoreTaxi");
        auto_chooser.addOption("TAXI", "Taxi");
        SmartDashboard.putData(auto_chooser);

        CameraServer.startAutomaticCapture();
    }

    /**
     * Configures driver and copilot command bindings. 
     * <p>
     * * This does not configure the driveBase defaults. That's done in {@link #RobotContainer()} ensure prioritization.
     */
    private void configureBindings() {
        CopilotController.leftBumper().whileTrue(new InstantCommand(() -> clamp.setSpeed(-0.3), clamp))
            .onFalse(new InstantCommand(() -> clamp.setSpeed(0.0), clamp));
        CopilotController.rightBumper().whileTrue(new InstantCommand(() -> clamp.setSpeed(0.3), clamp))
            .onFalse(new InstantCommand(() -> clamp.setSpeed(0.0), clamp));

        //CopilotController.leftTrigger().whileTrue(new InstantCommand(() -> clamp.setPos(clamp.getRawPos()), clamp));
        CopilotController.leftTrigger().whileTrue(new InstantCommand(() -> clamp.setSpeed(0.073), clamp))
            .onFalse(new InstantCommand(() -> clamp.setSpeed(0.0), clamp));

        CopilotController.y().whileTrue(new ManualArmRotation(arm, 1));
        CopilotController.a().whileTrue(new ManualArmRotation(arm, -1));

        CopilotController.b().whileTrue(new InstantCommand(() -> arm.setPos(arm.getRawPos()), arm));
        CopilotController.povUp().whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_TOP), arm));
        CopilotController.povDown().whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_BOTTOM), arm));
        CopilotController.povRight().whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_L2), arm));

        CopilotController.start().whileTrue(new Rotate(driveBase, 0.1));
        // CopilotController.start().whileTrue(new ManualArmExtension(arm, 1));
        // CopilotController.back().whileTrue(new ManualArmExtension(arm, -1));
    }

    private void configureTestBindings() {
        DriveController.button(5).whileTrue(new InstantCommand(() -> clamp.setSpeed(-0.3), clamp))
            .onFalse(new InstantCommand(() -> clamp.setSpeed(0.0), clamp));
        DriveController.button(6).whileTrue(new InstantCommand(() -> clamp.setSpeed(0.3), clamp))
            .onFalse(new InstantCommand(() -> clamp.setSpeed(0.0), clamp));

        // DriveController.button(3).whileTrue(new ManualArmRotation(arm, 1));
        // DriveController.button(4).whileTrue(new ManualArmRotation(arm, -1));

        DriveController.button(8).whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_TOP), arm));
        DriveController.button(2).whileTrue(new InstantCommand(() -> arm.setPos(arm.getRawPos()), arm));
        DriveController.button(12).whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_BOTTOM), arm));
        DriveController.button(10).whileTrue(new InstantCommand(() -> arm.setPos(PID.POS_L2), arm));
    }

    /**
     * Returns a command routine to be run during autonomous mode.
     * Routines are defined in {@link frc.robot.auto}.
     */
    public Command getAutonomousCommand() {
        switch(auto_chooser.getSelected()) {
            case "Score":
                return new Score(arm, clamp);
            /*case "ScoreTwo":
                return new ScoreTwo(arm, clamp, driveBase, 30);*/
            case "ScoreTaxi":
                return new ScoreTaxi(arm, clamp, driveBase, 70);
            case "Taxi":
                return new DriveForPeriod(driveBase, -0.25, 70);
            default:
                return null;
        }
    }

    /**
     * Returns a command sequence to be run during test mode.
     */
    public Command getTestCommand() {
        return null;
    }

    /**
     * Zero the selected encoders and reference values.
     * @param driveBase Whether to zero the initialized {@link DriveBase} encoders.
     * @param arm Whether to zero the initialized {@link Arm} encoders.
     * @param clamp Whether to zero the initialized {@link Clamp} encoder.
     */
    public void zeroEncoders(boolean driveBase, boolean arm, boolean clamp) {
        if(driveBase)
            this.driveBase.zero();
        
        if(arm)
            this.arm.zero();

        if(clamp)
            this.clamp.zero();
    }
} 