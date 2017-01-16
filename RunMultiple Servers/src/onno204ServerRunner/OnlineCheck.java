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
			}
			if(!main.LobbyT.isAlive()){
				System.out.println("Restarting lobby!");
				main.LobbyT.stop();
				main.LobbyT = new Thread(new Server1T());
				main.LobbyT.start();
			}
			if(!main.MinetopiaT.isAlive()){
				System.out.println("Restarting Minetopia!");
				main.MinetopiaT.stop();
				main.MinetopiaT = new Thread(new Server3T());
				main.MinetopiaT.start();
			}
			if(!main.KitPvPT.isAlive()){
				System.out.println("Restarting KitPvp!");
				main.KitPvPT.stop();
				main.KitPvPT = new Thread(new Server4T());
				main.KitPvPT.start();
			}
			if(!main.Factionst.isAlive()){
				System.out.println("Restarting Factions!");
				main.Factionst.stop();
				main.Factionst = new Thread(new Server5T());
				main.Factionst.start();
			}
			if(!main.TestT.isAlive()){
				System.out.println("Restarting Test!");
				main.TestT.stop();
				main.TestT = new Thread(new Server5T());
				main.TestT.start();
			}
			
			try { Thread.sleep(8234);
			} catch (InterruptedException e) { System.out.println(e.toString()); }
		}
	}
}