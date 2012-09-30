package com.blogspot.lashchenko.azbar.camera;

import com.blogspot.lashchenko.azbar.processing.BarcodeCallback;
import com.blogspot.lashchenko.azbar.processing.BarcodeTask;
import com.blogspot.lashchenko.azbar.processing.GUIProxyBarcodeCallback;
import com.blogspot.lashchenko.azbar.processing.ThreadPool;

import android.hardware.Camera;
import android.util.Log;

/**
 * Processing given frames from camera.<br>
 * 
 * @see android.hardware.Camera.PreviewCallback
 */
public class PreviewCallbackImpl implements Camera.PreviewCallback {

	private static final String TAG = "PreviewCallbackImpl";

	private CameraManager manager;

	private ThreadPool poolUnautofocused;
	private ThreadPool poolAutofocused;

	private final int unautofocusedThreads = 2;
	private final int autofocusedThreads = 2;

	private BarcodeCallback barcodeCallback;

	public PreviewCallbackImpl(CameraManager manager) {
		this.manager = manager;

		poolAutofocused = new ThreadPool(autofocusedThreads);
		poolUnautofocused = new ThreadPool(unautofocusedThreads);

		barcodeCallback = GUIProxyBarcodeCallback.getInstance();
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		Log.v(TAG, "PreviewCallback.onPreviewFrame( ... ) started ");

		if (camera == null) {
			return;
		}

		if (manager.isAutofocusSupported() && manager.isAutofocused() && poolAutofocused.isPossibleProcessing()) {
			poolAutofocused.execute(new BarcodeTask(data, manager.getPreviewWidth(), manager.getPreviewHeight(), barcodeCallback));
		} else if (poolUnautofocused.isPossibleProcessing()) {
			poolUnautofocused.execute(new BarcodeTask(data, manager.getPreviewWidth(), manager.getPreviewHeight(), barcodeCallback));
		} else {
			camera.setPreviewCallback(null);
			Log.v(TAG, "All thread running!");
		}
	}
}
