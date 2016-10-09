package com.javafusionphp.app.dataseeder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.javafusionphp.app.beans.City;
import com.javafusionphp.app.dao.DataOperations;

public class InsertCityListDataSeederImpl implements DataSeeder {

	@Override
	public void run() {
		this.insertCityList();
	}

	public void insertCityList() {
		List<City> cities = this.loadFile();
		DataOperations.insertCityList(cities);
	}

	public List<City> loadFile() {
		List<City> cities = new ArrayList<>();
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		try {
			BufferedReader br = new BufferedReader(new FileReader("JsonObjects/city.list.json"));
			JsonElement jsonElement = jsonParser.parse(br);

			// Create generic type
			Type type = new TypeToken<List<City>>() {
			}.getType();

			return gson.fromJson(jsonElement, type);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return cities;
	}

}
