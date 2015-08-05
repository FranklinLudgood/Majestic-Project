package com.DeepSix.majestic;
 
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import com.jme3.app.AndroidHarness;
import com.jme3.system.android.AndroidConfigChooser.ConfigType;
import java.util.logging.Level;
import java.util.logging.LogManager;
import com.jme3.input.controls.TouchListener;
import android.hardware.SensorEventListener;
import com.jme3.input.event.TouchEvent;
import GameInput.GameInputManager;

 
public class MainActivity extends AndroidHarness implements SensorEventListener, TouchListener{
 
    /*
     * Note that you can ignore the errors displayed in this file,
     * the android project will build regardless.
     * Install the 'Android' plugin under Tools->Plugins->Available Plugins
     * to get error checks and code completion for the Android project files.
     */
    
    
    //private GameTouchListner listener;
 
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

    public void onSensorChanged(SensorEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void onAccuracyChanged(Sensor event, int arg1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void onTouch(String name, TouchEvent event, float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
}
