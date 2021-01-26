package com.shrinqghana.myapplication.Inc;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;

import com.shrinqghana.myapplication.R;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Util {

    public static final String SHARED_PREF_KEY_USER_CREDENTIALS_USER_ID = "SHARED_PREF_KEY_USER_CREDENTIALS_USER_ID";
    public static final String SHARED_PREF_KEY_USER_CREDENTIALS_USER_NAME = "SHARED_PREF_KEY_USER_CREDENTIALS_USER_NAME";
    public static final String SHARED_PREF_KEY_USER_CREDENTIALS_USER_PHONE_NUMBER = "SHARED_PREF_KEY_USER_CREDENTIALS_USER_PHONE_NUMBER";
    public static final String SHARED_PREF_KEY_USER_CREDENTIALS_USER_POINTS = "SHARED_PREF_KEY_USER_CREDENTIALS_USER_POINTS";
    public static final String SHARED_PREF_KEY_USER_CREDENTIALS_USER_RATE = "SHARED_PREF_KEY_USER_CREDENTIALS_USER_RATE";
    public static final String SHARED_PREF_KEY_USER_CREDENTIALS_USER_VCODE_USER_ID = "SHARED_PREF_KEY_USER_CREDENTIALS_USER_VCODE_USER_ID";
    public static final String SHARED_PREF_KEY_USER_CREDENTIALS_USER_LAST_REDEMPTION_DATE = "SHARED_PREF_KEY_USER_CREDENTIALS_USER_LAST_REDEMPTION_DATE";
    public static final String SHARED_PREF_KEY_USER_CREDENTIALS_USER_VCODE = "SHARED_PREF_KEY_USER_CREDENTIALS_USER_VCODE";
    public static final String SHARED_PREF_KEY_USER_CREDENTIALS_USER_VCODE_IMG_LINK = "SHARED_PREF_KEY_USER_CREDENTIALS_USER_VCODE_IMG_LINK";
    public static final String SHARED_PREF_KEY_USER_CREDENTIALS_USER_ACCESS_TOKEN = "SHARED_PREF_KEY_USER_CREDENTIALS_USER_ACCESS_TOKEN";

    //SHOWING LOGS
    public static  void show_log(String title, String log){
        Log.e(title, log);
    }

    // OPENING A FRAGMENT
    public static void open_fragment(FragmentManager fragmentManager, int fragmentContainerId, Fragment newFragment, String fragmentName, int includeAnimation){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(includeAnimation == 1){
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_right, R.anim.slide_out_right);
        } else if (includeAnimation == 2){
            transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (includeAnimation == 3){
            transaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down,R.anim.slide_in_down, R.anim.slide_out_up);
        }
        transaction.addToBackStack(fragmentName);
        transaction.add(fragmentContainerId, newFragment, fragmentName).commit();
    }

    public static void show_dialogType(Context context, String title, String body, boolean canNotBeClosedFromOutSideClick){
        if(context != null) {
            new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(body)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        }
    }


    // GET SHARED PREFERENCE STRING
    public static String getSharedPreferenceString(Context context, String key) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        context = null;
        return preferences.getString(key, "");

    }

    // EDIT SHARED PREFERENCE STRING
    public static void setSharedPreferenceString(Context context, String key, String value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
        context = null;
    }

    // GET SHARED PREFERENCE INT
    public static int getSharedPreferenceInt(Context context, String key) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        context = null;
        return preferences.getInt(key, 0);

    }

    // SET SHARED PREFERENCE INT
    public static void setSharedPreferenceInt(Context context, String key, int value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
        context = null;
    }



    // GET SHARED PREFERENCE INT
    public static int getSharedPreferenceFloat(Context context, String key) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        context = null;
        return preferences.getInt(key, 0);

    }

    // SET SHARED PREFERENCE INT
    public static void setSharedPreferenceFloat(Context context, String key, float value) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
        context = null;
    }



}
