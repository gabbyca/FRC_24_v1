package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.SubsystemConstants;


public class HangSubsystem extends SubsystemBase {

     private final CANSparkMax hangMotor;

    public HangSubsystem(){
        hangMotor = new CANSparkMax(SubsystemConstants.kHangMotorCANID, MotorType.kBrushless);
        hangMotor.setIdleMode(IdleMode.kBrake); 
        hangMotor.burnFlash(); 
    }

    public void hang(double speed){
        hangMotor.set(speed);
    }

}