import java.rmi.*;
import java.rmi.server.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class DistributedRemote extends UnicastRemoteObject implements Distributed{
	
	int lockOn=1,lockOff=0;
	int _lock = lockOff;
	int reset=0,stop=-1,reading=1,pause=2;
	HashMap<String, Integer > read = new HashMap<String, Integer>();
	DistributedRemote()throws RemoteException{
		super();
	}

	public void readingControl(String i, String val){
		switch(val){
			case "start":
				read.put(i,0);
				reading(i);
				break;
			case "pause":
				System.out.println("*****************Reader "+i+" paused reading*****************");
				read.put(i, pause);
				break;
			case "stop":
				stopReading(i);
				break;
			case "resume":
				System.out.println("*****************Reader "+i+" resumed reading*****************");
				read.put(i, reading);
				break;
		}
	}

	public void stopReading(String i){
		read.put(i, stop);
	}
	public void lock(){
		System.out.print("*****************Writer started writing, Reader ");
		for (String i : read.keySet()) {
			if(read.get(i) == reading || read.get(i) == pause)
				System.out.println(i+" ");
		}
		System.out.print("on hold*****************\n");
		_lock = lockOn;
	}
	public void release(){
		for(String i : read.keySet()) {
			if(read.get(i) == reading) {
				read.put(i,reset);
			}
		}
		_lock = lockOff;
		System.out.println("*****************Writer finished writing*****************");
	}
	public void writing(String input){
		try{    
		   FileWriter fw=new FileWriter("content",true);	    
		   fw.write(input+'\n');    
		   fw.close();    
		  }catch(Exception e){System.out.println(e);}    
		System.out.println(input+" Written successfully...");      
	}
	public void reading(String i){
		
		try {
		File file = new File("content"); 
	    	Scanner sc = new Scanner(file); 
	
	   		while (sc.hasNext()) 
			{
				if(_lock==lockOff)
				{

					if(read.get(i) == stop)
					{
						read.put(i,reset);
						System.out.println("*****************Reader "+i+" reading incomplete*****************");

						break;

					}

					else if(read.get(i) == reset)

					{

						System.out.println("*****************Reader "+i+" started reading*****************");

						read.put(i,reading);

					}

					

					else if(read.get(i) == reading)

					{

						System.out.println("Reader "+i+" reads "+sc.next());

						TimeUnit.SECONDS.sleep(2);

					}
				}
			}
		}catch(Exception e){System.out.println(e);}
		System.out.println("*****************Reader "+i+" exits*****************");
	}

}
