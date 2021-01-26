package com.shrinqghana.myapplication.Fragments;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shrinqghana.myapplication.Inc.Util;
import com.shrinqghana.myapplication.R;

public class BareExampleFragment extends VCodeScanFragment implements VCodeImageDecoder.VCodeResultListener, VCodeScanView.VCodeScanImageListener,
        VCodeScanView.VCodeScanFragmentListener {

    /*
    VCodeImageDecoder.VCodeResultListener vCodeResultListener;
    VCodeScanImageListener vCodeScanImageListener;
    VCodeScanFragmentListener vCodeScanFragmentListener;

    public void setListener(@Nullable VCodeImageDecoder.VCodeResultListener listener) {
        this.vCodeResultListener = listener;
    }

    public void setListener2(@Nullable VCodeScanImageListener listener) {
        this.vCodeScanImageListener = listener;
    }

    public void setListener3(@Nullable VCodeScanFragmentListener listener) {
        this.vCodeScanFragmentListener = listener;
    }
     */

    public static BareExampleFragment newInstance() {
        BareExampleFragment fragment = new BareExampleFragment();
        //Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Util.show_log("BareExampleFragment", "onCreateView CALLED");

        //setListener(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void gotVCodeResult(byte[] bytes, byte[] bytes1, float[] floats, long l) {
        Util.show_log("BareExampleFragment", "gotVCodeResult CALLED");
    }

    @Override
    public void gotErrorWhileDecoding(Error error) {
        Util.show_log("BareExampleFragment", "gotErrorWhileDecoding CALLED");
    }

    @Override
    public void onFoundUTI(@NonNull String s, @NonNull RectF rectF) {
        Util.show_log("BareExampleFragment", "onFoundUTI CALLED");
    }

    @Override
    public void onCompletedScan(@Nullable Error error, @Nullable String s, @Nullable RectF rectF) {
        Util.show_log("BareExampleFragment", "onCompletedScan CALLED");
    }
}
