# WeatherServiceManager
Example of multithreading with producer and consumer using BlockingQueue

This application has 2 threads which works as producer and consumer using BlockingQueue:
One is reading data from api.openweathermap.org using Jersey Rest Client.
```java
public class ReadJson implements Runnable {
	public void run() {
				Client client = Client.create();
				WebResource webResource = client.resource(URL + "?id=" + String.valueOf(cityId) + "&APPID=" + KEY);
	}
}
```

Another is saving data into Mysql database.
```java
public class WriteToDataBase implements Runnable {
	public void run() {
			CurrentWeather currentWeather = queue.take();
			if (currentWeather.getName() == null) {
				break;
			}
			DataOperations.insertCurrentWeather(currentWeather);
	}
}
```
