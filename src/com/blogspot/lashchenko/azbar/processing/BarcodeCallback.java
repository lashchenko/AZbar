package com.blogspot.lashchenko.azbar.processing;

/**
 * Gets the results of image processing.
 */
public interface BarcodeCallback {
	public void process(String result, byte[] data, int w, int h);
}
