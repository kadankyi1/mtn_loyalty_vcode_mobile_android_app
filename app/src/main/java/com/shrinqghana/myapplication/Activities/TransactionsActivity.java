package com.shrinqghana.myapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shrinqghana.myapplication.DataGenerators.TransactionsDataGenerator;
import com.shrinqghana.myapplication.Inc.Util;
import com.shrinqghana.myapplication.Models.TransactionModel;
import com.shrinqghana.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TransactionsActivity extends AppCompatActivity implements View.OnClickListener {

    public static RecyclerView m_transactions_recyclerview;
    private LinearLayoutManager m_linearlayoutmanager;
    private ImageView m_reload_imageview, back_imageview;
    private ProgressBar m_loading_progressbar;
    private TextView m_last_redemption_date_textview, m_points_textview;
    private Thread network_thread = null;
    int getting = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        back_imageview = findViewById(R.id.back_imageview);
        m_transactions_recyclerview = findViewById(R.id.transaction_fragment_constraintlayout_listitem1);
        m_loading_progressbar = findViewById(R.id.buying_progressbar);
        m_reload_imageview = findViewById(R.id.reload);

        m_last_redemption_date_textview = findViewById(R.id.transaction_fragment_textview_lasttransactiondate);
        m_points_textview = findViewById(R.id.transaction_fragment_textview_points);

        m_last_redemption_date_textview.setText(Util.getSharedPreferenceString(getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_LAST_REDEMPTION_DATE));
        m_points_textview.setText(String.valueOf(Util.getSharedPreferenceInt(getApplicationContext(), Util.SHARED_PREF_KEY_USER_CREDENTIALS_USER_POINTS)));

        m_reload_imageview.setOnClickListener(this);
        back_imageview.setOnClickListener(this);


        m_linearlayoutmanager = new LinearLayoutManager(TransactionsActivity.this);

        m_transactions_recyclerview.setItemViewCacheSize(20);
        m_transactions_recyclerview.setDrawingCacheEnabled(true);
        m_transactions_recyclerview.setHasFixedSize(true);
        m_transactions_recyclerview.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        m_transactions_recyclerview.setLayoutManager(m_linearlayoutmanager);
        m_transactions_recyclerview.setAdapter(new RecyclerViewAdapter());

        call_redemptions_api();
    }


    @Override
    public void onClick(View view) {
        if(view.getId() == m_reload_imageview.getId()){

            network_thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    call_redemptions_api();
                }
            });
            network_thread.start();
        } else if(view.getId() == back_imageview.getId()){
            onBackPressed();
        }
    }


    private class RecyclerViewAdapter extends RecyclerView.Adapter {

        @Override
        public int getItemViewType(int position) {
            if(TransactionsDataGenerator.getAllData().get(position).getRowId() == 0){
                return 0;
            }
            return 1;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder vh;
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reemptions_list_item, parent, false);
            vh = new TransactionViewHolder(v);

            return vh;
        }


        public class TransactionViewHolder extends RecyclerView.ViewHolder {
            private TextView m_merchant_name_textview, m_date_textview, m_amount_textview, m_code_textview;

            public TransactionViewHolder(View v) {
                super(v);
                m_merchant_name_textview = v.findViewById(R.id.transaction_fragment_textview_listitem1_title);
                m_date_textview = v.findViewById(R.id.transaction_fragment_textview_listitem1_date);
                m_amount_textview = v.findViewById(R.id.transaction_fragment_textview_listitem1_points);
                m_code_textview = v.findViewById(R.id.transaction_fragment_textview_code);

            }
        }

        @Override
        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

            ((TransactionViewHolder) holder).m_date_textview.setText(TransactionsDataGenerator.getAllData().get(position).getTransaction_date());
            if(TransactionsDataGenerator.getAllData().get(position).getTransaction_type() == 1){
                ((TransactionViewHolder) holder).m_amount_textview.setTextColor(getResources().getColor(R.color.colorGreenTransctionPoint, null));
                ((TransactionViewHolder) holder).m_merchant_name_textview.setText("Airtime Top Up");
                ((TransactionViewHolder) holder).m_amount_textview.setText("+" + TransactionsDataGenerator.getAllData().get(position).getTransaction_amt());
                ((TransactionViewHolder) holder).m_code_textview.setVisibility(View.GONE);
            } else {
                ((TransactionViewHolder) holder).m_amount_textview.setTextColor(getResources().getColor(R.color.colorRedTransctionPoint, null));
                ((TransactionViewHolder) holder).m_merchant_name_textview.setText(TransactionsDataGenerator.getAllData().get(position).getMerchant_name());
                ((TransactionViewHolder) holder).m_amount_textview.setText("-" + TransactionsDataGenerator.getAllData().get(position).getTransaction_amt());
                ((TransactionViewHolder) holder).m_code_textview.setText("Voucher Code - " + TransactionsDataGenerator.getAllData().get(position).getCode());
                ((TransactionViewHolder) holder).m_code_textview.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return TransactionsDataGenerator.getAllData().size();
        }

    }



    private void call_redemptions_api(){

        if(getting == 0) {
            getting = 1;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    m_transactions_recyclerview.setVisibility(View.INVISIBLE);
                    m_reload_imageview.setVisibility(View.INVISIBLE);
                    m_loading_progressbar.setVisibility(View.VISIBLE);
                }
            });

            if (!TransactionsActivity.this.isFinishing() && getApplicationContext() != null) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://104.156.237.47/api/v1/customer/redemptions/list",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                getting = 0;
                                Util.show_log("AudiosListAct", "response: " + response);
                                if (!TransactionsActivity.this.isFinishing()) {
                                    try {
                                        JSONObject response_json_object = new JSONObject(response);

                                        if (response_json_object.getString("status").equalsIgnoreCase("success")) {
                                            JSONArray linkupsSuggestionsArray = response_json_object.getJSONArray("redemptions");
                                            Util.show_log("AudiosListAct", "linkupsSuggestionsArray: " + linkupsSuggestionsArray.toString());
                                            if (linkupsSuggestionsArray.length() > 0) {
                                                TransactionsDataGenerator.getAllData().clear();

                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        m_transactions_recyclerview.getAdapter().notifyDataSetChanged();
                                                    }
                                                });
                                                for (int i = 0; i < linkupsSuggestionsArray.length(); i++) {
                                                    TransactionModel mine1 = new TransactionModel();
                                                    final JSONObject k = linkupsSuggestionsArray.getJSONObject(i);
                                                    mine1.setTransaction_id(k.getInt("redemption_id"));
                                                    mine1.setMerchant_name(k.getString("merchant_fullname"));
                                                    mine1.setTransaction_date(k.getString("created_at"));
                                                    mine1.setTransaction_amt(k.getInt("redeemed_points"));
                                                    mine1.setTransaction_type(k.getInt("is_not_a_redemption"));
                                                    mine1.setCode(k.getString("redemption_code"));
                                                    TransactionsDataGenerator.addOneData(mine1);

                                                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            if (!TransactionsActivity.this.isFinishing() && m_transactions_recyclerview != null) {
                                                                m_transactions_recyclerview.getAdapter().notifyItemInserted(TransactionsDataGenerator.getAllData().size());
                                                            }
                                                        }
                                                    });
                                                }
                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        m_loading_progressbar.setVisibility(View.INVISIBLE);
                                                        m_reload_imageview.setVisibility(View.INVISIBLE);
                                                        m_transactions_recyclerview.setVisibility(View.VISIBLE);
                                                    }
                                                });
                                            } else {

                                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        m_loading_progressbar.setVisibility(View.INVISIBLE);
                                                        m_reload_imageview.setVisibility(View.INVISIBLE);
                                                        m_transactions_recyclerview.setVisibility(View.VISIBLE);
                                                        Toast.makeText(TransactionsActivity.this.getApplicationContext(), "No Transactions found", Toast.LENGTH_LONG).show();
                                                    }
                                                });
                                            }
                                        } else {
                                            new Handler(Looper.getMainLooper()).post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    m_loading_progressbar.setVisibility(View.INVISIBLE);
                                                    m_transactions_recyclerview.setVisibility(View.INVISIBLE);
                                                    m_reload_imageview.setVisibility(View.VISIBLE);
                                                }
                                            });
                                        }
                                    } catch (JSONException e) {
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(TransactionsActivity.this.getApplicationContext(), "An unexpected error occurred", Toast.LENGTH_LONG).show();
                                                m_loading_progressbar.setVisibility(View.INVISIBLE);
                                                m_transactions_recyclerview.setVisibility(View.INVISIBLE);
                                                m_reload_imageview.setVisibility(View.VISIBLE);
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
                                getting = 0;
                                new Handler(Looper.getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() {
                                        m_loading_progressbar.setVisibility(View.INVISIBLE);
                                        m_transactions_recyclerview.setVisibility(View.INVISIBLE);
                                        m_reload_imageview.setVisibility(View.VISIBLE);
                                    }
                                });
                                new AlertDialog.Builder(new ContextThemeWrapper(TransactionsActivity.this, R.style.myDialog))
                                        .setTitle("Alert")
                                        .setMessage("GPRS/Wi-Fi is disabled. Please enable GPRS/Wi-Fi from device setting to access the application")

                                        // Specifying a listener allows you to take an action before dismissing the dialog.
                                        // The dialog is automatically dismissed when a dialog button is clicked.
                                        .setPositiveButton("exit", new DialogInterface.OnClickListener() {
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

                RequestQueue requestQueue = Volley.newRequestQueue(TransactionsActivity.this.getApplicationContext());
                requestQueue.add(stringRequest);
            }
        }
    }

}
