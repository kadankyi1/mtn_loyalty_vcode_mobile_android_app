package com.shrinqghana.myapplication.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shrinqghana.myapplication.Activities.ScanActivity;
import com.shrinqghana.myapplication.Activities.TransactionsActivity;
import com.shrinqghana.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment implements View.OnClickListener {

    private ConstraintLayout mScanOptionConstraintLayout, mTransactionsConstraintLayout,
                                mFindVendorsConstraintLayout, mConvertPointsConstraintLayout;

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

        mScanOptionConstraintLayout = view.findViewById(R.id.dashboard_fragment_constraintLayout_scanquickoption);
        mTransactionsConstraintLayout = view.findViewById(R.id.dashboard_fragment_constraintLayout_transactions);
        mConvertPointsConstraintLayout = view.findViewById(R.id.dashboard_fragment_constraintLayout_convertpoints);
        mFindVendorsConstraintLayout = view.findViewById(R.id.dashboard_fragment_constraintLayout_merchants);

        mScanOptionConstraintLayout.setOnClickListener(this);
        mTransactionsConstraintLayout.setOnClickListener(this);
        mConvertPointsConstraintLayout.setOnClickListener(this);
        mFindVendorsConstraintLayout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == mScanOptionConstraintLayout.getId()){
            Intent intent = new Intent(getActivity().getApplicationContext(), ScanActivity.class);
            startActivity(intent);
        } else if(view.getId() == mTransactionsConstraintLayout.getId()){
            Intent intent = new Intent(getActivity().getApplicationContext(), TransactionsActivity.class);
            startActivity(intent);
        } else if(view.getId() == mConvertPointsConstraintLayout.getId()){

        } else if(view.getId() == mFindVendorsConstraintLayout.getId()){

        }
    }
}