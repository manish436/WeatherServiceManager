package com.javafusionphp.app;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.javafusionphp.app.beans.CurrentWeather;
import com.javafusionphp.app.dataseeder.DataSeeder;
import com.javafusionphp.app.dataseeder.InsertCityListDataSeederImpl;

public class AppMain {

	public static void main(String[] args) {

		DataSeeder cityListInserter = new InsertCityListDataSeederImpl();
		Thread threadDataSeeder = new Thread(cityListInserter);
		threadDataSeeder.setName("Data Seeder");
		threadDataSeeder.start();
		try {
			threadDataSeeder.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		BlockingQueue<CurrentWeather> queue = new ArrayBlockingQueue<>(1024);

		ReadJson readerThread = new ReadJson(queue);
		Thread thReader = new Thread(readerThread);
		thReader.setName("reader Thread");
		thReader.start();

		WriteToDataBase writerThread = new WriteToDataBase(queue);
		Thread thWriter = new Thread(writerThread);
		thWriter.setName("writer Thread");
		thWriter.start();

	}

}
