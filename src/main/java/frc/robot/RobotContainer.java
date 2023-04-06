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
     * Configures the robot container. 
     */
     public RobotContainer() {
        driveBase.setDefaultCommand(new DriveWithJoystick(driveBase, () -> DriveController.getY(), () -> DriveController.getX(), () -> (1 + (-DriveController.getThrottle())) / 2));
        armExtension.setDefaultCommand(new ExtendArm(armExtension, () -> extensionTarget));
        
        configureBindings();

        auto_chooser.setDefaultOption("NO AUTO", "Nothing");
        auto_chooser.addOption("SCORE BOTTOM", "ScoreLow");
        auto_chooser.addOption("SCORE", "Score");
        auto_chooser.addOption("SCORE TOP", "ScoreTop");
        auto_chooser.addOption("SCORE TOP & TAXI", "ScoreTopTaxi");
        auto_chooser.addOption("SCORE CONE", "ScoreCone");
        auto_chooser.addOption("SCORE CONE & TAXI", "ScoreConeTaxi");
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
        CopilotController.y().whileTrue(new InstantCommand(() -> armRotation.setSpeed(0.7), armRotation))
            .onFalse(new InstantCommand(() -> armRotation.setSpeed(0.0), armRotation));
        CopilotController.a().whileTrue(new InstantCommand(() -> armRotation.setSpeed(-0.3), armRotation))
            .onFalse(new InstantCommand(() -> armRotation.setSpeed(0.0), armRotation));

        // Arm Rotation presets and lock button
        CopilotController.b().whileTrue(new InstantCommand(() -> armRotation.setPos(armRotation.getRawPos()), armRotation));
        CopilotController.povUp().whileTrue(new InstantCommand(() -> armRotation.setPos(PID.POS_TOP), armRotation));
        CopilotController.povDown().whileTrue(new InstantCommand(() -> armRotation.setPos(PID.POS_BOTTOM), armRotation));
        CopilotController.povRight().whileTrue(new InstantCommand(() -> armRotation.setPos(PID.POS_L2), armRotation));

        // Arm Extension incrementing. The fact that armExtension has a default command should just tell it to keep running as you update these values.
        CopilotController.back().whileTrue(new InstantCommand(() -> softLimit(100)).repeatedly());
        CopilotController.start().whileTrue(new InstantCommand(() -> softLimit(-100)).repeatedly());
    }

    /**
     * Returns a command routine to be run during autonomous mode.
     * Routines are defined in {@link frc.robot.auto}.
     */
    public Command getAutonomousCommand() {
        switch(auto_chooser.getSelected()) {
            case "ScoreLow": 
                return new SequentialCommandGroup(
                    new InstantCommand(() -> clamp.setSpeed(-0.33), clamp),
                    new WaitUntilCommand(clamp::getLimit),
                    new InstantCommand(() -> clamp.setSpeed(0.0), clamp)
                );
            case "Score":
                return new Score(armRotation, clamp);
            case "ScoreTop": 
                return new SequentialCommandGroup(
                    new InstantCommand(() -> softLimit(AUTO.ARM_EXT_TOP)),
                    new Score(armRotation, clamp, AUTO.ARM_ROT_TOP)  
                );
            case "ScoreTopTaxi": 
                return new SequentialCommandGroup(
                    new InstantCommand(() -> softLimit(AUTO.ARM_EXT_TOP)),
                    new ScoreTaxi(armRotation, clamp, driveBase, AUTO.OPTIMAL_DRIVE_PERIOD, AUTO.ARM_ROT_TOP)  
                );
            case "ScoreCone": 
                return new SequentialCommandGroup(
                    new InstantCommand(() -> clamp.setPos(AUTO.CLAMP_CONE), clamp),
                    new InstantCommand(() -> softLimit(AUTO.ARM_EXT_CONE)),
                    new Score(armRotation, clamp, AUTO.ARM_ROT_CONE)
                );
            case "ScoreConeTaxi":
                return new SequentialCommandGroup(
                    new InstantCommand(() -> clamp.setPos(AUTO.CLAMP_CONE), clamp),
                    new InstantCommand(() -> softLimit(AUTO.ARM_EXT_CONE)),
                    new ScoreTaxi(armRotation, clamp, driveBase, AUTO.OPTIMAL_DRIVE_PERIOD, AUTO.ARM_ROT_CONE)
                );
            case "ScoreTaxi":
                return new ScoreTaxi(armRotation, clamp, driveBase, AUTO.OPTIMAL_DRIVE_PERIOD);
            case "Taxi":
                return new DriveForPeriod(driveBase, -0.25, AUTO.OPTIMAL_DRIVE_PERIOD);
            default:
                return null;
        }
    }

    /**
     * Returns a command sequence to be run during test mode.
     */
    public Command getTestCommand() {
        return new InstantCommand(() -> zeroEncoders(true, true, true, true));
    }

    /**
     * Handle PID target incrementation and soft limits for the arm extension.
     * @param incVal Increment value
     */
    public void softLimit(double incVal) {
        if((incVal + extensionTarget) < PID.ARM_EXT_LIMIT) 
            extensionTarget = PID.ARM_EXT_LIMIT;
        else if ((incVal + extensionTarget) > 0) 
            extensionTarget = 0;
        else
            extensionTarget += incVal;
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