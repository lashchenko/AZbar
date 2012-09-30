package com.blogspot.lashchenko.azbar.barcode;

public class AZbar {
	static {
		System.loadLibrary("zbar");
	}

	public native String process(int width, int height, byte[] imgData);
}
