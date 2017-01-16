package onno204ServerRunner;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class Server implements Runnable{
	
    public void run() {
    	ServerSocket listener = null;
        try {
            System.out.println("The capitalization server is running.");
            listener = new ServerSocket(8551);
            while (true) {
                new Capitalizer(listener.accept()).start();
            }
        }catch(Exception e){
        	System.err.println(e.toString());
        } finally {
            try {
				listener.close();
			} catch (IOException e) { System.out.println(e.toString()); }
        }
    }
    
    //Extra Class
    
    private static class Capitalizer extends Thread {
        private Socket socket;

        public Capitalizer(Socket socket) {
            this.socket = socket;
            if(!CheckIps(socket.getInetAddress().getHostAddress())){
            	try { 
            		PrintWriter prt = new PrintWriter(socket.getOutputStream(), true);
            		prt.write("DENIED! \n");
                	this.socket.getInputStream().close();
                	prt.close();
                	this.socket.getOutputStream().close();
                	this.socket.close();
				} catch (IOException e) { e.printStackTrace(); }
            }
            log("New connection with client at " + socket);
        }
        
        @SuppressWarnings("deprecation")
		public static boolean CheckIps(String ip){
        	ArrayList<String> temp = new ArrayList<String>();
        	ArrayList<String> temp2 = new ArrayList<String>();
    		FileInputStream fstream;
    		try {
    			fstream = new FileInputStream("IPS.txt");
    			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
    			String strLine;
    			while ((strLine = br.readLine()) != null)   {
    				temp.add(strLine);
    			}
    			fstream = new FileInputStream("IPSLog.txt");
    			br = new BufferedReader(new InputStreamReader(fstream));
    			while ((strLine = br.readLine()) != null)   {
    				temp2.add(strLine);
    			}
    			br.close();
    			FileWriter fw = new FileWriter("IPSLog.txt");
    			Date d = new Date();
    			fw.flush();
    			for(String s : temp2){ fw.write(s + "\n"); }
    			fw.write(d.getDay() + " -" + d.getHours() + "h-" + d.getMinutes()+ "m: " +  ip);
    			fw.close();
    			
    		} catch (Exception e) { e.printStackTrace(); }
    		if(temp.contains(ip)){ return true;
    		}else{ return false;
    		}
        }
    	
    	
		public void Incomming(BufferedReader in){
    		try {
    			String s;
    			while((s = in.readLine()) != null){
    				log(s);
    				Write(s);
    			}
    		}catch (Exception e) { System.err.println(e.getMessage()); }
    	}
    	
    	
    	
    	public void OutGoing(PrintWriter out){
    		out.write("Remote connection accepted!");
    		while(true){
    			try {
    	    		out.write("Status: Connected\n");
    	    		out.flush();
    				Thread.sleep(2500);
    			}catch (Exception e) { System.err.println(e.getMessage()); }
    		}
    	}
        
    	BufferedReader in;
    	static PrintWriter out;
    	
		public void run() {
            try {
                in = new BufferedReader( new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);
			    main.Buffers.add(out);
			    Thread t1 = new Thread(new Runnable() {
			        public void run() {
			        	Incomming(in);
			        }
			    });
			    t1.start();
                OutGoing(out);
            } catch (IOException e) {
                log("Error handling client: " + e);
            } finally {
            	main.Buffers.remove(out);
                try {
                    socket.close();
                } catch (IOException e) {
                    log("Couldn't close a socket, what's going on?");
                }
                log("Connection with client closed");
            }
        }
        
        private void log(String message) {
            System.out.println(message);
        }
        
    	@SuppressWarnings("deprecation")
		public static void Write(String text){
    		try { 
				//Ask for command
				
				//Check's if it's a need to stop
				if(text.toLowerCase().startsWith("stop")){
					main.Checker.stop();
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
								//Destroy the porcess after 10000 mil. seconds
								proc.destroyForcibly();
							}
						}
						x=1;
					}
					System.exit(1);
					return;
				}
				//Command sender
				//Example: LobbeHelp  > Executes Help
				if(text.startsWith("Lobby") ){
					//Write's to stream (Not working)
					Utils.Write(text.replaceFirst("Lobby", ""), ServerTypes.Lobby);
					//Message to console it has been send
				}else if(text.startsWith("Bungeecord") ){
					Utils.Write(text.replaceFirst("Bungeecord", ""), ServerTypes.BungeeCord);
				}else if(text.startsWith("Minetopia") ){
					Utils.Write(text.replaceFirst("Minetopia", ""), ServerTypes.Minetopia);
				}else if(text.startsWith("KitPvp") ){
					Utils.Write(text.replaceFirst("KitPvp", ""), ServerTypes.KitPvp);
				}else if(text.startsWith("Factions") ){
					Utils.Write(text.replaceFirst("Factions", ""), ServerTypes.Factions);
				}else if(text.startsWith("Test") ){
					Utils.Write(text.replaceFirst("Test", ""), ServerTypes.Test);
				}else if(text.startsWith("All") ){
					text = text.replaceFirst("All", "");
					if(text.startsWith(" ")){ text = text.replaceFirst(" ", ""); }
					try{ Utils.Write(text, ServerTypes.Lobby);
		    		}catch(Exception e){ e.printStackTrace(out); }
					try{ Utils.Write(text, ServerTypes.Minetopia);
		    		}catch(Exception e){ e.printStackTrace(out); }
					try{ Utils.Write(text, ServerTypes.KitPvp);
		    		}catch(Exception e){ e.printStackTrace(out); }
					try{ Utils.Write(text, ServerTypes.Factions);
		    		}catch(Exception e){ e.printStackTrace(out); }
					try{ Utils.Write(text, ServerTypes.Test);
		    		}catch(Exception e){ e.printStackTrace(out); }
				}else{

					System.out.println("Sending to all servers!(Except bungeecord.)");
					System.out.println("We prefer is if you use the command per server:");
					try{ Utils.Write(text, ServerTypes.Lobby);
		    		}catch(Exception e){ e.printStackTrace(out); }
					try{ Utils.Write(text, ServerTypes.Minetopia);
		    		}catch(Exception e){ e.printStackTrace(out); }
					try{ Utils.Write(text, ServerTypes.KitPvp);
		    		}catch(Exception e){ e.printStackTrace(out); }
					try{ Utils.Write(text, ServerTypes.Factions);
		    		}catch(Exception e){ e.printStackTrace(out); }
					try{ Utils.Write(text, ServerTypes.Test);
		    		}catch(Exception e){ e.printStackTrace(out); }
					
					//Help message
					out.println("");
					out.println("==================================================================");
					out.println("Lobby / Minetopia / KitPvp / Factions / Test / Bungeecord / All");
					out.println("Example: LobbyHelp");
					out.println("==================================================================");
				}
    		}catch(Exception e){ e.printStackTrace(); }
    	}
    }
}