package com.shrinqghana.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shrinqghana.myapplication.Activities.ScanActivity;
import com.shrinqghana.myapplication.Activities.TransactionsActivity;
import com.shrinqghana.myapplication.Inc.Util;
import com.shrinqghana.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    private ConstraintLayout mScanOptionConstraintLayout, mTransactionsConstraintLayout,
                                mFindVendorsConstraintLayout, mConvertPointsConstraintLayout;

    private TextView m_last_redemption_date_textview, m_points_textview;
    private Button m_redeem_button;
    private ImageView m_vcode_img;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        m_redeem_button = view.findViewById(R.id.dashboard_fragment_button_redeem);
        mScanOptionConstraintLayout = view.findViewById(R.id.dashboard_fragment_constraintLayout_scanquickoption);
        mTransactionsConstraintLayout = view.findViewById(R.id.dashboard_fragment_constraintLayout_transactions);
        mConvertPointsConstraintLayout = view.findViewById(R.id.dashboard_fragment_constraintLayout_convertpoints);
        mFindVendorsConstraintLayout = view.findViewById(R.id.dashboard_fragment_constraintLayout_merchants);
        m_vcode_img = view.findViewById(R.id.vcode_img);

        
        m_last_redemption_date_textview = view.findViewById(R.id.dashboard_fragment_textview_lasttransactiondate);
        m_points_textview = view.findViewById(R.id.dashboard_fragment_textview_points);

        m_last_redemption_date_textview.setText(Util.getSharedPreferenceString(getActivity().getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_LAST_REDEMPTION_DATE));
        m_points_textview.setText(String.valueOf(Util.getSharedPreferenceInt(getActivity().getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_POINTS)));

        Glide.with(getActivity().getApplicationContext())
                .load(Util.getSharedPreferenceString(getActivity().getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_VCODE_IMG_LINK))
                .into(m_vcode_img);

        m_redeem_button.setOnClickListener(this);
        mScanOptionConstraintLayout.setOnClickListener(this);
        mTransactionsConstraintLayout.setOnClickListener(this);
        mConvertPointsConstraintLayout.setOnClickListener(this);
        mFindVendorsConstraintLayout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == mScanOptionConstraintLayout.getId() || view.getId() == m_redeem_button.getId()){
            Intent intent = new Intent(getActivity().getApplicationContext(), ScanActivity.class);
            startActivity(intent);
        } else if(view.getId() == mTransactionsConstraintLayout.getId()){
            Intent intent = new Intent(getActivity().getApplicationContext(), TransactionsActivity.class);
            startActivity(intent);
        } else if(view.getId() == mConvertPointsConstraintLayout.getId()){

        } else if(view.getId() == mFindVendorsConstraintLayout.getId()){

        }
    }


    /*
    private void call_audio_list_api(final String token){

        if(!this.isFinishing() && getApplicationContext() != null){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    m_recyclerview.setVisibility(View.INVISIBLE);
                    m_loading_progressbar.setVisibility(View.VISIBLE);
                }
            });

            Util.show_log_in_console("AudiosListAct", "\n token: " + token);


            StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.LINK_AUDIO_LIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Util.show_log_in_console("AudiosListAct", "response: " +  response);
                            if(!AudiosListActivity.this.isFinishing()){
                                try {
                                    JSONObject response_json_object = new JSONObject(response);

                                    //Toast.makeText(getApplicationContext(), response_json_object.getString("message"), Toast.LENGTH_LONG).show();
                                    if(response_json_object.getString("status").equalsIgnoreCase("success")){
                                        JSONObject audios_list_json_object = response_json_object.getJSONObject("data").getJSONObject("data");

                                        Util.show_log_in_console("AudiosListAct", "response: " +  audios_list_json_object);

                                    } else {
                                        m_loading_progressbar.setVisibility(View.INVISIBLE);
                                        m_recyclerview.setVisibility(View.VISIBLE);
                                        Toast.makeText(getApplicationContext(), response_json_object.getString("message"), Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "An unexpected error occurred.", Toast.LENGTH_LONG).show();
                                    m_loading_progressbar.setVisibility(View.INVISIBLE);
                                    m_recyclerview.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Check your internet connection and swipe down to try again", Toast.LENGTH_LONG).show();
                            m_loading_progressbar.setVisibility(View.INVISIBLE);
                            m_recyclerview.setVisibility(View.INVISIBLE);
                        }
                    }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Accept", "application/json");
                    headers.put("Authorization", token);
                    //headers.put("ContentType", "application/json");
                    return headers;
                }


                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> map = new HashMap<>();
                    map.put("user_phone_number", phone_number);
                    map.put("password", password);
                    Util.show_log_in_console("LoginActivity", "Map: " +  map.toString());
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
    */
}