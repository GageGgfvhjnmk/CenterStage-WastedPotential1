package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "OnePlayerMode")

public class MecanumTeleOp extends LinearOpMode {



    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    private DcMotor Intake = null;


    //private Boolean clawClosed = true;

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        frontLeft = hardwareMap.get(DcMotor.class,"frontLeft"); //frontleft, port 0
        frontRight = hardwareMap.get(DcMotor.class,"frontRight");  //frontright, port 1
        backLeft = hardwareMap.get(DcMotor.class,"backLeft"); //backleft, port 3
        backRight = hardwareMap.get(DcMotor.class,"backRight");  //backright, port 2
        Intake = hardwareMap.get(DcMotor.class,"Intake");  //Intake,



        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        Intake.setDirection(DcMotor.Direction.FORWARD);


        waitForStart();

        while (opModeIsActive()) {

            double G1rightStickY = gamepad1.right_stick_y;
            double G1leftStickY = gamepad1.left_stick_y;
            double G1rightStickX = gamepad1.right_stick_x;
            double G1leftStickX = gamepad1.left_stick_x;
            boolean G1rightBumper = gamepad1.right_bumper;
            boolean G1leftBumper = gamepad1.left_bumper;
            boolean G1Y = gamepad1.y;
            boolean G1B = gamepad1.b;
            boolean G1X = gamepad1.x;
            boolean G1A = gamepad1.a;
            double G1RT = -gamepad1.right_trigger;
            double G1LT = gamepad1.left_trigger;






            //Driving movements
            if (G1rightStickX > 0) {  // Clockwise
                frontLeft.setPower(0.5);
                backLeft.setPower(0.5);
                frontRight.setPower(-0.5);
                backRight.setPower(-0.5);
            } else if (G1rightStickX < 0) { // Counterclockwise
                frontLeft.setPower(-0.5);
                backLeft.setPower(-0.5);
                frontRight.setPower(0.5);
                backRight.setPower(0.5);
            } else if (G1leftStickY > 0) { // Backwards
                frontLeft.setPower(-0.5);
                backLeft.setPower(-0.5);
                frontRight.setPower(-0.5);
                backRight.setPower(-0.5);
            } else if (G1leftStickY < 0) { // Forwards
                frontLeft.setPower(.5);
                backLeft.setPower(.5);
                frontRight.setPower(.5);
                backRight.setPower(.5);
            } else if (gamepad1.dpad_right) { //strafe right
                frontLeft.setPower(1);
                frontRight.setPower(-1);
                backLeft.setPower(-1);
                backRight.setPower(1);
            } else if (gamepad1.dpad_left) { //strafe left
                frontLeft.setPower(-1);
                frontRight.setPower(1);
                backLeft.setPower(1);
                backRight.setPower(-1);
            } else if (gamepad1.dpad_up) { // forwards
                frontLeft.setPower(0.65);
                frontRight.setPower(0.65);
                backLeft.setPower(0.65);
                backRight.setPower(0.65);
            } else if (gamepad1.dpad_down) { // backwards
                frontLeft.setPower(-0.65);
                frontRight.setPower(-0.65);
                backLeft.setPower(-0.65);
                backRight.setPower(-0.65);
            } else if (G1Y) { // Intake Forward
                Intake.setPower(.5);
            } else if (G1A) { // Intake Backward
                Intake.setPower(-.5);


            } else {
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
                Intake.setPower(0);
            }

            telemetry.addData("Status", "Running");
            telemetry.update();

        }
    }

}