package com.blogspot.lashchenko.azbar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.blogspot.lashchenko.azbar.camera.CameraManager;
import com.blogspot.lashchenko.azbar.processing.GUIProxyBarcodeCallback;

public class AZbarActivity extends Activity {

	private static final String TAG = "AZbarActivity";

	// element to display frames from camera
	private static SurfaceView surfaceView;

	// element to display information from barcode
	private static TextView textView;

	private static View view;

	@SuppressWarnings("unused")
	private static CameraManager manager;

	// Proxy with threads and GUI
	// if need - possible to start new Activity with cool animation or open
	// browser for go to URL, etc ...
	static Handler handler = new Handler() {

		public void handleMessage(Message msg) {
			Log.v(TAG, "handleMessage()");

			String result = msg.getData().getString("result");
			Log.v(TAG, "handleMessage() RESULT " + result);

			view.invalidate();

			if (result == null) {
				textView.setTextColor(Color.RED);
				return;
			}

			textView.setTextColor(Color.GREEN);
			textView.setText(result);

			// Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
			// String term = getSearchTerm();
			// intent.putExtra(SearchManager.QUERY, result);
			// startActivity(intent);
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		surfaceView = (SurfaceView) findViewById(R.id.surface);
		textView = (TextView) findViewById(R.id.text);
		view = (View) findViewById(R.id.viewCustom);

		GUIProxyBarcodeCallback.getInstance().setBarcodeHandler(handler);

		AZbarActivity.manager = new CameraManager(this, surfaceView);
	}
}