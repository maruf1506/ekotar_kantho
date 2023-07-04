package com.bestweby.enewz.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.bestweby.enewz.R;

/**
 * Created by Md Sahidul Islam on 19-Jan-19.
 */

public class DialogHelper {

    public static void showDialogPrompt(Activity activity, String title, String message, String positiveButtonText, String negativeButtonText, boolean isCancellable, final DialogActionListener dialogActionListener) {
        showDialog(activity, title, message, positiveButtonText, negativeButtonText, isCancellable, dialogActionListener);
    }

    public static void showMessageDialog(Activity activity, String title, String message, String negativeButtonText) {
        showDialog(activity, title, message, null, negativeButtonText, true, null);
    }

    public static void showActionDialog(Activity activity, String title, String message, String positiveButtonText, DialogActionListener dialogActionListener) {
        showDialog(activity, title, message, positiveButtonText, null, false, dialogActionListener);
    }

    private static void showDialog(Activity activity, String title, String message, String positiveButtonText, String negativeButtonText, boolean isCancellable, final DialogActionListener dialogActionListener) {
        if (activity != null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.DialogTheme);
            if (title != null) {
                alertDialogBuilder.setTitle(title);
            }
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setCancelable(isCancellable);

            if (dialogActionListener != null) {
                alertDialogBuilder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        dialogActionListener.onPositiveClick();
                    }
                });
            }

            if (dialogActionListener != null) {
                alertDialogBuilder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
            }

            alertDialogBuilder.create().show();
        }
    }

    public interface DialogActionListener {
        public void onPositiveClick();
    }

}
