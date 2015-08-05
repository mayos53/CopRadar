package com.app.copradar.util.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Yossef on 4/26/15.
 */
public class Tools {

    public final static boolean DEBUG = false;
    public final static boolean CONFIRM_SMS = false;


    public static boolean contactExists(Context context, String number) {
        /// number is the phone number
        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String[] mPhoneNumberProjection = {ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME};
        Cursor cur = context.getContentResolver().query(lookupUri, mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }

    public static String convertInputStreamToString(InputStream inputStream)
            throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }


    //add 0 before if there is 1 digit
    public static String numberToString(int number) {
        String str;
        if (number < 10) {
            str = "0" + number;
        } else {
            str = "" + number;
        }
        return str;

    }

    public static void showToast(Activity activity, int textResourceId) {
        Toast toast = Toast.makeText(activity, textResourceId, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }



    public static void showAlert(Activity activity, int title, int message, DialogInterface.OnClickListener okListener) {
        showAlert(activity, title, message, android.R.string.ok, -1, okListener);
    }

    public static void showAlert(Activity activity, int message, DialogInterface.OnClickListener
            okListener) {
        showAlert(activity, -1, message, android.R.string.ok,android.R.string.cancel, okListener);
    }

    public static void showAlert(Activity activity, int title, int message, int ok, int cancel, DialogInterface.OnClickListener
            okListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setMessage(message);
        if(title > 0){
            builder.setTitle(title);
        }
        if (okListener == null) {
            builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            builder.setPositiveButton(ok, okListener);
        }
        if (cancel > 0) {
            builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        builder.show();
    }

    public static void showAlert(Activity activity, int title, int message) {
        showAlert(activity, title, message, null);
    }
    public static String getRawResource(Context context, int rawResource) {
        InputStream is = null;
        try {
            is = context.getResources().openRawResource(rawResource);
            return convertInputStreamToString(is);
        } catch (Exception e) {
            Loger.e("Tools", "", e);
        }
        return null;

    }

    public static String getUdid(Context context) {
        String deviceId = ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if(deviceId == null){ //emulator returns null
            return android.provider.Settings.System.getString(context.getContentResolver(),
                    android.provider.Settings.Secure.ANDROID_ID);
        }
        return deviceId;
    }

    public static void markSmsAsRead(Context context,final String from, final long date) {

        Uri uri = Uri.parse("content://sms/inbox");
        String selection = "address = ? AND date = ?";
        String[] selectionArgs = {from, String.valueOf(date)};

        ContentValues values = new ContentValues();
        values.put("read", true);
        context.getContentResolver().update(uri, values, selection, selectionArgs);
    }

}


