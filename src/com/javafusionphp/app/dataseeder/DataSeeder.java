package com.javafusionphp.app.dataseeder;

import java.util.List;
import com.javafusionphp.app.beans.City;

public interface DataSeeder extends Runnable {
	public void insertCityList();

	public List<City> loadFile();

}
