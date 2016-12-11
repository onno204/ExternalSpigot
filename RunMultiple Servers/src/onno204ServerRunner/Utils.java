package onno204ServerRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
			}
		} catch (IOException e) { }
	}
	//Error reader, Same as above
	public static void error(InputStream s, String Programname){
		BufferedReader in = new BufferedReader(new InputStreamReader(s));
		String line = null;
		try {
			while((line = in.readLine()) != null) {
				System.out.println(Programname + "[Error]> " + line);
			}
		} catch (IOException e) { }
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
				BungeeCordStream = proc.getOutputStream();
				ReCreateWritters();
				//List for the /stop
				ProcST.put(proc, ServerTypes.BungeeCord);
				System.out.println("BungeeCord proccess Created");
			}else if(server == ServerTypes.Lobby){
				LobbyStream = proc.getOutputStream();
				ReCreateWritters();
				ProcST.put(proc, ServerTypes.Lobby);
				System.out.println("Lobby proccess Created");
			}
			System.out.println("Creating listeners");
			//Logging to console
			InputStream ListenS = proc.getInputStream();
			//Separete thread so we can also listen to the error's
			Thread t = new Thread(new Runnable() { public void run() { Listnen(ListenS, Programname ); } });
			t.start();
			System.out.println(pb.directory());
			list.add(proc);
			error(proc.getErrorStream(), Programname);
			
		} catch (IOException e) { System.out.println(e.toString()); }
	}
	//Recreate writters, Set the writters when RunProcces() is called
	public static void ReCreateWritters(){
		System.out.println("ReCreating!");
		try {
			for (Process process : ProcST.keySet()) {
				ServerTypes server = ProcST.get(process);
				if(server == ServerTypes.BungeeCord){
					BungeeCordStream = process.getOutputStream();
					BungeecordWritter = new OutputStreamWriter(BungeeCordStream, "UTF-8");
				}else if(server == ServerTypes.Lobby){
					LobbyStream = process.getOutputStream();
					LobbyWritter = new OutputStreamWriter(LobbyStream, "UTF-8");
				}
			}
		} catch (Exception e) { System.out.println(e.toString()); }
	}
	
	//Write to the console of spigot/bungee
	public static void Write(String s, ServerTypes type){
		//I didn't know it any more and delete the old code :|
		if(type == ServerTypes.BungeeCord){
			
		}else if(type == ServerTypes.Lobby){
			
		}
	}
	
	
	
	public static ArrayList<Process> list = new ArrayList<Process>();
	public static HashMap<Process, ServerTypes> ProcST = new HashMap<Process, ServerTypes>();
	//public static HashMap<String, OutputStream> InputStreams = new HashMap<String, OutputStream>();
	
	public static OutputStream LobbyStream;
	public static OutputStream BungeeCordStream;
	public static Writer LobbyWritter;
	public static Writer BungeecordWritter;
	
	
}
