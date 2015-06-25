package client;
import help.MongodbLink;

import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;
import java.awt.event.*;


public class ChatClient extends Frame implements Runnable{
	private  String username;
	//与服务器的连接
	Socket s = null; 
	//输入输出流
	DataOutputStream dos = null;
	DataInputStream din = null;
	private boolean connected = false;
	static Main frame = new Main();
	
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();
	Thread tRecv = new Thread(new RecveThread());
	
	public  ChatClient(String username,String title) {
		this.setTitle(title);
		this.username = username;
		//客户端界面
		this.launchFrame();


	}
	
	
	//launch the Frame
	public void launchFrame(){
			
		setLocation(400,300);
		this.setSize(400, 300);
		add(tfTxt,BorderLayout.SOUTH);
		add(taContent,BorderLayout.NORTH);
		pack();//消除空白
		MongodbLink ml = new MongodbLink();
		String [] messages = ml.getMessageFromDB(this.getTitle(), this.username);
		try{
			for(int i=0;i<messages.length;i++){
				taContent.setText(taContent.getText()+messages[i]+"\n");
			}
		}catch(NullPointerException e){
			System.out.println("No messages!");
		}
		
		this.addWindowListener(new WindowAdapter(){
			
			@Override
			public void windowClosing(WindowEvent arg0){
				disconnect();	//disconnect with the server
				setVisible(false);
				
				try {
					this.clone();
				} catch (CloneNotSupportedException e) {
//					e.printStackTrace();
				}
			}

		});

		tfTxt.addActionListener(event->{
			String str = tfTxt.getText().trim();
			//获取输入内容
			String temps = taContent.getText();
			temps = temps+"\n"+str;
			temps = temps.trim();
			
			tfTxt.setText("");
			try {
				ml.writeMessageToDB(this.username, this.getTitle(), str);
					dos.writeUTF(str);
					dos.flush();
			
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
		});
		setVisible(true);
		connect();
		
		tRecv.start();
	}
	
	
	//建立与服务器链接
	public void connect(){
		
		try {
			s = new Socket("127.0.0.1",11111);
			dos = new DataOutputStream(s.getOutputStream());
			din = new DataInputStream(s.getInputStream());
			connected = true;
		}catch (UnknownHostException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	//断开与服务器连接
	public void disconnect(){
		try {
			dos.close();
			din.close();
			s.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	
	}
		
	
	private class RecveThread implements Runnable {

		public void run() {
				try {
					while(connected){
						String str = din.readUTF();
						taContent.setText(taContent.getText()+str +'\n');
					}
				}catch (SocketException e){
					System.out.println("exit");
				}catch(EOFException e){
					System.out.println("exit");
				}catch (IOException e) {
					
					e.printStackTrace();
				}
		}
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
		
}

