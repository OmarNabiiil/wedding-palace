package com.weddingpalace.weddingpalaceapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;

public class AlertDialogHelper {

    public static void showAlert(Context context, String title, String message, String positive, String negative, final Callback callback) {
        new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MaterialDialogStyle)).setTitle(title).setMessage(message)
                .setNegativeButton(negative, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onCancel();
                    }
                })
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onSuccess();
                    }
                }).show();
    }

    public static void showAlert(Context context, String title, String message, String positive, final Callback callback) {
        new AlertDialog.Builder(context).setTitle(title).setMessage(message)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callback.onSuccess();
                    }
                }).show();
    }

    public static void showAlert(Context context, String title, String message, String positive) {
        new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MaterialDialogStyle)).setTitle(title).setMessage(message)
                .setPositiveButton(positive, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    public interface Callback {

        public void onSuccess();
        public void onCancel();

    }

}
