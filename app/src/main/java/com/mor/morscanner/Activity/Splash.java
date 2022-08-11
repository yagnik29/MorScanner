package com.mor.morscanner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.mor.morscanner.Constant.Constant;
import com.mor.morscanner.R;
import com.mor.morscanner.Utils.SessionManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import timber.log.Timber;

public class Splash extends AppCompatActivity {


    private static final int DELAY_FOR_OPENING_LANDING_ACTIVITY = 3000;

    Splash objSplash;
    int MY_PER;

    SessionManager sessionManager;
    int PERMISSION_ALL = 1;

    String[] PERMISSIONS = {
            /*Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,*/
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        objSplash = this;

        sessionManager = new SessionManager(objSplash);




        givePermissions();

    }


    private void givePermissions() {

        if (!hasPermissions(this, PERMISSIONS)) {

            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);

        } else {

            startMainScreen();

        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {

        if (context != null && permissions != null) {

            for (String permission : permissions) {

                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

                    return false;
                }
            }
        }

        return true;
    }
    private void startMainScreen() {

        Integer intKey = sessionManager.getKeyUserid();

        Timber.e("Token %s", intKey);



        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {


                if (intKey == 0) {

                    Intent i = new Intent(objSplash, Login.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    objSplash.finish();
                } else {

                    Intent imain = new Intent(objSplash, Dashboard.class);
                    imain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    imain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(imain);
                    objSplash.finish();


                }


            }
        }, DELAY_FOR_OPENING_LANDING_ACTIVITY);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean allgranted = false;

        Timber.e("size %s", String.valueOf(grantResults.length));

        if (requestCode == MY_PER) {
            Map<String, Integer> perms = new HashMap<>();
            // Initialize the map with both permissions
            perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);

            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for both permissions
                if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//                    Timber.d("Write storage & location services permission granted");
                    startMainScreen();
                    // process the normal flow
                    //else any one or both the permissions are not granted
                } else {
//                    Timber.d("Some permissions are not granted ask again");
                    //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                    //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                        givePermissions();
                    }
                    //permission is denied (and never ask again is  checked)
                    //shouldShowRequestPermissionRationale will return false
                    else {
                        Toast.makeText(this, "Grant the required permissions to use the application.", Toast.LENGTH_LONG).show();
                        //proceed with logic by disabling the related features or quit the app.
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        finish();
                    }
                }
            }

        }


        if (grantResults.length == 1) {
            startMainScreen();

        }
    }

}