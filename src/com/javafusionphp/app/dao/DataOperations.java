package com.javafusionphp.app.dao;

import java.util.Iterator;
import java.util.List;

import com.javafusionphp.app.beans.City;
import com.javafusionphp.app.beans.CurrentWeather;
import com.javafusionphp.app.beans.Weather;
import com.javafusionphp.app.util.ConnectionHelper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DataOperations {
	public static void insertCurrentWeather(CurrentWeather currentWeatherEach) {
		Connection c = null;
		PreparedStatement ps = null;

		try {
			c = (Connection) ConnectionHelper.getConnection();
			ps = (PreparedStatement) c.prepareStatement(
					"INSERT INTO `currentweather` (`cityid`, `cityName`, `main`, `description`, `temp`, `pressure`) VALUES (?, ?, ?, ?, ?, ?)",
					new String[] { "id" });
			ps.setString(1, String.valueOf(currentWeatherEach.getId()));
			ps.setString(2, String.valueOf(currentWeatherEach.getName()));
			Weather[] weather = currentWeatherEach.getWeather();
			if (weather.length > 0) {
				ps.setString(3, String.valueOf(weather[0].getMain()));
				ps.setString(4, String.valueOf(weather[0].getDescription()));
			} else {
				ps.setString(3, "");
				ps.setString(4, "");
			}

			ps.setString(5, String.valueOf(currentWeatherEach.getMain().getTemp()));
			ps.setString(6, String.valueOf(currentWeatherEach.getMain().getPressure()));
			ps.executeUpdate();
			System.out.println(ps.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(c);
		}
	}

	public static void insertCityList(List<City> cities) {

		Connection c = null;
		PreparedStatement ps = null;

		Iterator<City> city = cities.iterator();

		while (city.hasNext()) {
			try {
				City cityEach = (City) city.next();
				c = (Connection) ConnectionHelper.getConnection();
				ps = (PreparedStatement) c.prepareStatement(
						"INSERT INTO `weatherservice`.`city` (`id`, `name`, `country`) VALUES (?, ?, ?)",
						new String[] { "id" });
				ps.setString(1, String.valueOf(cityEach.get_id()));
				ps.setString(2, cityEach.getName());
				ps.setString(3, cityEach.getCountry());
				ps.executeUpdate();
				// System.out.println(ps.toString());
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				ConnectionHelper.close(c);
			}
		}
	}

}
