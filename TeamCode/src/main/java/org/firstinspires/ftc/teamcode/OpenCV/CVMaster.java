package org.firstinspires.ftc.teamcode.OpenCV;

/*
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.LOGGER;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.dashboard;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.isTeleop;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.op;
import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.packet;
 */


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

/** Warren Class that contaions all CV processes, should make it easier to use */
public class CVMaster {
    private OpenCvWebcam webcam;

    private LinearOpMode op;

    //private RFAprilCam cam; // Keep commited out if not using AprilTags

    // Establishing OpenCV Pipelines
    public RedSpikeObserverPipeline posRedLeft = null;
    public BlueSpikeObserverPipeline posBlueLeft = null;


    public RedRightSpikeObserverPipeline posRedRight = null;
    public BlueRightSpikeObserverPipeline posBlueRight = null;

    private boolean isObservingPole = false, isObservingCone = false;

    private boolean isStreaming = false;

    private boolean isRed = true, isBlue = false;

    /**
     * initializes opencv webcam, starts observing spike logs that func is called to general surface
     * log logs that opencv webcam is initialized and recording spike pipeline to general surface log
     */



    public CVMaster() {
        /*
        LOGGER.log(RFLogger.Severity.INFO, "intializing OpenCV");
        webcam =
                OpenCvCameraFactory.getInstance()
                        .createWebcam(op.hardwareMap.get(WebcamName.class, "Webcam 1"));

         */

        //op = p_op;
        //initialize webcam
        webcam = OpenCvCameraFactory.getInstance().createWebcam(op.hardwareMap.get(WebcamName.class, "Webcam 1"));

        observeSpike();



    }

    public void setRed(boolean p_isRed) {
        isRed = p_isRed;
    }

    /** observes spike logs that spike is being observed general surface log */
    public void observeSpike() {
        if (isRed&&!isBlue) {
            posRedLeft = new RedSpikeObserverPipeline();
            webcam.setPipeline(posRedLeft);
        } else if(isRed&&isBlue){
            posBlueLeft = new BlueSpikeObserverPipeline();
            webcam.setPipeline(posBlueLeft);
        }else if(!isRed&&isBlue){
            posBlueRight = new BlueRightSpikeObserverPipeline();
            webcam.setPipeline(posBlueRight);
        }
        else {
            posRedRight = new RedRightSpikeObserverPipeline();
            webcam.setPipeline(posRedRight);
        }
        isObservingPole = true;
        webcam.openCameraDeviceAsync(
                new OpenCvCamera.AsyncCameraOpenListener() {
                    @Override
                    public void onOpened() {
                        /*
                         * Tell the webcam to start streaming images to us! Note that you must make sure
                         * the resolution you specify is supported by the camera. If it is not, an exception
                         * will be thrown.
                         *
                         * Keep in mind that the SDK's UVC driver (what OpenCvWebcam uses under the hood) only
                         * supports streaming from the webcam in the uncompressed YUV image format. This means
                         * that the maximum resolution you can stream at and still get up to 30FPS is 480p (640x480).
                         * Streaming at e.g. 720p will limit you to up to 10FPS and so on and so forth.
                         *
                         * Also, we specify the rotation that the webcam is used in. This is so that the image
                         * from the camera sensor can be rotated such that it is always displayed with the image upright.
                         * For a front facing camera, rotation is defined assuming the user is looking at the screen.
                         * For a rear facing camera or a webcam, rotation is defined assuming the camera is facing
                         * away from the user.
                         */
                        if (isRed&&!isBlue) {
                            webcam.setPipeline(posRedLeft);
                        } else if(isRed&&isBlue){
                            webcam.setPipeline(posBlueLeft);
                        }else if(!isRed&&isBlue){
                            webcam.setPipeline(posBlueRight);
                        }
                        else {
                            webcam.setPipeline(posRedRight);
                        }

                        webcam.startStreaming(640, 480, OpenCvCameraRotation.UPRIGHT);
                        //dashboard.startCameraStream(webcam, 10);  // ------------THIS MAY BE A PROBLEM (check FTC Repo BasicRobot)
                    }

                    @Override
                    public void onError(int errorCode) {
                        /*
                         * This will be called if the camera could not be opened
                         */
                    }
                });
        isStreaming = true;
    }

    /**
     * gets calculated position from spike pipeline logging is done internally
     *
     * @return position from spike pipeline
     */
    public int getRedLeftPosition() {

        int pos = 0;
        if (isRed) pos = posRedLeft.getPosition();
        else pos = posRedRight.getPosition();
        telemetry.addData("cvPosition", pos);
        return pos;
    }

    public int getRedRightPosition() {
        int pos = 0;
        if (isRed) {
            isRed = false;
            observeSpike();
        }
        pos = posRedRight.getPosition();
        telemetry.addData("cvPosition", pos);
        //packet.put("cvPosition", pos);
        return pos;
    }
    public int getBlueLeftPosition() {
        int pos = 0;
        if (!isBlue) {
            isBlue = true;
            observeSpike();
        }
        pos = posBlueLeft.getPosition();
        telemetry.addData("cvPosition", pos);
        telemetry.update(); // May not need this
        return pos;
    }




    public int getBlueRightPosition() {
        int pos = 0;
        if (isRed) {
            isRed = false;
            isBlue = true;
            observeSpike();
        }
        pos = posBlueRight.getPosition();
        telemetry.addData("cvPosition", pos);
        return pos;
    }




    /** switches to apriltag camera logs to general surface log */
    /*
    public void switchToApril() {
        webcam.stopRecordingPipeline();
        cam = new RFAprilCam();
        isStreaming = false;
    }

     */

    public void stop() {
        webcam.stopStreaming();
        //cam.stop(); May also be a problem
    }

    /**
     * updates the aprilTag info if you are currently on aprilTag mode logs to general surface(inside
     * RFAprilCam class)
     */
    /* May also be a problem
    public void update() {
        LOGGER.setLogLevel(RFLogger.Severity.FINEST);
        LOGGER.log("updating camera info");
        if (!isTeleop && op.isStarted() && isStreaming) {
            switchToApril();
            isStreaming = false;
        }
        if(isTeleop&&isStreaming){
            stop();
        }
        if (!isStreaming) {
            cam.update();
        }


    }

     */
}