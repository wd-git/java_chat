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
	

	// ����Ʈ�� �߰��ϴ� �޼ҵ�
	// ���̵�� �߰� �Ѵٸ�
	// User ����Ʈ���� ���ͼ� �ذ��ϰھ�.
	// ������ ����
	//�ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
	public void addWaitingRoomUserList(Vector<User> connetedUserList, String id) {
		for (User temp : connetedUserList) {
			if (temp.equals(id)) {
				if(!(waitingRoomUserList.contains(temp))) {
					waitingRoomUserList.add(temp);		
				}
			}
		}
	}
	//�ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
	public void addWaitingRoomUserList(User user) {
		if(!(waitingRoomUserList.contains(user))) {
			waitingRoomUserList.add(user);		
		}
	}
	//�ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
	// ����Ʈ���� �����ϴ� �޼ҵ�
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
	//�ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
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
