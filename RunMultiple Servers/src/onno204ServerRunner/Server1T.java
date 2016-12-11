package onno204ServerRunner;

public class Server1T implements Runnable {
	public void run() {

		System.out.println("Starting Spigot.");
		Utils.Run("spigot-1.8.8.jar", "Lobby", ServerTypes.Lobby);
	}
}