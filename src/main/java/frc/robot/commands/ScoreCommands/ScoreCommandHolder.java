package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.ScoreCommandHolderConstants;
import frc.robot.commands.AutoCommands.AutoCommandHolder;
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

     public SequentialCommandGroup scoreSpeakerAuto(){
        return new SequentialCommandGroup(
            new ShootCommand(endEffectorSubsystem, 0.85),
            new LiftCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kSpeakerDistanceSetpoint),
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

     public SequentialCommandGroup scoreSpeakerDistance(){
        return new SequentialCommandGroup(
            new ShootCommand(endEffectorSubsystem, 1),
            new LiftCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kSpeakerDistanceSetpoint)
        ); 
    }

    public SequentialCommandGroup outtake(){
        return new SequentialCommandGroup( new Outtake(endEffectorSubsystem, 0.5, true), new WaitLittleCommand(), new Outtake(endEffectorSubsystem, 0, true)); 
    }

    public Command hang(){
        return new LiftCommand(endEffectorSubsystem, ScoreCommandHolderConstants.kIntakeSetpoint); 
    }

    public Command liftDown(){
        return new LiftUp(endEffectorSubsystem);
    }

    public Command liftUp(){
        return new LiftDown(endEffectorSubsystem);
    }

    
    public SequentialCommandGroup autoSpeaker(){
        return new SequentialCommandGroup(scoreSpeakerAuto(), new WaitCommand(), shootNote());
    }


    //TEST METHODS

    public Command testIntake(){
       return new IntakeCommand(endEffectorSubsystem, 0.5, false); 
    }

    public Command testShoot(){
        return new ShootCommand(endEffectorSubsystem, 0.8); 
    }

    public Command liftScoreAmp(){
        return new LiftCommand(endEffectorSubsystem, -0.19); 
    }

    public SequentialCommandGroup liftScoreSpeaker(){
        return new SequentialCommandGroup(
        new LiftCommand(endEffectorSubsystem, -0.1), 
        new LiftCommand(endEffectorSubsystem, -0.04));
    }



    public Command shootMotorZero(){
        return new ShootCommand(endEffectorSubsystem, 0); 
    }
    public Command intakeMotorZero(){
        return new IntakeCommand(endEffectorSubsystem, 0, false); 
    }
   
}