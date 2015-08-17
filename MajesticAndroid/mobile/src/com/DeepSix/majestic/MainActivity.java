package com.DeepSix.majestic;
 
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import com.jme3.app.AndroidHarness;
import com.jme3.system.android.AndroidConfigChooser.ConfigType;
import java.util.logging.Level;
import java.util.logging.LogManager;
//import com.jme3.input.controls.TouchListener;
//import com.jme3.input.InputManager;
import android.hardware.SensorEventListener;
//import com.jme3.input.event.TouchEvent;
import GameInput.GameInputManager;
import android.hardware.SensorManager;
import android.os.Bundle;

 
public class MainActivity extends AndroidHarness implements SensorEventListener {
 
    /*
     * Note that you can ignore the errors displayed in this file,
     * the android project will build regardless.
     * Install the 'Android' plugin under Tools->Plugins->Available Plugins
     * to get error checks and code completion for the Android project files.
     */
    private SensorManager sensorManager;
    private float[] rotationMatrix;
    private float[] accelerationValues;
    private float[] magneticValues;
    private float[] orientationValues;
    private boolean rotationMatrixGenerated;
    
 
    public MainActivity(){
        // Set the application class to run
        appClass = "mygame.Main";
        // Try ConfigType.FASTEST; or ConfigType.LEGACY if you have problems
        eglConfigType = ConfigType.BEST;
        // Exit Dialog title & message
        exitDialogTitle = "Exit?";
        exitDialogMessage = "Press Yes";
        // Enable verbose logging
        eglConfigVerboseLogging = false;
        // Choose screen orientation
        screenOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        // Enable MouseEvents being generated from TouchEvents (default = true)
        mouseEventsEnabled = true;
        // Set the default logging level (default=Level.INFO, Level.ALL=All Debug Info)
        LogManager.getLogManager().getLogger("").setLevel(Level.INFO);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        
        // Get a reference to the sensor service
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        rotationMatrix = new float[16];
        orientationValues = new float[3];
        rotationMatrixGenerated = false;
    }
    
     @Override
    protected void onResume()
    {
        super.onResume();
        RegisterSensors();
    }
     
     @Override
    protected void onPause()
    {
        super.onPause();
        // Unregister updates from sensors
        sensorManager.unregisterListener(this);
    }
    
    private void RegisterSensors(){
        
        sensorManager.registerListener(this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                    SensorManager.SENSOR_DELAY_NORMAL);
        
        sensorManager.registerListener(this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                    SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        
        switch(event.sensor.getType()){
            
            case Sensor.TYPE_ACCELEROMETER:
                    accelerationValues = event.values.clone();
                    generateRotationMatrix();
                break;
                
            case Sensor.TYPE_MAGNETIC_FIELD:
                    magneticValues = event.values.clone();
                    generateRotationMatrix();
                break;
                
        }
        
        if(rotationMatrixGenerated){
            SensorManager.getOrientation(rotationMatrix, orientationValues);
            GameInputManager.GetInstance().SendOrientationEvent(orientationValues[0], 
                                           orientationValues[1], orientationValues[2]);
        }
                
                
        
        
    }
    
     @Override
    public void onAccuracyChanged(Sensor event, int accuracy) {
         
    }
    
    
    
    private void generateRotationMatrix() {
        
        if(accelerationValues != null && magneticValues != null)
        {
            rotationMatrixGenerated =
                    SensorManager.getRotationMatrix(rotationMatrix,
                    null,
                    accelerationValues,
                    magneticValues);
         }
        else
            rotationMatrixGenerated = false;
    }
     
}
