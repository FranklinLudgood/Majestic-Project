package com.DeepSix.majestic;
 
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import com.jme3.app.AndroidHarness;
import com.jme3.system.android.AndroidConfigChooser.ConfigType;
import java.util.logging.Level;
import java.util.logging.LogManager;
import android.hardware.SensorEventListener;
import GameInput.GameInputManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.AccountPicker;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.accounts.AccountManager;
import android.os.AsyncTask;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import java.io.IOException;
import android.util.Log;


 
public class MainActivity extends AndroidHarness implements SensorEventListener, OnCancelListener{
    
    public static final String TAG = "Majestic";
    private static final String PREFS_IS_AUTHORIZED = "isAuthorized";
    private static final String PREFS_SELECTED_ACCOUNT = "selectedAccount";
    private static final String AUTHORIZED_SCOPE =  "oauth2:https://www.googleapis.com/auth/drive.appdata " +
                                                    "https://www.googleapis.com/auth/userinfo.profile " +
                                                    "https://www.googleapis.com/auth/plus.me";
    
     private static final String PREFS_AUTH_TOKEN = "authToken";
    
    
    private static final int REQUEST_CODE_RECOVER_PLAY_SERVICES = 1001;
    private static final int REQUEST_GOOGLE_PLAY_ACCOUNT = 999;
    private static final int REQUEST_TOKEN = 2002;
 
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
    private SharedPreferences prefrences;
   
    
 
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
        
        prefrences = PreferenceManager.getDefaultSharedPreferences(this);
        
    }
    
     @Override
    protected void onResume()
    {
        super.onResume();
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS){
            if(GooglePlayServicesUtil.isUserRecoverableError(resultCode) == true){
                Dialog errorDialog =  GooglePlayServicesUtil.getErrorDialog(resultCode, this, 
                    REQUEST_CODE_RECOVER_PLAY_SERVICES, (OnCancelListener) this);
                errorDialog.show();
            }
            } else {
                Toast.makeText(this, "This device is not Supported.", Toast.LENGTH_LONG).show();
                finish();
            }
        //} else if(resultCode == ConnectionResult.SUCCESS && accountName != null){
        //    ConnectToGooglePlay();
        //}
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

    public void onCancel(DialogInterface dialog) { 
         Toast.makeText(this, "Google Play Services must be installed to Play Majestic.", Toast.LENGTH_LONG).show();
         finish();
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
          case REQUEST_CODE_RECOVER_PLAY_SERVICES:
            if (resultCode == RESULT_CANCELED) {
              Toast.makeText(this, "Google Play Services must be installed to Play Majestic.",
                  Toast.LENGTH_SHORT).show();
              finish();
            }
            break;
              
          case REQUEST_GOOGLE_PLAY_ACCOUNT:
              if(resultCode == RESULT_OK){
                String  accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                  prefrences.edit().putBoolean(PREFS_IS_AUTHORIZED, true).putString(PREFS_SELECTED_ACCOUNT, accountName)
                          .apply();
                  invalidateOptionsMenu();
                 new AuthorizationTask().execute(accountName);
              } else if(resultCode == RESULT_CANCELED){
                  Toast.makeText(this, "You must log onto Google Play to play Majestic", Toast.LENGTH_SHORT).show();
                  finish();
              }
              break;
              
          case REQUEST_TOKEN:
              if(resultCode == RESULT_OK){
                  new AuthorizationTask().execute(prefrences.getString(PREFS_SELECTED_ACCOUNT, null));
              }
              break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
    private void ConnectToGooglePlay(){
    
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, 
                                                               "Pick a Google Account to connect to.", null, null, null);
        startActivityForResult(intent, REQUEST_GOOGLE_PLAY_ACCOUNT); 
    }
    
    
    class AuthorizationTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... accountName) {
             String authToken = null;
            try {
                authToken = GoogleAuthUtil.getToken(MainActivity.this,
                        accountName[0], AUTHORIZED_SCOPE);
            } catch (IOException e) {
                Log.e(TAG, "Error getting auth token.", e);
            } catch (UserRecoverableAuthException e) {
                Log.d(TAG, "User recoverable error.");
                cancel(true);
                startActivityForResult(e.getIntent(), REQUEST_TOKEN);
            } catch (GoogleAuthException e) {
                Log.e(TAG, "Error getting auth token.", e);
            }
            return authToken;
        }
        
        @Override
        protected void onPostExecute(String result){
             if (result != null) {
                prefrences.edit().putString(PREFS_AUTH_TOKEN, result).apply();
            }
        }
    }
     
}
