# WeatherServiceManager
Example of multithreading with producer and consumer using BlockingQueue

This application has 2 threads which works as producer and consumer using BlockingQueue:
One is reading data from api.openweathermap.org using Jersey Rest Client.
```java
public class ReadJson implements Runnable {
	public void run() {
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
Application Main class
```java
public class AppMain {
	public static void main(String[] args) {
		BlockingQueue<CurrentWeather> queue = new ArrayBlockingQueue<>(1024);

		Thread thReader = new Thread(new ReadJson(queue));
		thReader.setName("reader Thread");
		thReader.start();

		Thread thWriter = new Thread(new WriteToDataBase(queue));
		thWriter.setName("writer Thread");
		thWriter.start();
	}
}
```