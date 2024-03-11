package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.SubsystemConstants;


public class EndEffectorSubsystem extends SubsystemBase {
    
    private final CANSparkMax intakeMotor;
    public boolean intakeMotorInverted; 
    public double intakeMotorSpeed; 
    public static double filteredCurrentIntake;
    public LinearFilter filterIntake;


    private static CANSparkMax shootLead;
    private final CANSparkMax shootFollow;
    public static double filteredCurrentShoot;
    public LinearFilter filterShoot;
    

    private final CANSparkMax liftLeadLeft;
    private final CANSparkMax liftFollowLeft;

    private final CANSparkMax liftLeadRight;
    private final CANSparkMax liftFollowRight;

    static PIDController liftLeadLeftPIDController; 
    PIDController liftLeadRightPIDController; 

    DutyCycleEncoder liftEncoder;
    double liftEncoderPosition; 
    static double liftEncoderSetpoint; 

    private final CANSparkMax hangMotor;
    RelativeEncoder hangEncoder; 
    double hangEncoderPosition; 
    static double hangEncoderSetpoint; 
    PIDController hangPIDController; 

    


    public EndEffectorSubsystem(){
      intakeMotor = new CANSparkMax(SubsystemConstants.kIntakeMotorCANID, MotorType.kBrushless);
      intakeMotorSpeed = 0; 
      intakeMotorInverted = false; 
      filteredCurrentIntake = 0; 
      filterIntake = LinearFilter.movingAverage(SubsystemConstants.kIntakeSampleSize); 

      shootLead = new CANSparkMax(SubsystemConstants.kShootLead, MotorType.kBrushless);
      shootFollow =  new CANSparkMax(SubsystemConstants.kShootFollow, MotorType.kBrushless);
      shootFollow.follow(shootLead, false);
      shootLead.setIdleMode(IdleMode.kCoast);
      shootFollow.setIdleMode(IdleMode.kCoast); 
      filterShoot = LinearFilter.movingAverage(SubsystemConstants.kShootSampleSize); 

      liftLeadLeft = new CANSparkMax(SubsystemConstants.kLiftLeadLeft, MotorType.kBrushless);
      liftLeadLeft.setInverted(true);
      liftFollowLeft =  new CANSparkMax(SubsystemConstants.kLiftFollowLeft, MotorType.kBrushless);
      liftFollowLeft.follow(liftLeadLeft, true);
      liftLeadLeft.setIdleMode(IdleMode.kBrake);
      liftFollowLeft.setIdleMode(IdleMode.kBrake);
      liftLeadLeftPIDController = new PIDController(SubsystemConstants.kLiftLeadLeftP, SubsystemConstants.kLiftLeadLeftI, SubsystemConstants.kLiftLeadLeftD);
      liftLeadLeftPIDController.setTolerance(5, 10);

      liftLeadRight = new CANSparkMax(SubsystemConstants.kLiftLeadRight, MotorType.kBrushless);
      liftLeadRight.setInverted(false);
      liftFollowRight=  new CANSparkMax(SubsystemConstants.kLiftFollowRight, MotorType.kBrushless);
      liftFollowRight.follow(liftLeadRight, false);
      liftLeadRight.setIdleMode(IdleMode.kBrake);
      liftFollowRight.setIdleMode(IdleMode.kBrake);
      liftLeadRightPIDController = new PIDController(SubsystemConstants.kLiftLeadRightP, SubsystemConstants.kLiftLeadRightI, SubsystemConstants.kLiftLeadRightD);
      liftLeadRightPIDController.setTolerance(5, 10);
      lift(Constants.SubsystemConstants.kLiftInitializedSetpoint);


      liftEncoder = new DutyCycleEncoder(SubsystemConstants.kLiftEncoderChannel); 

      liftEncoder.setDistancePerRotation(Math.toRadians(360));
      liftEncoderPosition = liftEncoder.getAbsolutePosition();

      hangMotor = new CANSparkMax(SubsystemConstants.kHangMotorCANID, MotorType.kBrushless); 
      hangMotor.setIdleMode(IdleMode.kBrake);
      hangEncoder = hangMotor.getEncoder();
      hangEncoder.setPosition(0); 
      hangPIDController = new PIDController(SubsystemConstants.kHangP, SubsystemConstants.kHangI, SubsystemConstants.kHangD);

      shootLead.burnFlash(); 
      shootFollow.burnFlash(); 
      liftLeadLeft.burnFlash();
      liftFollowLeft.burnFlash();
      liftLeadRight.burnFlash();
      liftFollowRight.burnFlash();

  }

  public void setIntakeSpeedDirection(double intakeMotorSpeed, boolean intakeMotorInverted){
    this.intakeMotorSpeed = intakeMotorSpeed; 
    this.intakeMotorInverted = intakeMotorInverted;
    intakeMotor.set(intakeMotorSpeed);
    intakeMotor.setInverted(intakeMotorInverted);
  }

  public static void shootLeadMotor(double speed){
    shootLead.set(speed);
  }

  public double getLiftDistance(){
    liftEncoderPosition = liftEncoder.getDistance();
    SmartDashboard.putNumber("LiftCurDist", liftEncoderPosition);
    return liftEncoderPosition;
  }
   
  public void resetliftEncoder(){
    liftEncoder.reset();
  }

  public static void lift(double pose) { 
    liftLeadLeftPIDController.setSetpoint(pose);
    liftEncoderSetpoint = pose;
 }

  public void calculateLift(){
    double outputLift;
    if(liftEncoderSetpoint < liftEncoder.getDistance()) 
        outputLift = MathUtil.clamp(liftLeadLeftPIDController.calculate(liftEncoder.getDistance()), -SubsystemConstants.kLiftLeadLeftMaxPower, SubsystemConstants.kLiftLeadLeftMaxPower);
    else
        outputLift = MathUtil.clamp(liftLeadLeftPIDController.calculate(liftEncoder.getDistance()), -SubsystemConstants.kLiftLeadLeftMaxPower, SubsystemConstants.kLiftLeadLeftMaxPower) ;
    
    liftLeadLeft.set(outputLift);
    liftLeadRight.set(outputLift);
   
   SmartDashboard.putNumber("Lift Output", outputLift);
   SmartDashboard.putNumber("Lift Setpoint", liftEncoderSetpoint);
  }

  public void limelightLift(double speed){

    if((getPose() < Constants.LimelightConstants.kLiftLimitUp) && (getPose() > Constants.LimelightConstants.kLiftLimitDown))
      liftLeadLeft.set(speed);
      liftLeadRight.set(speed);
    if (getPose() > Constants.LimelightConstants.kLiftLimitUp){
      if(speed > 0){
        speed = 0; 
      liftLeadLeft.set(speed);
      liftLeadRight.set(speed);
      }
    }
    else{
      if (speed < 0)
      speed = 0; 
      liftLeadLeft.set(speed);
      liftLeadRight.set(speed);
    }

  }

  public double getPose(){
    return liftEncoder.getDistance();
  }

  public double getHangDistance(){
    hangEncoderPosition = hangEncoder.getPosition();
    SmartDashboard.putNumber("HangDist", hangEncoderPosition);
    return hangEncoderPosition; 
  }

  public void resetEncoderHang(){
    hangEncoder.setPosition(0);    
  }

  public void moveHang(double pose){
    hangPIDController.setSetpoint(pose);
    hangEncoderSetpoint = pose;
  }

  public void calculateHang(){
    double output;
    if(hangEncoderSetpoint > hangEncoder.getPosition())
        output = MathUtil.clamp(hangPIDController.calculate(hangEncoder.getPosition()), -SubsystemConstants.kHangMaxPower, SubsystemConstants.kHangMaxPower);
    else
        output = MathUtil.clamp(hangPIDController.calculate(hangEncoder.getPosition()), -SubsystemConstants.kHangMaxPower, -SubsystemConstants.kHangMaxPower);
    
      hangMotor.set(output);

   SmartDashboard.putNumber("Hang Output", output);
   SmartDashboard.putNumber("Hang Setpoint", hangEncoderSetpoint);
  }

  @Override
  public void periodic(){
    filteredCurrentIntake = filterIntake.calculate(intakeMotor.getOutputCurrent());
    filteredCurrentShoot = filterShoot.calculate(shootLead.getOutputCurrent());
    
    calculateLift();
    getLiftDistance();
    calculateHang();
    getHangDistance();

   }

}
