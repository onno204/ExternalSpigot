package onno204ServerRunner;

import java.util.Scanner;

public class main {
	
	//AutoReboot
	 

	static Thread Checker = new Thread(new OnlineCheck());
	static Thread LobbyT = new Thread(new Server1T());
	static Thread BungeeT = new Thread(new Server2T());
	static Thread MinetopiaT = new Thread(new Server3T());
	static Thread KitPvPT = new Thread(new Server4T());
	
	
	//start
	public static void main(String[] args) {
		//Start Spigot
		LobbyT.start();
		
		//STart bungeecord
		BungeeT.start();
		
		//Start Minetopia
		MinetopiaT.start();
		
		//Start Minetoia
		KitPvPT.start();
		
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
					Utils.Write("end", ServerTypes.BungeeCord);
					Utils.Write("kickall &4&lServer shutting down!", ServerTypes.Lobby);
					Utils.Write("kickall &4&lServer shutting down!", ServerTypes.Minetopia);
					Utils.Write("kickall &4&lServer shutting down!", ServerTypes.KitPvp);
					Utils.Write("stop", ServerTypes.Lobby);
					Utils.Write("stop", ServerTypes.Minetopia);
					Utils.Write("stop", ServerTypes.KitPvp);
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
								//Destroy the porcess after 10000 mil. seconds
								proc.destroyForcibly();
							}
						}
						x=1;
					}
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
				}else if(cmd.startsWith("All") ){
					cmd = cmd.replaceFirst("All", "");
					if(cmd.startsWith(" ")){ cmd = cmd.replaceFirst(" ", ""); }
					Utils.Write(cmd, ServerTypes.Lobby);
					Utils.Write(cmd, ServerTypes.BungeeCord);
				}else{

					System.out.println("Sending to all servers!(Except bungeecord.)");
					System.out.println("We prefer is if you use the command per server:");
					Utils.Write(cmd, ServerTypes.Lobby);
					Utils.Write(cmd, ServerTypes.Minetopia);
					Utils.Write(cmd, ServerTypes.KitPvp);
					
					//Help message
					System.out.println("");
					System.out.println("==================================================================");
					System.out.println("Lobby / Minetopia / KitPvp / Bungeecord / All");
					System.out.println("Example: LobbyHelp");
					System.out.println("==================================================================");
				}
			} catch (Exception e) { System.out.println(e.toString()); }
			
		}
		reader.close();
	}
}