package frc.robot;

import com.revrobotics.CANSparkBase.IdleMode;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.util.Units; 

public final class Constants {
  public static final class DriveConstants {
    // Driving Parameters - Note that these are not the maximum capable speeds of
    // the robot, rather the allowed maximum speedss
    public static  double kMaxSpeedMetersPerSecond = 4.2;
    public static final double kMaxAngularSpeed = 2 * Math.PI; // radians per second

    public static final double kDirectionSlewRate = 999; // radians per second
    public static final double kMagnitudeSlewRate = 999; // percent per second (1 = 100%)
    public static final double kRotationalSlewRate = 2 * 2; // percent per second (1 = 100%)

    // Chassis configuration
    public static final double kTrackWidth = Units.inchesToMeters(21);
    // Distance between centers of right and left wheels on robot
    public static final double kWheelBase = Units.inchesToMeters(32.5);
    // Distance between front and back wheels on robot
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
        new Translation2d(kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(kWheelBase / 2, -kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, kTrackWidth / 2),
        new Translation2d(-kWheelBase / 2, -kTrackWidth / 2));

    // Angular offsets of the modules relative to the chassis in radians
    public static final double kFrontLeftChassisAngularOffset = -Math.PI / 2;
    public static final double kFrontRightChassisAngularOffset = 0;
    public static final double kBackLeftChassisAngularOffset = Math.PI;
    public static final double kBackRightChassisAngularOffset = Math.PI / 2;

    // SPARK MAX CAN IDs
    public static final int kFrontLeftDrivingCanId = 5; 
    public static final int kRearLeftDrivingCanId = 8; 
    public static final int kFrontRightDrivingCanId = 4;
    public static final int kRearRightDrivingCanId = 2; 

    public static final int kFrontLeftTurningCanId = 6;
    public static final int kRearLeftTurningCanId = 7; 
    public static final int kFrontRightTurningCanId = 3;
    public static final int kRearRightTurningCanId = 1;

    public static final boolean kGyroReversed = false;
  }

  public static final class ModuleConstants {
    // The MAXSwerve module can be configured with one of three pinion gears: 12T,
    // 13T, or 14T.
    // This changes the drive speed of the module (a pinion gear with more teeth
    // will result in a
    // robot that drives faster).
    public static final int kDrivingMotorPinionTeeth = 14 ;

    public static final boolean kTurningEncoderInverted = true;

    // Calculations required for driving motor conversion factors and feed forward
    public static final double kDrivingMotorFreeSpeedRps = NeoMotorConstants.kFreeSpeedRpm / 60;
    public static final double kWheelDiameterMeters = 0.0762;
    public static final double kWheelCircumferenceMeters = kWheelDiameterMeters * Math.PI;
    // 45 teeth on the wheel's bevel gear, 22 teeth on the first-stage spur gear, 15
    // teeth on the bevel pinion
    public static final double kDrivingMotorReduction = (45.0 * 22) / (kDrivingMotorPinionTeeth * 15);
    public static final double kDriveWheelFreeSpeedRps = (kDrivingMotorFreeSpeedRps * kWheelCircumferenceMeters)
        / kDrivingMotorReduction;

    public static final double kDrivingEncoderPositionFactor = (kWheelDiameterMeters * Math.PI)
        / kDrivingMotorReduction; // meters
    public static final double kDrivingEncoderVelocityFactor = ((kWheelDiameterMeters * Math.PI)
        / kDrivingMotorReduction) / 60.0; // meters per second

    public static final double kTurningEncoderPositionFactor = (2 * Math.PI); // radians
    public static final double kTurningEncoderVelocityFactor = (2 * Math.PI) / 60.0; // radians per second

    public static final double kTurningEncoderPositionPIDMinInput = 0; // radians
    public static final double kTurningEncoderPositionPIDMaxInput = kTurningEncoderPositionFactor; // radians

    public static final double kDrivingP = 0.04;
    public static final double kDrivingI = 0;
    public static final double kDrivingD = 0;
    public static final double kDrivingFF = 1 / kDriveWheelFreeSpeedRps;
    public static final double kDrivingMinOutput = -1;
    public static final double kDrivingMaxOutput = 1;

    public static final double kTurningP = 1;
    public static final double kTurningI = 0;
    public static final double kTurningD = 0;
    public static final double kTurningFF = 0;
    public static final double kTurningMinOutput = -1;
    public static final double kTurningMaxOutput = 1;
    
    public static final IdleMode kDrivingMotorIdleMode = com.revrobotics.CANSparkBase.IdleMode.kBrake;
    public static final IdleMode kTurningMotorIdleMode = com.revrobotics.CANSparkBase.IdleMode.kBrake;

    public static final int kDrivingMotorCurrentLimit = 50; // amps
    public static final int kTurningMotorCurrentLimit = 20; // amps
  }

  public static final class OIConstants {
    public static final int kDriverControllerPort = 0;
    public static final double kDriveDeadband = .1;
  }

  public static final class AutoConstants {
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kMaxAngularSpeedRadiansPerSecond = Math.PI;
    public static final double kMaxAngularSpeedRadiansPerSecondSquared = Math.PI;

    public static final double kPXController = 1;
    public static final double kPYController = 1;
    public static final double kPThetaController = 1;

    // Constraint for the motion profiled robot angle controller
    public static final TrapezoidProfile.Constraints kThetaControllerConstraints = new TrapezoidProfile.Constraints(
        kMaxAngularSpeedRadiansPerSecond, kMaxAngularSpeedRadiansPerSecondSquared);
  }

  public static final class NeoMotorConstants {
    public static final double kFreeSpeedRpm = 5676;
  }

  public static final class SubsystemConstants {
    public static final int kIntakeMotorCANID = 15; 
    public static final int kIntakeSampleSize = 5; 
    public static final double kIntakeCurrentThreshold = 12; 
    public static final boolean kIntakeDebounce = false; 
    public static final double kIntakeDebounceTime = 1; 

    public static final int kShootLead = 14; 
    public static final int kShootFollow = 13; 
    public static final int kShootSampleSize = 5; 
    public static final int kShootCurrentThreshold = 0; 
    public static final boolean kShootDebounce = false; 
    public static final double kShootDebounceTime = 0.05; 

    public static final int kLiftLeadLeft = 12; 
    public static final int kLiftFollowLeft = 11; 
    public static final double kLiftLeadLeftP = 7.4; 
    public static final double kLiftLeadLeftI = 0.0; 
    public static final double kLiftLeadLeftD = 0;  
    public static final double kLiftLeadLeftMaxPower = 1; 

    public static final int kLiftLeadRight = 9; 
    public static final int kLiftFollowRight = 10; 
    public static final double kLiftLeadRightP = 7.4; 
    public static final double kLiftLeadRightI = 0.0; 
    public static final double kLiftLeadRightD = 0;  
    public static final double kLiftLeadRightMaxPower = 1; 

    public static final int kLiftEncoderChannel = 0; 

  }

  public static final class ScoreCommandHolderConstants {
    public static final double kIntakeSetpoint = 0; 
    public static final double kCompactSetpoint = -0.83; 
    public static final double kAmpSetpoint = -0.19; 
    public static final double kSpeakerSetpoint = -0.04; 
  }





  public static final class JoystickConstants {
    public static final int kXStick1 = 0;
    public static final int kYStick1 = 1;
    public static final int kLeftTrigger = 2;
    public static final int kRightTrigger = 3;
    public static final int kXStick2 = 4;
    public static final int kYStick2 = 5;

    public static final int kJoystick1Port = 0;
    public static final int kJoystick2Port = 1;
  }

}
