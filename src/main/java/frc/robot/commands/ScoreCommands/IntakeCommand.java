package frc.robot.commands.ScoreCommands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.EndEffectorSubsystem;

public class IntakeCommand extends Command{

    private final EndEffectorSubsystem intakeSubsystem;
    private final double speed;
    private final boolean inverted; 

    public IntakeCommand(EndEffectorSubsystem intakeSubsystem, double speed, boolean inverted){
        this.intakeSubsystem = intakeSubsystem; 
        this.speed = speed;
        this.inverted = inverted; 
        addRequirements(intakeSubsystem);
    }

    @Override 
    public void initialize(){
      intakeSubsystem.setIntakeSpeedDirection(speed, inverted);
    }

    @Override 
    public boolean isFinished(){ 
        return !intakeSubsystem.getSensorInput(); 
    }

    @Override
    public void end(boolean interrupted){
      if(interrupted){
            intakeSubsystem.setIntakeSpeedDirection(0, inverted); 
        }
        else {
            intakeSubsystem.setIntakeSpeedDirection(0, inverted);
        }
    }
}