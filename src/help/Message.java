package help;
import java.util.Date;


public class Message implements java.io.Serializable{		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mesType;
	private String sender;
	private String getter;
	private String contain;
	private Date date;
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getGetter() {
		return getter;
	}

	public void setGetter(String getter) {
		this.getter = getter;
	}

	public String getContain() {
		return contain;
	}

	public void setContain(String contain) {
		this.contain = contain;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMesType() {
		return mesType;
	}

	public void setMesType(String mesType) {
		this.mesType = mesType;
	}
	
	
}
