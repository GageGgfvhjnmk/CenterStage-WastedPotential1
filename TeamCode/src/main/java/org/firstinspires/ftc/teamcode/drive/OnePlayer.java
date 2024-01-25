package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "OnePlayer")

public class OnePlayer extends LinearOpMode {



    private DcMotor frontLeft = null;
    private DcMotor frontRight = null;
    private DcMotor backLeft = null;
    private DcMotor backRight = null;

    private DcMotor Intake = null;

   private DcMotor slide = null;

   private CRServo drone = null;

    private CRServo conveyor = null;

    private Servo bucket = null;

    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        frontLeft = hardwareMap.get(DcMotor.class,"frontLeft"); //frontleft, port 0
        frontRight = hardwareMap.get(DcMotor.class,"frontRight");  //frontright, port 1
        backLeft = hardwareMap.get(DcMotor.class,"backLeft"); //backleft, port 3
        backRight = hardwareMap.get(DcMotor.class,"backRight");  //backright, port 2
        Intake = hardwareMap.get(DcMotor.class,"Intake");  //Intake
        slide = hardwareMap.get(DcMotor.class,"slide");

        conveyor = hardwareMap.get(CRServo.class,"conveyor"); // Port 5 Expansion Hub
        drone = hardwareMap.get(CRServo.class,"drone");



        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        Intake.setDirection(DcMotor.Direction.FORWARD);
        slide.setDirection(DcMotor.Direction.FORWARD);

        conveyor.setDirection(DcMotorSimple.Direction.FORWARD);


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
            boolean G1RightStick = gamepad1.right_stick_button;
            double G1RT = -gamepad1.right_trigger;
            double G1LT = gamepad1.left_trigger;
            boolean G2A = gamepad2.a;

            double power = 0.65;

            //Driving movements
            if (G1rightStickX > 0) {  // Clockwise
                frontLeft.setPower(.5);
                backLeft.setPower(.5);
                frontRight.setPower(-.5);
                backRight.setPower(-.5);
            } else if (G1rightStickX < 0) { // Counterclockwise
                frontLeft.setPower(-.5);
                backLeft.setPower(-.5);
                frontRight.setPower(.5);
                backRight.setPower(.5);
            } else if (G1leftStickY > 0) { // Backwards
                frontLeft.setPower(-power);
                backLeft.setPower(-power);
                frontRight.setPower(-power);
                backRight.setPower(-power);
            } else if (G1leftStickY < 0) { // Forwards
                frontLeft.setPower(power);
                backLeft.setPower(power);
                frontRight.setPower(power);
                backRight.setPower(power);
            } else if (gamepad1.dpad_right) { //strafe right
                frontLeft.setPower(power);
                frontRight.setPower(-power);
                backLeft.setPower(-power);
                backRight.setPower(power);
            } else if (gamepad1.dpad_left) { //strafe left
                frontLeft.setPower(-power);
                frontRight.setPower(power);
                backLeft.setPower(power);
                backRight.setPower(-power);
            } else if (gamepad1.dpad_up) { // forwards
                frontLeft.setPower(power);
                frontRight.setPower(power);
                backLeft.setPower(power);
                backRight.setPower(power);
            } else if (gamepad1.dpad_down) { // backwards
                frontLeft.setPower(-power);
                frontRight.setPower(-power);
                backLeft.setPower(-power);
                backRight.setPower(-power);
            } else if (G1Y) { // Intake Forward
                Intake.setPower(.33);
                conveyor.setPower(-1);
            } else if (G1A) { // Intake Backward
                Intake.setPower(-.33);
                conveyor.setPower(1);

            } else if (G1leftBumper) {
                slide.setPower(1);


            } else if (G1rightBumper) {
                slide.setPower(-1);
            }else if (G2A){
                drone.setPower(1);
                


            } else {
                frontLeft.setPower(0);
                frontRight.setPower(0);
                backLeft.setPower(0);
                backRight.setPower(0);
                Intake.setPower(0);
                slide.setPower(0);
                conveyor.setPower(0);
                drone.setPower(0);
            }

            telemetry.addData("Status", "Running");
            telemetry.update();

        }
    }

}