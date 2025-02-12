package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-32, 65, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(-36, 35, Math.toRadians(-90)))

                                .build()


                        /* Right(MeepMeep) Parking BlueRight (Actual)
                        drive.trajectorySequenceBuilder(new Pose2d(-32, 65, Math.toRadians(90)))
                                .lineToLinearHeading(new Pose2d(-36, 30, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(-36, 12, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(35, 12, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(45, 40, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(45, 10, Math.toRadians(0)))
                                .lineToLinearHeading(new Pose2d(55, 10, Math.toRadians(0)))
                                .build()
                         */
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}