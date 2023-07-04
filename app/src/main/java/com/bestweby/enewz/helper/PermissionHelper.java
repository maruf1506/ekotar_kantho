package com.bestweby.enewz.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


/**
 * Created by MD Sahidul Islam on 11/30/2016.
 */

public class PermissionHelper {

    public static final int CAMERA_REQUEST = 400;
    public static final int IMAGE_CAPTURE_PERMISSION = 401;
    public static final int ACCESS_LOCATION = 403;
    public static final int WRITE_EXTERNAL_STORAGE = 405;
    public static final int FILE_UPLOAD = 406;
    public static final int FILE_DOWNLOAD = 407;
    public static final int REQUEST_CALL = 408;

    public static boolean isPermissionGranted(Context context, int implicitPermission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            switch (implicitPermission) {

                case CAMERA_REQUEST:
                    return ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

                case WRITE_EXTERNAL_STORAGE:
                    return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

                case FILE_UPLOAD:
                    return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

                case FILE_DOWNLOAD:
                    return ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

                case ACCESS_LOCATION:
                    return !(ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED);

                case REQUEST_CALL:
                    return ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED;

                default:
                    return false;
            }
        } else {
            return true;
        }
    }

    public static void requestCameraPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
    }

    public static void requestWritePermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE);
    }

    public static void requestUploadPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FILE_UPLOAD);
    }

    public static void requestDownloadPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FILE_DOWNLOAD);
    }

    public static void requestLocationPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_LOCATION);
    }

    public static void requestCallPermission(Context context) {
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
    }
}
