package onno204ServerRunner;

import java.io.BufferedReader;
import java.io.Writer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Utils {

	public static void Listnen(InputStream s, String Programname){
		BufferedReader in = new BufferedReader(new InputStreamReader(s));
		String line = null;
		try {
			while((line = in.readLine()) != null) {
			  System.out.println(Programname + "> " + line);
			}
		} catch (IOException e) { }
	}
	public static void error(InputStream s, String Programname){
		BufferedReader in = new BufferedReader(new InputStreamReader(s));
		String line = null;
		try {
			while((line = in.readLine()) != null) {
				System.out.println(Programname + "[Error]> " + line);
			}
		} catch (IOException e) { }
	}
	public static void Run(String file, String Programname, ServerTypes server){
        try {
        	File dir = new File(System.getProperty("user.dir") + "/" + Programname + "/");
        	if(!dir.exists()) {
        		dir.mkdir();
        	}
			Process proc = null;
			ProcessBuilder pb = new ProcessBuilder("java", "-jar", dir.getAbsolutePath() + "/" + file);
			pb.directory(dir);
			proc = pb.start();
			if(server == ServerTypes.BungeeCord){
				BungeeCordStream = proc.getOutputStream();
				ReCreateWritters();
				ProcST.put(proc, ServerTypes.BungeeCord);
				System.out.println("BungeeCord proccess Created");
			}else if(server == ServerTypes.Lobby){
				LobbyStream = proc.getOutputStream();
				ReCreateWritters();
				ProcST.put(proc, ServerTypes.Lobby);
				System.out.println("Lobby proccess Created");
			}
			System.out.println("Creating listeners");
			InputStream ListenS = proc.getInputStream();
			Thread t = new Thread(new Runnable() { public void run() { Listnen(ListenS, Programname ); } });
			t.start();
			System.out.println(pb.directory());
			list.add(proc);
			//InputStreams.put(Programname, proc.getOutputStream());
			//Listnen( proc.getInputStream(), Programname );
			error(proc.getErrorStream(), Programname);
			
		} catch (IOException e) { System.out.println(e.toString()); }
	}
	
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
	
	public static void Write(String s, ServerTypes type){
		
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
