package onno204ServerRunner;

public class Server6T implements Runnable {
	public void run() {

		System.out.println("Starting Test.");
		Utils.Run("spigot.jar", "Test", ServerTypes.Test);
	}
	   
}