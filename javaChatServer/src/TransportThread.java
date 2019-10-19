import java.io.*;
import java.net.*;
import java.nio.channels.GatheringByteChannel;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.*;

public class TransportThread extends Thread implements Protocol, Serializable {
	private static final long serialVersionUID = 8L;
	private ObjectInputStream ois; // 클라이언트로 부터 받는 것
	private ObjectOutputStream oos; // 클라이언트에 쓰는것
	private Map<String, ObjectOutputStream> hm;
	private Map<String, User> userList;
	private boolean isCorrectInfo;
	private SendData data;
	private Object buf;
	private Server server;
	private ClientManager cm;

	public TransportThread(Socket s, Map<String, User> userList,
			ClientManager cm, Server server) {
		this.userList = userList;
		this.cm = cm;
		this.server = server;
		try {
			ois = new ObjectInputStream(s.getInputStream());
			oos = new ObjectOutputStream(s.getOutputStream());
			Object buf;
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ObjectInputStream getOis() {
		return ois;
	}

	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}

	public ObjectOutputStream getOos() {
		return oos;
	}

	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}

	// 서버의 데이터 리시버
	@Override
	public void run() {
		try {
			synchronized (this) {
				while ((data = (SendData) ois.readObject()) != null) {
					
					switch (data.getOpcode()) {
					// ///////////////////////////////////////////////////////////////
					case LOGIN_PLEASE:
						checkId(data);
						break;
					case LOGIN_SUCCESS_SCATTER_PLEASE:
						loginfSuccessScatterPlease(data);
						break;

					case LOGOUT:
						logout(data);
						break;
					case VERIFY_ID:
						checkOverlapId(data);
						break;
					case JOIN:
						join(data);
						break;
					case LOOKUP_ID:
						lookup_Id(data);
						break;
					case LOOKUP_PW:
						lookup_Pw(data);
						break;
					case SEND_WAITINGROOM_NORMAL_MSG:
						sendWaitingNormalMsg(data);
						break;
					case SEND_WAITINGROOM_WHISPER_MSG:
						sendWhisper(data);
						break;

					case CREATE_CHATROOM:
						// data : id,personLimit, title, pw
						createChatRoom();
						break;

					case CREATE_CAHTROOM_SUCESS_SCATTER_PLEASE:
						// data 는 null
						creatChatRoomSuccessScatterPlease();
						break;

					case SHOW_MY_PROFILE:
						successShowMyProfile(data);
						break;
					case WITHDRAW:
						withdraw(data);
						break;
					case MODIFY_MY_PROFILE:
						modifyMyProfile(data);
						break;

					case SHOW_OTHER_PROFILE:
						showOtherProfile(data);
						break;

					case ENTER_CHATROOM:
						enterChatRoom(data);
						break;
					// 방에 들어가면 대기방에 들한테는 없애고 방에 있는 애들한테는 추가해야 됨.

					case PASSWORD_INPUT_DONE:
						checkChatRoomPassword(data);
						break;
					case ENTER_CHATROOM_SUCCESS_SCATTER_PLEASE:
						enterChatRoomSuccessscatterPlease();
						break;

					case EXIT_CHATROOM:
						exitChatRoomPlease(data);
						break;

					case CHANGE_CHATROOM_HEAD_PLEASE_AND_GETOUT:
						changeChatRoomHeadPleaseAndGetOut(data);
						break;

					case SUCCESS_EXIT_CHATROOM_PLEASE_SCATTER:
						successExitChatRoomPleaseScatter(data);
						break;

					case KICKOUT_CHATROOM:
						kickOutChatRoom(data);
						break;

					case ADD_FRIEND:
						addFriend(data);
						break;

					case RESPOND_YES_REQUSET_ADD_FRIEND:
						respondYesRequestAddFriend(data); // 정상욱 추가 06-24 20:00
						break;

					case RESPOND_NO_REQUSET_ADD_FRIEND:
						respondNoRequestAddFriend(data); // 정상욱 추가 06-24 20:00
						break;

					case DELETE_FRIEND:
						deleteFriend(data); // 정상욱 추가 06-24 24:00
						break;

					case ADD_BLACKLIST:
						addBlackList(data); // 정상욱 추가 06-25 12:07
						break;

					case SHOW_BLACKLIST:
						showBlackList(data); // 정상욱 추가 06-25 16:27
						break;

					case DELETE_BLACKLIST:
						deleteBlackList(data);
						break;

					case REQUEST_ONEONONE_MSG: // 정상욱 추가 06-25 20:41
						requestOneononeMsg(data);
						break;

					case SUCCESS_SEND_ONEONONE_MSG:
						successSendoneMsg(data);
						break;

					case FAIL_SEND_ONEONE_MSG:
						failSendOneoneMSg(data);
						break;

					case SEND_ONEONONE_MSG:
						sendOneononeMsg(data);
						break;

					case ONEONONECHAT_OUT:
						oneononeChatOut(data);
						break;

					// case TRY_SEND_NOTE: // 임준석 추가 6.25
					// trySendNote(data);
					// break;
					// case SEND_NOTE_TO_ME:
					// System.out.println("메세지잘감?");
					// sendNoteToMe(data);
					// break;

					case NOTE_SEND_PLEASE:
						noteSend(data);
						break;

					case OPEN_NOTEBOX_PLEASE:
						openNoteBox(data);
						break;

					/*
					 * 190626 이재영 추가
					 */
					case REQUEST_MODIFY_ROOM_TITLE:
						modifyRoomTitle(data);
						break;
					case SCATTER_MODIFY_ROOM_TITLE_PLEASE:
						scatterModifyRoomTitlePlease(data);
						break;
					/*
					 * 190627 이재영이 추가
					 */
					case SEND_CHATROOM_MSG_PLEASSE:
						sendMsgToChatRoomUser(data);
						break;
					/*
					 * 190628 이재영 추가 방장변경
					 */
					case CHANGE_CHATROOM_HEAD_PLEASE:
						changeChatRoomHead(data);
						break;
					case SHAKE_IT_BABY_ONE_ON_ONE_PLEASE:
						shakeItBabyOneOnOne(data);
						break;
					}

				}
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}

	private synchronized void scatterModifyRoomTitlePlease(SendData data) {
		sendMsgToUser(cm.getWr().getWaitingRoomUserList(),
				GET_SCATTER_MODIFY_ROOM_TITLE, data.getData());
	}

	private synchronized void kickOutChatRoom(SendData data) {
		ChatRoom cr = (ChatRoom) data.getData()[0];
		String targetId = (String) data.getData()[1];

		// data[0] = cr
		// data[1] = targetId

		cm.getWr().addWaitingRoomUserList((userList.get(targetId)));

		for (ChatRoom temp : cm.getChatRoomList()) {
			if (temp.equals(cr)) {
				sendMsgToUser(temp.getChatRoomUserList(),
						SUCCESS_EXIT_CHATROOM_SCATTER_TO_CHATROOM,
						userList.get(targetId), temp);
				sendMsgToUser(cm.getWr().getWaitingRoomUserList(),
						SUCCESS_EXIT_CHATROOM_SCATTER_TO_WAITINGROOM,
						userList.get(targetId), temp);
				temp.removeChatRoomUser(userList.get(targetId));

				if (temp.getChatRoomUserList().size() == 0) {
					cm.getChatRoomList().remove(temp);
				}

				break;
			}
		}

		sendData(new SendData(SUCCESS_KICKOUT)); // 방장에게 알려줌.
		sendMsgToOnlyOneUserById(targetId, YOU_ARE_KICKED, cm.getWr());

	}

	private synchronized void successExitChatRoomPleaseScatter(SendData data) {
		// 0626 손원도 작성
		// 1. 대기방에 유저 추가.
		// 2. 채팅방에서 유저 삭제.

		// 대기방에 유저 추가
		// data[0] = id;

		// 0702 방에 사람이 없으면 방도 없애버린다 그냥.
		//
		// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		cm.getWr().addWaitingRoomUserList((userList.get(data.getData()[0])));

		// (String)data.getData()[1] = 방제목 => 방 객체 찾거라.

		for (ChatRoom temp : cm.getChatRoomList()) {
			if (temp.getTitle().equals((String) data.getData()[1])) {
				sendMsgToUser(temp.getChatRoomUserList(),
						SUCCESS_EXIT_CHATROOM_SCATTER_TO_CHATROOM,
						userList.get(data.getData()[0]), temp);
				sendMsgToUser(cm.getWr().getWaitingRoomUserList(),
						SUCCESS_EXIT_CHATROOM_SCATTER_TO_WAITINGROOM,
						userList.get(data.getData()[0]), temp);
				// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
				temp.removeChatRoomUser(userList.get(data.getData()[0]));

				if (temp.getChatRoomUserList().size() == 0) {
					cm.getChatRoomList().remove(temp);
				}

				break;
			}
		}
		// 0이 되면 방 없애소
	}

	private synchronized void changeChatRoomHeadPleaseAndGetOut(SendData data) {
		// data
		String title = (String) data.getData()[0];
		String selectedId = (String) data.getData()[1];
		String tempExitId = (String) data.getData()[2];

		for (ChatRoom temp : cm.getChatRoomList()) {
			if (temp.getTitle().equals(title)) {
				temp.setHeadId(selectedId);
				temp.removeChatRoomUser(userList.get(tempExitId));

				sendMsgToUser(temp.getChatRoomUserList(),
						OK_SUCESS_CHANGED_HEAD, title, selectedId);
				break;
			}
		}
	}

	private synchronized void enterChatRoomSuccessscatterPlease() {
		String tempId = (String) data.getData()[0];
		ChatRoom tempCr = (ChatRoom) data.getData()[1];

		for (ChatRoom temp : cm.getChatRoomList()) {
			if (temp.equals(tempCr)) {
				// temp.addChatRoomUser(userList.get(tempId));
				sendMsgToUser(temp.getChatRoomUserList(),
						ENTER_GET_SCATTER_ORDER_IN_CHATROOM,
						userList.get(tempId), temp);
				sendMsgToUser(cm.getWr().getWaitingRoomUserList(),
						ENTER_GET_SCATTER_ORDER_IN_WAITINGROOM,
						userList.get(tempId), temp);
			}
		}

		cm.getWr().removeWaitingRoomUserList(userList.get(tempId));
	}

	private synchronized void creatChatRoomSuccessScatterPlease() {
		String id = (String) data.getData()[1];
		sendMsgToUser(cm.getWr().getWaitingRoomUserList(),
				CREATE_GET_SCATTER_ORDER, cm.getChatRoomList().lastElement(),
				id);
		// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		cm.getWr().removeWaitingRoomUserList(new User(id));
	}

	private synchronized void createChatRoom() {
		createNewChatRoom(data.getData());
		server.getSf().refreshUsers((String) data.getData()[0],
				(String) data.getData()[2]);
		server.getSf().appendMsg(
				(String) data.getData()[0] + "님이 " + (String) data.getData()[2]
						+ " 방을 만들었습니다. \n");
	}

	private synchronized void sendWaitingNormalMsg(SendData data) {
		sendToJupsok(new SendData(SUCCESS_SEND_WATINGROOM_NORMAL_MSG,
				data.getData()));
	}

	private synchronized void shakeItBabyOneOnOne(SendData data) {
		String targetId = (String) data.getData()[0];
		String shakeId = (String) data.getData()[1];
		ObjectOutputStream targetOos = cm.getThreadMap().get(targetId).getOos();
		try {
			targetOos.writeObject(new SendData(OK_SHAKE_IT_BABY_ONE_ON_ONE,
					shakeId));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 190702 이재영이 추가
	 */

	private synchronized void openNoteBox(SendData data) {
		String id = (String) data.getData()[0];
		User tempUser = userList.get(id);
		sendData(new SendData(SUCESSE_OPEN_NOTEBOX, tempUser.getNoteBox()));

	}

	/*
	 * 190628 이재영이 추가
	 */

	private synchronized void changeChatRoomHead(SendData data) {
		String title = (String) data.getData()[0];
		String selectedId = (String) data.getData()[1];

		for (ChatRoom temp : cm.getChatRoomList()) {
			if (temp.getTitle().equals(title)) {
				temp.setHeadId(selectedId);
				// 채팅방내의 유저들에게 전송!
				sendMsgToUser(temp.getChatRoomUserList(),
						OK_SUCESS_CHANGED_HEAD, title, selectedId);
				break;
			}
		}
	}

	/*
	 * 190627 이재영이 추가
	 */
	private synchronized void sendMsgToChatRoomUser(SendData data) {
		String id = (String) data.getData()[0];
		String msg = (String) data.getData()[1];
		String title = (String) data.getData()[2];

		for (ChatRoom temp : cm.getChatRoomList()) {
			if (temp.getTitle().equals(title)) {
				sendMsgToUser(temp.getChatRoomUserList(),
						REQUEST_SEND_CHATROOM_MSG, id, msg, title);
			}
		}
	}

	/*
	 * 190702 이재영이 수정
	 */
	private synchronized void noteSend(SendData data) {
		String msg = (String) data.getData()[0];
		String myId = (String) data.getData()[1];
		String targetId = (String) data.getData()[2];

		User myUser = userList.get(myId);
		User targetUser = userList.get(targetId);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat();
		if (!targetUser.getBlackList().contains(myUser)) {
			myUser.addNote(new Note(myId, targetId, msg, sdf.format(date)));
			targetUser.addNote(new Note(myId, targetId, msg, sdf.format(date)));

			Utils.writeFile(userList, "data\\data.dat");
			ObjectOutputStream targetOos = cm.getThreadMap().get(targetId)
					.getOos();

			try {
				targetOos.writeObject(new SendData(SUCCESSE_NOTE_SEND, msg,
						myId, targetId));
				targetOos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
	}

	private synchronized void loginfSuccessScatterPlease(SendData data) {
		Vector<User> waitingUserList = cm.getWr().getWaitingRoomUserList();
		for (User temp : waitingUserList) {
			if (temp.getId().equals(data.getData()[0])) {
				sendMsgToUser(waitingUserList, LOGIN_GET_SCATTTER_ORDER, temp,
						cm.getChatRoomList());
			}
		}
	}

	private synchronized void modifyRoomTitle(SendData data) {
		String oriTitle = (String) data.getData()[0];
		String changedTitle = (String) data.getData()[1];

		ChatRoom tempCr = null;
		boolean flag = true;
		int index = 0;
		while (flag) {
			if (cm.getChatRoomList().get(index).equals(new ChatRoom(oriTitle))) {
				tempCr = cm.getChatRoomList().get(index);
				tempCr.setTitle(changedTitle);
				flag = false;
			}
		}
		sendData(new SendData(SUCESS_MODIFY_ROOM_TITLE, oriTitle, changedTitle));
	}

	/*
	 * 190623 이재영이 추가 채팅방에 입장할떄 일어나야하는 일 서버에서 처리해야할것들 CM에 있는 챗룸객체를 찾아서 아이디 추가해주고
	 * 해당 ChatRoom객체를 클라이언트에게 전송
	 */

	// 0626 손원도 10:10 추가
	// 웨이팅룸에서 지워준다. -> 죽은자의 온기가 남아있습니다.
	public synchronized void enterChatRoom(SendData data) {

		Object[] dataArr = data.getData();
		String id = (String) dataArr[0];
		String title = (String) dataArr[1];

		// 손원도 0628 00:50 추가
		// 이게 for-Each로는 갱신이 안되나봐.

		for (int i = 0; i < cm.getChatRoomList().size(); i++) {
			if (cm.getChatRoomList().get(i).getTitle().equals(title)) {

				ChatRoom crTemp = cm.getChatRoomList().get(i);
				if (crTemp.getChatRoomUserList().size() < crTemp
						.getPersonLimit()) {
					if (cm.getChatRoomList().get(i).getPassword().equals("")) {
						cm.getChatRoomList().get(i)
								.addChatRoomUser(userList.get(id));
						// 해당 아이디 리스트에 추가
						sendData(new SendData(SUCCESS_ENTER_CHATROOM, cm
								.getChatRoomList().get(i), userList.get(id)));
						server.getSf().appendMsg(
								id + "님이 " + title + " 방에 들어 갔습니다. \n");
					} else {
						sendData(new SendData(PASSWORD_INPUT_PLEASE, title, id));
					}
				} else {
					sendData(new SendData(OVERFLOW_PERSON_LIMIT_IN_CHATROOM));
				}

			}
		}
	}

	private synchronized void exitChatRoomPlease(SendData data) {
		String roomTitle = (String) data.getData()[0];
		String exitId = (String) data.getData()[1];
		ChatRoom exitCr = null;

		for (ChatRoom temp : cm.getChatRoomList()) {
			if (temp.getTitle().equals(roomTitle)) {
				exitCr = temp;
			}
		}

		// 방장이면 권한을 넘기고 나가세요
		if (exitCr.getHeadId().equals(exitId)
				&& !(exitCr.getChatRoomUserList().size() == 1)) {
			sendData(new SendData(CHANGE_CHATROOM_HEAD_BEFORE_EXIT, exitCr,
					exitId));
		} else if (exitCr.getChatRoomUserList().size() == 1) {
			sendData(new SendData(SUCCESS_EXIT_CHATROOM, cm.getWr(), roomTitle));
		} else {
			sendData(new SendData(SUCCESS_EXIT_CHATROOM, cm.getWr(), roomTitle));
		}

		server.getSf().appendMsg(exitId + "님이 " + roomTitle + " 방에서 나왔습니다. \n");
		server.getSf().refreshUsers((String) data.getData()[0], "대기실");
	}

	private synchronized void checkChatRoomPassword(SendData data) {
		String pw = (String) data.getData()[0];
		String title = (String) data.getData()[1];
		String id = (String) data.getData()[2];

		for (ChatRoom tempCr : cm.getChatRoomList()) {
			if (tempCr.getTitle().equals(title)) {// 해당 채팅방을 찾아서
				if (tempCr.getPassword().equals(pw)) {
					sendData(new SendData(SUCCESS_ENTER_CHATROOM, tempCr,
							userList.get(id)));
				} else {
					sendData(new SendData(FAIL_ENTER_CHATROOM_WRONG_PASSWORD));
				}
			}
		}

	}

	private synchronized void oneononeChatOut(SendData data) {
		User user = (User) data.getData()[0];
		User targetUser = (User) data.getData()[1];
		String targetId = targetUser.getId();

		ObjectOutputStream targetOos = cm.getThreadMap().get(targetId).getOos();
		try {
			targetOos.writeObject(new SendData(ONEONONECHAT_OUT, user,
					targetUser));
			targetOos.flush();
			// targetOos.reset();
		} catch (IOException e) {

		}
	}

	private synchronized void sendOneononeMsg(SendData data) {
		User user = (User) data.getData()[0];
		User targetUser = (User) data.getData()[1];
		String msg = (String) data.getData()[2];
		String targetId = targetUser.getId();
		ObjectOutputStream targetOos = cm.getThreadMap().get(targetId).getOos();
		try {
			targetOos.writeObject(new SendData(SEND_ONEONONE_MSG, user,
					targetUser, msg));
			targetOos.flush();
			// targetOos.reset();
		} catch (IOException e) {

		}
	}

	private synchronized void requestOneononeMsg(SendData data) {
		String myId = (String) data.getData()[0];
		String targetId = (String) data.getData()[1];
		User user = userList.get(myId);
		User targetUser = userList.get(targetId);

		ObjectOutputStream targetOos = cm.getThreadMap().get(targetId).getOos();
		try {
			targetOos.writeObject(new SendData(REQUEST_ONEONONE_MSG, user,
					targetUser));
			targetOos.flush();
			// targetOos.reset();
		} catch (IOException e) {

		}
	}

	private synchronized void successSendoneMsg(SendData data) {
		User user = (User) data.getData()[0];
		User targetUser = (User) data.getData()[1];

		sendData(new SendData(SUCCESS_SEND_ONEONONE_MSG, targetUser, user));
		ObjectOutputStream tempOos = cm.getThreadMap().get(user.getId())
				.getOos();
		try {
			tempOos.writeObject(new SendData(SUCCESS_SEND_ONEONONE_MSG, user,
					targetUser));
			tempOos.flush();
			// tempOos.reset();
		} catch (IOException e) {

		}
	}

	private synchronized void failSendOneoneMSg(SendData data) {
		User user = (User) data.getData()[0];

		ObjectOutputStream tempOos = cm.getThreadMap().get(user.getId())
				.getOos();
		try {
			tempOos.writeObject(new SendData(FAIL_SEND_ONEONE_MSG, data));
			tempOos.flush();
			// tempOos.reset();
		} catch (IOException e) {

		}
	}

	private synchronized void showBlackList(SendData data) {
		String myId = (String) data.getData()[0];
		User myUser = userList.get(myId);
		sendData(new SendData(SUCCESS_SHOW_BLACKLIST, myUser.getBlackList()));
	}

	// 정상욱 추가 06-24 24:00

	private synchronized void deleteFriend(SendData data) {
		String myId = (String) data.getData()[0];
		String targetId = (String) data.getData()[1];
		User user = userList.get(myId);
		if (userList.containsKey(targetId)) {
			user = userList.get(myId);
			User targetUser = userList.get(targetId);
			// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
			user.removeFriendList(targetUser);
			targetUser.removeFriendList(user);

			userList.put(myId, user);
			userList.put(targetId, targetUser);
			Utils.writeFile(userList, "data\\data.dat");
			sendData(new SendData(DELETE_FRIEND, targetUser));
		} else {

		}
		ObjectOutputStream targetOos = cm.getThreadMap().get(targetId).getOos();
		try {
			targetOos.writeObject(new SendData(DELETE_FRIEND, user));
			targetOos.flush();
			// targetOos.reset();
		} catch (IOException e) {

		}

	}

	/*
	 * 190623 이재영이 추가
	 */
	// 어떤 종류의 유저들에게 일괄적으로 데이터를 보냄
	private synchronized void sendMsgToUser(Vector<User> tempList, int opcode,
			Object... sData) {
		for (int i = 0; i < tempList.size(); i++) {
			User key = tempList.get(i);
			if (cm.getThreadMap().containsKey(key.getId())) {
				ObjectOutputStream tempOos = cm.getThreadMap().get(key.getId())
						.getOos();
				try {
					tempOos.writeObject(new SendData(opcode, sData));
					tempOos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private synchronized void sendMsgToOnlyOneUserById(String targetId,
			int opcode, Object... sData) {
		User key = userList.get(targetId);
		if (cm.getThreadMap().containsKey(key.getId())) {
			ObjectOutputStream tempOos = cm.getThreadMap().get(key.getId())
					.getOos();
			try {
				tempOos.writeObject(new SendData(opcode, sData));
				tempOos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private synchronized void deleteBlackList(SendData data) {
		String myId = (String) data.getData()[0];
		User targetUser = (User) data.getData()[1];
		User user = userList.get(myId);

		// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		user.removeBlackList(targetUser);
		userList.put(myId, user);

		Utils.writeFile(userList, "data\\data.dat");

		sendData(new SendData(DELETE_BLACKLIST, targetUser));

	}

	// 6.24일 오후12시20분 임준석 추가 (쪽지)
	private synchronized void trySendNote(SendData data) {
		String msg = (String) data.getData()[0];
		String myId = (String) data.getData()[1];
		String TargetId = (String) data.getData()[2];
		User user = userList.get(myId);
		User tagetUser = userList.get(TargetId);
		ObjectOutputStream msgOos = cm.getThreadMap().get(TargetId).getOos();
		try {
			msgOos.writeObject(new SendData(TRY_SEND_NOTE, msg, user, tagetUser));
			msgOos.flush();
			// msgOos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 6.26 추가 임준석
	// private void sendNoteToMe(SendData data) {
	// String msg = (String) data.getData()[0];
	// User user = (User) data.getData()[1];
	// User tagetUser = (User) data.getData()[2];
	// ObjectOutputStream msgOos = cm.getThreadMap().get(user.getId()).getOos();
	// try {
	// msgOos.writeObject(new SendData(TRY_SEND_NOTE, msg, tagetUser, user));
	// msgOos.flush();
	// msgOos.reset();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	/*
	 * 190623 이재영이 추가 /* 190623 이재영이 추가 서버의 CM이 ChatRoom객체를 만들고 관리해야한다 만등어서
	 * 클라이언트로 넘겨주면 Sucess create chatroom 신호로 전달 클라이언트가 받아서 그걸로 패널에 만들어 붙임 그리고
	 * CM은 대기실인원까지 관리해줘야함.
	 * ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
	 * 그리고 대기실인원들에게 변경된 인원에 대한 정보를 보내줘야함.
	 */
	private synchronized void createNewChatRoom(Object... dataArr) {
		String id = (String) dataArr[0];
		int personLimit = (int) dataArr[1];
		String title = (String) dataArr[2];
		String pw = null;
		if (dataArr[3] != null) {
			pw = (String) dataArr[3];
		}
		ChatRoom cr = cm.createNewRoom(id, personLimit, title, pw);
		cr.addChatRoomUser(userList.get(id));
		/*
		 * 190623 손기사님 User객체로 수정요망
		 */
		// cm.getWr().removeWaitingRoomUserList(user);
		sendData(new SendData(SUCCESS_CREATE_CHATROOM, cr));
	}

	private synchronized void sendWhisper(SendData data) {
		Object[] dataArr = data.getData();
		String myId = (String) dataArr[0];
		String targetId = (String) dataArr[1];
		User tempUser = userList.get(targetId);
		// String msg = (String)dataArr[2];

		if (cm.getWr().getWaitingRoomUserList().contains(tempUser)) {
			ObjectOutputStream myOos = cm.getThreadMap().get(myId).getOos();
			ObjectOutputStream targetOos = cm.getThreadMap().get(targetId)
					.getOos();
			try {
				myOos.writeObject(data);
				targetOos.writeObject(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			sendData(new SendData(NO_JUPSOK_SO_CANT_WHISPER));
		}

	}

	// 정상욱 추가 06-24 20:00

	private synchronized void addFriend(SendData data) {
		String targetId = (String) data.getData()[0];
		String myId = (String) data.getData()[1];
		String msg = (String) data.getData()[2];
		User user = userList.get(myId);
		User targetUser = userList.get(targetId);
		if (userList.containsKey(targetId)) {
			if (!user.getFriendsIdList().contains(targetUser)) {
				ObjectOutputStream targetOos = cm.getThreadMap().get(targetId)
						.getOos();
				try {
					targetOos.writeObject(new SendData(ADD_FRIEND, targetId,
							myId, msg));
					targetOos.flush();
					// targetOos.reset();
				} catch (IOException e) {
					// TODO: handle exception
				}
			} else {
				sendData(new SendData(OVERLAP_FRIEND));
			}

		} else {
			sendData(new SendData(NOT_EXIST_ID_FAIL_ADD_FRIEND));
		}

	}

	// 정상욱 추가 06-24 20:00

	public synchronized void respondNoRequestAddFriend(SendData data) {
		String myId = (String) data.getData()[0];

		ObjectOutputStream myOos = cm.getThreadMap().get(myId).getOos();
		try {
			myOos.writeObject(new SendData(RESPOND_NO_REQUSET_ADD_FRIEND, data));
			myOos.flush();
			// myOos.reset();
		} catch (IOException e) {

		}
	}

	// 정상욱 추가 06-24 20:00

	public synchronized void respondYesRequestAddFriend(SendData data) {
		String myId = (String) data.getData()[0];
		String targetId = (String) data.getData()[1];
		User user = userList.get(myId);
		User targetUser = userList.get(targetId);
		// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		user.addFriendList(targetUser);
		targetUser.addFriendList(user);

		// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
		userList.put(myId, user);
		userList.put(targetId, targetUser);
		Utils.writeFile(userList, "data\\data.dat");

		sendData(new SendData(RESPOND_YES_REQUSET_ADD_FRIEND, user));

		ObjectOutputStream myOos = cm.getThreadMap().get(myId).getOos();
		try {
			myOos.writeObject(new SendData(RESPOND_YES_REQUSET_ADD_FRIEND,
					targetUser));
			myOos.flush();
			// myOos.reset();
		} catch (IOException e) {

		}
	}

	public synchronized void showOtherProfile(SendData data) {
		String id = (String) data.getData()[0];
		if (!userList.containsKey(id)) {
			sendData(new SendData(NO_EXIST_ID_SEARCH_ID));
		} else {
			User user = userList.get(id);
			sendData(new SendData(SUCCESS_SHOW_OTHER_PROFILE, user));
		}

	}

	public synchronized void modifyMyProfile(SendData data) { // 비밀번호가 일치하다면 기존
																// 유저리스트에서 삭제,
		// 다시 추가후 저장
		User user = (User) data.getData()[0]; // 기존 패스워드와 아이디를 담고 있는 유저
		User user2 = userList.get(user.getId()); // 기존 유저리스트에 있는 유저
		User user3 = (User) data.getData()[1]; // 수정된 유저정보
		if (user2.getPw().equals(user.getPw())) {

			for (User myFriendUser : user2.getFriendList()) {
				// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
				user3.addFriendList((myFriendUser));
			}
			userList.remove(user2);
			userList.put(user3.getId(), user3);
			cm.removeConntectedUserList(user2);
			cm.addConnectedUserList(user3);
			
			cm.getWr().removeWaitingRoomUserList(user);
			cm.getWr().addWaitingRoomUserList(user3);
			
			sendData(new SendData(SUCCESS_SELF_MODIFY_PROFILE, user2, user3));
			
			for (User friendUser : user3.getFriendList()) {
				// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
				friendUser.removeFriendList(user2);
				// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
				friendUser.addFriendList(user3);
				ObjectOutputStream targetOos = cm.getThreadMap()
						.get(friendUser.getId()).getOos();
				try {
					targetOos.writeObject(new SendData(MYPROFILE_OTHER_REFRESH,
							user2, user3));
					targetOos.flush();

				} catch (IOException e) {

				}
			}
			if (cm.getWr().getWaitingRoomUserList().contains(user2)) {
				for (User WatingRoomList : cm.getWr().getWaitingRoomUserList()) {

					ObjectOutputStream watingListOos = cm.getThreadMap()
							.get(WatingRoomList.getId()).getOos();
					try {
						watingListOos.writeObject(new SendData(
								MYPROFILE_WAITINGUSER_REFRESH, user2, user3));
						watingListOos.flush();

					} catch (IOException e) {
						// TODO: handle exception
					}
				}
			}

			for(ChatRoom tempCr : cm.getChatRoomList()){
				System.out.println(MYPROFILE_CHATROOMUSER_REFRESH);
				if(tempCr.getChatRoomUserList().contains(user2)){
					tempCr.getChatRoomUserList().remove(user2);
					tempCr.getChatRoomUserList().add(user3);
					
					sendMsgToUser(tempCr.getChatRoomUserList(), MYPROFILE_CHATROOMUSER_REFRESH, user2, user3);
				}
			}

		} else {
			sendData(new SendData(FAIL_SELF_MODIFY_PROFILE));
		}
		Utils.writeFile(userList, "data\\data.dat");
	}

	public synchronized void withdraw(SendData data) {
		String id = (String) data.getData()[0];
		String pw = (String) data.getData()[1];
		User user = userList.get(id);
		if (user.getPw().equals(pw)) { // 클라이언트에서 입력된 비밀번호와 서버에 저장되있는 비밀번호가
										// 일치한다면 리스트삭제후 저장
			userList.remove(id);
			Utils.writeFile(userList, "data\\data.dat");
			sendData(new SendData(WITHDRAW_OK));
		} else { // 일치 하지 않는 경우
			sendData(new SendData(NOT_FOUND_INFO_WITHDARW));
		}

	}

	public synchronized void successShowMyProfile(SendData data) {
		String id = (String) data.getData()[0];

		sendData(new SendData(SUCCESS_SHOW_MY_PROFILE, userList.get(id)));
	}

	public synchronized void join(SendData data) {
		User user = (User) data.getData()[0];
		String id = user.getId();
		userList.put(id, user);
		// 리스트에 추가
		System.out.println("회원수 : " + userList.size());
		String[] dataArr = { id };

		sendData(new SendData(JOIN_SUCCESS, dataArr));
		Utils.writeFile(userList, "data\\data.dat");

	}

	// 서버가 클라이언트에 쓰는거
	public synchronized void sendData(SendData data) {
		try {
			oos.writeObject(data);
			oos.flush();
			// oos.reset();
			// oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private synchronized void sendToJupsok(SendData data) {
		// 포문돌면서 메세지 전송
		int listSize = cm.getWr().getWaitingRoomUserList().size();
		for (int i = 0; i < listSize; i++) {
			User user = cm.getWr().getWaitingRoomUserList().get(i);
			if (cm.getThreadMap().containsKey(user.getId())) {// 만약 가지고 있다면
				ObjectOutputStream tempOos = cm.getThreadMap()
						.get(user.getId()).getOos();
				try {
					tempOos.writeObject(data);
					tempOos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private synchronized void checkOverlapId(SendData data) {
		String id = (String) data.getData()[0];
		if (!userList.containsKey(id)) {// 존재하면 클라이언트에 데이터를 보냄
			sendData(new SendData(SUCCESS_OVERLAP_CHECK));
		} else {// 이미 존재하는 아이디
			sendData(new SendData(JOIN_ALREADY_EXIST_ID));
		}
	}

	private synchronized void checkId(SendData data) {
		// opcode 100
		String id = (String) data.getData()[0];
		String pw = (String) data.getData()[1];
		try {

			if (userList.get(id) == null) {// 아이디가 존재하지 않으면
				sendData(new SendData(LOGIN_NOT_FOUND_ID));
			} else {
				if (pw.equals(userList.get(id).getPw())) {// 비밀번호가 일치합
					if (cm.getConnectedUserList().contains(new User(id))) {// 이미접속중
						sendData(new SendData(LOGIN_DUPLICATION));
					} else {
						// 로그인.성공적.
						cm.addJupsokUser(userList.get(id));
						cm.addJupsokThread(id, this);
						User user = userList.get(id);
						Object[] loginSuccessData = { id, cm.getWr(), user,
								user.getFriendsIdList() };
						server.getSf().appendMsg(id + " 님이 로그인 하셨습니다. \n");
						server.getSf().addUsers(id, "대기실");
						sendData(new SendData(LOGIN_SUCCESS_PRIVATE,
								loginSuccessData));
					}
				} else {// 비밀번호가 일치 하지 않음
					sendData(new SendData(LOGIN_WRONG_PASSWORD));
				}
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
		}

	}

	public synchronized void lookup_Id(SendData data) {
		// Opcode 120
		String result = "NF";

		for (User temp : userList.values()) {
			if (temp.getEmail().equals(data.getData()[0])) {
				result = temp.getId();
			}
		}

		if (result.equals("NF")) {
			sendData(new SendData(NOT_FOUND_ID, "Not Found ID"));
		} else {
			sendData(new SendData(FOUND_ID, result));
		}
	}

	public synchronized void lookup_Pw(SendData data) {
		// Opcode 130
		String result = "NF";

		for (User temp : userList.values()) {
			if (temp.getEmail().equals(data.getData()[1])) {
				result = temp.getId();
				if (result.equals(data.getData()[0])) {
					sendData(new SendData(FOUND_PW, temp.getPw()));
				} else {
					sendData(new SendData(NOT_FOUND_INFO_PW, "Not Found PW"));
				}
			} else {
				sendData(new SendData(NOT_FOUND_INFO_PW, "Not Found PW"));
			}
		}
	}

	// 190621 15:10 추가
	// 190624 11:40 숸도 수정
	public synchronized void logout(SendData data) {
		System.out.println(data.toString());
		String id = (String) data.getData()[0];
		server.getSf().appendMsg(id + " 님이 로그아웃 하셨습니다. \n");
		server.getSf().removeUsers(id);
		sendMsg(new SendData(ANNOUNCE_LOGOUT_INFO, id));
		// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★

		cm.removeAllById(id);

	}

	private synchronized void sendMsg(SendData data) {
		// 포문돌면서 메세지 전송
		for (int i = 0; i < cm.getWr().getWaitingRoomUserList().size(); i++) {
			User user = cm.getWr().getWaitingRoomUserList().get(i);
			if (cm.getThreadMap().containsKey(user.getId())) {
				ObjectOutputStream tempOos = cm.getThreadMap()
						.get(user.getId()).getOos();
				try {
					tempOos.writeObject(data);
					tempOos.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public boolean isCorrectInfo() {
		return isCorrectInfo;
	}

	public void alramFriendJubsok() {

	}

	public void SendMsg(int opcode, Vector<String> list) {
		// code로 판별해서
	}

	public void addFriend() {

	}

	public void delFriend() {

	}

	public void showProfile() {

	}

	// 2019-06-25 정상욱 수정

	public synchronized void addBlackList(SendData data) {
		String myId = (String) data.getData()[0];
		String targetId = (String) data.getData()[1];

		User user = userList.get(myId);
		User targetUser = userList.get(targetId);

		if (userList.containsKey(targetId)) {
			if (!user.getBlackList().contains(targetUser)) {
				// ★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★★
				user.addBlackList(targetUser);
				userList.put(myId, user);
				Utils.writeFile(userList, "data\\data.dat");
				sendData(new SendData(ADD_BLACKLIST, targetUser));
			} else {
				sendData(new SendData(OVERLAP_BLACKLIST));
			}
		} else {
			sendData(new SendData(FAIL_ADD_BLACKLIST));
		}

	}

	public void delBlackList() {

	}

	public void createChatRoom(int type) {
		// type 0 : 공개 / 1 : 비공개

	}

	public void exitChatRoom() {

	}

	public void inviteChatRoom() {

	}

	public void kickOut() {

	}

	public void grantHeadRight() {

	}

	public void changeTitle() {

	}

}
