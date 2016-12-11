package onno204ServerRunner;

public class Server2T implements Runnable {
	public void run() {

		System.out.println("Starting Bungee.");
		Utils.Run("BungeeCord.jar", "Bungeecord", ServerTypes.BungeeCord);
	}
	   
}