package com.javafusionphp.app;

import java.util.concurrent.BlockingQueue;

import com.javafusionphp.app.beans.CurrentWeather;
import com.javafusionphp.app.dao.DataOperations;

public class WriteToDataBase implements Runnable {
	BlockingQueue<CurrentWeather> queue = null;

	public WriteToDataBase(BlockingQueue<CurrentWeather> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			try {
				CurrentWeather currentWeather = queue.take();
				if (currentWeather.getName() == null) {
					break;
				}
				DataOperations.insertCurrentWeather(currentWeather);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
