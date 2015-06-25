package help;
import java.net.UnknownHostException;
import java.util.*;

import javax.swing.JOptionPane;

import com.mongodb.*;


public class MongodbLink {
    static MongoClient client;
    static DB db;
    static DBCollection collection;
    
    
    //create a connection to connect the database
    public MongodbLink(){
    	try {
			client = new MongoClient("127.0.0.1",27017);
		} catch (UnknownHostException e) {
			Object[] options = { "OK"};
			JOptionPane.showOptionDialog(null, "服务器连接失败！", "警告", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, null); 
		}
    }
    
    
    //获取密码，以字符串形式返回
	@SuppressWarnings("unchecked")
	public String getPassword(String username){
    	String password = null;    	
    	if(client!=null){
        	db = client.getDB("chatsystem");
			DBCollection collection = db.getCollection("user");
			BasicDBObject fi = new BasicDBObject();
			fi.put("username",username);
			Map<String,String> co = new HashMap<String,String>();
			co = collection.findOne(fi).toMap();
			password = co.get("password");
    	}
		return password;
    }
    
    
    //set the user's infomation
    public void setUserInfo(String username,String password){
        if(client!=null){
        	db = client.getDB("chatsystem");
			DBCollection collection = db.getCollection("user");
			BasicDBObject document = new BasicDBObject();
			document.put("username",username);
			document.put("password", password);
			collection.insert(document);
        }
    }
    
    
    //verify the username is exist or not 
    public boolean verifyUserExistOrNot(String username){
    	int count = 0;
        if(client!=null){
        	db = client.getDB("chatsystem");
			DBCollection collection = db.getCollection("user");
			BasicDBObject document = new BasicDBObject();
			document.put("username", username);
			DBCursor dbc = collection.find(document);
			count = dbc.count();
        }
		return count>0;
       	
    }
    
    
    //用户匹配查询,即查找用户
    public DBObject searchUser(String username){
    	DBObject result = null;
        if(client!=null){
        	db = client.getDB("chatsystem");
			DBCollection collection = db.getCollection("user");
			BasicDBObject document = new BasicDBObject();
			document.put("username", username);
			result = collection.findOne(document);
        }
    	
		return result;
    	
    }
    
    
    //查询好友数目
    @SuppressWarnings("unchecked")
	public int friendsNumber(String username){
    	BasicDBObject document = new BasicDBObject();
    	String result = null;
    	if(client!=null){
        	db = client.getDB("chatsystem");
			DBCollection collection = db.getCollection("user");
			document.put("username", username);
			Map<String,String> co = new HashMap<String,String>();
			co = collection.findOne(document).toMap();
			result = co.get("friendnumber");
			
    	}
		return Integer.parseInt(result);
    }
    
    
    //获取好友列表
    public String [] getFriendList(String username){
    	BasicDBObject document = new BasicDBObject();
    	DBCursor dbc = null;
    	if(client!=null){
        	db = client.getDB("chatsystem");
			DBCollection collection = db.getCollection("user");
			document.put("username", username);
			dbc = collection.find(document);
			
			for(int i=0;i<dbc.count();i++){
				
			}	
    	}
		return null;
    }
    
    
    //Change password
    public void changePassword(String username, String newPassword){
    	DBCollection collection = db.getCollection("user");
    	BasicDBObject document = new BasicDBObject();
    	document.append("$set", new BasicDBObject().append("password", newPassword));
    	collection.update(new BasicDBObject().append("username", username),document);
    }
    
    
    //sent the message to MongoDB
    public void writeMessageToDB(String senter,String reciever,String message){
    	
    	if(client!=null){
        	db = client.getDB("chatsystem");
			DBCollection collection = db.getCollection("message");
			BasicDBObject document = new BasicDBObject();
			document.put("senter", senter);
			document.put("reciever", reciever);
			DBCursor dbc = collection.find(document);
			if (dbc.count()>0){
				Map<String,String> co = new HashMap<String,String>();
				DBObject temp = new BasicDBObject();//作为临时缓存
				temp = collection.findOne(document);
				co = collection.findOne(document).toMap();
				Integer count = Integer.parseInt(co.get("messageCount"));
				temp.put((count).toString(), message);
				count++;
				String messageCount = (count).toString();
				temp.put("messageCount", messageCount);
				count = Integer.parseInt(co.get("noGet"));
				count++;
				temp.put("noGet", count.toString());
				collection.update(document, temp, true, false);
			}else{
				document.put("messageCount","1");
				document.put("noGet","1");
				document.put("0", message);
				collection.insert(document);
			}
    	}//end if
    }
    
    
    //get the message from MongoDB
    public String [] getMessageFromDB(String senter,String reciever){
    	String messages[] = null;
    	if(client!=null){
        	db = client.getDB("chatsystem");
			DBCollection collection = db.getCollection("message");
			BasicDBObject document = new BasicDBObject();
			document.put("senter", senter);
			document.put("reciever", reciever);

			if (collection.find(document).count()>0){
				Map<String,String> co = new HashMap<String,String>();
				co = collection.findOne(document).toMap();

				//count is the begin of the location of the message noGet
				Integer count = Integer.parseInt(co.get("messageCount"))-Integer.parseInt(co.get("noGet"));
				count--;
				messages = new String[Integer.parseInt(co.get("noGet"))];
				try{
					for(Integer i = 0;i<Integer.parseInt(co.get("noGet"));i++){
						count++;
						messages[i] = co.get(count.toString());
					}
					DBObject temp = new BasicDBObject();
					temp = collection.findOne(document);
					temp.put("noGet", "0");
					collection.update(document, temp, true, false);
				}catch(NullPointerException e){
					System.out.println("No messages!");
				}
			}else{
				System.out.println("No messages!");
			}
    	}
    	
		return messages;
    }//end getMessageFromDB
}
