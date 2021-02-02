package com.shrinqghana.myapplication.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import uk.co.airsource.android.common.ui.cameraview.DecodeTask;
import uk.co.airsource.android.common.ui.cameraview.interfaces.CameraDecodeListener;
import uk.co.airsource.android.common.ui.cameraview.interfaces.CameraViewListener;
import vcode.vstenterprises.sdk.decoder.VCodeDecodeWorker;
import vcode.vstenterprises.sdk.decoder.VCodeImageDecoder;
import vcode.vstenterprises.sdk.ui.VCodeScanFragment;
import vcode.vstenterprises.sdk.ui.VCodeScanView;
import vcode.vstenterprises.sdk.utils.VCodeCameraPermission;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shrinqghana.myapplication.Inc.Util;
import com.shrinqghana.myapplication.R;


public class BareExampleFragment extends VCodeScanFragment implements VCodeScanView.VCodeScanFragmentListener
{
    VCodeScanFragmentListener vCodeScanFragmentListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            vCodeScanFragmentListener = (VCodeScanFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement VCodeScanFragmentListener");
        }
    }

    public BareExampleFragment() {}

    public static BareExampleFragment newInstance() {
        BareExampleFragment fragment = new BareExampleFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        this.setListener(this);
        return view;
    }


    public void onDetach() {
        super.onDetach();
        this.vCodeScanFragmentListener = null;
    }

    @Override
    public void decodeTaskCompleted(DecodeTask<VCodeDecodeWorker> task) {
        super.decodeTaskCompleted(task);
        Log.e("BareFragVcode", "decodeTaskCompleted: ");
        Log.e("BareFragVcode", "task: " + task.getFrameBuffer().toString());
    }

    @Override
    public void onFoundUTI(@NonNull String s, @NonNull RectF rectF) {
        Log.e("BareFragVcode", "onFoundUTI: " + s);
        vCodeScanFragmentListener.onFoundUTI(s, rectF);
    }


}

