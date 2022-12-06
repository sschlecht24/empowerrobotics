OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)relicTemplate.getListener()).getPose(); 
telemetry.addData("Pose", format(pose)); 

if (pose != null) { 
    VectorF trans = pose.getTranslation(); 
    Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES); 
 
    // Extract the X, Y, and Z components of the offset of the target relative to the robot 
    double tX = trans.get(0); 
    double tY = trans.get(1); 
    double tZ = trans.get(2); 
 
    // Extract the rotational components of the target relative to the robot 
    double rX = rot.firstAngle; 
    double rY = rot.secondAngle; 
    double rZ = rot.thirdAngle; 
} 