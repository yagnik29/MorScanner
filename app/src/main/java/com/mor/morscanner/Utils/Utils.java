package com.mor.morscanner.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;

import androidx.fragment.app.FragmentActivity;

import com.mor.morscanner.R;

public class Utils {


    public static Utils utils;

    public static Dialog dialog_Progress;

    public static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    static boolean bl_Internet;

//    SessionManager sessionManager;

    public Utils(FragmentActivity activity) {

        utils = this;
//        sessionManager = new SessionManager(activity);
//        generalFunc = new GeneralFunctions(activity);
    }


    public static boolean getInternetConnection(Context context) {


        ConnectivityManager ConnectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected() == true) {


            bl_Internet = true;

        } else {


            bl_Internet = false;

        }

        return bl_Internet;

    }


    public static void showProgressDialoug(Context context) {


        Activity activity = (Activity) context;

        dialog_Progress = new Dialog(context);
        dialog_Progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_Progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_Progress.getWindow().setGravity(Gravity.CENTER);

        dialog_Progress.setCanceledOnTouchOutside(true);


        //setting custom layout to dialog_car_variant
        dialog_Progress.setContentView(R.layout.dialoug_progressbar);


        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);

        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;


        dialog_Progress.getWindow().setLayout(width, height);

        if (dialog_Progress != null && !dialog_Progress.isShowing()) {
            dialog_Progress.show();

        }

    }


    public static void dismissDialoug() {


        if (dialog_Progress != null && dialog_Progress.isShowing()) {
            dialog_Progress.dismiss();

        }
    }


}
