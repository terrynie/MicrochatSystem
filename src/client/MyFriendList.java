package client;
import help.MongodbLink;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class MyFriendList extends JFrame implements MouseListener, Runnable{
	private static final long serialVersionUID = 1L;
	
	//显示好友
	JPanel northPanel,friendsListPanel,southPanel;
	JButton myFriendButton,strangerButton,limitedButton;
	JScrollPane myFriendsListScroll;
	
	//显示陌生人
	JPanel jpmsr1,jpmsr2,jpmsr3;
	JButton jpmsr_jb1,jpmsr_jb2,jpmsr_jb3;
	JScrollPane jsp2;
	
	
	CardLayout cl,cl2;
	public MyFriendList(String username){
		
		MongodbLink ml = new MongodbLink();
		int friendsNumber = ml.friendsNumber(username);
		//第一张卡片
		myFriendButton = new JButton("我的好友");
		strangerButton = new JButton("陌生人");
		northPanel = new JPanel(new BorderLayout());
		friendsListPanel = new JPanel(new GridLayout(50,1,4,4));
		JLabel [] jbls = new JLabel[friendsNumber];
		
		for(int i=0;i<jbls.length;i++){
			jbls[i] = new JLabel(i+1+"",new ImageIcon("/Volumes/代码库/Java1/WW/images/users2.jpg"),JLabel.LEFT);
			jbls[i].addMouseListener(this);
			friendsListPanel.add(jbls[i]);
		}
		
		
		
		southPanel = new JPanel(new GridLayout(1,1));
		
		southPanel.add(strangerButton);
		myFriendsListScroll = new JScrollPane(friendsListPanel);
		
		northPanel.add(myFriendButton,"North");
		northPanel.add(myFriendsListScroll,"Center");
		northPanel.add(southPanel,"South");
		this.add(northPanel,"Center");
		
		//第二张卡片
		jpmsr_jb1 = new JButton("我的好友");
		jpmsr_jb2 = new JButton("陌生人");
		jpmsr1 = new JPanel(new BorderLayout());
		jpmsr2 = new JPanel(new GridLayout(20,1,4,4));
		JLabel []jbls2 = new JLabel[20];
		
		
		for (int i=0;i<jbls2.length;i++){
			jbls2[i] = new JLabel(i+1+"",new ImageIcon("/Volumes/代码库/Java1/WW/images/users2.jpg"),JLabel.LEFT);
			jpmsr2.add(jbls2[i]);
		}
		jpmsr3 = new JPanel(new GridLayout(2,1));
		
		jpmsr3.add(jpmsr_jb1);
		jpmsr3.add(jpmsr_jb2);
		
		jsp2 = new JScrollPane(jpmsr2);
		
		jpmsr1.add(jpmsr3,"North");
		jpmsr1.add(jsp2,"Center");
		
		cl = new CardLayout();
		this.setLayout(cl);
		this.add(northPanel,"1");
		this.add(jpmsr1,"2");
		
		
		
		this.setSize(270,600);
		this.setVisible(true);
		this.setTitle(username);
		
		
		
		strangerButton.addActionListener(event->{
			if(event.getSource()==strangerButton){
				cl.show(this.getContentPane(), "2");
			}
		});
		
		jpmsr_jb1.addActionListener(event->{
			if(event.getSource()==jpmsr_jb1){
				cl.show(this.getContentPane(), "1");
			}
		});
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()==2){
			String friendUsername = ((JLabel)e.getSource()).getText();
			ChatClient cc =new ChatClient(this.getTitle(),friendUsername);
//			Thread thread = new Thread(cc).start();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		JLabel label = (JLabel)e.getSource();
		label.setForeground(Color.RED);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		JLabel label = (JLabel)e.getSource();
		label.setForeground(Color.black);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
