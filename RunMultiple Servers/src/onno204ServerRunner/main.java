package onno204ServerRunner;

import java.util.Scanner;

public class main {
	
	static int interval;
	
	public static void main(String[] args) {
		Runnable r = new Server1T();
		new Thread(r).start();
		
		r = new Server2T();
		new Thread(r).start();
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
				System.out.println("Command: ");
				String cmd = reader.nextLine();
				System.out.println("Command: " + cmd);
				//System.in.read();
				
				if(cmd.toLowerCase().startsWith("stop")){
					Utils.BungeecordWritter.write("end");
					Utils.LobbyWritter.write("stop");
					Utils.LobbyWritter.close();
					Utils.ReCreateWritters();
					int x = 1;
					for(int i=0; i < Utils.list.size(); i++){
						System.out.println("Stopping server " + i);
						Process proc = Utils.list.get(i);
						while(proc.isAlive()){
							System.out.println("waitloop: " + x + "/10000 | Server " + i);
							x++;
							Thread.sleep(2);
							if(x > 10000){
								proc.destroyForcibly();
							}
						}
						//proc.destroyForcibly();
					}
					break;
				}
				
				if(cmd.startsWith("Lobby") ){
					Utils.Write(cmd.replaceFirst("Lobby", ""), ServerTypes.Lobby);
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