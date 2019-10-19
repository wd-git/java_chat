import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/*
 * 190623 �翵 ������ ������� �� ���� / �޼ҵ� �߰�
 */

/*
 * 190627 14:30 �տ��� �߰� Į�� 
 * -> Vector�� �ߺ����� �� ���� ������ ó�� ��.
 * 	//�ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ� �κ� ���� �ؾ���.
 */


public class ClientManager implements Serializable {
	private static final long serialVersionUID = 7L;

	private Vector<User> connectedUserList;
	private WaitingRoom wr;
	private Vector<ChatRoom> chatRoomList;
	private Map<String, TransportThread> threadMap;
	private Map<String, Object> roomInfoMap;

	/*
	 * private Vector<String> waitingRoom`ist; private Vector<String>
	 * connetedUserList; private Map<String, TransportThread> threadMap; private
	 * Map<String, Object> roomInfoMap; private Vector<String> idNickList;
	 */

	public ClientManager() {
		init();
	}

	private void init() {
		connectedUserList = new Vector<User>();
		chatRoomList = new Vector<ChatRoom>();
		wr = new WaitingRoom(chatRoomList);
		threadMap = new HashMap<String, TransportThread>();
		roomInfoMap = new HashMap<String, Object>();
	}

	/*
	 * 190624 ���翵�� ���� ê�븮��Ʈ �߰� /����
	 */
	public void addChatRoom(ChatRoom cr) {		
		if(!(chatRoomList.contains(cr))){
			chatRoomList.add(cr);	
		}
	}
	//�ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
	public void removeChatRoom(ChatRoom cr) {
		if((chatRoomList.contains(cr))){
			chatRoomList.remove(cr);	
		}
	}

	public void addJupsokUser(User user) {
		wr.addWaitingRoomUserList(user);	// ���Ƿ� ����. ���� �� �Ҷ�
		addConnectedUserList(user);		// �ϴ� ȯ���Ѵ�.
		System.out.println("���� ���� ������ : " + connectedUserList.size());
		System.out.println("���� ���� ������ : " + wr.getWaitingRoomUserList().size());
	}

	public void addJupsokThread(String id, TransportThread thread) {
		threadMap.put(id, thread);
		System.out.println("������ ���ͻ����� :" + threadMap.size());
	}

	public void removeAllById(String id) {// ������� ������. �̷þ��� ��������.
		threadMap.remove(id);
		wr.removeWaitingRoomUserList(new User(id));
		removeConntectedUserList((new User(id)));
		
//		System.out.println("remove info from all list");
//
//		System.out.println("threadMap : " + threadMap.toString());
//		System.out.println("wr.wrlist : "
//				+ wr.getWaitingRoomUserList().toString());
//		System.out.println("connetedUserList : " + connectedUserList.toString());
		
	}

	// ä�ù� ���� ����� �޼ҵ�
	// ���� or ��й����� ����
	// Null��ƾ���
	public ChatRoom createNewRoom(String id, int personLimit, String title,
			String pw) {
		ChatRoom cr = null;
		int chatRoomNum = createRoomNum();
		if (pw == null) {
			cr = new ChatRoom(chatRoomNum, personLimit, id, title);
		} else {
			cr = new ChatRoom(chatRoomNum, personLimit, id, title, pw);
		}
		//chatRoomList.add(cr);
		//�ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
		addChatRoom(cr);
		return cr;
	}

	// ä�ù� ��ȣ�� �����ϴ� �޼ҵ�
	private int createRoomNum() {
		int size = chatRoomList.size();
		System.out.println("��ȣ ���� " + size);
		for (int i = 0; i < size; i++) {

			// ���� ���� �ϴ� ������� �����ͼ� ��ȣ Ȯ���ؼ� ?���� ���� ��ȣ ã��
			if ((i + 1) != chatRoomList.get(i).getRoomNum()) {

				// ������ΰ� �ƴ϶�� �� ��ȣ�� �־��ش�
				return i + 1;
			}
		}
		return size + 1;
	}

	public Map<String, TransportThread> getThreadMap() {
		return threadMap;
	}

	public void setThreadMap(Map<String, TransportThread> threadMap) {
		this.threadMap = threadMap;
	}

	public Map<String, Object> getRoomInfoMap() {
		return roomInfoMap;
	}

	public void setRoomInfoMap(Map<String, Object> roomInfoMap) {
		this.roomInfoMap = roomInfoMap;
	}

	public Vector<User> getConnectedUserList() {
		return connectedUserList;
	}

	public void setConnectedUserList(Vector<User> connetedUserList) {
		this.connectedUserList = connetedUserList;
	}

	public WaitingRoom getWr() {
		return wr;
	}

	public void setWr(WaitingRoom wr) {
		this.wr = wr;
	}

	public Vector<ChatRoom> getChatRoomList() {
		return chatRoomList;
	}

	public void setChatRoomList(Vector<ChatRoom> chatRoomList) {
		this.chatRoomList = chatRoomList;
	}
	//�ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
	public void addConnectedUserList(User user) {
		if(!(connectedUserList.contains(user))){
			connectedUserList.add(user); // �ϴ� ȯ���Ѵ�.	
		}
	}
	//�ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
	public void removeConntectedUserList(User user) {
		if((connectedUserList.contains(user))){
			connectedUserList.remove(user); // �ϴ� ȯ���Ѵ�.	
		}
	}
	//�ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
	public void addChatRoomList(ChatRoom cr) {
		if(!(chatRoomList.contains(cr))) {
			chatRoomList.add(cr);
		}
	}
	//�ڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡڡ�
	public void removeChatRoomList(ChatRoom cr) {
		if(!(chatRoomList.contains(cr))) {
			chatRoomList.remove(cr);
		}
	}

}
