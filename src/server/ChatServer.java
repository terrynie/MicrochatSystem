package server;
import java.io.*;
import java.net.*;
import java.util.*;
public class ChatServer {
	
	boolean started = false;
	ServerSocket ss = null;
	
	List<Client> clients = new ArrayList<Client>();
	
	public static void main(String[] args) {
		new ChatServer().start();	
		
	}
	
	
	public void start(){
		try {
			ss = new ServerSocket(11111);
			started = true;
		}catch(BindException e){
			e.printStackTrace();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		
		
		try{
			while(started){
				Socket s = ss.accept();
				Client c = new Client(s);
				new Thread(c).start();
				clients.add(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	class Client implements Runnable {
		private Socket s;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean connected = false;
		
		
		public Client(Socket s){
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				connected = true;
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		
		
		//发送消息
		public void sent(String s) {
			try {
				dos.writeUTF(s);
			} catch (IOException e) {
				clients.remove(this);
				e.printStackTrace();
			}
			
		}
		
		
		public void run() {
			try{
				while(connected){
					String str = dis.readUTF();
					for(int i=0;i<clients.size();i++){
						Client c = clients.get(i);
						c.sent(str);
					}
				}
			}catch(EOFException e){
				System.out.println("Client closed!");
			}catch(IOException e){
				e.printStackTrace();
			}finally {
				try {
					if (dis != null) 
						dis.close();
					if (dos != null) 
						dos.close();
					if (s != null) {
						s.close();
						s = null;
					}
						
				}catch(IOException e1){
					e1.printStackTrace();
				}
				
			}
			
		}
		
	}
}
