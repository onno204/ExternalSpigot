package onno204ServerRunner;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
	
	//AutoReboot
	 
	public static ArrayList<PrintWriter> Buffers = new ArrayList<PrintWriter>();
	
	static Thread Checker = new Thread(new OnlineCheck());
	static Thread LobbyT = new Thread(new Server1T());
	static Thread BungeeT = new Thread(new Server2T());
	static Thread MinetopiaT = new Thread(new Server3T());
	static Thread KitPvPT = new Thread(new Server4T());
	static Thread Factionst = new Thread(new Server5T());
	static Thread TestT = new Thread(new Server6T());
	
	
	//start
	public static void main(String[] args) {
		Thread t = new Thread(new Server());
		t.start();
		
		//Start Spigot
		LobbyT.start();
		
		//STart bungeecord
		BungeeT.start();
		
		//Start Minetopia
		MinetopiaT.start();
		
		//Start KitPvPT
		KitPvPT.start();
		
		//Start Factions
		Factionst.start();
		
		//Start Test
		TestT.start();
		
		//Start auto reboot
		Checker.start();
		
		//Start Command Listener
		KeyListener();
		return;
	}

	
	@SuppressWarnings("deprecation")
	static void KeyListener(){
		Scanner reader = new Scanner(System.in);
		while(true){
			try { 
				//Ask for command
				String cmd = reader.nextLine();
				
				//Check's if it's a need to stop
				if(cmd.toLowerCase().startsWith("stop")){
					Checker.stop();
					Utils.Write("kickall &4&lServer shutting down!", ServerTypes.Lobby);
					Utils.Write("kickall &4&lServer shutting down!", ServerTypes.Minetopia);
					Utils.Write("kickall &4&lServer shutting down!", ServerTypes.KitPvp);
					Utils.Write("kickall &4&lServer shutting down!", ServerTypes.Factions);
					Utils.Write("kickall &4&lServer shutting down!", ServerTypes.Test);
					Utils.Write("end", ServerTypes.BungeeCord);
					Utils.Write("stop", ServerTypes.Lobby);
					Utils.Write("stop", ServerTypes.Minetopia);
					Utils.Write("stop", ServerTypes.KitPvp);
					Utils.Write("stop", ServerTypes.Factions);
					Utils.Write("stop", ServerTypes.Test);
					//Couting so the server has gotten a time to close
					int x = 1;
					for(int i=0; i < Utils.list.size(); i++){
						System.out.println("Stopping server " + i);
						Process proc = Utils.list.get(i);
						while(proc.isAlive()){
							System.out.println("waitloop: " + x + "/5000 | Server " + i);
							x++;
							Thread.sleep(2);
							if(x > 5000){
								//Destroy the porcess after 5000 mil. seconds
								proc.destroyForcibly();
							}
						}
						x=1;
					}
					System.exit(1);
					break;
				}
				//Command sender
				//Example: LobbeHelp  > Executes Help
				if(cmd.startsWith("Lobby") ){
					//Write's to stream (Not working)
					Utils.Write(cmd.replaceFirst("Lobby", ""), ServerTypes.Lobby);
					//Message to console it has been send
				}else if(cmd.startsWith("Bungeecord") ){
					Utils.Write(cmd.replaceFirst("Bungeecord", ""), ServerTypes.BungeeCord);
				}else if(cmd.startsWith("Minetopia") ){
					Utils.Write(cmd.replaceFirst("Minetopia", ""), ServerTypes.Minetopia);
				}else if(cmd.startsWith("KitPvp") ){
					Utils.Write(cmd.replaceFirst("KitPvp", ""), ServerTypes.KitPvp);
				}else if(cmd.startsWith("Factions") ){
					Utils.Write(cmd.replaceFirst("Factions", ""), ServerTypes.Factions);
				}else if(cmd.startsWith("Test") ){
					Utils.Write(cmd.replaceFirst("Test", ""), ServerTypes.Test);
				}else if(cmd.startsWith("All") ){
					cmd = cmd.replaceFirst("All", "");
					if(cmd.startsWith(" ")){ cmd = cmd.replaceFirst(" ", ""); }
					Utils.Write(cmd, ServerTypes.Lobby);
					Utils.Write(cmd, ServerTypes.Minetopia);
					Utils.Write(cmd, ServerTypes.KitPvp);
					Utils.Write(cmd, ServerTypes.Test);
				}else{
					Utils.Write(cmd, ServerTypes.Lobby);
					Utils.Write(cmd, ServerTypes.Minetopia);
					Utils.Write(cmd, ServerTypes.KitPvp);
					Utils.Write(cmd, ServerTypes.Factions);
					Utils.Write(cmd, ServerTypes.Test);
					
					//Help message
					System.out.println("");
					System.out.println("================================================");
					System.out.println("Lobby/Minetopia/KitPvp/Factions/Test/Bungeecord/All");
					System.out.println("Example: LobbyHelp");
					System.out.println("================================================");
				}
			} catch (Exception e) { System.out.println(e.toString()); }
			
		}
		reader.close();
	}
}