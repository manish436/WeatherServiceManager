package com.javafusionphp.app.beans;

public class Sys {
	private String sunset;

	private String sunrise;

	private String country;

	public String getSunset() {
		return sunset;
	}

	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	public String getSunrise() {
		return sunrise;
	}

	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "ClassPojo [sunset = " + sunset + ", sunrise = " + sunrise + ", country = " + country + "]";
	}
}
