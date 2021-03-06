package onno204ServerRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Utils {
	//Output every String
	public static void Listnen(InputStream s, String Programname){
		//Buffer reader to read.
		BufferedReader in = new BufferedReader(new InputStreamReader(s));
		String line = null;
		try {
			//Output to console
			while((line = in.readLine()) != null) {
			  System.out.println(Programname + "> " + line);
			  for(PrintWriter out : main.Buffers){
				  try{
					  out.println(Programname + "> " + line);
					  out.flush();
				  }catch(Exception e){ System.out.print(e.getMessage()); }
			  }
			}
		} catch (IOException e) { System.out.print(e.getMessage()); }
	}
	//Error reader, Same as above
	public static void error(InputStream s, String Programname){
		BufferedReader in = new BufferedReader(new InputStreamReader(s));
		String line = null;
		try {
			while((line = in.readLine()) != null) {
				System.out.println(Programname + "[Error]> " + line);
				  for(PrintWriter out : main.Buffers){
					  try{
						  out.println(Programname + "[Error]> " + line);
						  out.flush();
					  }catch(Exception e){ System.out.print(e.getMessage()); }
				  }
			}
		} catch (IOException e) { System.out.print(e.getMessage()); }
	}
	
	//Run (Called from SErver1T and Server2T)
	public static void Run(String file, String Programname, ServerTypes server){
        try {
        	//Get Server working dir
        	File dir = new File(System.getProperty("user.dir") + "/" + Programname + "/");
        	if(!dir.exists()) {
        		dir.mkdir();
        	}
        	//Start procces
			Process proc = null;
			ProcessBuilder pb = new ProcessBuilder("java", "-jar", dir.getAbsolutePath() + "/" + file);
			pb.directory(dir);
			proc = pb.start();
			//If ServerTpe = Bungeecord Set Bungeecord streams and writters
			if(server == ServerTypes.BungeeCord){
				//List for the /stop
				BungeecordWritter = new PrintWriter(new OutputStreamWriter(proc.getOutputStream(), "UTF-8") );
				ProcST.put(proc, ServerTypes.BungeeCord);
				System.out.println("BungeeCord proccess Created");
			}else if(server == ServerTypes.Lobby){
				LobbyWritter = new PrintWriter(new OutputStreamWriter(proc.getOutputStream(), "UTF-8") );
				ProcST.put(proc, ServerTypes.Lobby);
				System.out.println("Lobby proccess Created");
			}else if(server == ServerTypes.Minetopia){
				MinetopiaWritter = new PrintWriter(new OutputStreamWriter(proc.getOutputStream(), "UTF-8") );
				ProcST.put(proc, ServerTypes.Minetopia);
				System.out.println("Minetopia proccess Created");
			}else if(server == ServerTypes.KitPvp){
				KitPvpWritter = new PrintWriter(new OutputStreamWriter(proc.getOutputStream(), "UTF-8") );
				ProcST.put(proc, ServerTypes.KitPvp);
				System.out.println("KitPvp proccess Created");
			}else if(server == ServerTypes.Factions){
				FactionsWritter = new PrintWriter(new OutputStreamWriter(proc.getOutputStream(), "UTF-8") );
				ProcST.put(proc, ServerTypes.Factions);
				System.out.println("Factions proccess Created");
			}else if(server == ServerTypes.Test){
				TestWritter = new PrintWriter(new OutputStreamWriter(proc.getOutputStream(), "UTF-8") );
				ProcST.put(proc, ServerTypes.Test);
				System.out.println("Test proccess Created");
			}
			System.out.println("Creating listeners");
			//Logging to console
			InputStream ListenS = proc.getInputStream();
			//Separete thread so we can also listen to the error's
			Thread t = new Thread(new Runnable() { public void run() { Listnen(ListenS, Programname ); } });
			t.start();
			System.out.println("Started " + Programname + ".");
			list.add(proc);
			error(proc.getErrorStream(), Programname);
			
		} catch (IOException e) { System.out.println(e.toString()); }
	}
	
	//Write to the console of spigot/bungee
	public static void Write(String s, ServerTypes type){
		try {
			if(type == ServerTypes.BungeeCord){
				BungeecordWritter.write(s);
				BungeecordWritter.write(" \n");
				BungeecordWritter.flush();
			}else if(type == ServerTypes.Lobby){
				LobbyWritter.write(s);
				LobbyWritter.write(" \n");
				LobbyWritter.flush();
			}else if(type == ServerTypes.Minetopia){
				MinetopiaWritter.write(s);
				MinetopiaWritter.write(" \n");
				MinetopiaWritter.flush();
			}else if(type == ServerTypes.KitPvp){
				KitPvpWritter.write(s);
				KitPvpWritter.write(" \n");
				KitPvpWritter.flush();
			}else if(type == ServerTypes.Factions){
				FactionsWritter.write(s);
				FactionsWritter.write(" \n");
				FactionsWritter.flush();
			}else if(type == ServerTypes.Test){
				TestWritter.write(s);
				TestWritter.write(" \n");
				TestWritter.flush();
			}
		} catch (Exception e) { System.out.println(e.toString()); }
	}
	
	
	
	public static ArrayList<Process> list = new ArrayList<Process>();
	public static HashMap<Process, ServerTypes> ProcST = new HashMap<Process, ServerTypes>();
	
	public static PrintWriter LobbyWritter;
	public static PrintWriter BungeecordWritter;
	public static PrintWriter MinetopiaWritter;
	public static PrintWriter KitPvpWritter;
	public static PrintWriter FactionsWritter;
	public static PrintWriter TestWritter;
	
	
}
