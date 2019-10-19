import java.io.Serializable;


public class Note implements Serializable{
	private static final long serialVersionUID = 6L;
	private String sendId;
	private String receiveId;
	private String msg;
	private String date;
	
	
	public Note (String sendId, String receiveId, String msg, String date){
		this.sendId = sendId;
		this.receiveId = receiveId;
		this.msg = msg;
		this.date = date;
	}


	public String getSendId() {
		return sendId;
	}


	public void setSendId(String sendId) {
		this.sendId = sendId;
	}


	public String getReceiveId() {
		return receiveId;
	}


	public void setReceiveId(String receiveId) {
		this.receiveId = receiveId;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Note) || obj == null) {
			return false;
		}
		Note temp = (Note)obj;
		if(temp.date.equals(date)) {
			if(temp.msg.equals(msg)) {
				if(temp.sendId.equals(sendId)) {
					if(temp.receiveId.equals(receiveId)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
