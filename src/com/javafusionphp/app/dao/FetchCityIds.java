package com.javafusionphp.app.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.javafusionphp.app.util.ConnectionHelper;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class FetchCityIds implements Runnable {
	private static final int NUMBER_OF_RECORDS;
	private List<Long> cityIds = null;

	static {
		ResourceBundle bundle = ResourceBundle.getBundle("settings");
		NUMBER_OF_RECORDS = Integer.parseInt(bundle.getString("currentweather.numberofrecords"));
	}

	@Override
	public void run() {
		this.callData();
	}

	public void callData() {
		cityIds = new ArrayList<>();
		Connection connection = null;
		Statement statement = null;
		ResultSet rs = null;

		String sql = "SELECT id FROM weatherservice.city limit 0," + NUMBER_OF_RECORDS;
		try {
			connection = (Connection) ConnectionHelper.getConnection();
			statement = (Statement) connection.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				cityIds.add(rs.getLong("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			ConnectionHelper.close(connection);
		}
	}

	public List<Long> fetchEachCityData() {
		return cityIds;
	}

}
