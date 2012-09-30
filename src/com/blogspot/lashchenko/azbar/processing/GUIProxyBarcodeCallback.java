package com.blogspot.lashchenko.azbar.processing;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * This class is a test implementation of the BarcodeCallback.<br/>
 * If get the result - just sending it to the GUI thread.
 * 
 * @see com.blogspot.lashchenko.azbar.barcode.BarcodeCallback
 */
public class GUIProxyBarcodeCallback implements BarcodeCallback {

	private static final String TAG = "GUIProxyBarcodeCallback";

	private static GUIProxyBarcodeCallback instance;

	private static Handler handler;

	private GUIProxyBarcodeCallback() {
	}

	public static synchronized GUIProxyBarcodeCallback getInstance() {
		if (instance == null) {
			instance = new GUIProxyBarcodeCallback();
		}
		return instance;
	}

	public void setBarcodeHandler(Handler handler) {
		GUIProxyBarcodeCallback.handler = handler;
	}

	@Override
	public void process(String result, byte[] data, int w, int h) {
		Log.v(TAG, "process() " + result);

		Message msg = handler.obtainMessage();
		Bundle b = new Bundle();
		b.putString("result", result);

		if (result != null) {
			// send image ONLY if barcode processing successful finished.
			b.putInt("w", w);
			b.putInt("h", h);
			b.putByteArray("data", data);
			msg.setData(b);
			handler.sendMessageAtFrontOfQueue(msg);
		} else {
			msg.setData(b);
			handler.sendMessageDelayed(msg, 0);
		}
	}
}
