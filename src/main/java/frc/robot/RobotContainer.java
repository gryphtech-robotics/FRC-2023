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
    private final ArmRotation armRotation = new ArmRotation();
    private final ArmExtension armExtension = new ArmExtension();
    private final Clamp clamp = new Clamp();

    private final SendableChooser<String> auto_chooser = new SendableChooser<>();

    private double extensionTarget = 0;

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
        armExtension.setDefaultCommand(new ExtendArm(armExtension, () -> extensionTarget));
        
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
        // Bumper + Trigger clamp movement
        CopilotController.leftBumper().whileTrue(new InstantCommand(() -> clamp.setSpeed(-0.3), clamp))
            .onFalse(new InstantCommand(() -> clamp.setSpeed(0.0), clamp));
        CopilotController.rightBumper().whileTrue(new InstantCommand(() -> clamp.setSpeed(0.3), clamp))
            .onFalse(new InstantCommand(() -> clamp.setSpeed(0.0), clamp));
        CopilotController.leftTrigger().whileTrue(new InstantCommand(() -> clamp.setSpeed(0.073), clamp))
            .onFalse(new InstantCommand(() -> clamp.setSpeed(0.0), clamp));

        // Arm Rotation with A and Y buttons.
        CopilotController.y().whileTrue(new InstantCommand(() -> armRotation.setSpeed(0.25), armRotation))
            .onFalse(new InstantCommand(() -> armRotation.setSpeed(0.0), clamp));
        CopilotController.a().whileTrue(new InstantCommand(() -> armRotation.setSpeed(-0.25), armRotation))
            .onFalse(new InstantCommand(() -> armRotation.setSpeed(0.0), armRotation));

        // Arm Rotation presets and lock button
        CopilotController.b().whileTrue(new InstantCommand(() -> armRotation.setPos(armRotation.getRawPos()), armRotation));
        CopilotController.povUp().whileTrue(new InstantCommand(() -> armRotation.setPos(PID.POS_TOP), armRotation));
        CopilotController.povDown().whileTrue(new InstantCommand(() -> armRotation.setPos(PID.POS_BOTTOM), armRotation));
        CopilotController.povRight().whileTrue(new InstantCommand(() -> armRotation.setPos(PID.POS_L2), armRotation));

        // Arm Extension incrementing. The fact that armExtension has a default command should just tell it to keep running as you update these values.
        CopilotController.back().whileTrue(new InstantCommand(() -> extensionTarget += 10).repeatedly());
        CopilotController.start().whileTrue(new InstantCommand(() -> extensionTarget -= 10).repeatedly());
    }

    private void configureTestBindings() {
        DriveController.button(5).whileTrue(new InstantCommand(() -> clamp.setSpeed(-0.3), clamp))
            .onFalse(new InstantCommand(() -> clamp.setSpeed(0.0), clamp));
        DriveController.button(6).whileTrue(new InstantCommand(() -> clamp.setSpeed(0.3), clamp))
            .onFalse(new InstantCommand(() -> clamp.setSpeed(0.0), clamp));

        DriveController.button(8).whileTrue(new InstantCommand(() -> armRotation.setPos(PID.POS_TOP), armRotation));
        DriveController.button(2).whileTrue(new InstantCommand(() -> armRotation.setPos(armRotation.getRawPos()), armRotation));
        DriveController.button(12).whileTrue(new InstantCommand(() -> armRotation.setPos(PID.POS_BOTTOM), armRotation));
        DriveController.button(10).whileTrue(new InstantCommand(() -> armRotation.setPos(PID.POS_L2), armRotation));
    }

    /**
     * Returns a command routine to be run during autonomous mode.
     * Routines are defined in {@link frc.robot.auto}.
     */
    public Command getAutonomousCommand() {
        switch(auto_chooser.getSelected()) {
            case "Score":
                return new Score(armRotation, clamp);
            case "ScoreTaxi":
                return new ScoreTaxi(armRotation, clamp, driveBase, 70);
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
     * @param armRotation Whether to zero the initialized {@link ArmRotate} encoders.
     * @param armExtension Whether to zero the initialized {@link ArmExtension} encoders.
     * @param clamp Whether to zero the initialized {@link Clamp} encoder.
     */
    public void zeroEncoders(boolean driveBase, boolean armRotation, boolean armExtension, boolean clamp) {
        if(driveBase)
            this.driveBase.zero();
        
        if(armRotation)
            this.armRotation.zero();

        if(armExtension)
            this.armExtension.zero();

        if(clamp)
            this.clamp.zero();
    }
} 