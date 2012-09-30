package com.blogspot.lashchenko.azbar.processing;

import java.util.LinkedList;

public class ThreadPool {

	private final int n;
	private final PoolWorker[] threads;
	private final LinkedList<BarcodeTask> queue;

	public ThreadPool(int nThreads) {
		this.n = nThreads;
		queue = new LinkedList<BarcodeTask>();
		threads = new PoolWorker[nThreads];

		for (int i = 0; i < nThreads; i++) {
			threads[i] = new PoolWorker();
			threads[i].start();
		}
	}

	public boolean isPossibleProcessing() {
		synchronized (queue) {
			return (queue.size() < n);
		}
	}

	public void execute(BarcodeTask task) {
		synchronized (queue) {
			queue.addLast(task);
			queue.notify();
		}
	}

	private class PoolWorker extends Thread {
		public void run() {
			BarcodeTask task;

			while (true) {
				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException ignored) {
						}
					}

					task = queue.removeFirst();
				}

				task.process();
			}
		}
	}
}
