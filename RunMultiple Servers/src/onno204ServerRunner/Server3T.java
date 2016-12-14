package onno204ServerRunner;

public class Server3T implements Runnable {
	public void run() {

		System.out.println("Starting Minetopia.");
		Utils.Run("spigot-1.8.8.jar", "Minetopia", ServerTypes.Minetopia);
	}
	   
}