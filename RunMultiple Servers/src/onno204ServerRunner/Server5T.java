package onno204ServerRunner;

public class Server5T implements Runnable {
	public void run() {

		System.out.println("Starting Factions.");
		Utils.Run("spigot-1.8.8.jar", "Factions", ServerTypes.Factions);
	}
	   
}