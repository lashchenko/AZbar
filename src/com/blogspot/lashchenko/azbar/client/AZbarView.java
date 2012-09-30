package com.blogspot.lashchenko.azbar.client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * This class drawing laser scanner cross
 */
public class AZbarView extends View {

	private final static int laserColor = Color.RED;
	private final static int alpha = 0xFF;

	public AZbarView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		int w = canvas.getWidth(); // 200
		int h = canvas.getHeight(); // 100

		int indent = (w + h) / 20;
		int border = 3;

		Paint paint = new Paint();

		// draw border
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(border);
		paint.setStyle(Paint.Style.STROKE);
		canvas.drawRect(2 * indent + border / 2, indent + border / 2, w - 2 * indent - border / 2, h - indent - border / 2, paint);

		// Draw a red "laser scanner" line through the middle to show decoding is active
		paint.setColor(laserColor);

		// draw laser cross scanner
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(laserColor);
		paint.setStrokeWidth(2f);

		// 100:0 100:100 ver
		// canvas.drawLine(w/2, 0 + indent + border, w/2, h - indent - border, paint);

		// 0:50 0:200 hor
		canvas.drawLine(0.0f + 2 * indent + border, h / 2, w - 2 * indent - border, h / 2, paint);

		paint.setColor(Color.BLACK);
		paint.setAlpha(alpha / 2);
		// 0:0 -> 200:10
		// 0:90 -> 200:100
		// 0:10 -> 10:90
		// 190:10 -> 200:90

		canvas.drawRect(0, 0, w, indent, paint); // top
		canvas.drawRect(0, h - indent, w, h, paint); // bottom

		canvas.drawRect(0, indent, 2 * indent, h - indent, paint); // left
		canvas.drawRect(w - 2 * indent, indent, w, h - indent, paint); // right

		super.onDraw(canvas);
	}
}