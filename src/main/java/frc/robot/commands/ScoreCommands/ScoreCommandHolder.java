package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.EndEffectorSubsystem;


public class ScoreCommandHolder extends Command {

    private final EndEffectorSubsystem endEffectorSubsystem;
    

    public ScoreCommandHolder(EndEffectorSubsystem endEffectorSubsystem){
        this.endEffectorSubsystem = endEffectorSubsystem; 
    }

    //TEST METHODS
    public Command testIntake(){
       return new IntakeCommand(endEffectorSubsystem, 0.5, false); 
    }

    public Command testShoot(){
        return new ShootCommand(endEffectorSubsystem, 0.8); 
    }

    public Command liftScoreAmp(){
        return new LiftCommand(endEffectorSubsystem, 28); 
    }

    public SequentialCommandGroup liftScoreSpeaker(){
        return new SequentialCommandGroup(
            new LiftCommand(endEffectorSubsystem, 10), 
            new LiftCommand (endEffectorSubsystem, 3.5)
        ); 
    }



    //intake position -- 
    //compact position -- 



     

    public Command shootMotorZero(){
        return new ShootCommand(endEffectorSubsystem, 0); 
    }
    public Command intakeMotorZero(){
        return new IntakeCommand(endEffectorSubsystem, 0, false); 
    }

    //REAL METHODS
    public SequentialCommandGroup name() {
         return new SequentialCommandGroup();
    }
   


}