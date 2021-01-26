package com.shrinqghana.myapplication.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shrinqghana.myapplication.Inc.Connectivity;
import com.shrinqghana.myapplication.Inc.Util;
import com.shrinqghana.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    private Thread network_thread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        if(!Connectivity.isConnectedWifi(getApplicationContext()) && !Connectivity.isConnectedMobile(getApplicationContext())){
             new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog))
                    .setTitle("Alert")
                    .setMessage("GPRS/Wi-Fi is disabled. Please enable GPRS/Wi-Fi from device setting to access the application")

                    // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setCancelable(false)
                    .show();
        } else {

            network_thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    call_audio_list_api();
                }
            });
            network_thread.start();
        }
    }


    private void call_audio_list_api(){

        if(!this.isFinishing() && getApplicationContext() != null){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://104.156.237.47/api/v1/customer/register",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Util.show_log("AudiosListAct", "response: " +  response);
                            if(!StartActivity.this.isFinishing()){
                                try {
                                    JSONObject response_json_object = new JSONObject(response);

                                    if(response_json_object.getString("status").equalsIgnoreCase("success")){
                                        int customer_id = response_json_object.getJSONObject("customer").getInt("customer_id");
                                        String customer_name = response_json_object.getJSONObject("customer").getString("customer_name");
                                        String customer_phone_number = response_json_object.getJSONObject("customer").getString("customer_phone_number");
                                        int points = response_json_object.getJSONObject("customer").getInt("points");
                                        int rate = response_json_object.getInt("rate");
                                        String last_redemption_date = response_json_object.getString("last_redemption");
                                        String customer_vcode_user_id = response_json_object.getJSONObject("customer").getString("customer_vcode_user_id");
                                        String customer_vcode = response_json_object.getJSONObject("customer").getString("customer_vcode");
                                        String customer_vcode_link = response_json_object.getJSONObject("customer").getString("customer_vcode_link");
                                        String access_token = response_json_object.getString("access_token");

                                        Util.setSharedPreferenceInt(getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_ID, customer_id);
                                        Util.setSharedPreferenceString(getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_NAME, customer_name);
                                        Util.setSharedPreferenceString(getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_PHONE_NUMBER, customer_phone_number);
                                        Util.setSharedPreferenceInt(getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_POINTS, points);
                                        Util.setSharedPreferenceInt(getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_RATE, rate);
                                        Util.setSharedPreferenceString(getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_LAST_REDEMPTION_DATE, last_redemption_date);
                                        Util.setSharedPreferenceString(getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_VCODE_USER_ID, customer_vcode_user_id);
                                        Util.setSharedPreferenceString(getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_VCODE, customer_vcode);
                                        Util.setSharedPreferenceString(getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_VCODE_IMG_LINK, customer_vcode_link);
                                        Util.setSharedPreferenceString(getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_ACCESS_TOKEN, access_token);


                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                new AlertDialog.Builder(new ContextThemeWrapper(StartActivity.this, R.style.myDialog))
                                                        .setTitle("Alert")
                                                        .setMessage("Failed to generate access token for your number. Try again later")

                                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                                        .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                finish();
                                                            }
                                                        }).show();
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();

                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            new AlertDialog.Builder(new ContextThemeWrapper(StartActivity.this, R.style.myDialog))
                                                    .setTitle("Alert")
                                                    .setMessage("An unexpected error occured. Try again later")

                                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                                    .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            finish();
                                                        }
                                                    }).show();
                                        }
                                    });
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                @Override
                                public void run() {
                                    new AlertDialog.Builder(new ContextThemeWrapper(StartActivity.this, R.style.myDialog))
                                            .setTitle("Alert")
                                            .setMessage("GPRS/Wi-Fi is disabled. Please enable GPRS/Wi-Fi from device setting to access the application")

                                            // Specifying a listener allows you to take an action before dismissing the dialog.
                                            // The dialog is automatically dismissed when a dialog button is clicked.
                                            .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            }).show();
                                }
                            });
                        }
                    }) {


                /*
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", token);
                    //headers.put("ContentType", "application/json");
                    return headers;
                }
                 */

                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> map = new HashMap<>();
                    map.put("customer_name", "customer_name");
                    map.put("customer_phone_number", "customer_phone_number");
                    map.put("customer_pin", "1234");
                    //Util.show_log("LoginActivity", "Map: " +  map.toString());
                    return map;
                }
            };
            stringRequest.setShouldCache(false);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

}
