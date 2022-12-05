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

public class ActualDriverControl extends OpMode {
    
    /* Speed Values here */
    
    public double speed = 1;
    
    /* Declare OpMode members. */
    public DcMotor leftDrive = null;
    public DcMotor rightDrive = null;
    public DcMotor arm = null;
    public DcMotor arm2 = null;
    
    public Servo A = null;
    public Servo B = null;


    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        leftDrive  = hardwareMap.get(DcMotor.class, "left_drive");
        rightDrive = hardwareMap.get(DcMotor.class, "right_drive");
        A = hardwareMap.get(Servo.class, "a");
        B = hardwareMap.get(Servo.class, "b");

        arm = hardwareMap.get(DcMotor.class, "arm");
        arm2 = hardwareMap.get(DcMotor.class, "arm2");



        // Most robots need the motor on one side to be reversed to drive forward
        // Reverse the motor that runs backwards when connected directly to the battery
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        
        
        arm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        arm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        
         B.setPosition(1);
         A.setPosition(0);

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
        if (gamepad1.dpad_up){
            leftDrive.setPower(speed);
            rightDrive.setPower(speed);
        }else{
            leftDrive.setPower(0);
            rightDrive.setPower(0);
        }
        
        
             if (gamepad1.dpad_down){
            leftDrive.setPower(-speed);
            rightDrive.setPower(-speed);
        }else{
            leftDrive.setPower(0);
            rightDrive.setPower(0);
        }
        
        
             if (gamepad1.dpad_left){
            leftDrive.setPower(speed);
            rightDrive.setPower(-speed);
        }else{
            leftDrive.setPower(0);
            rightDrive.setPower(0);
        }
        
             if (gamepad1.dpad_right){
                leftDrive.setPower(-speed);
                rightDrive.setPower(speed);
        }else{
            leftDrive.setPower(0);
            rightDrive.setPower(0);
        }
        
        
        if (gamepad2.dpad_up){
         arm.setPower(speed);
         arm2.setPower(-speed);


        }else {
            arm.setPower(0);
            arm2.setPower(0);

        }
        if (gamepad2.dpad_down){
            arm.setPower(-speed);
            arm2.setPower(speed);
            
            
        }else {
            arm.setPower(0);
            arm2.setPower(0);
            
        }
        

       if (gamepad2.x){
           B.setPosition(1);
           A.setPosition(0);
          
       }
       if (gamepad2.b){
           B.setPosition(0.8);
           A.setPosition(1);
           
       }
       
        
         
    }


    /*
     * Code to run ONCE after the driver hits STOP
     */
    @Override
    public void stop() {

    }
}
