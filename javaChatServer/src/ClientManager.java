import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/*
 * 190623 재영 난도질 멤버변수 다 수정 / 메소드 추가
 */

/*
 * 190627 14:30 손원도 추가 칼질 
 * -> Vector에 중복으로 된 값이 없도록 처리 함.
 * 	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★ 부분 수정 해야함.
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
	 * 190624 이재영이 수정 챗룸리스트 추가 /삭제
	 */
	public void addChatRoom(ChatRoom cr) {		
		if(!(chatRoomList.contains(cr))){
			chatRoomList.add(cr);	
		}
	}
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void removeChatRoom(ChatRoom cr) {
		if((chatRoomList.contains(cr))){
			chatRoomList.remove(cr);	
		}
	}

	public void addJupsokUser(User user) {
		wr.addWaitingRoomUserList(user);	// 대기실로 가라. 좋은 말 할때
		addConnectedUserList(user);		// 일단 환영한다.
		System.out.println("접속 유저 사이즈 : " + connectedUserList.size());
		System.out.println("대기실 유저 사이즈 : " + wr.getWaitingRoomUserList().size());
	}

	public void addJupsokThread(String id, TransportThread thread) {
		threadMap.put(id, thread);
		System.out.println("쓰레드 벡터사이즈 :" + threadMap.size());
	}

	public void removeAllById(String id) {// 갈사람은 가야지. 미련없이 보내주자.
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

	// 채팅방 새로 만드는 메소드
	// 공개 or 비밀방으로 구성
	// Null잡아야함
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
		//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		addChatRoom(cr);
		return cr;
	}

	// 채팅방 번호를 관리하는 메소드
	private int createRoomNum() {
		int size = chatRoomList.size();
		System.out.println("번호 관리 " + size);
		for (int i = 0; i < size; i++) {

			// 현재 존재 하는 순서대로 가져와서 번호 확인해서 ?간에 빠진 번호 찾기
			if ((i + 1) != chatRoomList.get(i).getRoomNum()) {

				// 순서대로가 아니라면 그 번호를 넣어준다
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
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void addConnectedUserList(User user) {
		if(!(connectedUserList.contains(user))){
			connectedUserList.add(user); // 일단 환영한다.	
		}
	}
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void removeConntectedUserList(User user) {
		if((connectedUserList.contains(user))){
			connectedUserList.remove(user); // 일단 환영한다.	
		}
	}
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void addChatRoomList(ChatRoom cr) {
		if(!(chatRoomList.contains(cr))) {
			chatRoomList.add(cr);
		}
	}
	//★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	public void removeChatRoomList(ChatRoom cr) {
		if(!(chatRoomList.contains(cr))) {
			chatRoomList.remove(cr);
		}
	}

}
