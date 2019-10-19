import java.io.Serializable;
import java.util.Vector;

public class WaitingRoom implements Serializable {
	private static final long serialVersionUID = 5L;

	private Vector<User> waitingRoomUserList;
	private Vector<ChatRoom> crList;
	public WaitingRoom() {
		waitingRoomUserList = new Vector<User>();
	}
	
	public WaitingRoom(Vector<ChatRoom> crList) {
		this.crList = crList;
		waitingRoomUserList = new Vector<User>();
	}
	

	// 리스트에 추가하는 메소드
	// 아이디로 추가 한다면
	// User 리스트에서 빼와서 해결하겠어.
	// 다형성 ㄱㄱ
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void addWaitingRoomUserList(Vector<User> connetedUserList, String id) {
		for (User temp : connetedUserList) {
			if (temp.equals(id)) {
				if(!(waitingRoomUserList.contains(temp))) {
					waitingRoomUserList.add(temp);		
				}
			}
		}
	}
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void addWaitingRoomUserList(User user) {
		if(!(waitingRoomUserList.contains(user))) {
			waitingRoomUserList.add(user);		
		}
	}
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	// 리스트에서 삭제하는 메소드
	public void removeWaitingRoomUserList(Vector<User> connetedUserList,
			String id) {
		for (User temp : connetedUserList) {
			if (temp.equals(id)) {
				if((waitingRoomUserList.contains(temp))) {
					waitingRoomUserList.remove(temp);		
				}
			}
		}
	}
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void removeWaitingRoomUserList(User user) {
		if((waitingRoomUserList.contains(user))) {
			waitingRoomUserList.remove(user);		
		}
	}

	public Vector<User> getWaitingRoomUserList() {
		return waitingRoomUserList;
	}

	public void setWaitingRoomUserList(Vector<User> waitingRoomUserList) {
		this.waitingRoomUserList = waitingRoomUserList;
	}
	public Vector<ChatRoom> getCrList() {
		return crList;
	}

	public void setCrList(Vector<ChatRoom> crList) {
		this.crList = crList;
	}
	

}
