
# Empower Robotics

Our code/guide for the 22/23 season.

## Video Guides
The password should be Maddox2536 for both robots 

Pairing Control Hubs to Robot:

https://www.youtube.com/watch?v=XOtzRAZ5HyM

**Hit Start + A and Start + B on each controller to pair

## How To Connect to Robot
1.) On your computer connect to the robot's wifi

* The password should be Maddox2536

2.) Open your browswer and go to 192.168.43.1

* If this doesn't work use 192.168.43.1:8080

3.) You can hit "OnBotJava" to enter the code editor

* From there, you can upload the files found in this github 

## Editing Code (19751)

The two pieces of code you can edit is the DriveForward.java file and the DriverControl.java file

The only place you should edit code should be under the comment:

```bash
    /* Edit Robot Speed Up Here */
```

These are the deafult values for 19751, you can change the speeds here.


```bash
    public double driveSpeed = .8;
    public double armSpeed = 1;
    public double navigationSpeed = .3;
```

To edit autonomous mode (DriveForward.java) look for:

```bash
    static final double     FORWARD_SPEED = 0.2;
    static final double     TURN_SPEED    = 0.5;
```

You can also change the time it drives here:


```bash
    while (opModeIsActive() && (runtime.seconds() < 3.5)) {
```

## Editing Code (18014)

The two pieces of code you can edit is the DriveForward.java file and the Auto.java file

The only place you should edit code should be under the comment:

```bash
    /* Speed Values here */
```

These are the deafult values for 18014, you can change the speeds here.


```bash
    public double speed = 1;
```

To edit autonomous mode (DriveForward.java) look for:

```bash
    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;
```

You can also change the time it drives here:


```bash
    while (opModeIsActive() && (runtime.seconds() < 3.0))  {
```

## Screenshots

Where to find OnBotJava/Network info:
![App Screenshot](https://i.ibb.co/61BJSt6/SS1.png)

How to upload files:

![App Screenshot](https://i.ibb.co/T4Y53BS/SS2.png)

Example of where to edit code:
![App Screenshot](https://i.ibb.co/wMSvXCp/SS3.png)


