import java.io.Serializable;
import java.util.Vector;

public class ChatRoom implements Serializable{
	private static final long serialVersionUID = 4L;
	private Vector<User> chatRoomUserList;
	private int roomNum;
	private String password;
	private int personLimit;
	private String headId;
	private String title;
	private boolean isPrivte;
	
	
	public ChatRoom(String title){
		this.title = title;
	}
	
	public ChatRoom(int roomNum, int personLimit, String headId, String title) {
		init();
		this.roomNum = roomNum;
		this.personLimit = personLimit;
		this.headId = headId;
		this.title = title;
		isPrivte = false;
	}
	
	public ChatRoom(int roomNum, int personLimit, String headId, String title,String password) {
		init();
		this.roomNum = roomNum;
		this.personLimit = personLimit;
		this.headId = headId;
		this.title = title;
		this.password = password;
		isPrivte = true;
	}
	
	private void init(){
		chatRoomUserList = new Vector<User>();
	}
	
//	public void addChatRoomUser(String id){
//		chatRoomUserList.add(new User(id));
//	}
//	
	public void addChatRoomUser(User user){
		if(!chatRoomUserList.contains(user)){
			chatRoomUserList.add(user);
		}
	}
	
	public void removeChatRoomUser(User user){
		if(chatRoomUserList.contains(user)){
			chatRoomUserList.remove(user);
		}
	}
	
	public Vector<User> getChatRoomUserList() {
		return chatRoomUserList;
	}

	public void setChatRoomUserList(Vector<User> chatRoomUserList) {
		this.chatRoomUserList = chatRoomUserList;
	}

	public int getPersonLimit() {
		return personLimit;
	}

	public void setPersonLimit(int personLimit) {
		this.personLimit = personLimit;
	}
	
	public int getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(int roomNum) {
		this.roomNum = roomNum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getHeadId() {
		return headId;
	}

	public void setHeadId(String headId) {
		this.headId = headId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void checkHeadRight(String id){
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ChatRoom) || obj == null){
			return false;
		}
		ChatRoom temp = (ChatRoom)obj;
		return title.equals(temp.title);
	}
	
	@Override
	public String toString() {
		return "방 번호 : " + roomNum + ", 방제 : " + title + ", 인원 : " + chatRoomUserList.size();
	}
}
