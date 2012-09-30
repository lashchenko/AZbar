package com.blogspot.lashchenko.azbar.processing;

import android.util.Log;

import com.blogspot.lashchenko.azbar.barcode.AZbar;

/**
 * Class performs asynchronous processing of images. <br/>
 * Will be instanced inside Camera.PreviewCallback and using inside ThreadPool.
 * 
 * <pre>
 * The <b>constructor</b> method accepts a byte array, width, height and BarcodeCallback implementation 
 * the width and height of an image taken from the outer class.
 * </pre>
 */
public class BarcodeTask {

	private static final String TAG = "BarcodeTask";

	private static final AZbar AZBAR = new AZbar();

	private BarcodeCallback callback;

	private byte[] data;
	private int width;
	private int height;

	String result;

	public BarcodeTask(byte[] data, int width, int height, BarcodeCallback callback) {
		this.data = data;
		this.width = width;
		this.height = height;

		this.callback = callback;
	}

	/**
	 * This method will be called from thread inside ThreadPool.<br/>
	 * Result will be sending to BarcodeCallback.
	 * 
	 * @see com.blogspot.lashchenko.azbar.barcode.BarcodeCallback
	 */
	public void process() {
		Log.v(TAG, "process()");

		start();
		end();
	}

	private void start() {
		Log.v(TAG, "start()");

		if (data == null) {
			Log.v(TAG, "byte[] data is NULL! Nothing to handling.");
			return;
		}

		String decoded = AZBAR.process(width, height, data);
		result = decoded;

		// if( decoded != null ) {
		// BarcodeContent content = BarcodeParser.getInstance().parse(decoded);
		// mResult = content.toString();
		// }
	}

	private void end() {
		Log.v(TAG, "end()");
		callback.process(result, data, width, height);
	}
}
