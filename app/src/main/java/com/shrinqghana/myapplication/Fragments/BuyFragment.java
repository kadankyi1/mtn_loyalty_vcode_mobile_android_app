package com.shrinqghana.myapplication.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shrinqghana.myapplication.Activities.MainActivity;
import com.shrinqghana.myapplication.Activities.ScanActivity;
import com.shrinqghana.myapplication.Activities.StartActivity;
import com.shrinqghana.myapplication.Activities.TransactionsActivity;
import com.shrinqghana.myapplication.Inc.Util;
import com.shrinqghana.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuyFragment extends Fragment implements View.OnClickListener  {


    ProgressBar m_buying_progressbar;
    EditText m_code_edittext;
    Button reload_button;
    private Thread network_thread = null;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public BuyFragment() {
        // Required empty public constructor
    }

    public static BuyFragment newInstance(String param1, String param2) {
        BuyFragment fragment = new BuyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_buy, container, false);

        m_buying_progressbar = view.findViewById(R.id.buying_progressbar);
        m_code_edittext = view.findViewById(R.id.dashboard_fragment_textview_points);
        reload_button = view.findViewById(R.id.dashboard_fragment_button_redeem);


        reload_button.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == reload_button.getId()){

            network_thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    call_load_airtime_api();
                }
            });
            network_thread.start();
        }
    }


    private void call_load_airtime_api(){

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                m_code_edittext.setVisibility(View.INVISIBLE);
                reload_button.setVisibility(View.INVISIBLE);
                m_buying_progressbar.setVisibility(View.VISIBLE);
            }
        });

        if(!getActivity().isFinishing() && getActivity().getApplicationContext() != null){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://104.156.237.47/api/v1/customer/airtime",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Util.show_log("AudiosListAct", "response: " +  response);
                            if(!getActivity().isFinishing()){
                                try {
                                    JSONObject response_json_object = new JSONObject(response);

                                    if(response_json_object.getString("status").equalsIgnoreCase("success")){
                                        final String this_status = response_json_object.getString("message");
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                m_buying_progressbar.setVisibility(View.INVISIBLE);
                                                reload_button.setVisibility(View.VISIBLE);
                                                m_code_edittext.setVisibility(View.VISIBLE);
                                                m_code_edittext.setText("");
                                                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog))
                                                        .setTitle("Alert")
                                                        .setMessage(this_status)

                                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.cancel();
                                                            }
                                                        }).show();
                                            }
                                        });
                                    } else {

                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                m_buying_progressbar.setVisibility(View.INVISIBLE);
                                                reload_button.setVisibility(View.VISIBLE);
                                                m_code_edittext.setVisibility(View.VISIBLE);
                                                new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog))
                                                        .setTitle("Alert")
                                                        .setMessage("Airtime reload failed. Try again later")

                                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                                        .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int which) {
                                                                dialog.cancel();
                                                            }
                                                        }).show();
                                            }
                                        });
                                    }
                                } catch (JSONException e) {
                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            m_buying_progressbar.setVisibility(View.INVISIBLE);
                                            reload_button.setVisibility(View.VISIBLE);
                                            m_code_edittext.setVisibility(View.VISIBLE);
                                            new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog))
                                                    .setTitle("Alert")
                                                    .setMessage("An unexpected error occured. Try again later")

                                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                                    .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            dialog.cancel();
                                                        }
                                                    }).show();
                                        }
                                    });
                                    e.printStackTrace();
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
                                    m_buying_progressbar.setVisibility(View.INVISIBLE);
                                    reload_button.setVisibility(View.VISIBLE);
                                    m_code_edittext.setVisibility(View.VISIBLE);
                                }
                            });
                            new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog))
                                    .setTitle("Alert")
                                    .setMessage("GPRS/Wi-Fi is disabled. Please enable GPRS/Wi-Fi from device setting to access the application")

                                    // Specifying a listener allows you to take an action before dismissing the dialog.
                                    // The dialog is automatically dismissed when a dialog button is clicked.
                                    .setPositiveButton("EXIT", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    }).show();
                        }
                    }) {

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

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

}