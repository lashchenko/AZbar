package com.blogspot.lashchenko.azbar.camera;

import java.io.IOException;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * CameraManager involved in setting up the camera parameters, <br>
 * initialization, setting the various handlers and release resources camera. <br>
 * 
 * <pre>
 * The constructor takes a Context that is used to check the availability of auto-focus on the device 
 * and SurfatseViev instance in which to display frames received from the camera.
 * </pre>
 */
public class CameraManager {

	private static final String TAG = "CameraManager";

	// If the device supports auto-focus, we will handle the priority focus shots after a successful
	private boolean isAutofocusSupported;
	private boolean isAutofocused;

	private Camera camera;

	private Camera.PreviewCallback previewCallback;
	private SurfaceHolder.Callback surfaceCallback;
	private Camera.AutoFocusCallback autofocusCallback;

	private SurfaceView surfaceView;
	private SurfaceHolder holder;

	private int previewWidth;
	private int previewHeight;

	private Handler autofocusHandler = new Handler() {

		public void handleMessage(Message msg) {
			Log.v(TAG, "handleMessage()");

			// When one auto focus pass finishes, start another. This is the closest thing to continuous AF.
			// It does seem to hunt a bit, but I'm not sure what else to do.
			doAutofocus();
		}
	};

	public CameraManager(Context context, SurfaceView surfaceView) {

		isAutofocusSupported = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_AUTOFOCUS);
		Log.v(TAG, "Autofocus on this device is " + isAutofocusSupported);

		previewCallback = new PreviewCallbackImpl(this);
		autofocusCallback = new AutofocusCallbackImpl(this);
		surfaceCallback = new SurfaceHolderCallbackImpl(this);

		this.surfaceView = surfaceView;

		holder = this.surfaceView.getHolder();
		holder.addCallback(surfaceCallback);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void start() {
		if (camera != null) {
			return;
		}

		camera = Camera.open();

		if (camera == null) {
			return;
		}

		camera.setPreviewCallback(previewCallback);

		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e) {
			Log.v(TAG, e.getMessage());
		}

		Parameters params = camera.getParameters();
		previewWidth = params.getPreviewSize().width;
		previewHeight = params.getPreviewSize().height;

		camera.startPreview();
		doAutofocus();
	}

	public void stop() {
		if (camera != null) {
			Log.v(TAG, "stopPreview() free camera");
			camera.stopPreview();
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
	}

	private void doAutofocus() {
		Log.v(TAG, "doAutofocus( ) started");

		if (camera == null) {
			Log.v(TAG, "doAutofocus( ) camera is null.");
			return;
		}

		camera.setPreviewCallback(previewCallback);
		camera.autoFocus(autofocusCallback);
	}

	public boolean isAutofocusSupported() {
		return isAutofocusSupported;
	}

	public boolean isAutofocused() {
		return isAutofocused;
	}

	public void setAutofocused(boolean isAutofocused) {
		this.isAutofocused = isAutofocused;
	}

	public int getPreviewWidth() {
		return previewWidth;
	}

	public int getPreviewHeight() {
		return previewHeight;
	}

	public Handler getAutofocusHandler() {
		return autofocusHandler;
	}
}
