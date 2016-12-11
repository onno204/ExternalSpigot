package onno204ServerRunner;

import java.util.Scanner;

public class main {
	
	static int interval;
	
	//start
	public static void main(String[] args) {
		//Start Spigot
		Runnable r = new Server1T();
		new Thread(r).start();
		//STart bungeecord
		r = new Server2T();
		new Thread(r).start();
		//Start Command Listener
		KeyListener();
		return;
		/*
		for(int i=0; i < Utils.list.size(); i++){
			Process proc = Utils.list.get(i);
			proc.destroyForcibly();
		} 
		System.out.println("Done starting.");
		*/
	}

	
	static void KeyListener(){
		Scanner reader = new Scanner(System.in);
		while(true){
			try { 
				//Ask for command
				System.out.println("Command: ");
				String cmd = reader.nextLine();
				System.out.println("Command: " + cmd); 
				
				//Check's if it's a need to stop
				if(cmd.toLowerCase().startsWith("stop")){
					Utils.Write("stop", ServerTypes.BungeeCord);
					Utils.Write("stop", ServerTypes.Lobby);
					//Couting so the server has gotten a time to close
					int x = 1;
					for(int i=0; i < Utils.list.size(); i++){
						System.out.println("Stopping server " + i);
						Process proc = Utils.list.get(i);
						while(proc.isAlive()){
							System.out.println("waitloop: " + x + "/10000 | Server " + i);
							x++;
							Thread.sleep(2);
							if(x > 10000){
								//Destroy the porcess after 20000 mil. seconds
								proc.destroyForcibly();
							}
						}
					}
					break;
				}
				//Command sender
				//Example: LobbeHelp  > Executes Help
				if(cmd.startsWith("Lobby") ){
					//Write's to stream (Not working)
					Utils.Write(cmd.replaceFirst("Lobby", ""), ServerTypes.Lobby);
					//Message to console it has been send
					System.out.println("Sending '" + cmd.replaceFirst("Lobby", "") + "' to Lobby.");
				}else if(cmd.startsWith("Bungeecord") ){
					Utils.Write(cmd.replaceFirst("Bungeecord", ""), ServerTypes.BungeeCord);
					System.out.println("Sending '" + cmd.replaceFirst("Bungeecord", "") + "' to Bungeecord.");
				}
				
				
			} catch (Exception e) { System.out.println(e.toString()); }
			
		}
		reader.close();
	}
}