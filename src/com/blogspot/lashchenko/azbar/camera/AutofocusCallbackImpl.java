package com.blogspot.lashchenko.azbar.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AutofocusCallbackImpl implements Camera.AutoFocusCallback {

	private static final String TAG = "AutofocusCallbackImpl";

	private static final int AUTOFOCUS_INTERVAL = 1000;

	private CameraManager manager;

	private Handler handler;

	public AutofocusCallbackImpl(CameraManager manager) {
		this.manager = manager;
		this.handler = manager.getAutofocusHandler();
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		if (camera == null) {
			return;
		}

		Log.v(TAG, "AutoFocusCallBackImpl.onAutoFocus(boolean " + success + " ) ");

		manager.setAutofocused(success);

		// Simulate continuous autofocus by sending a focus request every
		// AUTOFOCUS_INTERVAL milliseconds.
		Message msg = handler.obtainMessage();
		handler.sendMessageDelayed(msg, AUTOFOCUS_INTERVAL);
	}
}
