/*
Copyright 2021 FIRST Tech Challenge Team FTC
Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.text.SimpleDateFormat;
import java.util.Date;


@TeleOp

public class DriverControl extends OpMode {
    
    
    /* Edit Robot Speed Up Here */
    public double driveSpeed = .8;
    public double armSpeed = 1;
    public double navigationSpeed = .3;
   
   
   
    /* Declare OpMode members. */
    public DcMotor frontLeft = null;
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor frontRight = null;
    public DcMotor arm = null;
    
    public Servo grip = null;
    
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        arm = hardwareMap.get(DcMotor.class, "arm");

        grip = hardwareMap.get(Servo.class, "grip");


        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.FORWARD);
        leftDrive.setDirection(DcMotor.Direction.FORWARD);

        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
        grip.setPosition(1);
        

    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {

    }

    /*
     * Code to run REPEATEDLY after the driver hits PLAY but before they hit STOP
     */
    @Override
        public void loop() {
        if (gamepad1.dpad_left){
            rightDrive.setPower(driveSpeed);
            frontRight.setPower(-driveSpeed);
            frontLeft.setPower(-driveSpeed);
            leftDrive.setPower(driveSpeed);
            
        }else{
            leftDrive.setPower(0);
            frontLeft.setPower(0);
            rightDrive.setPower(0);
            frontRight.setPower(0);
        }
        
        if (gamepad1.dpad_right){
            rightDrive.setPower(-driveSpeed);
            frontRight.setPower(driveSpeed);
            frontLeft.setPower(driveSpeed);
            leftDrive.setPower(-driveSpeed); 
        }else{
            leftDrive.setPower(0);
            frontLeft.setPower(0);
            rightDrive.setPower(0);
            frontRight.setPower(0);
        }
        
        
        if (gamepad1.dpad_up){
            leftDrive.setPower(driveSpeed);
            frontLeft.setPower(driveSpeed);
            rightDrive.setPower(-driveSpeed);
            frontRight.setPower(-driveSpeed);
        }else{
            leftDrive.setPower(0);
            frontLeft.setPower(0);
            rightDrive.setPower(0);
            frontRight.setPower(0);
        }
        
        if (gamepad1.dpad_down){
            leftDrive.setPower(-driveSpeed);
            frontLeft.setPower(-driveSpeed);
            rightDrive.setPower(driveSpeed);
            frontRight.setPower(driveSpeed);

        }else{
            leftDrive.setPower(0);
            frontLeft.setPower(0);
            rightDrive.setPower(0);
            frontRight.setPower(0);
        }
        
        
        
        
        
        if (gamepad1.x){
            rightDrive.setPower(navigationSpeed);
            frontRight.setPower(-navigationSpeed);
            frontLeft.setPower(-navigationSpeed);
            leftDrive.setPower(navigationSpeed);
            
        }else{
            leftDrive.setPower(0);
            frontLeft.setPower(0);
            rightDrive.setPower(0);
            frontRight.setPower(0);
        }
        
        if (gamepad1.b){
            rightDrive.setPower(-navigationSpeed);
            frontRight.setPower(navigationSpeed);
            frontLeft.setPower(navigationSpeed);
            leftDrive.setPower(-navigationSpeed); 
        }else{
            leftDrive.setPower(0);
            frontLeft.setPower(0);
            rightDrive.setPower(0);
            frontRight.setPower(0);
        }
        
        
        if (gamepad1.y){
            leftDrive.setPower(navigationSpeed);
            frontLeft.setPower(navigationSpeed);
            rightDrive.setPower(-navigationSpeed);
            frontRight.setPower(-navigationSpeed);
        }else{
            leftDrive.setPower(0);
            frontLeft.setPower(0);
            rightDrive.setPower(0);
            frontRight.setPower(0);
        }
        
        if (gamepad1.a){
            leftDrive.setPower(-navigationSpeed);
            frontLeft.setPower(-navigationSpeed);
            rightDrive.setPower(navigationSpeed);
            frontRight.setPower(navigationSpeed);

        }else{
            leftDrive.setPower(0);
            frontLeft.setPower(0);
            rightDrive.setPower(0);
            frontRight.setPower(0);
        }
        
        
        //
        //     if (gamepad1.dpad_right){
        //    leftDrive.setPower(100);
        //    frontLeft.setPower(100);
         //   rightDrive.setPower(100);
        //    frontRight.setPower(100);
       // }else{
        //     leftDrive.setPower(0);
        //    frontLeft.setPower(0);
       //     rightDrive.setPower(0);
       //     frontRight.setPower(0);
      //  }
        
        
        if (gamepad1.left_bumper){
            rightDrive.setPower(-driveSpeed);
            frontRight.setPower(-driveSpeed);
            leftDrive.setPower(-0.6);
            frontLeft.setPower(-0.6);
            
        }else {
            leftDrive.setPower(0);
            frontLeft.setPower(0);
            rightDrive.setPower(0);
            frontRight.setPower(0);
        }
        
        if (gamepad1.right_bumper){
            leftDrive.setPower(driveSpeed);
            leftDrive.setPower(driveSpeed);
            rightDrive.setPower(0.6);
            frontRight.setPower(0.6);
            
            
        }else {
            leftDrive.setPower(0);
            frontLeft.setPower(0);
            rightDrive.setPower(0);
            frontRight.setPower(0);
        }
        
        if (gamepad2.dpad_up){
            arm.setPower(-armSpeed);
        }else {
            arm.setPower(0);
        }
        
        if (gamepad2.dpad_down){
            arm.setPower(armSpeed);
        }else {
            arm.setPower(0);
        }
        
        if (gamepad2.y){
            arm.setPower(-0.6);
        }else {
            arm.setPower(0);
        }
        
        if (gamepad2.a){
            arm.setPower(0.6);
        }else {
            arm.setPower(0);
        }
        
        if (gamepad2.x)
        {
            grip.setPosition(1);
            
        }
        if (gamepad2.b){
            grip.setPosition(0);
            
        }
    }
    
   
    
    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
}