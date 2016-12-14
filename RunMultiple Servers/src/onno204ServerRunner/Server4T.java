package onno204ServerRunner;

public class Server4T implements Runnable {
	public void run() {

		System.out.println("Starting KitPvp.");
		Utils.Run("spigot-1.8.8.jar", "KitPvp", ServerTypes.KitPvp);
	}
	   
}