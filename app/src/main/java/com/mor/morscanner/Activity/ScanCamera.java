package com.mor.morscanner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.mor.morscanner.R;
import com.mor.morscanner.databinding.ActivityScanCameraBinding;

import java.io.IOException;

import timber.log.Timber;

public class ScanCamera extends AppCompatActivity {

    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private ToneGenerator toneGenerator;

    ActivityScanCameraBinding binding;

    ScanCamera objScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScanCameraBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        objScanner = this;

        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);


        initializeDetectorAndSource();


    }

    private void initializeDetectorAndSource() {


        barcodeDetector = new BarcodeDetector.Builder(objScanner)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();


        cameraSource = new CameraSource.Builder(objScanner, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(100.0f)
                .build();


        binding.surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {


                try {
                    if (ActivityCompat.checkSelfPermission(objScanner, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions

                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(binding.surfaceView.getHolder());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {


            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {


            }

            @SuppressLint("MissingPermission")
            @Override
            public void receiveDetections(@NonNull Detector.Detections<Barcode> detections) {

                SparseArray<Barcode> barcode = detections.getDetectedItems();


                if (barcode.size() != 0) {

                    runOnUiThread(new Runnable() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void run() {


                            String strBarcodeData = barcode.valueAt(0).displayValue;

                            Timber.e("Barcode Value %s", strBarcodeData);

                            toneGenerator.startTone(ToneGenerator.TONE_PROP_PROMPT, 150);

                            Intent intent = new Intent();
                            intent.putExtra("barcode", strBarcodeData);
                            setResult(RESULT_OK, intent);

                            finish();


                           /* try {

                                Thread.sleep(1000);


                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }*/

                        }
                    });

                }

            }


        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Objects.requireNonNull(getSupportActionBar()).hide();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Objects.requireNonNull(getSupportActionBar()).hide();
        initializeDetectorAndSource();
    }
}