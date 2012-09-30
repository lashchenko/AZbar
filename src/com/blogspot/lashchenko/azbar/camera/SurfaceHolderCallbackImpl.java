package com.blogspot.lashchenko.azbar.camera;

import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Implementation of android.view.SurfaceHolder.Callback.<br>
 * Handling various conditions such as SurfatseViev creation and destruction.<br>
 * <br>
 * 
 * <pre>
 * The constructor takes an instance of the Camera Manager.
 * When created - initialize the camera, if deleted - frees up camera resources.
 * </pre>
 * 
 * @see android.view.SurfaceHolder.Callback
 * @see android.view.SurfaceView
 * @see com.blogspot.lashchenko.azbar.camera.CameraManager
 */
public class SurfaceHolderCallbackImpl implements SurfaceHolder.Callback {

	private static final String TAG = "SurfaceHolderCallbackImpl";

	private CameraManager manager;

	public SurfaceHolderCallbackImpl(CameraManager manager) {
		this.manager = manager;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.v(TAG, "surfaceCreated( )");
		manager.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Log.v(TAG, "surfaceChanged( )");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.v(TAG, "surfaceDestroyed( )");
		manager.stop();
	}
}
