package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ScoreCommandHolderConstants;
import frc.robot.Constants.SubsystemConstants;
import frc.robot.subsystems.EndEffectorSubsystem;


public class ScoreCommandHolder extends Command {

    private final EndEffectorSubsystem endEffectorSubsystem;
    

    public ScoreCommandHolder(EndEffectorSubsystem endEffectorSubsystem){
        this.endEffectorSubsystem = endEffectorSubsystem; 
    }

     public SequentialCommandGroup intakeNote(){
        return new SequentialCommandGroup(
            new LiftCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kIntakeFirstSetpoint),
            new LiftCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kIntakeSecondSetpoint),
            new IntakeCommand(endEffectorSubsystem, 0.8, false), 
            new IntakeTimeCommand(endEffectorSubsystem, 0.1, true, 0.3),
            new LiftCommand (endEffectorSubsystem, ScoreCommandHolderConstants.kCompactSetpoint)
        ); 
    }

      public SequentialCommandGroup shootNote(){
        return new SequentialCommandGroup(
            new IntakeTimeCommand(endEffectorSubsystem, 0.7, false, 2),
            new ShootCommand(endEffectorSubsystem, 0),
            compactPosition()
        ); 
    }

    public SequentialCommandGroup scoreAmp(){
        return new SequentialCommandGroup(
            new ShootCommand(endEffectorSubsystem, 0.3),
            new LiftCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kAmpSetpoint)
        ); 
    }

    public SequentialCommandGroup scoreSpeaker(){
        return new SequentialCommandGroup(
            new ShootCommand(endEffectorSubsystem, 0.85),
            new LiftCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kSpeakerSetpoint)
        ); 
    }

    public SequentialCommandGroup compactPosition(){
        return new SequentialCommandGroup(
            new LiftCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kCompactSetpoint),
            new IntakeCommand(endEffectorSubsystem, 0, false ),
            new ShootCommand(endEffectorSubsystem, 0) 
        ); 
    }

    public Command getHangReady(){
        return new LiftCommand(endEffectorSubsystem, SubsystemConstants.kLiftInitializedSetpoint); 
    }

    public SequentialCommandGroup hang(){
        return new SequentialCommandGroup(new LiftCommand(endEffectorSubsystem, SubsystemConstants.kLiftInitializedSetpoint), new HangCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kHangUpSetpoint), new HangCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kHangDownSetpoint) );
    }

    public SequentialCommandGroup outtake(){
        return new SequentialCommandGroup( new Outtake(endEffectorSubsystem, 0.5, true), new WaitCommand(0.05), new Outtake(endEffectorSubsystem, 0, true)); 
    }

     public Command liftDown(){
        return new LiftSetpointAdjuster(endEffectorSubsystem, false);
    }

    public Command liftUp(){
        return new LiftSetpointAdjuster(endEffectorSubsystem, true);
    }

    public Command shootMotorZero(){
        return new ShootCommand(endEffectorSubsystem, 0); 
    }

    public Command intakeMotorZero(){
        return new IntakeCommand(endEffectorSubsystem, 0, false); 
    }

    public Command intakeDown(){
        return new LiftCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kHangSetpoint); 
    }

 
}