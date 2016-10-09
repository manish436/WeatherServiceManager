package com.javafusionphp.app;

import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.BlockingQueue;

import com.google.gson.Gson;
import com.javafusionphp.app.beans.CurrentWeather;
import com.javafusionphp.app.dao.FetchCityIds;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ReadJson implements Runnable {
	private static final String URL;
	private static final String KEY;
	BlockingQueue<CurrentWeather> queue = null;

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("api");
		URL = bundle.getString("api.url");
		KEY = bundle.getString("api.key");
	}

	public ReadJson(BlockingQueue<CurrentWeather> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		FetchCityIds dataOperations = new FetchCityIds();
		Thread dataThread = new Thread(dataOperations);
		dataThread.start();
		try {
			dataThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<Long> citiesIdsList = dataOperations.fetchEachCityData();
		this.callWebservice(citiesIdsList);
	}

	public void callWebservice(List<Long> citiesIdsLists) {
		Iterator<Long> it = citiesIdsLists.iterator();
		while (it.hasNext()) {
			Long cityId = (Long) it.next();
			CurrentWeather currentWeather = null;
			try {
				Client client = Client.create();
				WebResource webResource = client.resource(URL + "?id=" + String.valueOf(cityId) + "&APPID=" + KEY);
				ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
				}
				currentWeather = new Gson().fromJson(response.getEntity(String.class), CurrentWeather.class);
				if (currentWeather != null) {
					this.queue.put(currentWeather);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			CurrentWeather currentWeather = new CurrentWeather();
			this.queue.put(currentWeather);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
