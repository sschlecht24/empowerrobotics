/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.robotcore.external.Func;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * This OpMode illustrates the basics of using the Vuforia engine to determine
 * the identity of Vuforia VuMarks encountered on the field. The code is structured as
 * a LinearOpMode. It shares much structure with {@link ConceptVuforiaNavigationWebcam}; we do not here
 * duplicate the core Vuforia documentation found there, but rather instead focus on the
 * differences between the use of Vuforia for navigation vs VuMark identification.
 *
 * @see ConceptVuforiaNavigationWebcam
 * @see VuforiaLocalizer
 * @see VuforiaTrackableDefaultListener
 * see  ftc_app/doc/tutorial/FTC_FieldCoordinateSystemDefinition.pdf
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list.
 *
 * IMPORTANT: In order to use this OpMode, you need to obtain your own Vuforia license key as
 * is explained in {@link ConceptVuforiaNavigationWebcam}.
 */

@Autonomous(name="Balls", group ="LinearOpMode")

public class Vumarks extends LinearOpMode {
    
    public DcMotor frontLeft = null;
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor frontRight = null;
        public Servo grip = null;

    
    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;
    
    private ElapsedTime     runtime = new ElapsedTime();


    static final double     FORWARD_SPEED = 0.2;

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    VuforiaLocalizer vuforia;

    /**
     * This is the webcam we are to use. As with other hardware devices such as motors and
     * servos, this device is identified using the robot configuration tool in the FTC application.
     */
    WebcamName webcamName;
    
 
    
    @Override public void runOpMode() {
        

        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
              grip = hardwareMap.get(Servo.class, "grip");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        
        webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        /*
         * To start up Vuforia, tell it the view that we wish to use for camera monitor (on the RC phone);
         * If no camera monitor is desired, use the parameterless constructor instead (commented out below).
         */
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // OR...  Do Not Activate the Camera Monitor View, to save power
        // VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        /*
         * IMPORTANT: You need to obtain your own license key to use Vuforia. The string below with which
         * 'parameters.vuforiaLicenseKey' is initialized is for illustration only, and will not function.
         * A Vuforia 'Development' license key, can be obtained free of charge from the Vuforia developer
         * web site at https://developer.vuforia.com/license-manager.
         *
         * Vuforia license keys are always 380 characters long, and look as if they contain mostly
         * random data. As an example, here is a example of a fragment of a valid key:
         *      ... yIgIzTqZ4mWjk9wd3cZO9T1axEqzuhxoGlfOOI2dRzKS4T0hQ8kT ...
         * Once you've obtained a license key, copy the string from the Vuforia web site
         * and paste it in to your code on the next line, between the double quotes.
         */
        parameters.vuforiaLicenseKey = "AZ5DcaP/////AAABmSXnGHOEv0uol8LpwV1BBchS+FeYEc0qH6SYlTBtfXD5Iur3LLc6NFNsnWY3cFLIFrtZ1GZP+jfYW1+XO6bO2zDObumn+Y90+Htn69FKWBdZRC1zPnC32K1mrTPHdeF/x0efNsDL1Y1qlYa+l+Vf/vevc+5EqaeffVyp986qcixZbhTX3Ehgcfk+Fd5MW5FWkxnSWxhCAuyIjOl2dMo+iwKSmEXpeZOZ58XBUDgRikJ2UQ3sgEWd3WXKOasS5au6W5TAWqqI595sTkIHIkzEf5GGrYt9jr3/XoMXUz4P3EBZCV3/Lv27SEaNfyGszgoniHiJiZf7M6gOB1CQZgl44VEeyr6rwXY6gD8cWHw3usJR";


        /**
         * We also indicate which camera on the RC we wish to use. For pedagogical purposes,
         * we use the same logic as in {@link ConceptVuforiaNavigationWebcam}.
         */
        parameters.cameraName = webcamName;
        this.vuforia = ClassFactory.getInstance().createVuforia(parameters);

        /**
         * Load the data set containing the VuMarks for Relic Recovery. There's only one trackable
         * in this data set: all three of the VuMarks in the game were created from this one template,
         * but differ in their instance id information.
         * @see VuMarkInstanceId
         */
        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();
        grip.setPosition(1);


        relicTrackables.activate();
        
        slowforward();

        while (opModeIsActive()) {


            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                
                telemetry.addData("VuMark", "%s visible", vuMark);
                break;
                
            }
            else {
                
               slowforward();
                
            }
            
                    telemetry.update();
        }
        
        sleep(3000);

        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);

        if (vuMark == RelicRecoveryVuMark.LEFT){
            
            sleep(1000);
            leftLeft();
            
           
        }
        if (vuMark == RelicRecoveryVuMark.CENTER){
            
            sleep(1000);
            stop();
            
        }
        if (vuMark == RelicRecoveryVuMark.RIGHT){
            
            sleep(1000);
            leftLeft();
            
        }
        
            
        
    }
    
    public void stopStop(){
        leftDrive.setPower(0);
        frontLeft.setPower(0);
        rightDrive.setPower(0);
        frontRight.setPower(0);
    }
    
    public void leftLeft(){
        leftDrive.setTargetPosition(3000);
        frontLeft.setTargetPosition(-3000);
        rightDrive.setTargetPosition(3000);
        frontRight.setTargetPosition(-3000);
        
            rightDrive.setPower(.8);
            frontRight.setPower(-.8);
            frontLeft.setPower(-.8);
            leftDrive.setPower(8);
    }
    
    public void digleftTest(){
        leftDrive.setTargetPosition(1000);
        frontLeft.setTargetPosition(1000);
        rightDrive.setTargetPosition(-3000);
        frontRight.setTargetPosition(-3000);

        leftDrive.setPower(0.1);
        frontLeft.setPower(0.1);
        rightDrive.setPower(0.2);
        frontRight.setPower(0.2);
        
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    } 
    
    public void forward(){
        leftDrive.setTargetPosition(1000);
        frontLeft.setTargetPosition(1000);
        rightDrive.setTargetPosition(-1000);
        frontRight.setTargetPosition(-1000);

        leftDrive.setPower(0.5);
        frontLeft.setPower(0.5);
        rightDrive.setPower(0.5);
        frontRight.setPower(0.5);
        
        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    } 
    
    public void slowforward(){
        while (opModeIsActive() && (runtime.seconds() < 3.5)) {
            leftDrive.setTargetPosition(1000);
            frontLeft.setTargetPosition(1000);
            rightDrive.setTargetPosition(-1000);
            frontRight.setTargetPosition(-1000);

            leftDrive.setPower(0.005);
            frontLeft.setPower(0.005);
            rightDrive.setPower(0.005);
            frontRight.setPower(0.005);
            
            leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
    } 
    
    public void right(){
        leftDrive.setPower(0); 
        frontLeft.setPower(0);
        rightDrive.setPower(0);
        frontRight.setPower(0);
        
        leftDrive.setTargetPosition(-2238);
        frontLeft.setTargetPosition(2238);
        rightDrive.setTargetPosition(-2238);
        frontRight.setTargetPosition(2242);

        leftDrive.setPower(-0.3); 
        frontLeft.setPower(0.3);
        rightDrive.setPower(-0.4);
        frontRight.setPower(0.4);
    
        leftDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);
        frontLeft.setMode(DcMotor.RunMode.RESET_ENCODERS);
        rightDrive.setMode(DcMotor.RunMode.RESET_ENCODERS);
        frontRight.setMode(DcMotor.RunMode.RESET_ENCODERS);

            
        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        

    } 
    
        public void rightstraight(){
        leftDrive.setPower(0); 
        frontLeft.setPower(0);
        rightDrive.setPower(0);
        frontRight.setPower(0);
        
        leftDrive.setTargetPosition(2240);
        frontLeft.setTargetPosition(-2240);
        rightDrive.setTargetPosition(2240);
        frontRight.setTargetPosition(-2238);

        leftDrive.setPower(0.3); 
        frontLeft.setPower(-0.3);
        rightDrive.setPower(0.4);
        frontRight.setPower(-0.4);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        

    } 
    
    
}
