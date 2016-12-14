package onno204ServerRunner;

public class OnlineCheck implements Runnable {
	@SuppressWarnings("deprecation")
	public void run() {
		while(true){
			
			
			if(!main.BungeeT.isAlive()){
				System.out.println("Restarting Bungeecord!");
				main.BungeeT.stop();
				main.BungeeT = new Thread(new Server2T());
				main.BungeeT.start();;
			}else if(!main.LobbyT.isAlive()){
				System.out.println("Restarting lobby!");
				main.LobbyT.stop();
				main.LobbyT = new Thread(new Server1T());
				main.LobbyT.start();
			}else if(!main.MinetopiaT.isAlive()){
				System.out.println("Restarting Minetopia!");
				main.MinetopiaT.stop();
				main.MinetopiaT = new Thread(new Server3T());
				main.MinetopiaT.start();
			}else if(!main.KitPvPT.isAlive()){
				System.out.println("Restarting KitPvp!");
				main.KitPvPT.stop();
				main.KitPvPT = new Thread(new Server4T());
				main.KitPvPT.start();
			}
			
			
			try { Thread.sleep(8234);
			} catch (InterruptedException e) { System.out.println(e.toString()); }
		}
	}
}