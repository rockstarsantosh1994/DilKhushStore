package com.rockstar.dilkhushstore.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.rockstar.dilkhushstore.LoginActivity;
import com.rockstar.dilkhushstore.R;
import com.shreyaspatil.MaterialDialog.MaterialDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonMethods {
    public final static String PRE_NAME = "appname_prefs";

    public static void setPreference(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(PRE_NAME, 0);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPrefrence(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PRE_NAME, 0);
        return prefs.getString(key, "DNF");
    }

    public static boolean IPAdressValidator(String ip) {
        Pattern pattern;
        Matcher matcher;
        //final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)(\\.[A-Za-z]{welcomeone,})$";
        final String IP_ADDRESS
                = ("((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                + "|[1-9][0-9]|[0-9]))");
        pattern = Pattern.compile(IP_ADDRESS);
        matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
     */
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            //Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            // connected to the mobile provider's data plan
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            Toast.makeText(context, AllKeys.NO_INTERNET_AVAILABLE, Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    public static void showKeyboard(Context ctx, View v) {
        v.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public static void closeKeyboard(Context ctx) {
        InputMethodManager inputMethodManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    public static void hideSoftKeyboard(Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(activity
                        .getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public static void openBrowser(Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static String getYmdString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static Date getYmdDate(String dateString) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void showDialogWindowForSuccess(Context context,String title,String message) {
        MaterialDialog mDialog = new MaterialDialog.Builder((Activity) context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setAnimation(R.raw.success_exploration)
                .setPositiveButton("Ok",
                        (dialogInterface, which) -> {
                            // Delete Operation
                            dialogInterface.dismiss();
                            ((Activity) context).finish();
                        })
                //.setNegativeButton("Cancel", (dialogInterface, which) -> dialogInterface.dismiss())
                .build();

        // Show Dialog
        mDialog.show();
    }
}
